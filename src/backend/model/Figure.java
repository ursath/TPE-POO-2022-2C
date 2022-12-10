package backend.model;

import backend.interfaces.Movable;
import frontend.interfaces.Drawable;
import javafx.scene.paint.Color;

public abstract class Figure implements Movable, Drawable {

    private Color fillColor;
    private Color lineColor;
    private double lineWidth;

    public abstract void move(double diffX, double diffY);
    public abstract String toString();
    public abstract boolean belongs(Point point);

    public Color getLineColor() {
        return lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setFormat(Color lineColor, Color fillColor, double width) {
       setLineWidth(width);
       setFillColor(fillColor);
       setLineColor(lineColor);
    }
}
