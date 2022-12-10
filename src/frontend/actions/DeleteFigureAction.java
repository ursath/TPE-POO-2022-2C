package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public abstract class DeleteFigureAction {

    private final CreateFigureAction oppositeAction;

    protected DeleteFigureAction(CreateFigureAction oppositeAction){
       this.oppositeAction = oppositeAction;
    }

    public void press(){
        oppositeAction.undo();
    }

    public void undo(){
        oppositeAction.press();
    }

    public String toString(){
        return "Borrar ";
    }
}
