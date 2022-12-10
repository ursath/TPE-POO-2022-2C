package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.input.MouseEvent;

public abstract class CreateFigureAction {
    private Point startPoint;
    private Point endPoint;
    protected Figure newFigure;
    protected CanvasState canvasState;

    public CreateFigureAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState,
                              Color lineColor, Color fillColor, double width) {
        this.canvasState = canvasState;
        // Se deberia primero instanciar la figura en el constructor de la clase hija antes de llamar al constructor
        // de la clase padre (asi mi newFigure no es null)
          }

    public String toString() {
        return "Dibujar ";
    }

}

