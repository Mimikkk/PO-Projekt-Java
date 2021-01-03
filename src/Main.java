import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Mapa
        Parent map = FXMLLoader.load(getClass().getResource("map.fxml"));
        primaryStage.setTitle("Map Window");
        primaryStage.setScene(new Scene(map));
        primaryStage.show();

        // Panel Kontrolny
        Parent controlPanel = FXMLLoader.load(getClass().getResource("controlpanel.fxml"));
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Control Panel");
        secondaryStage.setScene(new Scene(controlPanel));
        secondaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
