package classes;

import enums.SIZE;
import enums.WEAPON;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa używana w symulacji reprezentująca Lotniskowiec rozszerzająca klase Statek o informację związane z ruchem
 * i zachowaniem przy odtworzeniu wątku
 */
public class MilitaryShip extends Ship {
    private final WEAPON weapon;
    private final String name;
    private final ObservableList<MilitaryPlane> planes = FXCollections.observableArrayList();


    /**
     * @return Nazwa statku
     */
    public String getName() {
        return name;
    }

    /**
     * @param weapon Broń statku
     * @param name   Nazwa statku
     */
    public MilitaryShip(WEAPON weapon, String name) {
        super(TYPE.MILITARYSHIP);
        this.weapon = weapon;
        this.name = name;
        this.setSpeed(20);
    }

    private void sendPlanesFlying() {
        var toCut = planes.size() - this.getSize().getCapacity();
        if (planes.size() > this.getSize().getCapacity() && toCut > 0) planes.subList(0, toCut).clear();
        try {
            planes.stream().filter(MilitaryPlane::canStart).forEach(plane -> new Thread(plane).start());
        } catch (Exception ignored) { // Some graphics shenanigans
        }
    }

    /**
     * Implementacja rozpoczęcia pracy wątku, która działa dopóki isRunning jest prawdziwe,
     * która co sekundę próbuje wysłać kolejne samoloty wojskowe i następnie poruszyć się do następnej lokacji
     */
    @Override
    public void run() {
        while (isRunning()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(this::sendPlanesFlying);
            Platform.runLater(this::moveToNextLocation);
        }
    }

    @Override
    public String toString() {
        return this.name + " " + this.weapon;
    }

    /**
     * @return Wielkość statku
     */
    public SIZE getSize() {
        return SIZE.MEDIUM;
    }

    /**
     * @return Przechowywane statki
     */
    public ObservableList<MilitaryPlane> getPlanes() {
        return planes;
    }

    /**
     * @return Broń statku
     */
    public WEAPON getWeapon() {
        return weapon;
    }
}
