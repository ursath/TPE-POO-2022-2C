package backend.model;

public abstract class Square extends Rectangle{
    //necesito getter de size para el duplicate de Drawable
    private double size;
    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        //necesito getter de size para el duplicate de Drawable
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    public double getSize(){
        return size;
    }
}
