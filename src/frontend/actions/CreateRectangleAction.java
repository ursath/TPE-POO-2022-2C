package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.actions.CreateFigureAction;
import frontend.model.DrawableRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateRectangleAction extends CreateFigureAction {

    public CreateRectangleAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color lineColor, Color fillColor, double width){
        super(startPoint, event, gc, canvasState, lineColor, fillColor, width);
        newFigure = new DrawableRectangle(startPoint, new Point(event.getX(), event.getY()), gc);
        newFigure.setFormat(lineColor, fillColor, width);
    }

    public void press() {
        canvasState.addFigure(newFigure);
    }

    public void undo(){
        canvasState.deleteFigure(newFigure);
    }

    public String toString(){
        return "Dibujar Rectángulo";
    }
}
