package backend.actions;

import backend.CanvasState;

public abstract class CreateFigureAction extends Action {

    CreateFigureAction(CanvasState canvasState) {
        setCanvasState(canvasState);
    }

    public void press() {
        canvasState.addFigure(accionableFigure);
    }

    public void undo(){
        canvasState.deleteFigure(accionableFigure);
    }

    @Override
    public String toString() {
        return "Dibujar ";
    }
}

