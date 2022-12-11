package frontend.actions;

import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Undoable;

public class CopyAction implements Undoable {
    private final Figure selectedFigure;
    private Figure copiedFigure;
    private final Point center = new Point(400, 300);

    public CopyAction(Figure selectedFigure){
        this.selectedFigure = selectedFigure;
    }

    public void press(){
        this.copiedFigure = selectedFigure.getDuplicate(center);
        this.copiedFigure.setFormat(selectedFigure.getLineColor(), selectedFigure.getFillColor(), selectedFigure.getLineWidth());
    }

    public void undo(){
        this.copiedFigure = null;
    }

    public String toString(){
        return String.format("Copiar %s", selectedFigure.getName());
    }

    public Figure getCopiedFigure() {
        return copiedFigure;
    }
}
