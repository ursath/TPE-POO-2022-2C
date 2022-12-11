package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

public class CopyFormatAction {

    private Color newLineColor;
    private Color newFillColor;
    private double newLineWidth;
    private Figure selectedFigure;
    private CanvasState canvasState;
    private Point eventPoint;

    public CopyFormatAction(Figure selectedFigure, CanvasState canvasState, Point eventPoint, Color newLineColor, Color newFillColor, double newLineWidth){
        this.canvasState = canvasState;
        this.newLineColor = newLineColor;
        this.newFillColor = newFillColor;
        this.newLineWidth = newLineWidth;
        this.selectedFigure = selectedFigure;
        this.eventPoint = eventPoint;
    }

    public void press(){
        for (Figure figure : canvasState.figures()) {
            if (figure.belongs(eventPoint)) {
                figure.setFormat(newLineColor, newFillColor, newLineWidth);
            }
        }
    }

    public void undo() {

    }

    public String toString() {
        return String.format("Copiar Formato de %s", selectedFigure.getName());
    }
}
