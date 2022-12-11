package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class PasteAction extends CopyAction {

    public PasteAction(Figure copyFigure, CanvasState canvasState){         // copyFigure es la figura que será duplicada
        super(copyFigure);                                                  // -> permite que se duplique las veces necesarias
        setCanvasState(canvasState);
    }

    @Override
    public void press() {
        super.press();
        canvasState.addFigure(getCopiedFigure());
    }

    @Override
    public void undo() {
        DeleteFigureAction delete = new DeleteFigureAction(getCopiedFigure(), canvasState);
        delete.press();
    }

    @Override
    public String toString(){
        return String.format("Pegar %s", getCopiedFigure().getName());
    }
}
