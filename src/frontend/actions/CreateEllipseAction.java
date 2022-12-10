package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawableEllipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateEllipseAction extends CreateFigureAction{
    public CreateEllipseAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color lineColor, Color fillColor, double width) {
        super(canvasState);
        Point centerPoint = new Point(Math.abs(event.getX() + startPoint.getX()) / 2, (Math.abs((event.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(event.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(event.getY() - startPoint.getY());
        newFigure = new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis, gc);
        newFigure.setFormat(lineColor, fillColor, width);
    }

    public String toString(){
        return super.toString() + "Ellipse";
    }
}
