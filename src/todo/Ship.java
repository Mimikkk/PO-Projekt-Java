package todo;

import javafx.geometry.Point2D;

public abstract class Ship extends Entity {
    Ship(Tag tag, Point2D pos, int id) {
        super(tag, pos, id);
    }
}
