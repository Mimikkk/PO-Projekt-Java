import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Supplier;

@SuppressWarnings("unused") public class ControlPanelController implements Initializable {
    private MapController mapController;
    private final Random random = new Random();
    
    //region [Main Pages]
    
    //region [Data]
    
    //region [Civilian Planes]
    @FXML private ListView<CivilianPlane> listView_CivilianPlaneDisplay;
    @FXML private TextField textField_CivilianPlaneDisplay_x;
    @FXML private TextField textField_CivilianPlaneDisplay_y;
    @FXML private TextField textField_CivilianPlaneDisplay_id;
    @FXML private TextField textField_CivilianPlaneDisplay_size;
    
    @FXML private TextField textField_CivilianPlaneDisplay_currentDestination;
    @FXML private TextField textField_CivilianPlaneDisplay_currentPassenger;
    @FXML private TextField textField_CivilianPlaneDisplay_maxCapacity;
    @FXML private SVGPath svgPath_CivilianPlaneDisplay_icon;
    @FXML private Button button_CivilianPlaneDisplay_Remove;
    
    @FXML private ListView<Airport> listView_CivilianPlaneDisplay_Path;
    @FXML private void button_CivilianPlaneListViewDisplay_OnClicked(MouseEvent event) {
        var entity = listView_CivilianPlaneDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        
        textField_CivilianPlaneDisplay_x.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_CivilianPlaneDisplay_y.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_CivilianPlaneDisplay_id.setText(String.valueOf(entity.getID()));
        textField_CivilianPlaneDisplay_size.setText(entity.getSize().name());
        
        textField_CivilianPlaneDisplay_currentDestination.setText(
                (entity.getLocation() != null) ? entity.getLocation().toString() : "W powietrzu"
        );
        
        textField_CivilianPlaneDisplay_currentPassenger.setText(
                entity.getPassengerCount() != 0 ? String.valueOf(entity.getPassengerCount()) : "Ładuje..."
        );
        textField_CivilianPlaneDisplay_maxCapacity.setText(String.valueOf(entity.getMaxPassengerCount()));
        listView_CivilianPlaneDisplay_Path.setItems(entity.getPath());
        
        var icon = svgPath_CivilianPlaneDisplay_icon;
        icon.setContent(entity.getSprite().getContent());
        icon.setScaleX(entity.getSize().getSizeMultiplier());
        icon.setScaleY(entity.getSize().getSizeMultiplier());
        icon.setTranslateX(45 - entity.getType().getOrigin().getX());
        icon.setTranslateY(45 - entity.getType().getOrigin().getY());
        icon.setFill(entity.getSprite().getFill());
    }
    @FXML private void button_CivilianPlane_Remove_OnClicked(ActionEvent event) {
        var entity = listView_CivilianPlaneDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        
        textField_CivilianPlaneDisplay_size.clear();
        textField_CivilianPlaneDisplay_x.clear();
        textField_CivilianPlaneDisplay_y.clear();
        textField_CivilianPlaneDisplay_id.clear();
        textField_CivilianPlaneDisplay_currentDestination.clear();
        textField_CivilianPlaneDisplay_currentPassenger.clear();
        textField_CivilianPlaneDisplay_maxCapacity.clear();
        listView_CivilianPlaneDisplay_Path.setItems(FXCollections.emptyObservableList());
        svgPath_CivilianPlaneDisplay_icon.setContent("");
        
        entity.getLocation().getPlanes().remove(entity);
        listView_CivilianPlaneDisplay.getItems().remove(entity);
        mapController.removeFromMap(entity);
    }
    //endregion
    
    //region [Military Planes]
    @FXML private ListView<MilitaryPlane> listView_MilitaryPlaneDisplay;
    //endregion
    
    //region [Civilian Ships]
    @FXML private ListView<CivilianShip> listView_CivilianShipDisplay;
    //endregion
    
    //region [Military Ships]
    @FXML private ListView<MilitaryShip> listView_MilitaryShipDisplay;
    //endregion
    
    //region [Airports]
    @FXML private ListView<Airport> listView_AirportDisplay;
    @FXML private TextField textField_AirportDisplay_x;
    @FXML private TextField textField_AirportDisplay_y;
    @FXML private TextField textField_AirportDisplay_id;
    @FXML private TextField textField_AirportDisplay_size;
    @FXML private SVGPath svgPath_AirportDisplay_icon;
    @FXML private Button button_AirportDisplay_Remove;
    
    
    @FXML ListView<CivilianPlane> listView_AirportDisplay_cargo;
    @FXML private TextField textField_AirportDisplay_capacityCurrent;
    @FXML private TextField textField_AirportDisplay_capacityMax;
    @FXML private void button_AirportListViewDisplay_OnClicked(MouseEvent event) {
        var entity = listView_AirportDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        
        textField_AirportDisplay_size.setText(entity.getSize().toString());
        textField_AirportDisplay_x.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_AirportDisplay_y.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_AirportDisplay_capacityCurrent.setText(String.valueOf(entity.getPlanes().size()));
        textField_AirportDisplay_capacityMax.setText(String.valueOf(entity.getSize().getCapacity()));
        listView_AirportDisplay_cargo.setItems(FXCollections.observableList(entity.getPlanes()));
        textField_AirportDisplay_id.setText(String.valueOf(entity.getID()));
        
        var icon = svgPath_AirportDisplay_icon;
        icon.setContent(entity.getSprite().getContent());
        icon.setScaleX(entity.getSize().getSizeMultiplier());
        icon.setScaleY(entity.getSize().getSizeMultiplier());
        icon.setTranslateX(45 - entity.getType().getOrigin().getX());
        icon.setTranslateY(45 - entity.getType().getOrigin().getY());
        icon.setFill(entity.getSprite().getFill());
    }
    @FXML private void button_Airport_Remove_OnClicked(ActionEvent event) {
        var entity = listView_AirportDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;
        
        textField_AirportDisplay_size.clear();
        textField_AirportDisplay_x.clear();
        textField_AirportDisplay_y.clear();
        textField_AirportDisplay_capacityCurrent.clear();
        textField_AirportDisplay_capacityMax.clear();
        textField_AirportDisplay_id.clear();
        listView_AirportDisplay_cargo.setItems(FXCollections.emptyObservableList());
        svgPath_AirportDisplay_icon.setContent("");
        
        listView_AirportDisplay.getItems().remove(entity);
        mapController.removeFromMap(entity);
    }
    //endregion
    //endregion
    
    //region [Constructor]
    
    //region [Civilian Planes]
    @FXML private ChoiceBox<Airport> choiceBox_CivilianPlane_selectedAirport;
    @FXML private ToggleGroup toggleGroup_CivilianPlane_size;
    @FXML private RadioButton radioButton_CivilianPlane_small;
    @FXML private RadioButton radioButton_CivilianPlane_medium;
    @FXML private RadioButton radioButton_CivilianPlane_big;
    
    @FXML private Button button_CivilianPlane_AddToAirport;
    @FXML private Button button_CivilianPlane_SelectRandomAirport;
    @FXML private Button button_CivilianPlane_CreateRandomPath;
    @FXML private Button button_CivilianPlane_AddSelectedToPath;
    @FXML private Button button_CivilianPlane_RemoveSelectedFromPath;
    
    @FXML private ListView<Airport> listView_CivilianPlane_Path;
    @FXML private void button_CivilianPlane_SelectRandomAirport_OnClick(ActionEvent event) {
        var size = Database.getAirports().size();
        if (size < 1) return;
        choiceBox_CivilianPlane_selectedAirport.getSelectionModel().select(random.nextInt(size));
    }
    @FXML private void button_CivilianPlane_CreateRandomPath_OnClick(ActionEvent event) {
        var size = Database.getAirports().size();
        if (size < 2) return;
        
        var ref = new Object() {
            int prev = -1;
            int i;
        };
        listView_CivilianPlane_Path.getItems().clear();
        random.ints(2 + random.nextInt(size * 2 - 2)).forEach(ignored -> {
            do {ref.i = random.nextInt(size);} while (ref.i == ref.prev);
            ref.prev = ref.i;
            listView_CivilianPlane_Path.getItems().add(
                    Database.getAirports().get(ref.i)
            );
        });
    }
    @FXML private void button_CivilianPlane_AddSelectedToPath_OnClick(ActionEvent event) {
        var airport = this.choiceBox_CivilianPlane_selectedAirport.getValue();
        if (airport == null
                || (!this.listView_CivilianPlane_Path.getItems().isEmpty()
                && airport == this.listView_CivilianPlane_Path.getItems()
                .get(this.listView_CivilianPlane_Path.getItems().size() - 1))) return;
        this.listView_CivilianPlane_Path.getItems().add(airport);
    }
    
    private SIZE CivilianPlane_getSize() {
        if (radioButton_CivilianPlane_small.isSelected()) return SIZE.SMALL;
        if (radioButton_CivilianPlane_medium.isSelected()) return SIZE.MEDIUM;
        return SIZE.BIG;
    }
    
    @FXML private void button_CivilianPlane_RemoveSelectedFromPath_OnClick(ActionEvent event) {
        this.listView_CivilianPlane_Path.getItems().remove(
                this.listView_CivilianPlane_Path.getSelectionModel().getSelectedItem());
    }
    @FXML private void button_CivilianPlane_AddToAirport_OnClick(ActionEvent event) {
        if (listView_CivilianPlane_Path.getItems().size() < 2) return;
        
        var size = CivilianPlane_getSize();
        ObservableList<Airport> path = FXCollections.observableArrayList();
        path.addAll(listView_CivilianPlane_Path.getItems());
        var plane = new CivilianPlane(size, path);
        
        addEntityToMap(plane);
        listView_CivilianPlane_Path.getItems().get(0).getPlanes().add(plane);
        listView_CivilianPlane_Path.getItems().clear();
    }
    //endregion
    
    //region [Military Planes]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddMilitaryPlane;
    @FXML private void button_AddMilitaryPlane_OnClick(ActionEvent event) {
        addEntityToMap(new MilitaryPlane());
    }
    //endregion
    
    //region [Civilian Ships]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddCivilianShip;
    @FXML private void button_AddCivilianShip_OnClick(ActionEvent event) {
        addEntityToMap(new CivilianShip());
    }
    //endregion
    
    //region [Military Ships]
    // TODO Like Civilian Plane
    @FXML private Button buttonAddMilitaryShip;
    @FXML private SIZE MilitaryShip_getSize() {return SIZE.MEDIUM;}
    @FXML private void button_AddMilitaryShip_OnClick(ActionEvent event) {
        addEntityToMap(new MilitaryShip(MilitaryShip_getSize()));
    }
    //endregion
    
    //region [Airport]
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
        var entity = new Airport(Airport_getSize());
        int x = (int) Math.max(0, Math.min(Double.parseDouble(textField_Airport_x.getText()),
                this.mapController.getMap().getLayoutBounds().getWidth()
                        - entity.getType().getOrigin().getX()));
        int y = (int) Math.max(0, Math.min(Double.parseDouble(textField_Airport_y.getText()),
                this.mapController.getMap().getLayoutBounds().getHeight()
                        - entity.getType().getOrigin().getY()));
        
        addEntityToMap(createEntityAtXY(entity, x, y));
    }
    private String createRandomString() {
        return random.ints(97, 123).limit(12).parallel()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    private Point2D createRandomXYInBounds(Point2D origin) {
        int x = (int) (origin.getX() + random.nextInt(
                (int) (this.mapController.getMap().getLayoutBounds().getWidth() - 5 * origin.getX())));
        int y = (int) (origin.getY() + random.nextInt(
                (int) (this.mapController.getMap().getLayoutBounds().getHeight() - 5 * origin.getY())));
        return new Point2D(x, y);
    }
    
    @FXML private void button_Airport_SelectRandomName_OnClick(ActionEvent event) {
        this.textField_Airport_name.setText(createRandomString());
    }
    @FXML private void button_Airport_SelectRandomCoords_OnClick(ActionEvent event) {
        var point = createRandomXYInBounds(Entity.TYPE.AIRPORT.getOrigin());
        textField_Airport_x.setText(String.valueOf((int) point.getX()));
        textField_Airport_y.setText(String.valueOf((int) point.getY()));
    }
    //endregion
    //endregion
    //endregion
    
    private <T extends Entity> void addEntityToMap(T entity) {
        // I hate it.
        if (entity instanceof Airport) {
            Database.getAirports().add((Airport) entity);
        } else if (entity instanceof CivilianPlane) {
            Database.getCivilianPlanes().add((CivilianPlane) entity);
        } else if (entity instanceof MilitaryPlane) {
            Database.getMilitaryPlanes().add((MilitaryPlane) entity);
        } else if (entity instanceof CivilianShip) {
            Database.getCivilianShips().add((CivilianShip) entity);
        } else if (entity instanceof MilitaryShip) {
            Database.getMilitaryShips().add((MilitaryShip) entity);
        }
        this.mapController.addToMap(entity);
    }
    
    private <T extends Entity> T createEntityAtXY(T entity, double x, double y) {
        entity.getSprite().setOnMouseClicked(e -> {
            if (entity instanceof MilitaryPlane) {
                listView_MilitaryPlaneDisplay.getSelectionModel().select((MilitaryPlane) entity);
                button_AirportListViewDisplay_OnClicked(e);
            } else if (entity instanceof CivilianPlane) {
                listView_CivilianPlaneDisplay.getSelectionModel().select((CivilianPlane) entity);
                button_AirportListViewDisplay_OnClicked(e);
            } else if (entity instanceof CivilianShip) {
                listView_CivilianShipDisplay.getSelectionModel().select((CivilianShip) entity);
                button_CivilianPlaneListViewDisplay_OnClicked(e);
            } else if (entity instanceof MilitaryShip) {
                listView_MilitaryShipDisplay.getSelectionModel().select((MilitaryShip) entity);
                button_AirportListViewDisplay_OnClicked(e);
            } else if (entity instanceof Airport) {
                listView_AirportDisplay.getSelectionModel().select((Airport) entity);
                button_AirportListViewDisplay_OnClicked(e);
            }
        });
        entity.getSprite().setTranslateX(x);
        entity.getSprite().setTranslateY(y);
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
    
    //region [Initialization]
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
    private void initializeListViews() {
        listView_AirportDisplay.setItems(Database.getAirports());
        listView_CivilianPlaneDisplay.setItems(Database.getCivilianPlanes());
        listView_MilitaryPlaneDisplay.setItems(Database.getMilitaryPlanes());
        listView_CivilianShipDisplay.setItems(Database.getCivilianShips());
        listView_MilitaryShipDisplay.setItems(Database.getMilitaryShips());
    }
    
    private void initializeChoiceBoxes() {
        choiceBox_CivilianPlane_selectedAirport.setItems(Database.getAirports());
    }
    
    public void init(MapController mapController) { this.mapController = mapController; }
    @Override public void initialize(URL location, ResourceBundle resources) {
        initializeChoiceBoxes();
        initializeTextFields();
        initializeListViews();
    }
    //endregion
}
