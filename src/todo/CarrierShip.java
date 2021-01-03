package todo;

import javafx.geometry.Point2D;
import javafx.scene.Group;

import java.util.Vector;

public class CarrierShip extends Ship {
    private final int loadCapacity;
    private int loadCount;
    private final String armament;
    private Vector<MilitaryPlane> planes;

    CarrierShip(Tag tag, Point2D pos, int id, int loadCapacity,
                int loadCount, String armament, Vector<MilitaryPlane> planes) {
        super(tag, pos, id);
        this.loadCapacity = loadCapacity;
        this.loadCount = loadCount;
        this.armament = armament;
        this.planes = planes;
    }

    @Override
    public void render(Group root) {

    }

    @Override
    public void update(double dt) {

    }

    public Vector<MilitaryPlane> getPlanes() {
        return planes;
    }

    public void setPlanes(Vector<MilitaryPlane> planes) {
        this.planes = planes;
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public int getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(int loadCount) {
        this.loadCount = loadCount;
    }

    public String getArmament() {
        return armament;
    }

    public void run() {

    }

}
