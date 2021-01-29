package classes;

import enums.FIRM;
import enums.SIZE;
import javafx.application.Platform;

import java.util.Random;

/**
 * Klasa reprezentująca Wycieczkowiec rozszerająca klasę Statek
 */
public class CivilianShip extends Ship {
    private final SIZE size;
    private final int maxPassengerCount;
    private final FIRM firm;
    private int passengerCount;
    
    /**
     * @param size Wielkość statku
     * @param firm Firma, do której należy statek
     */
    public CivilianShip(SIZE size, FIRM firm) {
        super(TYPE.CIVILIANSHIP);
        this.size = size;
        this.firm = firm;
        maxPassengerCount = size.getCapacity() * 35;
        this.setSpeed(60);

        this.changePassengers();
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    
    @Override public String toString() {
        return "Wycieczkowiec " + this.firm + " korporacji ";
    }
    /**
     * @return Maksymalną liczbę pasażerów
     */
    public int getMaxPassengerCount() {
        return maxPassengerCount;
    }
    
    /**
     * Implementacja rozpoczęcia pracy wątku, która działa dopóki isRunning jest prawdziwe,
     * która co pół sekundy próbuje sprawdzić czy może się poruszyć do następnej lokacji
     */
    @Override public void run() {
        while (isRunning()) {
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            Platform.runLater(this::moveToNextLocation);
        }
    }

    private void changePassengers() {
        this.passengerCount = 1 + new Random().nextInt(maxPassengerCount - 1);
    }
    
    /**
     * @return Wielkość statku
     */
    public SIZE getSize() {
        return size;
    }
    /**
     * @return Liczba pasażerów
     */
    public int getPassengerCount() {
        return passengerCount;
    }
    /**
     * @return Firma przynależności
     */
    public FIRM getFirm() {
        return firm;
    }
}
