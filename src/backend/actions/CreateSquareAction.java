package backend.actions;

import backend.CanvasState;
import backend.model.Point;
import frontend.model.DrawableSquare;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateSquareAction extends CreateFigureAction {
    public CreateSquareAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color lineColor, Color fillColor, double width) {
        super(canvasState);
        double size = Math.abs(event.getX() - startPoint.getX());
        setAccionableFigure(new DrawableSquare(startPoint, size, gc));
        accionableFigure.setFormat(lineColor, fillColor, width);
    }

    @Override
    public String toString(){
        return super.toString() + "Cuadrado";
    }
}
