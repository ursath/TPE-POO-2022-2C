package frontend.actions;

import backend.model.Figure;
import backend.model.Point;

public class CopyAction {
    private final Figure selectedFigure;
    private Figure copiedFigure;
    private final Point center = new Point(400, 300);

    CopyAction(Figure selectedFigure){
        this.selectedFigure = selectedFigure;
    }

    public void press(){
        this.copiedFigure = selectedFigure.getDuplicate(center);
        this.copiedFigure.copyFormat(selectedFigure);
    }

    public void undo(){
        this.copiedFigure = null;
    }

    public String toString(){
        return String.format("Copiar %s", selectedFigure);
    }
}
