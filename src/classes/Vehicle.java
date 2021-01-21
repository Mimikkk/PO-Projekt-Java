package classes;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public abstract class Vehicle extends Entity {
    private final PathTransition transition = new PathTransition();
    private String status;
    
    public Vehicle(TYPE type) {
        super(type);
        transition.setDuration(Duration.seconds(2));
        transition.setNode(this.getSprite());
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        
    }
    
    public Path moveTo(double x, double y) {
        var path = new Path();
        MoveTo start = new MoveTo(
                this.getSprite().getTranslateX() + this.getType().getOrigin().getX(),
                this.getSprite().getTranslateY() + this.getType().getOrigin().getY());
        path.getElements().add(start);
        return path;
    }
    
    public PathTransition getTransition() { return transition; }
    public String getStatus() {
        return this.status;
    }
}
