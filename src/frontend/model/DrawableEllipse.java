package frontend.model;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Drawable;
import javafx.scene.canvas.GraphicsContext;

public class DrawableEllipse extends Ellipse  {
    private final GraphicsContext gc;

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, GraphicsContext gc){
        super(centerPoint, sMayorAxis, sMinorAxis);
        this.gc = gc;
    }

    public void draw(){
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }
    @Override
    public Figure getDuplicate(Point center){
        DrawableEllipse toReturn = new DrawableEllipse(center,getsMayorAxis(),getsMinorAxis(),gc);
        //CPY FORMAT
        return toReturn;
    }
}
