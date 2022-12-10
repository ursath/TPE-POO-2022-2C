package frontend.actions;

import backend.model.Figure;

public class CutAction extends CopyAction{
    public CutAction(Figure selectedFigure){
        super(selectedFigure);
    }
    @Override
    public void press(){
        super.press();

    }
}
