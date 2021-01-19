package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Random;

public class Database {
    public static class IDS {
        private static final ObservableList<Integer> ids = FXCollections.observableArrayList();
        private static final Random rand = new Random();
        
        public static Integer getNew() {
            int id;
            do { id = rand.nextInt(999_999); } while (ids.contains(id));
            ids.add(id);
            return id;
        }
        public static void remove(int id) {
            ids.remove(id);
        }
    }
    
    private static final ObservableList<Airport> Airports = FXCollections.observableArrayList();
    private static final ObservableList<CivilianPlane> CivilianPlanes = FXCollections.observableArrayList();
    private static final ObservableList<MilitaryPlane> MilitaryPlanes = FXCollections.observableArrayList();
    private static final ObservableList<CivilianShip> CivilianShips = FXCollections.observableArrayList();
    private static final ObservableList<MilitaryShip> MilitaryShips = FXCollections.observableArrayList();
    
    public static ObservableList<Airport> getAirports() {
        return Airports;
    }
    public static ObservableList<CivilianPlane> getCivilianPlanes() {
        return CivilianPlanes;
    }
    public static ObservableList<MilitaryPlane> getMilitaryPlanes() {
        return MilitaryPlanes;
    }
    public static ObservableList<CivilianShip> getCivilianShips() {
        return CivilianShips;
    }
    public static ObservableList<MilitaryShip> getMilitaryShips() {
        return MilitaryShips;
    }
}
