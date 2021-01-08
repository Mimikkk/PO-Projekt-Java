import classes.*;
import javafx.animation.*;
import javafx.animation.PathTransition.OrientationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

public class ControlPanelController implements Initializable {
    private MapController mapController;
    private final Random random = new Random();
    
    @FXML private ListView<Airport> airportListView;
    @FXML private ListView<CivilianPlane> planeCivilianListView;
    @FXML private ListView<MilitaryPlane> planeMilitaryListView;
    @FXML private ListView<CivilianShip> shipCivilianListView;
    @FXML private ListView<MilitaryShip> shipMilitaryListView;
    
    //region [Main Pages]
    
    //region [Data]
    //region [Civilian Planes]
    
    //endregion
    //region [Military Planes]
    //endregion
    //region [Civilian Ships]
    //endregion
    //region [Military Ships]
    //endregion
    //region [Airports]
    @FXML private TextField textField_Airport_nameDisplay;
    @FXML private TextField textField_Airport_xDisplay;
    @FXML private TextField textField_Airport_yDisplay;
    @FXML private TextField textField_Airport_heldPlanesCountDisplay;
    @FXML private TextField textField_Airport_capacityDisplay;
    @FXML private TextField textField_Airport_idDisplay;
    
    @FXML private void button_AirportListView_OnClicked(MouseEvent event) {
        var a = airportListView.getSelectionModel().getSelectedItems();
        if (a.isEmpty()) return;
        
        var entity = airportListView.getSelectionModel().getSelectedItems().get(0);
        textField_Airport_nameDisplay.setText(entity.getType().toString());
        textField_Airport_xDisplay.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_Airport_yDisplay.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_Airport_capacityDisplay.setText(String.valueOf(entity.getSprite().getRotate()));
        textField_Airport_idDisplay.setText(String.valueOf(entity.getID()));
    }
    //endregion
    //endregion
    
    //region [Constructor]
    
    //region [Civilian Planes]
    // TODO add ability to choose the starting airport at will or at random
    // TODO add ability to choose the owning business at will or at random
    // TODO add the ability to choose the path at will or at random
    // TODO First choose the starting airport then the pathing
    
    @FXML private TextField textField_CivilianPlane_x;
    @FXML private TextField textField_CivilianPlane_y;
    
    @FXML private TextField textField_CivilianPlane_startingAirport; // FIXME not a TextField but a DropdownMenu
    @FXML private TextField textField_CivilianPlane_selectedAirport; // FIXME not a TextField but a DropdownMenu
    @FXML private TextField textField_CivilianPlane_selectedBusiness; // FIXME not a TextField but a DropdownMenu
    
    @FXML private Button button_CivilianPlane_Add;
    @FXML private Button button_CivilianPlane_SelectRandomStart;
    @FXML private Button button_CivilianPlane_SelectSpecifiedStart;
    @FXML private Button button_CivilianPlane_SelectRandomBusiness;
    @FXML private Button button_CivilianPlane_SelectSpecifiedBusiness;
    
    @FXML private Button button_CivilianPlane_CreateRandomPath;
    @FXML private Button button_CivilianPlane_SelectSpecifiedPath;
    @FXML private Button button_CivilianPlane_SelectRandomPath;
    @FXML private Button button_CivilianPlane_AddToSelectedPath;
    
    @FXML private Button button_CivilianPlane_RemoveFromPath;
    @FXML private void button_CivilianPlane_Add_OnClick(ActionEvent event) {
        addEntityToMap(planeCivilianListView, new CivilianPlane());
    }
    //endregion
    //region [Military Planes]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddMilitaryPlane;
    @FXML private void button_AddMilitaryPlane_OnClick(ActionEvent event) {
        addEntityToMap(planeMilitaryListView, new MilitaryPlane());
    }
    //endregion
    //region [Civilian Ships]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddCivilianShip;
    @FXML private void button_AddCivilianShip_OnClick(ActionEvent event) {
        addEntityToMap(shipCivilianListView, new CivilianShip());
    }
    //endregion
    //region [Military Ships]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddMilitaryShip;
    @FXML private void button_AddMilitaryShip_OnClick(ActionEvent event) {
        addEntityToMap(shipMilitaryListView, new MilitaryShip());
    }
    //endregion
    //region [Airport]
    // TODO Like Civilian Plane
    @FXML private Button button_Airport_AddAtSpecified;
    @FXML private Button button_Airport_AddAtRandom;
    
    @FXML TextField textField_Airport_x;
    @FXML TextField textField_Airport_y;
    @FXML TextField textField_Airport_size; // FIXME Dropdown not a TextField baka
    
    @FXML private void button_Airport_AddAtSpecified_OnClick(ActionEvent event) {
        var entity = new Airport();
        int x = (int) Math.max(0, Math.min(Double.parseDouble(textField_Airport_x.getText()),
                this.mapController.getMap().getLayoutBounds().getWidth()
                        - entity.getType().getOrigin().getX()));
        int y = (int) Math.max(0, Math.min(Double.parseDouble(textField_Airport_y.getText()),
                this.mapController.getMap().getLayoutBounds().getHeight()
                        - entity.getType().getOrigin().getY()));
        
        addEntityToMap(airportListView, createEntityAtXY(entity, x, y));
    }
    @FXML private void button_Airport_AddAtRandom_OnClick(ActionEvent event) {
        var entity = new Airport();
        var x = entity.getType().getOrigin().getX() + random.nextInt(
                (int) ((int) this.mapController.getMap().getLayoutBounds().getWidth()
                        - 2 * entity.getType().getOrigin().getX()));
        var y = entity.getType().getOrigin().getX() + random.nextInt(
                (int) ((int) this.mapController.getMap().getLayoutBounds().getHeight()
                        - 2 * entity.getType().getOrigin().getY()));
        addEntityToMap(airportListView, createEntityAtXY(entity, x, y));
    }
    //endregion
    //endregion
    //endregion
    
    private <T extends Entity> void addEntityToMap(ListView<T> listView, T entity) {
        listView.getItems().add(entity);
        this.mapController.addToMap(entity);
    }
    private <T extends Entity> T createEntityAtXY(T entity, double x, double y) {
        entity.getSprite().setTranslateX(x);
        entity.getSprite().setTranslateY(y);
        entity.getSprite().setUserData(entity.getClass());
        return entity;
    }
    
    @FXML private <T extends Vehicle> void moveTo(ListView<T> listView, double x, double y) {
        var entity = listView.getSelectionModel().getSelectedItem();
        var path = entity.moveTo(x,y);

        this.mapController.getMap().getChildren().add(path);
        entity.getTransition().setOnFinished(event -> this.mapController.getMap().getChildren().remove(path));
        entity.getTransition().play();
    }
    
    public void init(MapController mapController) { this.mapController = mapController; }
    @Override public void initialize(URL location, ResourceBundle resources) {
    }
}
