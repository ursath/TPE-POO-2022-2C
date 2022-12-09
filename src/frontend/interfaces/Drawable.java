package frontend.interfaces;


import backend.model.Figure;
import backend.model.Point;


public interface Drawable {

    void draw();

    Figure getDuplicate(Point center );

}
