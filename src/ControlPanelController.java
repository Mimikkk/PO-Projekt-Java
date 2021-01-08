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
    
    @FXML private TextField nameEntityDisplay;
    @FXML private TextField xEntityDisplay;
    @FXML private TextField yEntityDisplay;
    @FXML private TextField rotationEntityDisplay;
    @FXML private TextField idEntityDisplay;
    
    @FXML private void A_ListView_OnClicked(MouseEvent event) {
        var a = entityListView.getSelectionModel().getSelectedItems();
        if (a.isEmpty()) return;
        var entity = entityListView.getSelectionModel().getSelectedItems().get(0);
        
        nameEntityDisplay.setText(entity.getType().toString());
        xEntityDisplay.setText(String.valueOf(entity.getSprite().getLayoutX()));
        yEntityDisplay.setText(String.valueOf(entity.getSprite().getLayoutY()));
        rotationEntityDisplay.setText(String.valueOf(entity.getSprite().getRotate()));
        idEntityDisplay.setText(String.valueOf(entity.getID()));
    }
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
        addEntityToMap(airportListView, createEntityAtXY(new Airport(), x, y));
    }
    //endregion
    //endregion
    //endregion
    
    //region [Testing]
    private <T extends Entity> void addEntityToMap(ListView<T> listView, T entity) {
        listView.getItems().add(entity);
        this.mapController.addToMap(entity);
    }
    
    @FXML private TextField xEntityDisplayTest;
    @FXML private TextField yEntityDisplayTest;
    
    @FXML private Button buttonCreateAtSpecifiedCircle;
    @FXML private Button buttonCreateAtRandomTest;
    
    private <T extends Entity> T createEntityAtXY(T entity, double x, double y) {
        entity.getSprite().setTranslateX(x);
        entity.getSprite().setTranslateY(y);
        entity.getSprite().setUserData(entity.getClass());
        return entity;
    }
    
    @FXML private void buttonCreateAtSpecifiedCircleOnClick(ActionEvent event) {
        var x = Double.parseDouble(this.xEntityDisplayTest.getText());
        var y = Double.parseDouble(this.yEntityDisplayTest.getText());
    }
    
    @FXML private Button buttonMoveToSpecifiedTest;
    
    @FXML private <T extends Entity> void moveTo(ListView<T> listView, double x, double y) {
        // TODO Tidy this up; place inside the Entity directly as motor control
        var entity = (Entity) listView.getSelectionModel().getSelectedItem();
        Path path = new Path();
        
        var transition = new PathTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setPath(path);
        transition.setNode(entity.getSprite());
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        
        MoveTo start = new MoveTo(
                entity.getSprite().getTranslateX() + entity.getType().getOrigin().getX(),
                entity.getSprite().getTranslateY() + entity.getType().getOrigin().getY());
        path.getElements().add(start);
        
        if (Set.of(Entity.TYPE.CIVILIANPLANE, Entity.TYPE.MILITARYPLANE).contains(entity.getType())) {
            var p1 = new Point2D(entity.getSprite().getTranslateX(), entity.getSprite().getTranslateY());
            var p2 = new Point2D(x, y);
            var radius = Math.abs(p2.getX() - p1.getX());
            
            ArcTo end = new ArcTo();
            end.setX(p2.getX());
            end.setY(p2.getY());
            end.setRadiusX(radius);
            end.setRadiusY(radius / 3);
            
            path.getElements().add(end);
        } else { path.getElements().add(new LineTo(x, y)); }
        
        
        this.mapController.getMap().getChildren().add(path);
        transition.setOnFinished(event -> this.mapController.getMap().getChildren().remove(path));
        transition.play();
    }
    
    @FXML private void button_CreateAtRandomAirport_OnClick(ActionEvent event) {
        var rand = new Random();
        var x = (double) rand.nextInt(
                (int) mapController.getMap().getLayoutBounds().getWidth());
        var y = (double) rand.nextInt(
                (int) mapController.getMap().getLayoutBounds().getHeight());
        this.mapController.addToMap();
        this.mapController.getMap().getChildren().add(createCircleAtXY(x, y));
    }
    
    @FXML private void button_MoveToSpecified_OnClick(ActionEvent event) {
        var x = Double.parseDouble(xEntityDisplayTest.getText());
        var y = Double.parseDouble(yEntityDisplayTest.getText());
        moveTo(x, y);
    }
    
    @FXML private void buttonMoveToRandomOnClick(ActionEvent event) {
        Random rand = new Random();
        var x = (double) rand.nextInt(
                (int) this.mapController.getMap().getLayoutBounds().getWidth());
        var y = (double) rand.nextInt(
                (int) this.mapController.getMap().getLayoutBounds().getHeight());
        moveTo(x, y);
    }
    
    @FXML private void buttonMoveToSpecifiedAirportOnClick(ActionEvent event) {
        // TODO Proper Circle Selection
        var circle = mapController.getRandomCircle();
        if (circle.isEmpty()) {return;}
        var x = circle.get().getLayoutX();
        var y = circle.get().getLayoutY();
        
        moveTo(x, y);
    }
    
    @FXML private void buttonMoveToRandomAirportOnClick(ActionEvent event) {
        var circle = mapController.getRandomCircle();
        if (circle.isEmpty()) {return;}
        var x = circle.get().getLayoutX();
        var y = circle.get().getLayoutY();
        
        moveTo(x, y);
    }
    
    @FXML private Button buttonMoveToRandomTest;
    @FXML private Button buttonMoveToCircleRandomTest;
    @FXML private Button buttonMoveToCircleSpecifiedTest;
    //endregion
    
    public void init(MapController mapController) { this.mapController = mapController; }
    
    @Override public void initialize(URL location, ResourceBundle resources) {
    }
}
