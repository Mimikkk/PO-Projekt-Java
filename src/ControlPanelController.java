import classes.TYPE;
import classes.Entity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

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
        yEntityDisplay.setText(String.valueOf(entity.getSprite().getLayoutX()));
        rotationEntityDisplay.setText(String.valueOf(entity.getSprite().getRotate()));
        idEntityDisplay.setText(String.valueOf(entity.getID()));
    }
    
    @FXML private void addToEntityListView(ActionEvent event) {
        System.out.println(event.getEventType().getName());
        System.out.printf("%s, %s, %s, %s, %s\n",
                nameEntity.getText(),
                xEntity.getText(),
                yEntity.getText(),
                idEntity.getText(),
                rotationEntity.getText());
        
        Random rand = new Random();
        Entity entity = new Entity(TYPE.CIVILIANPLANE, rand.nextInt(600), rand.nextInt(600));
        entityListView.getItems().add(entity);
        this.mapController.getMap().getChildren().add(entity.getSprite());
    }
    
    public void init(MapController mapController) {
        this.mapController = mapController;
    }
    
    @Override public void initialize(URL location, ResourceBundle resources) {}
    
    public List<Entity> getEntityList() {
        return this.entityListView.getItems();
    }
}
