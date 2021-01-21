package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Random;

public class Database {
    private static final Random rand = new Random();
    
    public static class IDS {
        private static final ObservableList<Integer> ids = FXCollections.observableArrayList();
        
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
    
    public enum Names {
        Carriers("Abercrombie", "Acasta", "Achilles", "Ajax", "Amazon", "Ardent", "Arethusa", "Ark Royal", "Aurora", "Beagle", "Belfast", "Black Prince", "Bulldog", "Centaur", "Chaser", "Cheshire", "Comet", "Crescent", "Curacoa", "Curlew", "Cygnet", "Dido", "Dido µ", "Dorsetshire", "Drake", "Duke of York", "Eagle", "Echo", "Edinburgh", "Erebus", "Eskimo", "Exeter", "Fiji", "Formidable", "Fortune", "Foxhound", "Galatea", "Glasgow", "Glorious", "Gloucester", "Glowworm", "Grenville", "Hardy", "Hermes", "Hermione", "HMS Neptune", "Hood", "Howe", "Hunter", "Icarus", "Illustrious", "Illustrious µ", "Jamaica", "Javelin", "Jersey", "Juno", "Jupiter", "Kent", "King George V", "Leander", "Little Bel", "Little Illustrious", "Little Renown", "London", "Matchless", "Monarch", "Musketeer", "Nelson", "Newcastle", "Norfolk", "Perseus", "Prince of Wales", "Queen Elizabeth", "Renown", "Repulse", "Rodney", "Sheffield", "Sheffield µ", "Shropshire", "Sirius", "Southampton", "Suffolk", "Sussex", "Swiftsure", "Terror", "Unicorn", "Valiant", "Vampire", "Victorious", "Warspite", "York"),
        Airports("Ashdale", "Ashden", "Ashdon", "Ashholm", "Ashhurst", "Ashwich", "Ashwike Heath", "Barden", "Barhampton", "Birchbeck Lake", "Birchhaven", "Birchington", "Birchmarsh Head", "Birchwike", "Blackdon", "Dunhall", "Dunham", "Dunwich", "Eastholm", "Eastmarsh", "Eastthwaite", "Eastwich Heath", "Foxburn Bridge", "Foxbury", "Great Blackdale", "Great Blackworth", "Great Hazelwike", "Great Ilwell", "Guildden", "Harhampton", "Harholm", "Harmoor", "Hartlea Bridge", "Hartthorpe", "Hazelden Heath", "Hazelhey", "Hazelmere", "Henhampton", "Hinddon", "Hindhalgh", "Hindhey", "Hindhurst", "Hindlea Green", "Hopbrook", "Hopdale", "Ilhampton", "Keldhurst", "Keldmere", "Kinhampton", "Kinworth", "Kirden Cross", "Langhurst", "Langmouth", "Langpool", "Little Blackhurst", "Little Dunchester", "Little Dunpool", "Lower Moorfold", "Lower Otterhey", "Loxburgh Head", "Loxington", "Loxleigh", "Loxmarsh Green", "Market Barham", "Market Birchdale", "Market Guildfold", "Market Hendon", "Market Langmoor", "Market Portsmere", "Marsden", "Marshall", "Marsington", "Marsport", "Middlefold", "Middleholm Green", "Middlepool", "Millwich Head", "Moorcaster Bridge", "Nether Hopbrook", "Nether Kinhurst", "Nether Middleholm", "Nether Otterley", "Nether Oxby", "Norburgh", "Norham", "Northington Bridge", "Northlea", "Northleigh", "Northley", "Northorpe", "Northpool Lake", "Norwell", "Oakhurst", "Oakwick", "Overchester", "Overholm", "Oxchester", "Oxford", "Oxhall", "Oxington", "Oxmarsh Lake", "Portswood", "Presholm", "Redhaven", "Redlea", "Redwood", "Southbeck", "Southcaster", "Thornby", "Thorncaster Bridge", "Thornfield", "Upper Presdon", "Upper Thornhurst", "Westton", "Whitelea", "Woolchester", "Wooldon", "Woolford", "Woolhampton");
        
        private final ObservableList<String> unusedNames;
        private final ObservableList<String> usedNames = FXCollections.observableArrayList();
        
        Names(String... names) {
            unusedNames = FXCollections.observableArrayList(names);
        }
        
        public String getNew() {
            return unusedNames.get(rand.nextInt(unusedNames.size()));
        }
        public void use(String name) {
            if (unusedNames.contains(name)) {
                unusedNames.remove(name);
                usedNames.add(name);
            }
        }
        public void remove(String name) {
            if (usedNames.contains(name)) {
                usedNames.remove(name);
                unusedNames.add(name);
            }
        }
    }
    
    private static final ObservableList<Airport> airports = FXCollections.observableArrayList();
    private static final ObservableList<CivilianPlane> civilianPlanes = FXCollections.observableArrayList();
    private static final ObservableList<MilitaryPlane> militaryPlanes = FXCollections.observableArrayList();
    private static final ObservableList<CivilianShip> civilianShips = FXCollections.observableArrayList();
    private static final ObservableList<MilitaryShip> militaryShips = FXCollections.observableArrayList();
    private static final ObservableList<WEAPON> weapons = FXCollections.observableArrayList(WEAPON.TOPGUN, WEAPON.PEACEGUN, WEAPON.LOOVEGUN, WEAPON.BFG, WEAPON.GROOVEGUN);
    private static final ObservableList<FIRM> firms = FXCollections.observableArrayList(FIRM.BadCorp, FIRM.MediumCorp, FIRM.GoodCorp, FIRM.CorpCorp, FIRM.RandomCorp);
    
    public static ObservableList<Airport> getAirports() {
        return airports;
    }
    public static ObservableList<CivilianPlane> getCivilianPlanes() {
        return civilianPlanes;
    }
    public static ObservableList<MilitaryPlane> getMilitaryPlanes() {
        return militaryPlanes;
    }
    public static ObservableList<CivilianShip> getCivilianShips() {
        return civilianShips;
    }
    public static ObservableList<MilitaryShip> getMilitaryShips() {
        return militaryShips;
    }
    public static ObservableList<WEAPON> getWeapons() {
        return weapons;
    }
    public static ObservableList<FIRM> getFirms() {
        return firms;
    }
}