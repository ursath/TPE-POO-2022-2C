package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawableCircle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateCircleAction extends CreateFigureAction{
         public CreateCircleAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color lineColor, Color fillColor, double width) {
            super(canvasState);
            double circleRadius = Math.abs(event.getX() - startPoint.getX());
            newFigure = new DrawableCircle(startPoint, circleRadius, gc);
            newFigure.setFormat(lineColor, fillColor, width);
        }

        @Override
        public String toString(){
            return super.toString() + "Circulo";
         }
}

