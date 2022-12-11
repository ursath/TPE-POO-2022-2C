package frontend.model;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;

public class DrawableSquare extends Square {

    private final GraphicsContext gc;

    public DrawableSquare(Point topLeft, double size, GraphicsContext gc){
        super(topLeft,size);
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
        Point topLeft = getTopLeft();                       // MAL CENTRADO
        double size = getSize()/2;
        DrawableSquare toReturn = new DrawableSquare(topLeft,getSize(), gc);
        toReturn.move(center.getX()-topLeft.getX()-size, center.getY()-getTopLeft().getY()-size);
        return toReturn;
    }
}
