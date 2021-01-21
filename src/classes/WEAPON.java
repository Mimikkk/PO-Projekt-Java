package classes;

public enum WEAPON {
    LOOVEGUN("Broń Miłości"),
    TOPGUN("Najlepsza Broń"),
    PEACEGUN("Broń Wolności"),
    GROOVEGUN("Potrójna Wieża Strzelicza (6\"/47 Mk 16) 152mm Mod 1"),
    BFG("BFG8000");
    
    private final String text;
    WEAPON(String text) {
        this.text = text;
    }
    
    @Override public String toString() {
        return text;
    }
}
