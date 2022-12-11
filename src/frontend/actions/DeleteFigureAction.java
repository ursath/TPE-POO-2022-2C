package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Undoable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DeleteFigureAction implements Undoable {

    private final Figure originalFigure;
    private final CanvasState canvasState;

    public DeleteFigureAction(Figure originalFigure, CanvasState canvasState){
       this.originalFigure= originalFigure;
       this.canvasState = canvasState;
    }

    public void press(){
        canvasState.deleteFigure(originalFigure);
    }

    public void undo(){
        canvasState.addFigure(originalFigure);
    }

    public String toString(){
        return String.format("Borrar %s", originalFigure.getName());
    }
}
