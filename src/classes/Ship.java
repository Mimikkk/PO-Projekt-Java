package classes;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

public abstract class Ship extends Vehicle {
    public Ship(TYPE type) {
        super(type);
    }
    
    
    @Override public Path moveTo(double x, double y) {
        var path = super.moveTo(x, y);
        
        path.getElements().add(new LineTo(x, y));
        return path;
    }
}
