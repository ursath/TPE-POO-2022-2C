package backend.actions;

import backend.model.Figure;
import backend.model.Point;

public class CopyAction extends Action {
    private Figure copiedFigure;
    private final Point center = new Point(400, 300);

    public CopyAction(Figure selectedFigure) {
        setAccionableFigure(selectedFigure);
    }

    @Override
    public void press() {
        this.copiedFigure = accionableFigure.getDuplicate(center);
        this.copiedFigure.setFormat(accionableFigure.getLineColor(), accionableFigure.getFillColor(), accionableFigure.getLineWidth());
    }

    @Override
    public void undo() {
        this.copiedFigure = null;
    }

    @Override
    public String toString() {
        return String.format("Copiar %s", accionableFigure.getName());
    }

     public Figure getCopiedFigure() {
        return copiedFigure;
    }
}
