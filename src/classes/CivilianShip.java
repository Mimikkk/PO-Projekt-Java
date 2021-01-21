package classes;

import java.util.Random;

public class CivilianShip extends Ship {
    private final SIZE size;
    private final int maxPassengerCount;
    private final FIRM firm;
    private int passengerCount;
    
    public CivilianShip(SIZE size, FIRM firm) {
        super(TYPE.CIVILIANSHIP);
        this.size = size;
        this.firm = firm;
        maxPassengerCount = size.getCapacity() * 35;

        this.changePassengers();
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    @Override public void run() {
    
    }

    @Override public String toString() {
        return "Wycieczkowiec korporacji " + this.firm;
    }
    public int getMaxPassengerCount() {
        return maxPassengerCount;
    }

    public void changePassengers() {
        this.passengerCount = 1 + new Random().nextInt(maxPassengerCount - 1);
    }
    
    public SIZE getSize() {
        return size;
    }
}
