package backend.model;

public abstract class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius * 2, radius * 2);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getsMinorAxis()/2;
    }

}
