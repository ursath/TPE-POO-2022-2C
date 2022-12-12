package backend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class CopyFormatAction extends Action {

    private final Color newLineColor;
    private final Color newFillColor;
    private final double newLineWidth;
    private final Point eventPoint;
    private Color oldLineColor;
    private Color oldFillColor;
    private double oldLineWidth;
    private Figure auxFigure;

    public CopyFormatAction(Figure selectedFigure, CanvasState canvasState, Point eventPoint, Color newLineColor, Color newFillColor, double newLineWidth) {
        setCanvasState(canvasState);
        setAccionableFigure(selectedFigure);
        this.newLineColor = newLineColor;
        this.newFillColor = newFillColor;
        this.newLineWidth = newLineWidth;
        this.eventPoint = eventPoint;
    }

    public void press() {
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

    @Override
    public String toString() {
        return String.format("Copiar Formato de %s", accionableFigure.getName());
    }
}
