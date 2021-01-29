package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Kontroler mapy, na którym są rysowane reprezentacje graficzne wszelkich jednostek
 */
@SuppressWarnings("unused") public class MapController implements Initializable {
    private ControlPanelController controlPanelController;
    private Stage controlPanelStage;
    
    @FXML private MenuItem menuItem_MenuBar_openControlPanel;
    @FXML private MenuItem menuItem_MenuBar_quitApplication;
    
    @FXML private AnchorPane map;
    @FXML private Polygon islandCollision_1;
    @FXML private Polygon islandCollision_2;
    private ObservableList<Polygon> observableList_IslandColiders;
    
    /**
     * @return Mapa, po której poruszają się jednostki
     */
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
    
    /** Inicjalizacja Kontrolera podrzędnego i nadrzędnego
     * @param location lokacja
     * @param resources zasoby
     */
    @Override public void initialize(URL location, ResourceBundle resources) {
        observableList_IslandColiders = FXCollections.observableArrayList(islandCollision_1, islandCollision_2);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controlpanel.fxml"));
            Parent root = loader.load();
            
            this.controlPanelController = loader.getController();
            this.controlPanelController.initialize_MapController(this);
            this.controlPanelController.initialize_Entities();
            this.controlPanelStage = new Stage();
            
            controlPanelStage.setTitle("Panel Sterowania");
            controlPanelStage.setScene(new Scene(root));
            controlPanelStage.setResizable(false);
            
            controlPanelStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    /** Dodanie do mapy reprezentacji graficznej jednostki
     * @param node Reprezentacja graficzna
     * @param <T> Parametr uogólniający reprezentacje graficzne
     */
    public <T extends Node> void addToMap(T node) {
        this.map.getChildren().add(node);
    }
    /** Usunięcie z mapy reprezentacji graficznej jednostki
     * @param node Reprezentacja graficzna
     * @param <T> Parametr uogólniający reprezentacje graficzne
     */
    public <T extends Node> void removeFromMap(T node) {
        this.map.getChildren().remove(node);
    }
    /**
     * @return Kolidery wysp, które nie działają, bo Java, w c# działałoby bez problemu.
     */
    public ObservableList<Polygon> getIslandColliders() {
        return observableList_IslandColiders;
    }
}
