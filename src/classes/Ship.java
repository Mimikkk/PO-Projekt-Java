package classes;

import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Random;

/**
 * Klasa abstrakcyjna Statek rozszerzająca klasę Pojazd o dodatkowe metody i możliwości ruchu statku
 */
public abstract class Ship extends Vehicle {
    private final double moveRadius;
    
    /**
     * Konstruktor przypisujący możliwy zasięg ruchu
     *
     * @param type Typ
     */
    public Ship(TYPE type) {
        super(type);
        if (this instanceof CivilianShip) {
            moveRadius = 40;
        } else {
            moveRadius = 20;
        }
    }
    private final Random random = new Random();
    
    /**
     * @return Zwraca nowy punkt, do którego należy się przemieścić
     */
    protected Point2D generateNewMovePoint() {
        double r = moveRadius * Math.sqrt(random.nextDouble());
        var theta = random.nextDouble() * 2 * Math.PI;
        
        var x = this.getSprite().getTranslateX() + this.getType().getOrigin().getX() + r * Math.cos(theta);
        var y = this.getSprite().getTranslateY() + this.getType().getOrigin().getY() + r * Math.sin(theta);
        x = Math.max(0, Math.min(600, x));
        y = Math.max(0, Math.min(375, y));
        return new Point2D(x, y);
    }
    
    private boolean canStart = true;
    /**
     * Porusza do następnej lokacji odrygwając animację
     */
    protected void moveToNextLocation() {
        if (this.canStart) {
            var nextPoint = this.generateNewMovePoint();
            this.getTransition().setPath(this.moveTo(nextPoint.getX(), nextPoint.getY()));
            this.getTransition().setOnFinished(event -> this.canStart = true);
            var x = this.getSprite().getTranslateX();
            var y = this.getSprite().getTranslateY();
            this.getTransition().setDuration(Duration.seconds(new Point2D(x, y).distance(nextPoint) / this.getSpeed()));
            this.getTransition().play();
        }
    }
    
    /**
     * Generuje ścieżkę do punktu x,y
     *
     * @param x współrzędna x
     * @param y współrzędna y
     * @return Ścieżkę do punktu x,y
     */
    @Override public Path moveTo(double x, double y) {
        var path = super.moveTo(x, y);
        
        path.getElements().add(new LineTo(x, y));
        return path;
    }
}
