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

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

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
