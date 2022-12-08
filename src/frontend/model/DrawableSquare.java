package frontend.model;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class DrawableSquare extends Rectangle {

    private GraphicsContext gc;
    public DrawableSquare(Point topLeft, double size, GraphicsContext gc){
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        this.gc = gc;
    }

    public void draw(){
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

}
