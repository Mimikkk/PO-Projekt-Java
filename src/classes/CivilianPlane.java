package classes;

import javafx.collections.ObservableList;

import java.util.Random;

public class CivilianPlane extends Plane {
    private int passengerCount = 0;
    private Airport location;
    private final ObservableList<Airport> path;
    private final int maxPassengerCount;
    private final SIZE size;
    
    public CivilianPlane(SIZE size, ObservableList<Airport> path) {
        super(TYPE.CIVILIANPLANE);
        this.location = path.get(0);
        this.path = path;
        this.size = size;
        maxPassengerCount = size.getCapacity() * 20;
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    
    public int getPassengerCount() {
        return passengerCount;
    }
    public void changePassengers() {
        this.passengerCount = new Random().nextInt(maxPassengerCount);
    }
    public void setLocation(Airport location) {
        this.location = location;
    }
    
    public Airport getLocation() {
        return location;
    }
    
    public int getMaxPassengerCount() {
        return this.maxPassengerCount;
    };
    public SIZE getSize() {
        return size;
    }
    public ObservableList<Airport> getPath() {
        return path;
    }
    @Override public void run() {
    
    }
}
