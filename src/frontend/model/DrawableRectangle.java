package frontend.model;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import frontend.interfaces.Drawable;

public class DrawableRectangle extends Rectangle implements Drawable{
    private GraphicsContext gc;

    public DrawableRectangle(Point topLeft, Point bottomRight, GraphicsContext gc){
        super(topLeft, bottomRight);
        this.gc = gc;
    }

    public void draw(){
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }
}
