package classes;

import javafx.geometry.Point2D;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Path;

public abstract class Ship extends Vehicle {
    public Ship(TYPE type) {
        super(type);
    }
    
    
    @Override public Path moveTo(double x, double y) {
        var path = super.moveTo(x, y);
        
        var p1 = new Point2D(this.getSprite().getTranslateX(), this.getSprite().getTranslateY());
        var p2 = new Point2D(x, y);
        var radius = Math.abs(p2.getX() - p1.getX());
        
        ArcTo end = new ArcTo();
        end.setX(p2.getX());
        end.setY(p2.getY());
        end.setRadiusX(radius);
        end.setRadiusY(radius / 3);
        
        path.getElements().add(end);
        return path;
    }
}
