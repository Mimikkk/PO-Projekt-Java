package classes;

abstract class Vehicle extends Entity {
    private SIZE size;
    
    public Vehicle(TYPE type) {
        super(type);
    }
    
    public enum SIZE {
        SMALL,
        MEDIUM,
        BIG;
    }
}
