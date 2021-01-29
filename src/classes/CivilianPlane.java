package classes;

import enums.SIZE;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.Random;

/**
 * Klasa reprezentująca Samolot cywilny rozszerająca klasę Samolot
 */
public class CivilianPlane extends Plane {
    private int passengerCount = 0;
    private Airport destination;
    private final ObservableList<Airport> path;
    private int currentIndex;
    private int nextIndex;

    private final int maxPassengerCount;
    private final SIZE size;
    
    /**
     * @param size Wielkość samolotu
     * @param path Kolejne lotniska odwiedzane przez samolot
     */
    public CivilianPlane(SIZE size, ObservableList<Airport> path) {
        super(TYPE.CIVILIANPLANE);
        this.destination = path.get(0);
        this.currentIndex = 0;
        this.nextIndex = 0;
        this.setSpeed(70);

        this.getSprite().setTranslateX(path.get(0).getSprite().getTranslateX() + path.get(0).getType().getOrigin().getX());
        this.getSprite().setTranslateY(path.get(0).getSprite().getTranslateY() + path.get(0).getType().getOrigin().getY());
        this.path = path;
        this.size = size;
        maxPassengerCount = size.getCapacity() * 20;
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    
    /**
     * @return Liczba pasażerów
     */
    public int getPassengerCount() {
        return passengerCount;
    }
    private void boardPassengers() {
        this.passengerCount = new Random().nextInt(maxPassengerCount);
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
     * @return Sygnał informujący o możliwości lotu
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
     * @return Aktualny lotnisko znane jako cel
     */
    public Airport getDestination() {
        return destination;
    }
    
    /**
     * @return Maksymalna liczba pasażerów
     */
    public int getMaxPassengerCount() {
        return this.maxPassengerCount;
    }
    /**
     * @return Wielkość samolotu
     */
    public SIZE getSize() {
        return size;
    }
    /**
     * @return Lista następnych lotnisk
     */
    public ObservableList<Airport> getPath() {
        return path;
    }
    /**
     * Implementacja rozpoczęcia pracy wątku, która działa jednorazowo,
     * która zmienia liczbę pasażerów i wyrusza do następnego lotniska
     */
    @Override public void run() {
        Platform.runLater(this::boardPassengers);
        Platform.runLater(this::moveToNextLocation);
    }
    /**
     * @param destination Miejsce docelowe
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }
}
