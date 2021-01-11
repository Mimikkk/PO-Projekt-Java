package classes;

import javafx.scene.control.ListView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;

public abstract class Plane extends Vehicle {
    public Plane(TYPE type, ArrayList<Integer> ids) {
        super(type, ids);
    }
    
    @Override public Path moveTo(double x, double y) {
        var path = super.moveTo(x, y);
        path.getElements().add(new LineTo(x, y));
        return path;
    }
}
