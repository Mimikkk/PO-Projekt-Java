import classes.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unused") public class ControlPanelController implements Initializable {
    private MapController mapController;
    private final Random random = new Random();
    private final ArrayList<Integer> ids = new ArrayList<>();
    
    @FXML private ListView<Airport> airportListView;
    @FXML private ListView<CivilianPlane> planeCivilianListView;
    @FXML private ListView<MilitaryPlane> planeMilitaryListView;
    @FXML private ListView<CivilianShip> shipCivilianListView;
    @FXML private ListView<MilitaryShip> shipMilitaryListView;
    
    //region [Main Pages]
    
    //region [Data]
    //region [Civilian Planes]
    @FXML private TextField textField_CivilianPlane_xDisplay;
    @FXML private TextField textField_CivilianPlane_yDisplay;
    @FXML private TextField textField_CivilianPlane_idDisplay;
    @FXML private TextField textField_CivilianPlane_currentDestinationDisplay;
    @FXML private TextField textField_CivilianPlane_currentAirportDisplay;
    @FXML private TextField textField_CivilianPlane_currentPassengerDisplay;
    @FXML private TextField textField_CivilianPlane_currentStatusDisplay;
    @FXML private SVGPath svgPath_CivilianPlane_IconDisplay;
    @FXML private Button button_CivilianPlane_Remove;
    
    @FXML private void button_CivilianPlaneListView_OnClicked(MouseEvent event) {
        var entity = planeCivilianListView.getSelectionModel().getSelectedItem();
        if (entity == null) return;

        textField_CivilianPlane_xDisplay.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_CivilianPlane_yDisplay.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_CivilianPlane_idDisplay.setText(String.valueOf(entity.getID()));
    
        textField_CivilianPlane_currentDestinationDisplay.setText(entity.getDestination().toString());
        textField_CivilianPlane_currentStatusDisplay.setText(entity.getStatus());
        textField_CivilianPlane_currentAirportDisplay.setText(
                        (entity.getLocation() != null) ? entity.getLocation().toString() : "W powietrzu"
        );

        textField_CivilianPlane_currentPassengerDisplay.setText(
                entity.getPassengerCount() != 0 ? String.valueOf(entity.getPassengerCount()) : "Ładuje..."
        );

        var icon = svgPath_CivilianPlane_IconDisplay;
        icon.setContent(entity.getSprite().getContent());
        icon.setTranslateX(45 - entity.getType().getOrigin().getX());
        icon.setTranslateY(45 - entity.getType().getOrigin().getY());
        icon.setFill(entity.getSprite().getFill());
    }
    //endregion
    //region [Military Planes]
    //endregion
    //region [Civilian Ships]
    //endregion
    //region [Military Ships]
    //endregion
    //region [Airports]
    @FXML private TextField textField_Airport_xDisplay;
    @FXML private TextField textField_Airport_yDisplay;
    @FXML private TextField textField_Airport_idDisplay;
    @FXML private TextField textField_Airport_sizeDisplay;
    @FXML private SVGPath svgPath_Airport_IconDisplay;
    @FXML private Button button_Airport_Remove;
    
    
    @FXML ListView<CivilianPlane> listView_Airport_cargoDisplay;
    @FXML private TextField textField_Airport_capacityCurrentDisplay;
    @FXML private TextField textField_Airport_capacityMaxDisplay;
    @FXML private void button_AirportListView_OnClicked(MouseEvent event) {
        var entity = airportListView.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        
        textField_Airport_sizeDisplay.setText(entity.getSize().toString());
        textField_Airport_xDisplay.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_Airport_yDisplay.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_Airport_capacityCurrentDisplay.setText(String.valueOf(entity.getPlanes().size()));
        textField_Airport_capacityMaxDisplay.setText(String.valueOf(entity.getSize().getCapacity()));
        listView_Airport_cargoDisplay.setItems(FXCollections.observableList(entity.getPlanes()));
        textField_Airport_idDisplay.setText(String.valueOf(entity.getID()));
        
        var icon = svgPath_Airport_IconDisplay;
        icon.setContent(entity.getSprite().getContent());
        icon.setScaleX(entity.getSize().getSizeMultiplier());
        icon.setScaleY(entity.getSize().getSizeMultiplier());
        icon.setTranslateX(45 - entity.getType().getOrigin().getX());
        icon.setTranslateY(45 - entity.getType().getOrigin().getY());
        icon.setFill(entity.getSprite().getFill());
    }
    @FXML private void button_Airport_Remove_OnClicked(ActionEvent event) {
        var entity = airportListView.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        
        textField_Airport_sizeDisplay.clear();
        textField_Airport_xDisplay.clear();
        textField_Airport_yDisplay.clear();
        textField_Airport_capacityCurrentDisplay.clear();
        textField_Airport_capacityMaxDisplay.clear();
        textField_Airport_idDisplay.clear();
        listView_Airport_cargoDisplay.setItems(FXCollections.emptyObservableList());
        svgPath_Airport_IconDisplay.setContent("");
        
        this.airportListView.getItems().remove(entity);
        this.mapController.removeFromMap(entity);
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
        addEntityToMap(planeCivilianListView, new CivilianPlane(ids));
    }
    //endregion
    //region [Military Planes]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddMilitaryPlane;
    @FXML private void button_AddMilitaryPlane_OnClick(ActionEvent event) {
        addEntityToMap(planeMilitaryListView, new MilitaryPlane(ids));
    }
    //endregion
    //region [Civilian Ships]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddCivilianShip;
    @FXML private void button_AddCivilianShip_OnClick(ActionEvent event) {
        addEntityToMap(shipCivilianListView, new CivilianShip(ids));
    }
    //endregion
    //region [Military Ships]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddMilitaryShip;
    @FXML private SIZE MilitaryShip_getSize() {return SIZE.MEDIUM;}
    @FXML private void button_AddMilitaryShip_OnClick(ActionEvent event) {
        addEntityToMap(shipMilitaryListView, new MilitaryShip(MilitaryShip_getSize(), ids));
    }
    //endregion
    //region [Airport]
    // TODO Like Civilian Plane
    @FXML private Button button_Airport_Add;
    @FXML private Button button_Airport_SelectRandomCoords;
    @FXML private Button button_Airport_SelectRandomName;
    
    @FXML TextField textField_Airport_x;
    @FXML TextField textField_Airport_y;
    @FXML TextField textField_Airport_name;
    
    @FXML ToggleGroup toggleGroup_Airport_size;
    @FXML RadioButton radioButton_Airport_smallSize;
    @FXML RadioButton radioButton_Airport_mediumSize;
    @FXML RadioButton radioButton_Airport_bigSize;
    
    private SIZE Airport_getSize() {
        if (radioButton_Airport_smallSize.isSelected()) return SIZE.SMALL;
        if (radioButton_Airport_mediumSize.isSelected()) return SIZE.MEDIUM;
        return SIZE.BIG;
    }
    @FXML private void button_Airport_Add_OnClick(ActionEvent event) {
        var entity = new Airport(Airport_getSize(), ids);
        int x = (int) Math.max(0, Math.min(Double.parseDouble(textField_Airport_x.getText()),
                this.mapController.getMap().getLayoutBounds().getWidth()
                        - entity.getType().getOrigin().getX()));
        int y = (int) Math.max(0, Math.min(Double.parseDouble(textField_Airport_y.getText()),
                this.mapController.getMap().getLayoutBounds().getHeight()
                        - entity.getType().getOrigin().getY()));
        
        addEntityToMap(airportListView, createEntityAtXY(entity, x, y));
    }
    @FXML private void button_Airport_SelectRandomName_OnClick(ActionEvent event) {
        this.textField_Airport_name.setText(
                random.ints(97, 123).limit(12).parallel()
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString()
        );
    }
    @FXML private void button_Airport_SelectRandomCoords_OnClick(ActionEvent event) {
        var entity = new Airport(Airport_getSize(), ids);
        int x = (int) (entity.getType().getOrigin().getX() + random.nextInt(
                (int) (this.mapController.getMap().getLayoutBounds().getWidth()
                        - 2 * entity.getType().getOrigin().getX())));
        int y = (int) (entity.getType().getOrigin().getX() + random.nextInt(
                (int) (this.mapController.getMap().getLayoutBounds().getHeight()
                        - 2 * entity.getType().getOrigin().getY())));
        
        textField_Airport_x.setText(String.valueOf(x));
        textField_Airport_y.setText(String.valueOf(y));
    }
    //endregion
    //endregion
    //endregion
    
    private <T extends Entity> void addEntityToMap(ListView<T> listView, T entity) {
        listView.getItems().add(entity);
        this.mapController.addToMap(entity);
    }
    private <T extends Entity> T createEntityAtXY(T entity, double x, double y) {
        entity.getSprite().setOnMouseClicked(e -> {
            if (entity instanceof MilitaryPlane) {
                planeMilitaryListView.getSelectionModel().select((MilitaryPlane) entity);
                button_AirportListView_OnClicked(e);
            } else if (entity instanceof CivilianPlane) {
                planeCivilianListView.getSelectionModel().select((CivilianPlane) entity);
                button_AirportListView_OnClicked(e);
            } else if (entity instanceof CivilianShip) {
                shipCivilianListView.getSelectionModel().select((CivilianShip) entity);
                button_AirportListView_OnClicked(e);
            } else if (entity instanceof MilitaryShip) {
                shipMilitaryListView.getSelectionModel().select((MilitaryShip) entity);
                button_AirportListView_OnClicked(e);
            } else if (entity instanceof Airport) {
                airportListView.getSelectionModel().select((Airport) entity);
                button_AirportListView_OnClicked(e);
            }
        });
        var sprite = entity.getSprite();
        Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "Nazwa: "
                        + entity.getType().
                        name()
                        + "\nPozycja: "
                        + "x: " + sprite.getTranslateX()
                        + ", y: " + sprite.getTranslateY() + "\nID: " + entity.getID());
        Tooltip.install(entity.getSprite(), tooltip);
        
        sprite.hoverProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (observable.getValue().booleanValue()) {
                        tooltip.show(sprite,
                                sprite.getTranslateX()
                                        + sprite.getScene().getX()
                                        + sprite.getScene().getWindow().getX(),
                                sprite.getTranslateY()
                                        + sprite.getScene().getY()
                                        + sprite.getScene().getWindow().getY()
                                        + 4 * entity.getType().getOrigin().getY());
                    } else { tooltip.hide(); }
                });
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
        sprite.setUserData(entity.getClass());
        return entity;
    }
    @FXML private <T extends Vehicle> void moveTo(ListView<T> listView, double x, double y) {
        var entity = listView.getSelectionModel().getSelectedItem();
        var path = entity.moveTo(x, y);
        
        this.mapController.getMap().getChildren().add(path);
        entity.getTransition().setOnFinished(event -> this.mapController.getMap().getChildren().remove(path));
        entity.getTransition().play();
    }
    
    
    @FXML private void menu_closeTab_OnAction(ActionEvent event) {
        // Doesn't matter what i use, can't get from event action tho, so I use the first thing I can
        this.button_Airport_Add.getScene().getWindow().hide();
    }
    
    private void initializeTextFields() {
        Supplier<TextFormatter<Integer>> textFormatter = () -> {
            StringConverter<Integer> converter = new IntegerStringConverter() {
                @Override public Integer fromString(String s) {
                    if (s.isEmpty()) return 0;
                    return super.fromString(s);
                }
            };
            return new TextFormatter<>(converter, 0, change -> {
                if (change.getControlNewText().matches("([1-9][0-9]*)?")) {
                    return change;
                }
                return null;
            });
        };
        this.textField_Airport_x.setTextFormatter(textFormatter.get());
        this.textField_Airport_y.setTextFormatter(textFormatter.get());
    }
    public void init(MapController mapController) { this.mapController = mapController; }
    @Override public void initialize(URL location, ResourceBundle resources) {
        initializeTextFields();
    }
}
