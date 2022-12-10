package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawableEllipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateEllipseAction {
    private Point startPoint;
    private Point endPoint;
    private Figure newFigure;
    private Color lineColor = Color.YELLOW;
    private Color fillColor = Color.BLACK;
    private CanvasState canvasState;

    //para meter en clase abstracta: canvasstate, toString, press, undo (de la interfaz)
    CreateEllipseAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState) {
        this.startPoint = startPoint;
        this.endPoint = new Point(event.getX(), event.getY());
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        newFigure = new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis, gc);
        this.canvasState = canvasState;
        newFigure.setLineWidth(1);
        newFigure.setFillColor(fillColor);
        newFigure.setLineColor(lineColor);
    }

    public void press() {
        canvasState.addFigure(newFigure);
    }

    public void undo(){
        canvasState.deleteFigure(newFigure);
    }

    public String toString(){
        return "Dibujar Ellipse";
    }
}
