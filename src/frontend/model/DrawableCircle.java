package frontend.model;

import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Drawable;
import javafx.scene.canvas.GraphicsContext;

public class DrawableCircle extends Circle {
    GraphicsContext gc;
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
        DrawableCircle toReturn = new DrawableCircle(center,getRadius(),gc );
        //CPY FORMAT
        return toReturn;
    }
}
