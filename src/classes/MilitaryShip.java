package classes;

public class MilitaryShip extends Ship {
    private final WEAPON weapon;
    private final String name;
    
    public MilitaryShip(WEAPON weapon, String name) {
        super(TYPE.MILITARYSHIP);
        this.weapon = weapon;
        this.name = name;
    }
    
    @Override public void run() {
    
    }
    @Override public String toString() {
        return this.name + " " + this.weapon;
    }
    public WEAPON getWeapon() {
        return weapon;
    }
    public String getName() {
        return name;
    }
}
