package backend.model;

public class Point {

    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistanceToX(Point other){
        return other.getX() - x;
    }

    public double getDistanceToY(Point other){
        return other.getY() - y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    //Agregamos el casteo en el equals por la versión de java desde la que lo corrimos
    @Override
    public boolean equals(Object o){
        if ( o==this)
            return true;
        if ( o instanceof Point  ){
            return ((Point) o).getX() == x && ((Point) o).getY()==y;
        }
        return false;
    }
}
