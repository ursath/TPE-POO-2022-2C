package backend.model;

public abstract class Circle extends Ellipse {

    private static final String NAME = "Circulo";
    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius * 2, radius * 2);
        this.name = NAME;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getsMinorAxis()/2;
    }

}
