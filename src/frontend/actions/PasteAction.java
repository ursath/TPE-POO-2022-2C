package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class PasteAction extends CopyAction {

    private Figure copyFigure;                                            // copyFigura guarda la figura que va a ser duplicada
    private final CanvasState canvasState;                                // -> permite hacer varios paste

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
    @Override
    public String toString(){
        return String.format("Pegar %s", getCopiedFigure().getName());
    }
}
