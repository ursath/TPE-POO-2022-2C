package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import frontend.interfaces.Undoable;
import javafx.scene.paint.Color;


public abstract class FormatAction implements Undoable {
    protected Figure selectedFigure;
    protected Color color;
    protected CanvasState canvasState;
    protected Color oldColor;

    FormatAction(Figure selectedFigure, Color color, CanvasState canvasState) {
        this.canvasState = canvasState;
        this.color = color;
        this.selectedFigure = selectedFigure;
    }

    public abstract void undo();

    public void press() {
        changeColor(color);
    }

    public abstract void changeColor(Color color);

    public String toString(){
        return "Cambiar color de ";
    }
}
