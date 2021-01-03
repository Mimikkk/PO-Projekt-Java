package todo;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public class CivilianPlane extends Plane {
    CivilianPlane(Tag tag, Point2D pos, int id, int personnelCount, double maxFuel, double fuel) {
        super(tag, pos, id, personnelCount, maxFuel, fuel);
    }

    @Override
    public void render(Group root) {

    }

    @Override
    public void update(double dt) {

    }
    public void run() {

    }

}
