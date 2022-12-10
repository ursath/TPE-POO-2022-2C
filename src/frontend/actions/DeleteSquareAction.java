package frontend.actions;

import backend.CanvasState;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DeleteSquareAction extends DeleteFigureAction{

    public DeleteSquareAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color lineColor, Color fillColor, double width){
        super(new CreateRectangleAction(startPoint, event, gc, canvasState, lineColor, fillColor, width));
    }

    public String toString(){
        return super.toString() + "Square";
    }
}
