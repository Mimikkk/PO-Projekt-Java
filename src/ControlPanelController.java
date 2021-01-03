import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ControlPanelController implements Initializable {

    @FXML
    private ListView<String> airportListView;

    @FXML
    private ListView<String> planeCivilianListView;

    @FXML
    private ListView<String> planeMilitaryListView;

    @FXML
    private ListView<String> shipCivilianListView;

    @FXML
    private ListView<String> shipMilitaryListView;

    private void loadData(ListView<String> listView) {
        Random random = new Random();
        String str =
                random.ints(97, 123).limit(random.nextInt(100))
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
        String[] strings = new String[random.nextInt(100)];
        for (int i = 0; i < strings.length; ++i) {
            byte[] bytes = new byte[random.nextInt(20)];
            random.nextBytes(bytes);
            strings[i] = new String(bytes, StandardCharsets.UTF_16);
        }
        listView.getItems().clear();
        listView.getItems().addAll(strings);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stream.of(airportListView,
                planeCivilianListView,
                planeMilitaryListView,
                shipCivilianListView,
                shipMilitaryListView).forEach(this::loadData);
    }
}
