package backend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.interfaces.Undoable;

public abstract class Action implements Undoable {

    /* Accionable Figure representa la figura a la que se le aplica la accion en cada caso:
        - En FormatAction: es la figura a la que se le aplica el nuevo formato
        - En CreateFigureAction: es la figura nueva que se crea
        - En CopyAction: es la figura que se selecciona para copiar
        - En DeleteFigureAction: es la figura que se selecciona para borrar
     */
    protected Figure accionableFigure;
    protected CanvasState canvasState;

    public void setCanvasState (CanvasState canvasState) {
        this.canvasState = canvasState;
    }

    public void setAccionableFigure(Figure figure) {
        this.accionableFigure = figure;
    }

    public abstract String toString();
}
