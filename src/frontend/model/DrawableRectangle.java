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
        Point topLeft = getTopLeft();
        Point bottomRight = getBottomRight();
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.distanceToX(bottomRight)), Math.abs(topLeft.distanceToY(bottomRight)) );
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.distanceToX(bottomRight)), Math.abs(topLeft.distanceToY(bottomRight)) );
    }

    @Override
    public Figure getDuplicate(Point center) {
        double baseMitad = getBottomRight().distanceToX(getTopLeft())/2;
        double alturaMitad = getTopLeft().distanceToY(getBottomRight())/2;

        Point topLeft = new Point(center.getX()-baseMitad,center.getY()-alturaMitad );
        Point bottomRight = new Point(center.getX()+baseMitad,center.getY()+alturaMitad );

        return new DrawableRectangle(topLeft,bottomRight,gc);
    }

}
