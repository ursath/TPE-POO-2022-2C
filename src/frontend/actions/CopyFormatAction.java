package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Undoable;
import javafx.scene.paint.Color;

public class CopyFormatAction implements Undoable {

    private Color newLineColor;
    private Color newFillColor;
    private double newLineWidth;
    private Figure selectedFigure;
    private CanvasState canvasState;
    private Point eventPoint;
    private Color oldLineColor;
    private Color oldFillColor;
    private double oldLineWidth;
    private Figure auxFigure;

    public CopyFormatAction(Figure selectedFigure, CanvasState canvasState, Point eventPoint, Color newLineColor, Color newFillColor, double newLineWidth) {
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
                auxFigure = figure;
                oldFillColor = figure.getFillColor();
                oldLineColor = figure.getLineColor();
                oldLineWidth = figure.getLineWidth();
                figure.setFormat(newLineColor, newFillColor, newLineWidth);
            }
        }
    }

    public void undo() {
        for (Figure figure : canvasState.figures()) {
            if (figure == auxFigure) {
                figure.setFormat(oldLineColor, oldFillColor, oldLineWidth);
            }
        }
    }

    public String toString() {
        return String.format("Copiar Formato de %s", selectedFigure.getName());
    }
}
