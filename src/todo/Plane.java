package todo;

import javafx.geometry.Point2D;

public abstract class Plane extends Entity {
    private int personnelCount;
    private final double maxFuel;
    private double fuel;

    Plane(Tag tag, Point2D pos, int id, int personelCount, double maxFuel, double fuel) {
        super(tag, pos, id);
        this.personnelCount = personelCount;
        this.maxFuel = maxFuel;
        this.fuel = fuel;
    }

    public int getPersonnelCount() {
        return personnelCount;
    }

    public void setPersonnelCount(int personnelCount) {
        this.personnelCount = personnelCount;
    }

    public double getMaxFuel() {
        return maxFuel;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}
