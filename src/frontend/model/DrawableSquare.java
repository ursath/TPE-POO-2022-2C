package frontend.model;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import frontend.interfaces.Drawable;
import javafx.scene.canvas.GraphicsContext;

public class DrawableSquare extends Square {

    private GraphicsContext gc;
    public DrawableSquare(Point topLeft, double size, GraphicsContext gc){

        /* si rectangle
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        */
        super(topLeft,size);
        this.gc = gc;
    }

    public void draw(){
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public Figure getDuplicate(Point center) {
        Point topLeft = getTopLeft();               //raro q no funcione, porque con rectangle si funciona
        double size = getSize()/2;
        DrawableSquare toReturn = new DrawableSquare(topLeft,getSize(), gc);
        toReturn.move(center.getX()-topLeft.getX()-size, center.getY()-getTopLeft().getY()-size);
        //CPY FORMAT
        return toReturn;
    }
}
