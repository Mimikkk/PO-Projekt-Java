package classes;

import javafx.scene.control.ListView;

import java.util.ArrayList;

public class MilitaryPlane extends Plane {
    public MilitaryPlane(ArrayList<Integer> ids) {
        super(TYPE.MILITARYPLANE, ids);
    }
}
