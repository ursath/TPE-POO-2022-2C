package frontend.model;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import frontend.interfaces.Drawable;

public class DrawableRectangle extends Rectangle implements Drawable{

    private final GraphicsContext gc;

    public DrawableRectangle(Point topLeft, Point bottomRight, GraphicsContext gc){
        super(topLeft, bottomRight);
        this.gc = gc;
    }

    @Override
    public void draw(){
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public Figure getDuplicate(Point center) {
        Point topLeft = getTopLeft();
        Point bottomRight = getBottomRight();
        DrawableRectangle toReturn = new DrawableRectangle(topLeft,bottomRight,gc);
        toReturn.move(center.getX()-topLeft.getX(), center.getY()- bottomRight.getY());
        return toReturn;
    }

}
