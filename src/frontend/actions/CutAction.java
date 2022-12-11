package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class CutAction extends CopyAction {
    private final DeleteFigureAction delete;
    public CutAction(Figure selectedFigure, CanvasState canvasState){
        super(selectedFigure);
        setCanvasState(canvasState);
        delete = new DeleteFigureAction(selectedFigure,canvasState);
    }
    @Override
    public void press(){
        super.press();
        delete.press();

    }

    @Override
    public void undo(){
        super.undo();
        delete.undo();
    }

    @Override
    public String toString(){
        return String.format("Cortar %s", accionableFigure.getName());
    }
}
