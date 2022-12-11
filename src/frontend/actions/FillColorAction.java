package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class FillColorAction extends FormatAction {

    public FillColorAction(Figure selectedFigure, Color fillColor, CanvasState canvasState){
        super(selectedFigure, fillColor, canvasState);
    }

    // NOSE SI ESTO ESTA BIEN !!!!! CHEQUEAR
    public void undo(){
        changeColor(selectedFigure.getFillColor());
    }

    @Override
    public void changeColor(Color color) {
        for(Figure figure : canvasState.figures()) {
            if (figure == selectedFigure) {
                figure.setFillColor(color);
            }
        }
    }

    @Override
    public String toString(){
        return super.toString() + String.format("relleno de %s", selectedFigure.getName());
    }


}
