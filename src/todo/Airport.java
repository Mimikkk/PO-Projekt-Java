package todo;

import javafx.geometry.Point2D;
import javafx.scene.Group;

import java.util.Vector;

public class Airport extends Entity {
    private final int loadCapacity;
    private int loadCount;
    private Vector<CivilianPlane> planes;

    Airport(Tag tag, Point2D pos, int id, int loadCapacity, int loadCount, Vector<CivilianPlane> planes) {
        super(tag, pos, id);
        this.loadCapacity = loadCapacity;
        this.loadCount = loadCount;
        this.planes = planes;
    }

    @Override
    public void render(Group root) {

    }

    @Override
    public void update(double dt) {

    }

    public Vector<CivilianPlane> getPlanes() {
        return planes;
    }

    public void setPlanes(Vector<CivilianPlane> planes) {
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

    public void run() {

    }
}
