package classes;

public enum FIRM {
    GoodCorp("Dobrej"),
    MediumCorp("Średniej"),
    BadCorp("Złej"),
    RandomCorp("Szalonej"),
    CorpCorp("Korporacyjnej");

    private final String text;

    FIRM(String text){
        this.text = text;
    }
    String getText() {
        return text;
    }
}
