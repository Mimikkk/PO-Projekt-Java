package classes;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.Random;

public class CivilianPlane extends Plane {
    private int passengerCount = 0;
    private Airport location;
    private final ObservableList<Airport> path;
    private int currentIndex;
    private int nextIndex;
    
    private final int maxPassengerCount;
    private final SIZE size;
    
    public CivilianPlane(SIZE size, ObservableList<Airport> path) {
        super(TYPE.CIVILIANPLANE);
        this.location = path.get(0);
        this.currentIndex = 0;
        this.nextIndex = 0;
        
        this.path = path;
        this.size = size;
        maxPassengerCount = size.getCapacity() * 20;
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    
    public int getPassengerCount() {
        return passengerCount;
    }
    public void boardPassengers() {
        this.passengerCount = new Random().nextInt(maxPassengerCount);
    }
    
    public void moveToNextLocation() {
        this.canStart = false;
        var x = this.getLocation().getSprite().getTranslateX();
        var y = this.getLocation().getSprite().getTranslateY();

        this.getTransition().setPath(this.moveTo(x, y));
        this.getTransition().setOnFinished(event -> {
            this.getSprite().setVisible(false);
            this.canStart = true;
        });
        
        this.getSprite().setVisible(true);
        this.getLocation().getPlanes().remove(this);
        this.setNextLocation();
        this.getLocation().getPlanes().add(this);
        this.getTransition().play();
    }
    
    private boolean canStart = true;
    public boolean canStart() {
        var next = this.path.get(nextIndex);
        return canStart && next.getPlanes().size() < next.getSize().getCapacity();
    }
    
    private void setNextLocation() {
        this.location = this.path.get(this.nextIndex);
        this.currentIndex = nextIndex;
        this.nextIndex = (this.currentIndex + 1) % this.path.size();
    }
    
    public Airport getLocation() {
        return location;
    }
    
    public int getMaxPassengerCount() {
        return this.maxPassengerCount;
    }
    ;
    public SIZE getSize() {
        return size;
    }
    public ObservableList<Airport> getPath() {
        return path;
    }
    @Override public void run() {
        Platform.runLater(this::boardPassengers);
        Platform.runLater(this::moveToNextLocation);
    }
}
