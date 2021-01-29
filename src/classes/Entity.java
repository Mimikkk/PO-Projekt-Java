package classes;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.SVGPath;

import java.util.Random;
import java.util.Set;

/**
 * Klasa przechowująca wszelkie niezbędne pola składające abstrakcyjną jednostkę
 */
public abstract class Entity implements Runnable {
    private final SVGPath sprite;
    private final int id;
    private final TYPE type;
    private boolean isRunning = true;
    
    /**
     * Konstuktor, który inicjuje Spirte, który jest reprezentacją graficzną jednostki,
     * dla którego jest wybierany kolor, oś rotacyjna i tooltip, oraz na podstawie, której są obsługiwane koordynaty
     * unikalny identyfikator i oraz podawany przez klasy podrzędne typ.
     *
     * @param type Typ jednostki, parametr jest automatycznie podawany z klas podrzędnych
     */
    public Entity(final TYPE type) {
        var svg = new SVGPath();
        svg.setContent(type.getSvg());
        sprite = svg;

        svg.setEffect(new Bloom(-100000000)); // Obviously
        var rand = new Random();
        sprite.setFill(new LinearGradient(
                rand.nextDouble(), rand.nextDouble(),
                rand.nextDouble(), rand.nextDouble(),
                true, CycleMethod.REFLECT,
                new Stop(0, Color.hsb(rand.nextInt(360), 1.0, 0.70)),
                new Stop(1, Color.hsb(rand.nextInt(360), 1.0, 0.80))));
        
        if (Set.of(TYPE.MILITARYSHIP, TYPE.CIVILIANSHIP).contains(type))
            svg.setRotationAxis(new Point3D(0, 1, 0));
        
        this.id = Database.IDS.getNew();
        this.type = type;
        
        // Add Tooltip
        Tooltip tooltip = new Tooltip();
        Tooltip.install(this.sprite, tooltip);
        sprite.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue().booleanValue()) {
                tooltip.setText(
                        "Nazwa: "
                                + toString()
                                + "\nPozycja: "
                                + "x: " + sprite.getTranslateX()
                                + ", y: " + sprite.getTranslateY() + "\nID: " + this.id);
                
                
                tooltip.show(sprite,
                        sprite.getTranslateX()
                                + sprite.getScene().getX()
                                + sprite.getScene().getWindow().getX(),
                        sprite.getTranslateY()
                                + sprite.getScene().getY()
                                + sprite.getScene().getWindow().getY()
                                + 4 * type.getOrigin().getY());
            } else { tooltip.hide(); }
        });
    }
    
    /**
     * @return Identyfikator jednostki
     */
    public int getID() { return this.id; }
    /**
     * @return Typ jednostki
     */
    public TYPE getType() { return this.type; }
    /**
     * @return Reprezentację graficzna
     */
    public SVGPath getSprite() { return sprite; }
    @Override public String toString() { return type.getName() + " '" + id + "'"; }
    
    /**
     * @return zmienna Bool'owską informującą o tym, czy wątek jest nadal aktywny
     */
    protected boolean isRunning() {
        return isRunning;
    }
    
    /**
     * @param running Zmienna Bool'owska decydująca o aktywością wątku
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }
    
    /**
     * Wbudowany enum, typ, przechowujący wszelkie informacje i stałe wiążące się z typem jednostki
     */
    public enum TYPE {
        /**
         * Samolot cywilny
         */
        CIVILIANPLANE("Samolot Pasażerski", new Point2D(25, 25), "M 49.3 26.706 C 48.032 28.06 45.228 28.738 40.884 28.738 L 30.39 28.738 L 17.21 49.508 L 17.102 49.674 C 16.898 49.892 16.666 50 16.402 50 L 13.604 49.998 C 13.342 50 13.11 49.892 12.904 49.672 C 12.598 49.346 12.518 48.98 12.664 48.574 L 19.244 28.736 L 8.532 28.738 C 6.056 33.504 4.714 35.996 4.508 36.214 C 4.306 36.432 4.072 36.542 3.81 36.542 L 1.012 36.542 C 0.75 36.542 0.518 36.434 0.314 36.214 C 0.05 35.936 -0.038 35.592 0.05 35.188 L 2.784 25.024 L 0.05 14.858 C -0.064 14.454 0.016 14.104 0.292 13.808 C 0.494 13.592 0.728 13.48 0.992 13.48 L 3.81 13.458 C 4.072 13.458 4.306 13.568 4.508 13.786 C 4.714 14.004 6.056 16.496 8.532 21.262 L 19.244 21.264 L 12.664 1.426 C 12.518 1.02 12.598 0.654 12.904 0.328 C 13.11 0.108 13.34 0.002 13.604 0.002 L 16.402 0 C 16.664 0.002 16.896 0.11 17.1 0.328 C 17.16 0.392 17.196 0.444 17.21 0.492 L 30.39 21.262 L 40.884 21.262 C 45.228 21.262 48.032 21.94 49.3 23.294 C 49.766 23.792 50 24.362 50 25.002 C 50 25.638 49.766 26.208 49.3 26.706 Z"),
        /**
         * Samolot wojskowy - Odrzutowiec
         */
        MILITARYPLANE("Odrzutowiec", new Point2D(25, 25), "M 15.632 0 L 18.188 0.002 C 19.625 0.003 20.586 1.457 20.586 1.457 L 23.704 5.576 L 27.298 5.658 C 29.216 5.659 28.658 8.001 27.778 8.08 L 25.703 8.241 L 28.9 12.924 L 31.696 12.684 C 34.812 12.445 34.413 14.946 32.816 15.268 L 30.899 15.428 L 35.935 22.132 C 45.841 22.301 49.835 22.304 49.996 24.887 C 50.157 27.47 45.364 27.465 36.1 27.622 L 30.992 34.559 L 33.228 34.723 C 34.507 34.885 34.828 37.146 32.272 36.983 L 28.996 36.899 L 25.805 41.578 L 27.642 41.823 C 29.319 42.067 29.481 44.164 26.845 44.243 L 23.49 44.242 L 20.617 48.275 C 19.341 50.373 17.822 49.969 17.822 49.969 L 15.667 49.887 L 23.563 33.022 C 24.281 31.648 24.599 29.55 22.84 28.096 C 21.96 27.369 18.606 26.721 17.967 26.722 L 8.781 26.634 C 8.542 26.552 3.673 33.25 3.673 33.25 C 3.434 33.491 0 33.893 0 33.893 L 2.311 25.579 L 0.153 24.691 L 2.389 24.208 L 0.067 15.73 L 3.502 16.296 C 4.221 17.509 8.699 23.162 8.858 23.163 C 8.858 23.163 17.006 23.169 17.485 23.168 C 20.28 23.089 21.24 23.091 22.197 22.607 C 23.075 22.042 23.794 20.671 22.914 17.199 C 21.792 12.355 15.632 0 15.632 0 Z"),
        /**
         * Wycieczkowiec
         */
        CIVILIANSHIP("Wycieczkowiec", new Point2D(25, 10), "M 3.628 9.794 L 5.02 5.891 L 7.499 5.891 L 8.493 2.388 L 10.953 2.399 L 10.953 0 L 13.139 0 L 13.139 2.41 L 17.413 2.43 L 17.413 0 L 19.599 0 L 19.599 2.441 L 23.872 2.461 L 23.872 0 L 26.059 0 L 26.059 2.472 L 29.608 2.489 C 29.608 2.489 30.8 5.99 30.8 5.99 L 38.057 5.89 L 39.25 9.794 L 50 9.794 L 42.905 20 L 5.668 20 L 5.652 19.994 C 3.688 19.271 2.267 16.741 1.357 14.408 C 0.447 12.073 0.023 9.912 0.023 9.912 L 0 9.794 L 3.628 9.794 Z M 4.743 13.462 L 6.532 13.462 L 6.532 11.671 L 4.743 11.671 L 4.743 13.462 Z M 8.718 13.462 L 10.507 13.462 L 10.507 11.671 L 8.718 11.671 L 8.718 13.462 Z M 12.693 13.462 L 14.482 13.462 L 14.482 11.671 L 12.693 11.671 L 12.693 13.462 Z M 16.8 13.462 L 18.589 13.462 L 18.589 11.671 L 16.8 11.671 L 16.8 13.462 Z M 20.775 13.462 L 22.564 13.462 L 22.564 11.671 L 20.775 11.671 L 20.775 13.462 Z M 24.75 13.462 L 26.539 13.462 L 26.539 11.671 L 24.75 11.671 L 24.75 13.462 Z M 29.579 13.476 L 31.368 13.476 L 31.368 11.685 L 29.579 11.685 L 29.579 13.476 Z M 33.554 13.476 L 35.343 13.476 L 35.343 11.685 L 33.554 11.685 L 33.554 13.476 Z M 37.529 13.476 L 39.318 13.476 L 39.318 11.685 L 37.529 11.685 L 37.529 13.476 Z"),
        /**
         * Lotniskowiec
         */
        MILITARYSHIP("Lotniskowiec", new Point2D(50, 20), "M 1.486 21.337 C 1.486 21.337 6.706 21.337 6.74 21.337 C 6.706 21.469 6.774 18.271 6.774 18.271 C 6.774 18.271 12.26 18.075 12.26 18.141 C 12.26 18.141 12.394 -0.195 12.394 0.001 C 12.394 0.001 14.134 0.067 14.336 0.001 C 14.536 -0.065 17.95 15.727 17.95 15.791 C 17.95 15.727 21.966 21.403 22.032 21.469 C 22.098 21.469 27.854 21.469 27.922 21.599 C 27.922 21.665 34.346 27.602 34.346 27.668 C 34.346 27.734 83.336 28.124 95.316 28.124 C 95.984 28.124 96.186 27.212 96.788 27.212 C 98.862 27.212 100 27.212 100 27.212 C 100 27.212 97.256 32.496 95.65 34.128 C 94.044 35.694 87.218 40 87.218 40 L 4.43 40 L 2.624 35.954 C 2.624 35.954 0.148 31.91 0.148 31.91 C 0.148 31.778 -0.054 24.861 0.014 24.861 C 0.014 24.797 1.486 21.273 1.486 21.273 L 1.486 21.337 Z"),
        /**
         * Lotniski
         */
        AIRPORT("Lotnisko", new Point2D(20, 25), "M 20 0 C 8.956 0 0 9.154 0 20.448 C 0 31.917 8.75 39.756 20 50 C 31.25 39.756 40 31.917 40 20.448 C 40 9.154 31.044 0 20 0 Z M 20 37.5 C 11.162 37.5 4 30.038 4 20.833 C 4 11.629 11.162 4.168 20 4.168 C 28.838 4.168 36 11.629 36 20.833 C 36 30.038 28.838 37.5 20 37.5 Z M 12.78 22.496 L 18.442 19.575 L 12.638 14.419 L 14.434 13.494 L 23.828 16.798 L 28.316 14.483 C 29.5 13.865 31.454 14.031 31.898 14.965 C 31.968 15.113 32 15.281 32 15.462 C 31.996 16.418 31.08 17.708 30.094 18.223 L 25.606 20.538 L 22.434 30.325 L 20.638 31.25 L 20.22 23.318 L 14.556 26.235 L 13.388 29.169 L 12.132 29.815 L 11.694 25.385 L 8.576 22.335 L 9.832 21.688 L 12.78 22.496 Z");
        
        private final String svg;
        private final Point2D origin;
        private final String name;
        
        /**
         * Konstruktor stałej enum dla poszczególnych typów jednostek
         *
         * @param name   Formatowana nazwa jednostki
         * @param origin Punkt inforumujący o offset'cie względem centrum jednostki
         * @param svg    Ścieżka SVG relizująca reprezentację graficzną jednostki
         */
        TYPE(String name, Point2D origin, String svg) {
            this.name = name;
            this.svg = svg;
            this.origin = origin;
        }
        
        /**
         * @return Dwuwymiarowy punkt zawierający informacje o offset'cie względem punktu 0,0 dla danej jednostki
         */
        public Point2D getOrigin() { return this.origin; }
        /**
         * @return Słowo zawierające ścieżkę SVG, wykorzystaną do konstrukcji reprezentacji graficznej jednostki
         */
        public String getSvg() { return svg; }
        /**
         * @return Formatowana nazwa dla określonego tyup
         */
        String getName() { return name; }
    }
}
