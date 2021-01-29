package enums;

/**
 * Enum przetrzymujący informację o typach broni
 */
public enum WEAPON {
    /**
     * Pierwszy typ broni
     */
    LOOVEGUN("Broń Miłości"),
    /**
     * Drugi typ broni
     */
    TOPGUN("Najlepsza Broń"),
    /**
     * Trzeci typ broni
     */
    PEACEGUN("Broń Wolności"),
    /**
     * Czwarty typ broni
     */
    GROOVEGUN("Potrójna Wieża Strzelicza (6\"/47 Mk 16) 152mm Mod 1"),
    /**
     * Piąty typ broni
     */
    BFG("BFG8000");
    
    private final String text;
    /**
     * @param text Formatowana nazwa broni
     */
    WEAPON(String text) {
        this.text = text;
    }
    
    /**
     * @return Formatowana nazwa broni
     */
    @Override public String toString() {
        return text;
    }
}
