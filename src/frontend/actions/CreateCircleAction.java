package frontend.actions;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawableCircle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateCircleAction {
        private Point startPoint;
        private Point endPoint;
        private Figure newFigure;
        private Color lineColor = Color.YELLOW;
        private Color fillColor = Color.BLACK;
        private CanvasState canvasState;

         CreateCircleAction(Point startPoint, MouseEvent event, GraphicsContext gc, CanvasState canvasState) {
            this.startPoint = startPoint;
            this.endPoint = new Point(event.getX(), event.getY());
            double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
            this.newFigure = new DrawableCircle(startPoint, circleRadius, gc);
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
            return "Dibujar Circulo";
        }
}

