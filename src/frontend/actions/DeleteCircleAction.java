package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.actions.CreateCircleAction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DeleteCircleAction {

    private Figure selectedFigure;
    private CreateCircleAction oppositeAction;

    DeleteCircleAction(Figure selectedFigure, Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState) {
        this.selectedFigure = selectedFigure;
        this.oppositeAction = new CreateCircleAction(startPoint, event, gc, canvasState);
    }

    public void undo() {
        oppositeAction.press();
    }

    public void press() {
        oppositeAction.undo();
    }

    public String toString(){
        return "Borrar Circulo";
    }
}
