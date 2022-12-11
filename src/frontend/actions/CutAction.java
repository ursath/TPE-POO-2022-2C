package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class CutAction extends CopyAction{
    private final CanvasState canvasState;
    private DeleteFigureAction delete;
    public CutAction(Figure selectedFigure, CanvasState canvasState){
        super(selectedFigure);
        this.canvasState = canvasState;
    }
    @Override
    public void press(){
        super.press();
        delete = new DeleteFigureAction(selectedFigure,canvasState);
        delete.press();

    }
    public void undo(){
        super.undo();
        delete.undo();
    }

    @Override
    public String toString(){
        return String.format("Cortar %s", selectedFigure.getName());
    }
}
