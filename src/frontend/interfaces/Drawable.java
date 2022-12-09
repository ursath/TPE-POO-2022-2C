package frontend.interfaces;


import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawableCircle;
import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    void draw();

    Figure getDuplicate(Point center );
}
