package classes;

import enums.WEAPON;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 * Klasa Samolot wojskowy rozszerzająca klasę samolot
 */
public class MilitaryPlane extends Plane {
    private MilitaryShip destination;

    /**
     * @param destination Cel następnego lotu
     */
    public void setDestination(MilitaryShip destination) {
        this.destination = destination;
    }

    private final ObservableList<MilitaryShip> path;
    private int currentIndex;
    private int nextIndex;
    private final WEAPON weapon;
    private double fuel;
    private double maxFuel;
    /**
     * @param path Ścieżka kolejnych lotniskowców
     */
    public MilitaryPlane(ObservableList<MilitaryShip> path) {
        super(TYPE.MILITARYPLANE);
        this.path = path;
        this.destination = path.get(0);
        this.currentIndex = 0;
        this.nextIndex = 0;
        this.setSpeed(80);
        this.getSprite().setTranslateX(path.get(0).getSprite().getTranslateX() + path.get(0).getType().getOrigin().getX());
        this.getSprite().setTranslateY(path.get(0).getSprite().getTranslateY() + path.get(0).getType().getOrigin().getY());
        this.fuel = 0;
        this.maxFuel = 10_000;
        this.weapon = this.destination.getWeapon();
    }
    
    private void moveToNextLocation() {
        this.canStart = false;
        var x = this.getDestination().getSprite().getTranslateX() + this.getDestination().getType().getOrigin().getX();
        var y = this.getDestination().getSprite().getTranslateY() + this.getDestination().getType().getOrigin().getY();
        
        this.getTransition().setPath(this.moveTo(x, y));
        this.getTransition().setOnFinished(event -> {
            this.getSprite().setVisible(false);
            this.canStart = true;
        });
    
        this.getSprite().setVisible(true);
        this.getDestination().getPlanes().remove(this);
        this.setNextLocation();
        this.getDestination().getPlanes().add(this);
        this.getTransition().play();
    }
    
    private boolean canStart = true;
    /**
     * @return Sygnał informujący czy może rozpocząć się kolejny lot
     */
    public boolean canStart() {
        var next = this.path.get(nextIndex);
        return canStart && next.getPlanes().size() < next.getSize().getCapacity();
    }
    
    private void setNextLocation() {
        this.destination = this.path.get(this.nextIndex);
        this.currentIndex = nextIndex;
        this.nextIndex = (this.currentIndex + 1) % this.path.size();
    }
    
    /**
     * @return aktulany cel podróży
     */
    public MilitaryShip getDestination() {
        return destination;
    }
    /**
     * Implementacja rozpoczęcia pracy wątku, która działa jednorazowo,
     * która wyrusza do następnego lotniska
     */
    @Override public void run() {
        Platform.runLater(this::moveToNextLocation);
    }
    /**
     * @return Broń samolotu
     * */
    public WEAPON getWeapon() {
        return weapon;
    }
    /**
     * @return Ścieżka lotu
     */
    public ObservableList<MilitaryShip> getPath() {
        return path;
    }

    /**
     * @return Ilość paliwa
     */
    public double getFuel() {
        return fuel;
    }
}
