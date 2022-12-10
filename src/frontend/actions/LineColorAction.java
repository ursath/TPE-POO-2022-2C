package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class LineColorAction extends FormatAction {

    public LineColorAction(Figure selectedFigure, Color fillColor, CanvasState canvasState){
        super(selectedFigure, fillColor, canvasState);
    }

    // NOSE SI ESTO ESTA BIEN !!!!! CHEQUEAR
    public void undo(){
        changeColor(selectedFigure.getLineColor());
    }

    @Override
    public void changeColor(Color color) {
        for(Figure figure : canvasState.figures()) {
            if (figure == selectedFigure) {
                figure.setLineColor(color);
            }
        }
    }

    @Override
    public String toString(){
        return super.toString() + String.format("borde de %s", selectedFigure);
    }

}
