package backend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class DeleteFigureAction extends Action {

    public DeleteFigureAction(Figure originalFigure, CanvasState canvasState){
       setAccionableFigure(originalFigure);
       setCanvasState(canvasState);
    }

    public void press(){
        canvasState.deleteFigure(accionableFigure);
    }

    public void undo(){
        canvasState.addFigure(accionableFigure);
    }

    @Override
    public String toString(){
        return String.format("Borrar %s", accionableFigure.getName());
    }
}
