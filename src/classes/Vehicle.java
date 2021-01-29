package classes;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Klasa abstrakcyjna Pojazd rozszerzającą klasę Jednostka o dodatkowe informacje
 * zawierające pole o ścieżce i animacji poruszania
 */
public abstract class Vehicle extends Entity {
    private final PathTransition transition = new PathTransition();
    private double speed;
    /** Konstruktor ustawiający podstawowe parametry dla ścieżki
     * @param type Typ
     */
    public Vehicle(TYPE type) {
        super(type);
        transition.setNode(this.getSprite());
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
    }
    
    /**
     * @param x współrzędna x
     * @param y współrzędna y
     * @return Ścieżka prowadząca do podanego x,y
     */
    public Path moveTo(double x, double y) {
        var path = new Path();
        MoveTo start = new MoveTo(
                this.getSprite().getTranslateX() + this.getType().getOrigin().getX(),
                this.getSprite().getTranslateY() + this.getType().getOrigin().getY());
        path.getElements().add(start);
        return path;
    }
    
    /**
     * @return obiekt przechowujacy ścieżkę
     */
    public PathTransition getTransition() { return transition; }
    
    /**
     * @param speed Maksymalna prędkość poruszania się
     */
    protected void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return Maksymalna prędkość poruszania się
     */
    protected double getSpeed() {
        return speed;
    }
}
