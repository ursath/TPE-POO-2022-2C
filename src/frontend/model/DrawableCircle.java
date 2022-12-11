package frontend.model;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawableCircle extends Circle {

    private final GraphicsContext gc;

    public DrawableCircle(Point centerPoint, double radius, GraphicsContext gc){
        super(centerPoint, radius);
        this.gc = gc;
    }

    @Override
    public void draw() {
        double diameter = getRadius() * 2;
        gc.fillOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY() - getRadius(), diameter, diameter);
        gc.strokeOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY() - getRadius(), diameter, diameter);
    }

    @Override
    public Figure getDuplicate(Point center){
        return new DrawableCircle(center,getRadius(),gc );
    }
}
