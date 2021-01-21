package classes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.lang.Thread.sleep;

public class Airport extends Entity {
    private final SIZE size;
    private final ObservableList<CivilianPlane> planes = FXCollections.observableArrayList();
    private final String name;
    
    public Airport(SIZE size, String name) {
        super(TYPE.AIRPORT);
        
        this.name = name;
        this.size = size;
        
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    public SIZE getSize() {
        return size;
    }
    public ObservableList<CivilianPlane> getPlanes() {
        return this.planes;
    }
    
    @Override public void run() {
        while (isRunning()) {
            try { sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            Platform.runLater(() -> {
                for (var plane : planes) {
                    try { sleep(30); } catch (InterruptedException e) { e.printStackTrace(); }
                    if (plane.canStart()) {
                        plane.run();
                    }
                }
            });
        }
    }
    @Override public String toString() {
        return this.name;
    }
    public String getName() {
        return name;
    }
}
