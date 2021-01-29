import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Projekt zaliczeniowy z programowania obiektowego.
 * Autor: Daniel Zdancewicz
 *
 * Opis: Na start są inicjalizowane 2 okna, jedno z nich to okno zawierającę mapę, na której są jednostki reprezentujące
 * odpowiadające im instancje obiektów wymaganych przez projekt: Lotniska, samoloty, odrzutowce, wycieczkowce i lotniskowce,
 * gdzie na początek pojawia się pewna ilość predefiniowanych jednostek. 10 lotnisk, 3 lotniskowce, 8 samolotów, 3 odrzutowce, 3 wycieczkowce.
 *
 * Drugie okno zawiera interfejs odpowiedzialny za kontrolę wewnętrznej bazy danych i pozwala na manualne tworzenie nowych obiektów przy wyborze
 * odpowiadających im okien podzielonych na 2 podokna, które są podzielone na kolejne 5 podokien odpowiedzialnych za występujące jednostki.
 *
 * W projekcie nie występuje badanie kolizji ze względu na problemy napotkanę w czasie produkcji dlatego, statki potrafią
 * pływać po lądzie jak Pan Bóg przykazał. Projekt tak samo nie rozwiązuje problemu zakleszczeń, gdy te wystąpi, np poprzez zapełnienie lotniska,
 * nadmierne samoloty zostaną usunięte. Tak samo przy usunięciu lotniska, te wtedy usuwa wszelkie samoloty, które do niego zmierzały, oraz usuwa się
 * ze ścieżek innych samolotów.
 */
public class Main extends Application {
    /**
     * Włącz muzykę z pliku sakura trip
     */
    private void playSoundtrack() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/sakuratrip.mp3").toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.05);
        mediaPlayer.play();
    }
    
    
    /** Rozpoczyna symulację
     * @param primaryStage Główna scena
     */
    @Override public void start(Stage primaryStage) {
        try {
            playSoundtrack();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/map.fxml"));
            
            Parent root = loader.load();
            primaryStage.setTitle("Mapa");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setOnHidden(event -> Platform.exit());
    
            primaryStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    
    /** Inicjalizacja aplikacji
     * @param args argumenty, które nie są używane
     */
    public static void main(String[] args) {
        launch(args);
    }
}
