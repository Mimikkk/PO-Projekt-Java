package todo;

import java.util.Vector;

public class Path {
    private final Vector<Entity> path;
    private Entity current;
    private Entity next;
    private int current_index=0;

    Path(Vector<Entity> path) {
        this.path = path;
        this.current = path.get(0);
        this.next = path.get(1);
    }

    void next() {
        if (++this.current_index == path.size()) this.current_index = 0;
        this.current = next;
        this.next = path.get(current_index);
    }

    Vector<Entity> getPath(){
        return path;
    }

    public Entity getCurrent() {
        return current;
    }

    public Entity getNext() {
        return next;
    }
}
