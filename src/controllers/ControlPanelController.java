package controllers;

import classes.*;
import enums.FIRM;
import enums.SIZE;
import enums.WEAPON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

/**
 * Kontroler przetrzymujący wszytkie informację o przyciskach, polach tekstowych i innych podmiotach związanych z obsługą
 * interfejsu oraz symulacją
 */
@SuppressWarnings("unused")
public class ControlPanelController implements Initializable {
    private MapController mapController;
    private final Random random = new Random();
    //region [Main Pages]

    private <T extends Entity> boolean utils_hasIslandCollision(Point2D point) {
        return mapController.getIslandColliders().stream().anyMatch(x -> x.contains(point.subtract(new Point2D(x.getLayoutX(), x.getLayoutY()))));
    }

    //region [Data]

    private <T extends Entity> void utils_setupIcon(SVGPath icon, T based) {
        icon.setContent(based.getSprite().getContent());
        icon.setTranslateX(45 - based.getType().getOrigin().getX());
        icon.setTranslateY(45 - based.getType().getOrigin().getY());
        icon.setFill(based.getSprite().getFill());
        icon.setScaleX(based.getSprite().getScaleX());
        icon.setScaleY(based.getSprite().getScaleY());
    }

    private <T extends Entity> double utils_findDistance(T a, T b) {
        var p1 = new Point2D(a.getSprite().getTranslateX(), a.getSprite().getTranslateY());
        var p2 = new Point2D(b.getSprite().getTranslateX(), b.getSprite().getTranslateY());
        return p1.distance(p2);
    }

    private <T extends Entity, Y extends Entity> T utils_findClosestToList(ObservableList<T> list, Y searched) {
        return list.parallelStream().min(Comparator.comparing((x) -> utils_findDistance(x, searched))).orElseThrow();
    }

    //region [Civilian Planes]
    @FXML
    private ListView<CivilianPlane> listView_CivilianPlaneDisplay;
    @FXML
    private TextField textField_CivilianPlaneDisplay_x;
    @FXML
    private TextField textField_CivilianPlaneDisplay_y;
    @FXML
    private TextField textField_CivilianPlaneDisplay_id;
    @FXML
    private TextField textField_CivilianPlaneDisplay_size;

    @FXML
    private TextField textField_CivilianPlaneDisplay_currentDestination;
    @FXML
    private TextField textField_CivilianPlaneDisplay_currentPassenger;
    @FXML
    private TextField textField_CivilianPlaneDisplay_maxCapacity;
    @FXML
    private SVGPath svgPath_CivilianPlaneDisplay_icon;
    @FXML
    private Button button_CivilianPlaneDisplay_Remove;
    @FXML
    private Button button_CivilianPlaneDisplay_ChangePath;
    @FXML
    private Button button_CivilianPlaneDisplay_EmergencyLanding;

    @FXML
    private ListView<Airport> listView_CivilianPlaneDisplay_Path;

    @FXML
    private void button_CivilianPlaneListViewDisplay_OnClicked(MouseEvent event) {
        var entity = listView_CivilianPlaneDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;

        textField_CivilianPlaneDisplay_x.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_CivilianPlaneDisplay_y.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_CivilianPlaneDisplay_id.setText(String.valueOf(entity.getID()));
        textField_CivilianPlaneDisplay_size.setText(entity.getSize().name());

        textField_CivilianPlaneDisplay_currentDestination.setText(
                (entity.getDestination() != null) ? entity.getDestination().toString() : "W powietrzu"
        );

        textField_CivilianPlaneDisplay_currentPassenger.setText(
                entity.getPassengerCount() != 0 ? String.valueOf(entity.getPassengerCount()) : "Ładuje..."
        );
        textField_CivilianPlaneDisplay_maxCapacity.setText(String.valueOf(entity.getMaxPassengerCount()));
        listView_CivilianPlaneDisplay_Path.setItems(entity.getPath());
        utils_setupIcon(svgPath_CivilianPlaneDisplay_icon, entity);
    }

    @FXML
    private void button_CivilianPlane_Remove_OnClicked(ActionEvent event) {
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

        entity.getDestination().getPlanes().remove(entity);
        listView_CivilianPlaneDisplay.getItems().remove(entity);
        mapController.removeFromMap(entity.getSprite());
        entity.setRunning(false);
    }

    @FXML
    private void button_CivilianPlaneDisplay_EmergencyLanding_OnClicked(ActionEvent event) {
        var plane = listView_CivilianPlaneDisplay.getSelectionModel().getSelectedItem();
        var airport = utils_findClosestToList(Database.getAirports(), plane);
        plane.setDestination(airport);
    }

    @FXML
    private void button_CivilianPlaneDisplay_ChangePath_OnClicked(ActionEvent event) {
        var path = listView_CivilianPlaneDisplay.getSelectionModel().getSelectedItem().getPath();
        utils_generatePath(path, Database.getAirports());
    }
    //endregion

    //region [Military Planes]
    @FXML
    private ListView<MilitaryPlane> listView_MilitaryPlaneDisplay;
    @FXML
    private TextField textField_MilitaryPlaneDisplay_x;
    @FXML
    private TextField textField_MilitaryPlaneDisplay_y;
    @FXML
    private TextField textField_MilitaryPlaneDisplay_id;
    @FXML
    private TextField textField_MilitaryPlaneDisplay_weapon;
    @FXML
    private TextField textField_MilitaryPlaneDisplay_destination;
    @FXML
    private TextField textField_MilitaryPlaneDisplay_fuel;
    @FXML
    private SVGPath svgPath_MilitaryPlaneDisplay_icon;
    @FXML
    private Button button_MilitaryPlaneDisplay_Remove;
    @FXML
    private Button button_MilitaryPlaneDisplay_ChangePath;
    @FXML
    private Button button_MilitaryPlaneDisplay_EmergencyLanding;

    @FXML
    private ListView<MilitaryShip> listView_MilitaryPlaneDisplay_path;

    @FXML
    private void button_MilitaryPlaneListViewDisplay_OnClicked(MouseEvent event) {
        var entity = listView_MilitaryPlaneDisplay.getSelectionModel().getSelectedItem();

        textField_MilitaryPlaneDisplay_x.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_MilitaryPlaneDisplay_y.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_MilitaryPlaneDisplay_id.setText(String.valueOf(entity.getID()));
        textField_MilitaryPlaneDisplay_weapon.setText(String.valueOf(entity.getWeapon()));
        textField_MilitaryPlaneDisplay_destination.setText(String.valueOf(entity.getDestination()));
        textField_MilitaryPlaneDisplay_fuel.setText(String.valueOf(entity.getFuel()));
        listView_MilitaryPlaneDisplay_path.setItems(entity.getPath());
        utils_setupIcon(svgPath_MilitaryPlaneDisplay_icon, entity);
    }

    @FXML
    private void button_MilitaryPlaneDisplay_Remove_OnClicked(ActionEvent event) {
        var entity = listView_MilitaryPlaneDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;

        textField_MilitaryPlaneDisplay_x.clear();
        textField_MilitaryPlaneDisplay_y.clear();
        textField_MilitaryPlaneDisplay_id.clear();
        textField_MilitaryPlaneDisplay_weapon.clear();
        textField_MilitaryPlaneDisplay_fuel.clear();
        listView_MilitaryPlaneDisplay_path.setItems(FXCollections.emptyObservableList());
        svgPath_MilitaryPlaneDisplay_icon.setContent("");

        listView_MilitaryPlaneDisplay.getItems().remove(entity);
        mapController.removeFromMap(entity.getSprite());
    }

    @FXML
    private void button_MilitaryPlaneDisplay_EmergencyLanding_OnClicked(ActionEvent event) {
        var plane = listView_MilitaryPlaneDisplay.getSelectionModel().getSelectedItem();
        var carrier = utils_findClosestToList(Database.getMilitaryShips(), plane);
        plane.setDestination(carrier);
    }

    @FXML
    private void button_MilitaryPlaneDisplay_ChangePath_OnClicked(ActionEvent event) {
        var path = listView_MilitaryPlaneDisplay.getSelectionModel().getSelectedItem().getPath();
        utils_generatePath(path, Database.getMilitaryShips());
    }
    //endregion

    //region [Civilian Ships]
    @FXML
    private ListView<CivilianShip> listView_CivilianShipDisplay;
    @FXML
    private TextField textField_CivilianShipDisplay_x;
    @FXML
    private TextField textField_CivilianShipDisplay_y;
    @FXML
    private TextField textField_CivilianShipDisplay_id;
    @FXML
    private TextField textField_CivilianShipDisplay_size;
    @FXML
    private TextField textField_CivilianShipDisplay_firm;
    @FXML
    private TextField textField_CivilianShipDisplay_capacity;
    @FXML
    private TextField textField_CivilianShipDisplay_maxCapacity;
    @FXML
    private SVGPath svgPath_CivilianShipDisplay_icon;
    @FXML
    private Button button_CivilianShipDisplay_Remove;

    @FXML
    private void button_CivilianShipListViewDisplay_OnClicked(MouseEvent event) {
        var entity = listView_CivilianShipDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;

        textField_CivilianShipDisplay_x.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_CivilianShipDisplay_y.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_CivilianShipDisplay_id.setText(String.valueOf(entity.getID()));
        textField_CivilianShipDisplay_size.setText(String.valueOf(entity.getSize()));
        textField_CivilianShipDisplay_firm.setText(String.valueOf(entity.getFirm()));
        textField_CivilianShipDisplay_capacity.setText(String.valueOf(entity.getPassengerCount()));
        textField_CivilianShipDisplay_maxCapacity.setText(String.valueOf(entity.getMaxPassengerCount()));
        utils_setupIcon(svgPath_CivilianShipDisplay_icon, entity);

        mapController.removeFromMap(entity.getSprite());
    }

    @FXML
    private void button_CivilianShipDisplay_Remove_OnClicked(ActionEvent event) {
        var entity = listView_CivilianShipDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;

        textField_CivilianShipDisplay_x.clear();
        textField_CivilianShipDisplay_y.clear();
        textField_CivilianShipDisplay_id.clear();
        textField_CivilianShipDisplay_size.clear();
        textField_CivilianShipDisplay_firm.clear();
        textField_CivilianShipDisplay_capacity.clear();
        textField_CivilianShipDisplay_maxCapacity.clear();
        listView_AirportDisplay_cargo.setItems(FXCollections.emptyObservableList());
        svgPath_AirportDisplay_icon.setContent("");

        listView_CivilianShipDisplay.getItems().remove(entity);
        mapController.removeFromMap(entity.getSprite());
    }
    //endregion

    //region [Military Ships]
    @FXML
    private ListView<MilitaryShip> listView_MilitaryShipDisplay;
    @FXML
    private TextField textField_MilitaryShipDisplay_x;
    @FXML
    private TextField textField_MilitaryShipDisplay_y;
    @FXML
    private TextField textField_MilitaryShipDisplay_id;
    @FXML
    private TextField textField_MilitaryShipDisplay_weapon;
    @FXML
    private TextField textField_MilitaryShipDisplay_capacity;
    @FXML
    private TextField textField_MilitaryShipDisplay_maxCapacity;
    @FXML
    private SVGPath svgPath_MilitaryShipDisplay_icon;
    @FXML
    private Button button_MilitaryShipDisplay_Remove;

    @FXML
    private ListView<MilitaryPlane> listView_MilitaryShipDisplay_path;

    @FXML
    private void button_MilitaryShipListViewDisplay_OnClicked(MouseEvent event) {
        var ship = listView_MilitaryShipDisplay.getSelectionModel().getSelectedItem();
        if (ship == null) return;

        textField_MilitaryShipDisplay_x.setText(String.valueOf(ship.getSprite().getTranslateX()));
        textField_MilitaryShipDisplay_y.setText(String.valueOf(ship.getSprite().getTranslateY()));
        textField_MilitaryShipDisplay_id.setText(String.valueOf(ship.getID()));
        textField_MilitaryShipDisplay_weapon.setText(String.valueOf(ship.getWeapon()));
        textField_MilitaryShipDisplay_capacity.setText(String.valueOf(ship.getPlanes().size()));
        textField_MilitaryShipDisplay_maxCapacity.setText(String.valueOf(ship.getSize().getCapacity()));
        listView_MilitaryShipDisplay_path.setItems(ship.getPlanes());
        utils_setupIcon(svgPath_MilitaryShipDisplay_icon, ship);
    }

    @FXML
    private void button_MilitaryShipDisplay_Remove_OnClicked(ActionEvent event) {
        var ship = listView_MilitaryShipDisplay.getSelectionModel().getSelectedItem();
        if (ship == null) return;
        Database.getMilitaryPlanes().forEach(plane -> plane.getPath().removeAll(ship));
        Database.getMilitaryPlanes().removeAll(ship.getPlanes());

        textField_MilitaryShipDisplay_x.clear();
        textField_MilitaryShipDisplay_y.clear();
        textField_MilitaryShipDisplay_id.clear();
        textField_MilitaryShipDisplay_weapon.clear();
        textField_MilitaryShipDisplay_capacity.clear();
        textField_MilitaryShipDisplay_maxCapacity.clear();
        listView_MilitaryShipDisplay_path.setItems(FXCollections.emptyObservableList());
        svgPath_MilitaryShipDisplay_icon.setContent("");

        listView_MilitaryShipDisplay.getItems().remove(ship);
        Database.Names.Carriers.remove(ship.getName());
        mapController.removeFromMap(ship.getSprite());
        ship.setRunning(false);
    }
    //endregion

    //region [Airports]
    @FXML
    private ListView<Airport> listView_AirportDisplay;
    @FXML
    private TextField textField_AirportDisplay_x;
    @FXML
    private TextField textField_AirportDisplay_y;
    @FXML
    private TextField textField_AirportDisplay_id;
    @FXML
    private TextField textField_AirportDisplay_size;
    @FXML
    private SVGPath svgPath_AirportDisplay_icon;
    @FXML
    private Button button_AirportDisplay_Remove;


    @FXML
    ListView<CivilianPlane> listView_AirportDisplay_cargo;
    @FXML
    private TextField textField_AirportDisplay_capacityCurrent;
    @FXML
    private TextField textField_AirportDisplay_capacityMax;

    @FXML
    private void button_AirportListViewDisplay_OnClicked(MouseEvent event) {
        var entity = listView_AirportDisplay.getSelectionModel().getSelectedItem();
        if (entity == null) return;

        textField_AirportDisplay_size.setText(entity.getSize().toString());
        textField_AirportDisplay_x.setText(String.valueOf(entity.getSprite().getTranslateX()));
        textField_AirportDisplay_y.setText(String.valueOf(entity.getSprite().getTranslateY()));
        textField_AirportDisplay_capacityCurrent.setText(String.valueOf(entity.getPlanes().size()));
        textField_AirportDisplay_capacityMax.setText(String.valueOf(entity.getSize().getCapacity()));
        listView_AirportDisplay_cargo.setItems(FXCollections.observableList(entity.getPlanes()));
        textField_AirportDisplay_id.setText(String.valueOf(entity.getID()));
        utils_setupIcon(svgPath_AirportDisplay_icon, entity);
    }

    @FXML
    private void button_Airport_Remove_OnClicked(ActionEvent event) {
        var airport = listView_AirportDisplay.getSelectionModel().getSelectedItem();
        if (airport == null) return;
        Database.getCivilianPlanes().forEach(plane -> plane.getPath().removeAll(airport));
        Database.getCivilianPlanes().removeAll(airport.getPlanes());
        airport.getPlanes().clear();

        textField_AirportDisplay_size.clear();
        textField_AirportDisplay_x.clear();
        textField_AirportDisplay_y.clear();
        textField_AirportDisplay_capacityCurrent.clear();
        textField_AirportDisplay_capacityMax.clear();
        textField_AirportDisplay_id.clear();
        listView_AirportDisplay_cargo.setItems(FXCollections.emptyObservableList());
        svgPath_AirportDisplay_icon.setContent("");

        listView_AirportDisplay.getItems().remove(airport);
        Database.Names.Airports.remove(airport.getName());
        mapController.removeFromMap(airport.getSprite());
        airport.setRunning(false);
    }
    //endregion
    //endregion

    //region [Constructor]

    private void utils_raiseCreationAlert() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation Err");
        alert.setHeaderText("Not all items selected or entity is out of bounds");
        alert.showAndWait();
    }

    private Point2D utils_createRandomPointInBounds(Point2D origin) {
        int x = random.nextInt((int) (this.mapController.getMap().getLayoutBounds().getWidth()));
        int y = random.nextInt((int) (this.mapController.getMap().getLayoutBounds().getHeight()));
        return new Point2D(x, y);
    }

    private int utils_getXInBoundsFromField(TextField field, Entity.TYPE type) {
        return (int) Math.max(type.getOrigin().getX(),
                Math.min(Double.parseDouble(field.getText()),
                        this.mapController.getMap().getLayoutBounds().getWidth()
                                - type.getOrigin().getX()));
    }

    private int utils_getYInBoundsFromField(TextField field, Entity.TYPE type) {
        return (int) Math.max(type.getOrigin().getY(),
                Math.min(Double.parseDouble(field.getText()),
                        this.mapController.getMap().getLayoutBounds().getHeight() - type.getOrigin().getY()));
    }

    private void utils_SetRandomNameToField(Database.Names nameDatabase, TextField nameTextField) {
        var previous = nameTextField.getText();
        nameTextField.setText(nameDatabase.getNew());
        nameDatabase.remove(previous);
    }

    private <T extends Entity> void utils_generatePath(ObservableList<T> path, ObservableList<T> entities) {
        var size = entities.size();
        if (size < 2) return;

        var ref = new Object() {
            int prev = -1;
            int i;
        };

        path.clear();
        random.ints(2 + random.nextInt(size * 2 - 2)).forEach(ignored -> {
            do {
                ref.i = random.nextInt(size);
            } while (ref.i == ref.prev);
            path.add(entities.get(ref.i));
            ref.prev = ref.i;
        });

        while (true) {
            T first = path.get(0);
            T last = path.get(path.size() - 1);
            if (first == last) {
                path.remove(path.size() - 1);
            } else break;
        }
    }


    //region [Civilian Planes]
    @FXML
    private ChoiceBox<Airport> choiceBox_CivilianPlane_selectedAirport;
    @FXML
    private ToggleGroup toggleGroup_CivilianPlane_size;
    @FXML
    private RadioButton radioButton_CivilianPlane_small;
    @FXML
    private RadioButton radioButton_CivilianPlane_medium;
    @FXML
    private RadioButton radioButton_CivilianPlane_big;

    @FXML
    private Button button_CivilianPlane_AddToAirport;
    @FXML
    private Button button_CivilianPlane_SelectRandomAirport;
    @FXML
    private Button button_CivilianPlane_CreateRandomPath;
    @FXML
    private Button button_CivilianPlane_AddSelectedToPath;
    @FXML
    private Button button_CivilianPlane_RemoveSelectedFromPath;

    @FXML
    private ListView<Airport> listView_CivilianPlane_Path;

    @FXML
    private void button_CivilianPlane_SelectRandomAirport_OnClick(ActionEvent event) {
        var size = Database.getAirports().size();
        if (size < 1) return;
        choiceBox_CivilianPlane_selectedAirport.getSelectionModel().select(random.nextInt(size));
    }

    @FXML
    private void button_CivilianPlane_CreateRandomPath_OnClick(ActionEvent event) {
        utils_generatePath(listView_CivilianPlane_Path.getItems(), Database.getAirports());
    }

    @FXML
    private void button_CivilianPlane_AddSelectedToPath_OnClick(ActionEvent event) {
        var airport = this.choiceBox_CivilianPlane_selectedAirport.getValue();
        var path = this.listView_CivilianPlane_Path.getItems();
        if (airport == null
                || (!path.isEmpty() && airport == path.get(path.size() - 1))
                || path.get(0) == airport) return;
        path.add(airport);
    }

    @FXML
    private void button_CivilianPlane_RemoveSelectedFromPath_OnClick(ActionEvent event) {
        var path = this.listView_CivilianPlane_Path.getItems();
        path.remove(this.listView_CivilianPlane_Path.getSelectionModel().getSelectedItem());
        if (path.get(0) == path.get(path.size() - 1)) path.remove(path.size() - 1);
    }

    private SIZE utils_CivilianPlane_getSize() {
        if (radioButton_CivilianPlane_small.isSelected()) return SIZE.SMALL;
        if (radioButton_CivilianPlane_medium.isSelected()) return SIZE.MEDIUM;
        return SIZE.BIG;
    }

    @FXML
    private void button_CivilianPlane_AddToAirport_OnClick(ActionEvent event) {
        if (listView_CivilianPlane_Path.getItems().size() < 2) {
            utils_raiseCreationAlert();
            return;
        }
        ;

        var size = utils_CivilianPlane_getSize();
        ObservableList<Airport> path = FXCollections.observableArrayList();
        path.addAll(listView_CivilianPlane_Path.getItems());
        var plane = new CivilianPlane(size, path);

        addEntityToMap(plane);
        path.get(0).getPlanes().add(plane);
        this.button_CivilianPlane_CreateRandomPath_OnClick(event);
    }
    //endregion

    //region [Military Planes]
    @FXML
    private ChoiceBox<MilitaryShip> choiceBox_MilitaryPlane_selectedMilitaryShip;

    @FXML
    private Button button_MilitaryPlane_AddToMilitaryShip;
    @FXML
    private Button button_MilitaryPlane_SelectRandomMilitaryShip;
    @FXML
    private Button button_MilitaryPlane_CreateRandomPath;
    @FXML
    private Button button_MilitaryPlane_AddSelectedToPath;
    @FXML
    private Button button_MilitaryPlane_RemoveSelectedFromPath;

    @FXML
    private ListView<MilitaryShip> listView_MilitaryPlane_path;

    @FXML
    private void button_MilitaryPlane_SelectRandomMilitaryShip_OnClick(ActionEvent event) {
        var size = Database.getAirports().size();
        if (size < 1) return;
        choiceBox_MilitaryPlane_selectedMilitaryShip.getSelectionModel().select(random.nextInt(size));
    }

    @FXML
    private void button_MilitaryPlane_CreateRandomPath_OnClick(ActionEvent event) {
        utils_generatePath(listView_MilitaryPlane_path.getItems(), Database.getMilitaryShips());
    }

    @FXML
    private void button_MilitaryPlane_AddSelectedToPath_OnClick(ActionEvent event) {
        var militaryShip = this.choiceBox_MilitaryPlane_selectedMilitaryShip.getValue();
        var path = this.listView_MilitaryPlane_path.getItems();
        if (militaryShip == null
                || (!path.isEmpty() && militaryShip == path.get(path.size() - 1))
                || path.get(0) == militaryShip) return;
        path.add(militaryShip);
    }

    @FXML
    private void button_MilitaryPlane_RemoveSelectedFromPath_OnClick(ActionEvent event) {
        var path = this.listView_MilitaryPlane_path.getItems();
        path.remove(this.listView_MilitaryPlane_path.getSelectionModel().getSelectedItem());
        if (path.get(0) == path.get(path.size() - 1)) path.remove(path.size() - 1);
    }

    @FXML
    private void button_MilitaryPlane_Add_OnClick(ActionEvent event) {
        if (listView_MilitaryPlane_path.getItems().size() < 2) {
            utils_raiseCreationAlert();
            return;
        }

        var path = FXCollections.observableArrayList(listView_MilitaryPlane_path.getItems());

        var plane = new MilitaryPlane(path);
        addEntityToMap(plane);
        path.get(0).getPlanes().add(plane);

        this.button_MilitaryPlane_CreateRandomPath_OnClick(event);
    }
    //endregion

    //region [Civilian Ships]
    @FXML
    private Button button_CivilianShip_Add;
    @FXML
    private ImageView imageView_CivilianShip_pablo;

    @FXML
    private ChoiceBox<FIRM> choiceBox_CivilianShip_selectedFirm;
    @FXML
    private ToggleGroup toggleGroup_CivilianShip_size;
    @FXML
    private RadioButton radioButton_CivilianShip_small;
    @FXML
    private RadioButton radioButton_CivilianShip_medium;
    @FXML
    private RadioButton radioButton_CivilianShip_big;

    @FXML
    private Button button_CivilianShip_SelectRandomFirm;

    private SIZE utils_CivilianShip_getSize() {
        if (radioButton_CivilianShip_small.isSelected()) return SIZE.SMALL;
        if (radioButton_CivilianShip_medium.isSelected()) return SIZE.MEDIUM;
        return SIZE.BIG;
    }

    private FIRM utils_CivilianShip_getFirm() {
        return this.choiceBox_CivilianShip_selectedFirm.getValue();
    }

    @FXML
    private void button_CivilianShip_SelectRandomFirm_OnClick(ActionEvent event) {
        this.choiceBox_CivilianShip_selectedFirm.setValue(Database.getFirms().get(random.nextInt(Database.getFirms().size())));
    }

    @FXML
    private void button_CivilianShip_Add_OnClick(ActionEvent event) {
        var firm = utils_CivilianShip_getFirm();
        if (firm == null) {
            utils_raiseCreationAlert();
            return;
        }
        var entity = new CivilianShip(utils_CivilianShip_getSize(), utils_CivilianShip_getFirm());
        button_CivilianShip_SelectRandomFirm_OnClick(event);

        Point2D p;
        do {
            p = utils_createRandomPointInBounds(entity.getType().getOrigin());
        } while (utils_hasIslandCollision(p));
        addEntityToMap(createEntityAtXY(entity, p.getX(), p.getY()));
        Thread thread = new Thread(entity);
        thread.start();
    }
    //endregion

    //region [Military Ships/Carrier]
    @FXML
    private ChoiceBox<WEAPON> choiceBox_MilitaryShip_weapon;
    @FXML
    private TextField textField_MilitaryShip_x;
    @FXML
    private TextField textField_MilitaryShip_y;
    @FXML
    private TextField textField_MilitaryShip_name;

    @FXML
    private Button button_MilitaryShip_Add;
    @FXML
    private Button button_MilitaryShip_SelectRandomCoords;
    @FXML
    private Button button_MilitaryShip_SelectRandomWeapon;
    @FXML
    private Button button_MilitaryShip_SelectRandomName;

    private WEAPON utils_MilitaryShip_getWeapon() {
        return choiceBox_MilitaryShip_weapon.getValue();
    }

    @FXML
    private void button_MilitaryShip_SelectRandomWeapon_OnClick(ActionEvent event) {
        choiceBox_MilitaryShip_weapon.setValue(Database.getWeapons().get(random.nextInt(Database.getWeapons().size())));
    }

    @FXML
    private void button_MilitaryShip_SelectRandomCoords_OnClick(ActionEvent event) {

        Point2D p;
        do {
            p = utils_createRandomPointInBounds(Entity.TYPE.MILITARYSHIP.getOrigin());
        } while (utils_hasIslandCollision(p));
        textField_MilitaryShip_x.setText(String.valueOf((int) p.getX()));
        textField_MilitaryShip_y.setText(String.valueOf((int) p.getY()));
    }

    @FXML
    private void button_MilitaryShip_SelectRandomName_OnClick(ActionEvent event) {
        utils_SetRandomNameToField(Database.Names.Carriers, this.textField_MilitaryShip_name);
    }

    @FXML
    private void button_MilitaryShip_Add_OnClick(ActionEvent event) {
        var weapon = utils_MilitaryShip_getWeapon();
        var name = textField_MilitaryShip_name.getText();
        int x = utils_getXInBoundsFromField(textField_MilitaryShip_x, Entity.TYPE.MILITARYSHIP);
        int y = utils_getYInBoundsFromField(textField_MilitaryShip_y, Entity.TYPE.MILITARYSHIP);

        if (weapon == null || name.isEmpty() && utils_hasIslandCollision(new Point2D(x, y))) {
            utils_raiseCreationAlert();
            return;
        }


        Database.Names.Carriers.use(name);
        var entity = new MilitaryShip(weapon, name);


        this.button_MilitaryShip_SelectRandomName_OnClick(event);
        this.button_MilitaryShip_SelectRandomCoords_OnClick(event);
        this.button_MilitaryShip_SelectRandomWeapon_OnClick(event);

        addEntityToMap(createEntityAtXY(entity, x, y));
        Thread thread = new Thread(entity);
        thread.setDaemon(true);
        thread.start();
    }
    //endregion

    //region [Airport]
    @FXML
    private Button button_Airport_Add;
    @FXML
    private Button button_Airport_SelectRandomCoords;
    @FXML
    private Button button_Airport_SelectRandomName;

    @FXML
    private TextField textField_Airport_x;
    @FXML
    private TextField textField_Airport_y;
    @FXML
    private TextField textField_Airport_name;

    @FXML
    private ToggleGroup toggleGroup_Airport_size;
    @FXML
    private RadioButton radioButton_Airport_smallSize;
    @FXML
    private RadioButton radioButton_Airport_mediumSize;
    @FXML
    private RadioButton radioButton_Airport_bigSize;

    private SIZE utils_Airport_getSize() {
        if (radioButton_Airport_smallSize.isSelected()) return SIZE.SMALL;
        if (radioButton_Airport_mediumSize.isSelected()) return SIZE.MEDIUM;
        return SIZE.BIG;
    }

    @FXML
    private void button_Airport_SelectRandomName_OnClick(ActionEvent event) {
        utils_SetRandomNameToField(Database.Names.Airports, textField_Airport_name);
    }

    @FXML
    private void button_Airport_SelectRandomCoords_OnClick(ActionEvent event) {
        Point2D p;
        do {
            p = utils_createRandomPointInBounds(Entity.TYPE.AIRPORT.getOrigin());
        } while (!utils_hasIslandCollision(p));
        textField_Airport_x.setText(String.valueOf((int) p.getX()));
        textField_Airport_y.setText(String.valueOf((int) p.getY()));
    }

    @FXML
    private void button_Airport_Add_OnClick(ActionEvent event) {
        var name = this.textField_Airport_name.getText();
        int x = utils_getXInBoundsFromField(textField_Airport_x, Entity.TYPE.AIRPORT);
        int y = utils_getYInBoundsFromField(textField_Airport_y, Entity.TYPE.AIRPORT);

        if (name.isEmpty() && utils_hasIslandCollision(new Point2D(x, y))) {
            utils_raiseCreationAlert();
            return;
        }


        Database.Names.Airports.use(name);
        var entity = new Airport(utils_Airport_getSize(), name);

        addEntityToMap(createEntityAtXY(entity, x, y));
        this.button_Airport_SelectRandomName_OnClick(event);
        this.button_Airport_SelectRandomCoords_OnClick(event);

        Thread thread = new Thread(entity);
        thread.setDaemon(true);
        thread.start();
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
        this.mapController.addToMap(entity.getSprite());
    }

    private <T extends Entity> T createEntityAtXY(T entity, double x, double y) {
        entity.getSprite().setOnMouseClicked(e -> {
            // I hate This.
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

    @FXML
    private void menu_closeTab_OnAction(ActionEvent event) {
        // Doesn't matter what i use, can't get from event action tho, so I use the first thing I can
        this.button_Airport_Add.getScene().getWindow().hide();
    }

    //region [Initialization]
    private void initialize_TextFields() {
        Supplier<TextFormatter<Integer>> textFormatter = () -> {
            StringConverter<Integer> converter = new IntegerStringConverter() {
                @Override
                public Integer fromString(String s) {
                    if (s.isEmpty()) return 0;
                    return super.fromString(s);
                }
            };
            return new TextFormatter<>(converter, 0, change -> {
                if (change.getControlNewText().matches("([1-4][0-9]{0,2})?")) {
                    return change;
                }
                return null;
            });
        };
        this.textField_Airport_x.setTextFormatter(textFormatter.get());
        this.textField_Airport_y.setTextFormatter(textFormatter.get());
        this.textField_MilitaryShip_x.setTextFormatter(textFormatter.get());
        this.textField_MilitaryShip_y.setTextFormatter(textFormatter.get());
    }

    private void initialize_ListViews() {
        listView_AirportDisplay.setItems(Database.getAirports());
        listView_CivilianPlaneDisplay.setItems(Database.getCivilianPlanes());
        listView_MilitaryPlaneDisplay.setItems(Database.getMilitaryPlanes());
        listView_CivilianShipDisplay.setItems(Database.getCivilianShips());
        listView_MilitaryShipDisplay.setItems(Database.getMilitaryShips());
    }

    private void initialize_ChoiceBoxes() {
        choiceBox_CivilianPlane_selectedAirport.setItems(Database.getAirports());
        choiceBox_MilitaryPlane_selectedMilitaryShip.setItems(Database.getMilitaryShips());
        choiceBox_CivilianShip_selectedFirm.setItems(Database.getFirms());
        choiceBox_MilitaryShip_weapon.setItems(Database.getWeapons());
    }

    /**
     * Inicjalizacja kontrolera nadrzędnego
     *
     * @param mapController Nadrzędny kontroler mapy
     */
    public void initialize_MapController(MapController mapController) {
        this.mapController = mapController;
    }

    private void initialize_Airports() {
        var points = new ArrayList<>(Arrays.asList(
                new Point2D(23, 245), new Point2D(420, 100), new Point2D(191, 170),
                new Point2D(400, 240), new Point2D(180, 20), new Point2D(340, 30),
                new Point2D(44, 260), new Point2D(261, 230), new Point2D(290, 100),
                new Point2D(300, 140)));
        var sizes = new ArrayList<>(Arrays.asList(SIZE.SMALL, SIZE.MEDIUM, SIZE.BIG));

        points.forEach(p -> {
            var name = Database.Names.Airports.getNew();
            Database.Names.Airports.use(name);

            var entity = new Airport(sizes.get(random.nextInt(sizes.size())), name);

            addEntityToMap(createEntityAtXY(entity, p.getX(), p.getY()));
            Thread thread = new Thread(entity);
            thread.setDaemon(true);
            thread.start();
        });
    }

    private void initialize_CivilianPlanes() {
        var sizes = new ArrayList<>(Arrays.asList(SIZE.SMALL, SIZE.MEDIUM, SIZE.BIG));

        for (int i = 0; i < 8; i++) {
            ObservableList<Airport> path = FXCollections.observableArrayList();
            utils_generatePath(path, Database.getAirports());
            var plane = new CivilianPlane(sizes.get(random.nextInt(sizes.size())), path);

            addEntityToMap(plane);
            path.get(0).getPlanes().add(plane);
        }
    }

    private void initialize_CivilianShips() {
        var sizes = new ArrayList<>(Arrays.asList(SIZE.SMALL, SIZE.MEDIUM, SIZE.BIG));

        var points = new ArrayList<>(Arrays.asList(
                new Point2D(70, 120), new Point2D(520, 20), new Point2D(470, 300)));
        points.forEach(p -> {
            var entity = new CivilianShip(sizes.get(random.nextInt(sizes.size())), Database.getFirms().get(random.nextInt(Database.getFirms().size())));

            addEntityToMap(createEntityAtXY(entity, p.getX(), p.getY()));
            Thread thread = new Thread(entity);
            thread.setDaemon(true);
            thread.start();
        });
    }

    private void initialize_MilitaryPlanes() {

        for (int i = 0; i < 5; i++) {
            ObservableList<MilitaryShip> path = FXCollections.observableArrayList();
            utils_generatePath(path, Database.getMilitaryShips());
            var plane = new MilitaryPlane(path);

            addEntityToMap(plane);
            path.get(0).getPlanes().add(plane);
        }
    }

    private void initialize_MilitaryShips() {
        var points = new ArrayList<>(Arrays.asList(
                new Point2D(70, 120), new Point2D(520, 20), new Point2D(470, 300)));
        points.forEach(p -> {
            var name = Database.Names.Carriers.getNew();
            Database.Names.Carriers.use(name);

            var entity = new MilitaryShip(Database.getWeapons().get(random.nextInt(Database.getWeapons().size())), name);

            addEntityToMap(createEntityAtXY(entity, p.getX(), p.getY()));
            Thread thread = new Thread(entity);
            thread.setDaemon(true);
            thread.start();
        });
    }

    /**
     * Funkcja inicjalizująca początkowe jednostki na mapie
     */
    public void initialize_Entities() {
        initialize_Airports();
        initialize_CivilianPlanes();
        initialize_CivilianShips();
        initialize_MilitaryShips();
        initialize_MilitaryPlanes();
    }

    /**
     * Inicjalizacja wszelkich pól, które nie mogą być przypisane z poziomu SceneBuilder
     *
     * @param location  Lokacja url
     * @param resources zasoby
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize_ChoiceBoxes();
        initialize_TextFields();
        initialize_ListViews();
    }
    //endregion
}
