package classes;

import java.util.ArrayList;

public class MilitaryShip extends Ship {
    private final SIZE size;
    public MilitaryShip(SIZE size, ArrayList<Integer> ids) {
        super(TYPE.MILITARYSHIP, ids);
        this.size = size;
        this.getSprite().setScaleX(size.getSizeMultiplier());
        this.getSprite().setScaleY(size.getSizeMultiplier());
    }
}
