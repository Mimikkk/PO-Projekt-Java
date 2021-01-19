import classes.Entity;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


@SuppressWarnings("unused") public class MapController implements Initializable {
    private ControlPanelController controlPanelController;
    private Stage controlPanelStage;
    
    @FXML private MenuItem menuItem_MenuBar_openControlPanel;
    @FXML private MenuItem menuItem_MenuBar_quitApplication;
    
    @FXML private AnchorPane map;
    public AnchorPane getMap() { return map; }
    
    @FXML private void menuItem_MenuBar_openControlPanel_OnAction(ActionEvent event) {
        this.controlPanelStage.show();
        this.controlPanelStage.toFront();
    }
    @FXML private void menuItem_MenuBar_quitApplication_OnAction(ActionEvent event) {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Dialog");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(e -> {if (e == ButtonType.OK) Platform.exit(); });
    }
    
    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controlpanel.fxml"));
            Parent root = loader.load();
            
            this.controlPanelController = loader.getController();
            this.controlPanelController.init(this);
            this.controlPanelStage = new Stage();
            
            controlPanelStage.setTitle("Panel Sterowania");
            controlPanelStage.setScene(new Scene(root));
            controlPanelStage.setResizable(false);
            
            controlPanelStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    public void addToMap(Entity entity) {
        this.map.getChildren().add(entity.getSprite());
    }
    public void removeFromMap(Entity entity) {
        this.map.getChildren().remove(entity.getSprite());
    }
}
