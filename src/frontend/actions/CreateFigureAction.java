package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;

public abstract class CreateFigureAction {
    private Point startPoint;
    private Point endPoint;
    private Figure newFigure;
    private CanvasState canvasState;

    public CreateFigureAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState,
                              Color lineColor, Color fillColor, double width) {
        this.startPoint = startPoint;
        this.endPoint = new Point(event.getX(),event.getY());
        this.canvasState = canvasState;
        // Se deberia primero instanciar la figura en el constructor de la clase hija antes de llamar al constructor
        // de la clase padre (asi mi newFigure no es null)
        setFormat(lineColor, fillColor, width);
    }

    public String toString() {
        return "Dibujar ";
    }

    public void setFormat(Color lineColor, Color fillColor, double width) {
        newFigure.setLineWidth(width);
        newFigure.setFillColor(fillColor);
        newFigure.setLineColor(lineColor);
    }
}
