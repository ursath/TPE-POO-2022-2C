package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class FillColorAction {
    Figure selectedFigure;
    Color fillColor;
    CanvasState canvasState;

    public FillColorAction(Figure selectedFigure, Color fillColor, CanvasState canvasState){
        this.canvasState = canvasState;
        this.fillColor = fillColor;
        this.selectedFigure = selectedFigure;
    }

    public void press() {
        for(Figure figure : canvasState.figures()) {
            if (figure == selectedFigure) {
                figure.setFillColor(fillColor);
            }
        }
    }

    public void undo(){
        // FALTA
    }

    public String toString(){
        return String.format("Cambiar color de relleno de %s", selectedFigure);
    }

}
