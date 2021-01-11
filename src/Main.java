import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    
    @Override public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
            
            Parent root = loader.load();
            primaryStage.setTitle("Mapa");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setOnHidden(event -> Platform.exit());
            
            primaryStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
