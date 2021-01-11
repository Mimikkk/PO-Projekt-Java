package classes;

public enum SIZE {
    SMALL(0.7, 4, "Mały"),
    MEDIUM(1, 6, "Średni"),
    BIG(1.3, 8, "Wielki");
    
    private final double sizeMultiplier;
    private final int capacity;
    private final String text;
    SIZE(double sizeMultiplier, int capacity, String text) {
        this.sizeMultiplier = sizeMultiplier;
        this.capacity = capacity;
        this.text = text;
    }
    
    public int getCapacity() {
        return capacity;
    }
    public double getSizeMultiplier() {
        return sizeMultiplier;
    }
    @Override public String toString() {
        return text;
    }
}
