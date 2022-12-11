package frontend.actions;

import backend.CanvasState;
import backend.model.Point;
import frontend.model.DrawableRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateRectangleAction extends CreateFigureAction {

    public CreateRectangleAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color lineColor, Color fillColor, double width){
        super(canvasState);
        setAccionableFigure(new DrawableRectangle(startPoint, new Point(event.getX(), event.getY()), gc));
        accionableFigure.setFormat(lineColor, fillColor, width);
    }

    @Override
    public String toString(){
        return super.toString() + "Rectángulo";
    }
}
