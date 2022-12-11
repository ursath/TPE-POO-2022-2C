package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;


public abstract class FormatAction extends Action {

    protected Color color;
    protected Color oldColor;

    FormatAction(Figure selectedFigure, Color color, CanvasState canvasState) {
        setCanvasState(canvasState);
        this.color = color;
        setAccionableFigure(selectedFigure);
    }


    public abstract void undo();

    @Override
    public void press() {
        changeColor(color);
    }

    public abstract void changeColor(Color color);

    @Override
    public String toString(){
        return "Cambiar color de ";
    }
}
