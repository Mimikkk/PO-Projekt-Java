package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CivilianPlane extends Plane {
    private int passengerCount = 0;
    private Airport location;
    private final List<Airport> path;
    private Airport destination = null;
    
    public CivilianPlane(ArrayList<Integer> ids, Airport startingPort, ListView<Airport> airports) {
        super(TYPE.CIVILIANPLANE, ids);
        this.location = startingPort;
        this.path = this.generatePath(startingPort, airports.getItems());
    }
    
    public int getPassengerCount() {
        return passengerCount;
    }
    public void changePassengers() {
        this.passengerCount = new Random().nextInt(255);
    }
    public void setLocation(Airport location) {
        this.location = location;
    }
    
    public Airport getLocation() {
        return location;
    }
    
    private List<Airport> generatePath(Airport startingPort, ObservableList<Airport> airports) {
        Random random = new Random();
        var toVisit = random.nextInt(airports.size());
        var list = airports.filtered(port -> port != startingPort);
        Collections.shuffle(list);
        return list.get;
    }
    
    public Airport getDestination() {
        return null;
    }
}
