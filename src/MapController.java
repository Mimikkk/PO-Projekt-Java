import classes.Entity;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class MapController implements Initializable {
    private ControlPanelController controlPanelController;
    private Stage controlPanelStage;
    
    @FXML private MenuItem buttonControlPanelOpen;
    @FXML private MenuItem buttonQuit;
    @FXML private AnchorPane map;
    public AnchorPane getMap() { return map; }
    
    @FXML private void buttonControlPanelOnAction(ActionEvent event) {
        this.controlPanelStage.show();
    }
    
    @FXML private void buttonQuitOnAction(ActionEvent event) {
        // TODO Confirmation Window
        Platform.exit();
    }
    
    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controlpanel.fxml"));
            Parent root = loader.load();
            
            this.controlPanelController = loader.getController();
            this.controlPanelController.init(this);
            
            this.controlPanelStage = new Stage();
            
            controlPanelStage.setTitle("Control Panel");
            controlPanelStage.setScene(new Scene(root));
            controlPanelStage.setResizable(false);
            
            controlPanelStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
