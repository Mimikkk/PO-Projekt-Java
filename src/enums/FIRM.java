package enums;

/**
 * Enum przetrzymujący informację o dostępnych firmach symulacji
 */
public enum FIRM {
    /**
     * Dobra firma
     */
    GoodCorp("Dobrej"),
    /**
     * Średnia firma
     */
    MediumCorp("Średniej"),
    /**
     * Microsoft
     */
    BadCorp("Złej"),
    /**
     * Szalona firma
     */
    RandomCorp("Szalonej"),
    /**
     * Korporacyjna firma
     */
    CorpCorp("Korporacyjnej");
    
    private final String text;
    
    /** Konstruktor firmy
     * @param text Formatowana nazwa firmy
     */
    FIRM(String text) {
        this.text = text;
    }
    /**
     * @return Formatowana nazwa fimry
     */
    @Override public String toString() {
        return text;
    }
}
