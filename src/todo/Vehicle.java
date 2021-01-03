package todo;

import javafx.geometry.Point2D;

public abstract class Vehicle extends Entity {
    private final double maxVelocity;
    private double velocity;
    private Path path;

    Vehicle(Tag tag, Point2D pos, int id, double maxVelocity, double velocity, Path path) {
        super(tag, pos, id);
        this.maxVelocity = maxVelocity;
        this.velocity = velocity;
        this.path = path;
    }


    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
