package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class PasteAction extends CopyAction {

    private CopyAction previousCopy;
    private Figure copyFigure;
    private final CanvasState canvasState;

    public PasteAction(Figure copyFigure, CanvasState canvasState){
        //this.copiedFigure= copiedFigure;
        super(copyFigure);
        this.canvasState = canvasState;
    }

    @Override
    public void press() {
        //Figure aux = copiedFigure.getDuplicate(new Point(400,300));
        //aux.setFormat(copiedFigure.getLineColor(),copiedFigure.getFillColor(),copiedFigure.getLineWidth());
        super.press();
        canvasState.addFigure(getCopiedFigure());
    }

    @Override
    public void undo() {
        DeleteFigureAction delete = new DeleteFigureAction(getCopiedFigure(), canvasState);
        delete.press();
        //dudoso
    }
}
