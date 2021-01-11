package classes;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class Airport extends Entity {
    private final SIZE size;
    private final ArrayList<CivilianPlane> planes = new ArrayList<>();
    
    public Airport(SIZE size, ArrayList<Integer> ids) {
        super(TYPE.AIRPORT, ids);
        this.size = size;
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    public SIZE getSize() {
        return size;
    }
    public ArrayList<CivilianPlane> getPlanes() {
        return this.planes;
    }
}
