package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.input.MouseEvent;

public abstract class CreateFigureAction {
    protected Figure newFigure;
    protected CanvasState canvasState;

    public CreateFigureAction(CanvasState canvasState) {
        this.canvasState = canvasState;
    }

    public void press() {
        canvasState.addFigure(newFigure);
    }

    public void undo(){
        canvasState.deleteFigure(newFigure);
    }

    public String toString() {
        return "Dibujar ";
    }
}

