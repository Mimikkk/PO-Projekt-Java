package classes;

import javafx.geometry.Point2D;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Klasa abstrakcyjna Samolot rozszerzająca klasę Pojazd o metody poruszania
 */
public abstract class Plane extends Vehicle {
    /**
     * Konstruktor klasy Samolot
     *
     * @param type Typ
     */
    public Plane(TYPE type) {
        super(type);
    }
    
    /**
     * Generuje ściężkę po łuku od aktulanej pozycji statku do x,y
     *
     * @param x współrzędna x
     * @param y współrzędna y
     * @return Ścieżkę od aktualnej pozycji do punktu x,y
     */
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
        
        this.getTransition().setDuration(Duration.seconds(new Point2D(this.getSprite().getTranslateX(),
                this.getSprite().getTranslateY()).distance(x, y) / this.getSpeed()));
        return path;
    }
}
