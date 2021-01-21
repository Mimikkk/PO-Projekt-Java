package classes;

public class MilitaryPlane extends Plane {
    private final WEAPON weapon;
    public MilitaryPlane(WEAPON weapon) {
        super(TYPE.MILITARYPLANE);
        this.weapon = weapon;
    }
    @Override public void run() {
    
    }
}
