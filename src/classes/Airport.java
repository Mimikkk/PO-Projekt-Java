package classes;

import enums.SIZE;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa Lotnisko rozszerzajaca klasę jednostka
 */
public class Airport extends Entity {
    private final SIZE size;
    private final ObservableList<CivilianPlane> planes = FXCollections.observableArrayList();
    private final String name;

    /**
     * @param size Wielkość lotniska
     * @param name Nazwa lotniska
     */
    public Airport(SIZE size, String name) {
        super(TYPE.AIRPORT);

        this.name = name;
        this.size = size;

        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }

    /**
     * @return Wielkość lotniska
     */
    public SIZE getSize() {
        return size;
    }

    /**
     * @return Aktualnie przebywające samoloty
     */
    public ObservableList<CivilianPlane> getPlanes() {
        return this.planes;
    }

    private void sendPlanesFlying() {
        var toCut = planes.size() - this.getSize().getCapacity();
        if (planes.size() > this.getSize().getCapacity() && toCut > 0) planes.subList(0, toCut).clear();
        try {
            planes.stream().filter(CivilianPlane::canStart).forEach(plane -> new Thread(plane).start());
        } catch (Exception ignored) { // Some graphics shenanigans
        }
    }

    /**
     * Implementacja rozpoczęcia pracy wątku, która działa dopóki isRunning jest prawdziwe,
     * która co sekundę próbuje wysłać kolejne samoloty cywilne
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
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return Nazwa lotniska
     */
    public String getName() {
        return name;
    }

}
