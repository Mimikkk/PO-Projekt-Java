package classes;

public class MilitaryShip extends Ship {
    private final SIZE size;
    public MilitaryShip(SIZE size) {
        super(TYPE.MILITARYSHIP);
        this.size = size;
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
    @Override public void run() {
    
    }
}
