package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class LineColorAction extends FormatAction {

    public LineColorAction(Figure selectedFigure, Color lineColor, CanvasState canvasState){
        super(selectedFigure, lineColor, canvasState);
    }

    public void undo(){
        changeColor(oldColor);
    }

    @Override
    public void press() {
        oldColor = accionableFigure.getLineColor();
        super.press();
    }

    @Override
    public void changeColor(Color color) {
        for(Figure figure : canvasState.figures()) {
            if (figure == accionableFigure) {
                figure.setLineColor(color);
            }
        }
    }

    @Override
    public String toString(){
        return super.toString() + String.format("borde de %s", accionableFigure.getName());
    }

}
