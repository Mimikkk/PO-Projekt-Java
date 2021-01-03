package todo;

import javafx.geometry.Point2D;
import javafx.scene.Group;

public class MilitaryPlane extends Plane {
    private String armament;

    MilitaryPlane(Tag tag, Point2D pos, int id, int personnelCount, double maxFuel, double fuel, String armament) {
        super(tag, pos, id, personnelCount, maxFuel, fuel);
        this.armament = armament;
    }

    @Override
    public void render(Group root) {

    }

    @Override
    public void update(double dt) {

    }

    public String getArmament() {
        return armament;
    }

    public void setArmament(String armament) {
        this.armament = armament;
    }
    public void run() {

    }
}
