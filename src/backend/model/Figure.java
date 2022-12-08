package backend.model;

import backend.interfaces.Movable;
import frontend.interfaces.Drawable;

public abstract class Figure implements Movable, Drawable {

    public abstract void move(double diffX, double diffY);
    public abstract String toString();
    public abstract boolean belongs(Point point);
}
