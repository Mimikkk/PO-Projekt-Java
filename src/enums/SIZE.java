package enums;

/**
 * Enum przetrzymujący inforamcje o stałych związanych z wielkością
 */
public enum SIZE {
    /**
     * Mała wielkość
     */
    SMALL(0.5, 4, "Mały"),
    /**
     * Średnia wielkość
     */
    MEDIUM(0.7, 6, "Średni"),
    /**
     * Duża wielkość
     */
    BIG(1, 8, "Wielki");
    
    private final double sizeMultiplier;
    private final int capacity;
    private final String text;
    /** Konstruktor
     * @param sizeMultiplier Współczynnik wielkości wpływający na wielkość reprezentacji graficznej
     * @param capacity Współczynnik ładowności
     * @param text Formatowanie tekstu
     */
    SIZE(double sizeMultiplier, int capacity, String text) {
        this.sizeMultiplier = sizeMultiplier;
        this.capacity = capacity;
        this.text = text;
    }
    
    /**
     * @return Współczynnik ładowności
     */
    public int getCapacity() {
        return capacity;
    }
    /**
     * @return Współczynnik wielkości reprezentacji graficznej
     */
    public double getSizeMultiplier() {
        return sizeMultiplier;
    }
    /**
     * @return Formatowany zapis tekstowy
     */
    @Override public String toString() {
        return text;
    }
}
