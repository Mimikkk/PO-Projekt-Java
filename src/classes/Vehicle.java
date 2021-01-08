package classes;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public abstract class Vehicle extends Entity {
    private SIZE size;
    private final PathTransition transition = new PathTransition();
    
    public Vehicle(TYPE type) {
        super(type);
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
    
    public enum SIZE {
        SMALL,
        MEDIUM,
        BIG;
    }
}
