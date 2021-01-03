package todo;

import javafx.geometry.Point2D;

public abstract class Entity implements Renderer, Updater, Runnable {
    private final Tag tag;
    private Point2D pos;
    private final int id;

    Entity(Tag tag, Point2D pos, int id) {
        this.tag = tag;
        this.pos = pos;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Tag getTag() {
        return tag;
    }

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }
}
