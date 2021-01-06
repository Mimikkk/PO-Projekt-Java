import classes.TYPE;
import classes.Entity;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class ControlPanelController implements Initializable {
    MapController mapController;
    
    @FXML private ListView<Entity> airportListView;
    @FXML private ListView<String> planeCivilianListView;
    @FXML private ListView<String> planeMilitaryListView;
    @FXML private ListView<String> shipCivilianListView;
    @FXML private ListView<String> shipMilitaryListView;
    
    /// Testing
    @FXML private ListView<Entity> entityListView;
    
    
    private void loadData(ListView<String> listView) {
        Random random = new Random();
        String[] strings = random.ints(random.nextInt(100)).parallel()
                .mapToObj(x -> random.ints(97, 123)
                        .limit(random.nextInt(100))
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString()).toArray(String[]::new);
        
        listView.getItems().clear();
        listView.getItems().addAll(strings);
    }
    
    @FXML private Button addEntityButton;
    @FXML private TextField nameEntity;
    @FXML private TextField xEntity;
    @FXML private TextField yEntity;
    @FXML private TextField rotationEntity;
    @FXML private TextField idEntity;
    
    @FXML private TextField nameEntityDisplay;
    @FXML private TextField xEntityDisplay;
    @FXML private TextField yEntityDisplay;
    @FXML private TextField rotationEntityDisplay;
    @FXML private TextField idEntityDisplay;
    
    @FXML private void entityListViewOnClicked(MouseEvent event) {
        var a = entityListView.getSelectionModel().getSelectedItems();
        if (a.isEmpty()) return;
        var entity = entityListView.getSelectionModel().getSelectedItems().get(0);
        
        nameEntityDisplay.setText(entity.getType().toString());
        xEntityDisplay.setText(String.valueOf(entity.getSprite().getLayoutX()));
        yEntityDisplay.setText(String.valueOf(entity.getSprite().getLayoutY()));
        rotationEntityDisplay.setText(String.valueOf(entity.getSprite().getRotate()));
        idEntityDisplay.setText(String.valueOf(entity.getID()));
    }
    
    //region [Testing]
    private void addToEntityListView(TYPE type) {
        Entity entity = new Entity(type, 0, 0);
        entityListView.getItems().add(entity);
        this.mapController.getMap().getChildren().add(entity.getSprite());
    }
    
    @FXML private Button buttonAddCivilianPlane;
    @FXML private void buttonAddCivilianPlaneOnClick(ActionEvent event) { addToEntityListView(TYPE.CIVILIANPLANE); }
    
    @FXML private Button buttonAddMilitaryPlane;
    @FXML private void buttonAddMilitaryPlaneOnClick(ActionEvent event) { addToEntityListView(TYPE.MILITARYPLANE); }
    
    @FXML private Button buttonAddCivilianShip;
    @FXML private void buttonAddCivilianShipOnClick(ActionEvent event) { addToEntityListView(TYPE.CIVILIANSHIP); }
    
    @FXML private Button buttonAddMilitaryShip;
    @FXML private void buttonAddMilitaryShipOnClick(ActionEvent event) { addToEntityListView(TYPE.MILITARYSHIP); }
    
    @FXML private Button buttonAddAirport;
    @FXML private void buttonAddAirportOnClick(ActionEvent event) { addToEntityListView(TYPE.AIRPORT); }
    
    @FXML private TextField xEntityDisplayTest;
    @FXML private TextField yEntityDisplayTest;
    
    @FXML private Button buttonCreateAtSpecifiedCircle;
    @FXML private Button buttonCreateAtRandomTest;
    
    private Circle createCircleAtXY(double x, double y) {
        Circle circle = new Circle(10);
        circle.setLayoutX(x);
        circle.setLayoutY(y);
        circle.setUserData("Circle");
        return circle;
    }
    
    @FXML private void buttonCreateAtSpecifiedCircleOnClick(ActionEvent event) {
        var x = Double.parseDouble(this.xEntityDisplayTest.getText());
        var y = Double.parseDouble(this.yEntityDisplayTest.getText());
        this.mapController.getMap().getChildren().add(createCircleAtXY(x, y));
    }
    
    @FXML private Button buttonMoveToSpecifiedTest;

    @FXML private void moveTo(double x, double y) {
        var entity = entityListView.getSelectionModel().getSelectedItem();
        
        Path path = new Path();
        // TODO Bezier Curve calculation
//        if (Set.of(TYPE.CIVILIANPLANE, TYPE.MILITARYPLANE).contains(entity.getType())) {
//            var p1 = new Point2D(entity.getSprite().getTranslateX(), entity.getSprite().getTranslateY());
//            var p2 = new Point2D(x, y);
//            double mag = p1.distance(p2);
//
//            MoveTo start = new MoveTo(p1.getX(), p1.getY());
//            CubicCurveTo end = new CubicCurveTo(
//                    start.getX() + mag / 6, start.getY() - mag / 4,
//                    x - mag / 6, y - mag / 4,
//                    x, y);
//            path.getElements().addAll(start, end);
//        } else
        {
            MoveTo start = new MoveTo(entity.getSprite().getTranslateX(), entity.getSprite().getTranslateY());
            LineTo end = new LineTo(x, y);
            path.getElements().addAll(start, end);
        }
        
        var coolerTransition = new PathTransition(Duration.seconds(0.2), path, entity.getSprite());
        coolerTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        coolerTransition.setInterpolator(Interpolator.LINEAR);
        coolerTransition.play();
    }
    
    @FXML private void buttonCreateAtRandomCircleOnClick(ActionEvent event) {
        var rand = new Random();
        var x = (double) rand.nextInt(
                (int) mapController.getMap().getLayoutBounds().getWidth());
        var y = (double) rand.nextInt(
                (int) mapController.getMap().getLayoutBounds().getHeight());
        
        this.mapController.getMap().getChildren().add(createCircleAtXY(x, y));
    }
    
    @FXML private void buttonMoveToSpecifiedOnClick(ActionEvent event) {
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
    
    @FXML private void buttonMoveToSpecifiedCircleOnClick(ActionEvent event) {
        // TODO Proper Circle Selection
        var circle = mapController.getRandomCircle();
        if (circle.isEmpty()) {return;}
        var x = circle.get().getLayoutX();
        var y = circle.get().getLayoutY();
        
        moveTo(x, y);
    }
    
    @FXML private void buttonMoveToRandomCircleOnClick(ActionEvent event) {
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
