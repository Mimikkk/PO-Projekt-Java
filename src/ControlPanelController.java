import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import classes.Entity;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ControlPanelController implements Initializable {
    
    private enum SVG {
        CIVILIANPLANE(0, "m 1397,1324 q 0,-87 -149,-236 l -240,-240 143,-746 1,-6 q 0,-14 -9,-23 L 1079,9 q -9,-9 -23,-9 -21,0 -29,18 L 753,593 508,348 Q 576,110 576,96 576,82 567,73 L 503,9 Q 494,0 480,0 462,0 452,16 L 297,296 17,451 q -17,9 -17,28 0,14 9,23 l 64,65 q 9,9 23,9 14,0 252,-68 L 593,753 18,1027 q -18,8 -18,29 0,14 9,23 l 64,64 q 9,9 23,9 4,0 6,-1 l 746,-143 240,240 q 149,149 236,149 32,0 52.5,-20.5 20.5,-20.5 20.5,-52.5 z"),
        MILITARYPLANE(0, "M 157,184 L 173,168 C 182,159 197,162 197,162 L 242,168 L 265,146 C 277,134 288,152 283,158 L 271,172 L 320,181 L 336,162 C 354,141 367,159 359,171 L 348,184 L 421,194 C 484,133 509,108 526,123 C 543,138 513,168 456,227 L 467,302 L 482,289 C 491,282 507,294 490,309 L 469,329 L 478,378 L 491,368 C 503,359 517,371 501,388 L 480,409 C 480,409 487,452 487,452 C 492,473 480,480 480,480 L 466,493 L 411,339 C 407,326 396,311 376,313 C 366,314 341,331 337,335 L 279,392 C 277,393 288,465 288,465 C 288,468 269,492 269,492 L 232,426 L 213,434 L 224,417 L 157,379 L 182,361 C 194,364 257,371 258,370 C 258,370 309,319 312,316 C 329,298 335,292 338,283 C 340,274 336,261 309,245 C 272,222 157,184 157,184 z"),
        CIVILIANSHIP(0, "M 503 256 L 484 256 L 470 218 L 464 214 L 445 213 L 433 176 L 426 171 L 392 171 L 391 149 L 389 147 L 379 146 L 376 150 L 376 171 L 333 171 L 332 149 L 329 145 L 319 146 L 316 149 L 316 170 L 272 171 L 271 148 L 270 146 L 259 146 L 256 148 L 256 172 L 219 172 L 213 176 L 200 214 L 131 214 L 124 217 L 111 255 L 8 255 C 5 255 2 257 0 260 C -0 263 -0 266 1 269 L 69 363 C 71 365 74 366 76 366 C 117 366 433 366 460 366 C 462 366 463 366 464 366 C 506 345 511 303 511 264 C 512 259 508 255 503 255 Z M 110 285 C 110 288 109 290 106 290 L 98 290 C 95 290 93 288 93 285 L 93 277 C 93 274 95 273 98 273 L 106 273 C 109 273 110 274 110 277 L 110 285 Z M 145 285 C 145 288 143 290 140 290 L 132 290 C 129 290 128 288 128 285 L 128 277 C 128 274 129 273 132 273 L 140 273 C 143 273 145 274 145 277 L 145 285 Z M 179 285 C 179 288 177 290 174 290 L 166 290 C 164 290 162 288 162 285 L 162 277 C 162 274 164 273 166 273 L 174 273 C 177 273 179 274 179 277 L 179 285 Z M 213 285 C 213 288 211 290 209 290 L 200 290 C 198 290 196 288 196 285 L 196 277 C 196 274 198 273 200 273 L 209 273 C 211 273 213 274 213 277 L 213 285 Z M 247 285 C 247 288 245 290 243 290 L 234 290 C 232 290 230 288 230 285 L 230 277 C 230 274 232 273 234 273 L 243 273 C 245 273 247 274 247 277 L 247 285 Z M 281 285 C 281 288 279 290 277 290 L 268 290 C 266 290 264 288 264 285 L 264 277 C 264 274 266 273 268 273 L 277 273 C 279 273 281 274 281 277 L 281 285 Z M 315 285 C 315 288 313 290 311 290 L 302 290 C 300 290 298 288 298 285 L 298 277 C 298 274 300 273 302 273 L 311 273 C 313 273 315 274 315 277 L 315 285 Z M 349 285 C 349 288 347 290 345 290 L 337 290 C 334 290 332 288 332 285 L 332 277 C 332 274 334 273 337 273 L 345 273 C 347 273 349 274 349 277 L 349 285 Z M 384 285 C 384 288 382 290 379 290 L 371 290 C 368 290 366 288 366 285 L 366 277 C 366 274 368 273 371 273 L 379 273 C 382 273 384 274 384 277 L 384 285 Z M 418 285 C 418 288 416 290 413 290 L 405 290 C 402 290 401 288 401 285 L 401 277 C 401 274 402 273 405 273 L 413 273 C 416 273 418 274 418 277 L 418 285 Z M 452 285 C 452 288 450 290 448 290 L 439 290 C 437 290 435 288 435 285 L 435 277 C 435 274 437 273 439 273 L 448 273 C 450 273 452 274 452 277 L 452 285 Z"),
        MILITARYSHIP(0, "M 1491 375 C 1491 375 1413 375 1412.5 375 C 1413 377 1412 328 1412 328 C 1412 328 1330 325 1330 326 C 1330 326 1328 45 1328 48 C 1328 48 1302 49 1299 48 C 1296 47 1245 289 1245 290 C 1245 289 1185 376 1184 377 C 1183 377 1097 377 1096 379 C 1096 380 1000 471 1000 472 C 1000 473 268 479 89 479 C 79 479 76 465 67 465 C 36 465 19 465 19 465 C 19 465 60 546 84 571 C 108 595 210 661 210 661 L 1447 661 L 1474 599 C 1474 599 1511 537 1511 537 C 1511 535 1514 429 1513 429 C 1513 428 1491 374 1491 374 Z"),
        AIRPORT(0, "M 12 0c-5.522 0-10 4.394-10 9.815 0 5.505 4.375 9.268 10 14.185 5.625-4.917 10-8.68 10-14.185 0-5.421-4.478-9.815-10-9.815zm0 18c-4.419 0-8-3.582-8-8s3.581-8 8-8c4.419 0 8 3.582 8 8s-3.581 8-8 8zm-3.61-7.202l2.831-1.402-2.902-2.475.898-.444 4.697 1.586 2.244-1.111c.592-.297 1.569-.217 1.791.231.035.071.051.152.051.239-.002.458-.46 1.078-.953 1.325l-2.244 1.111-1.586 4.698-.898.444-.209-3.808-2.832 1.401-.584 1.408-.628.31-.219-2.126-1.559-1.464.628-.311 1.474.388z");
        
        private final double frontRotation;
        private final String svg;
        
        SVG(double frontRotation, String svg) {
            this.frontRotation = frontRotation;
            this.svg = svg;
        }
        
        private void resize(SVGPath svg, double width, double height) {
            double originalWidth = svg.prefWidth(-1);
            double originalHeight = svg.prefHeight(originalWidth);
            
            double scaleX = width / originalWidth;
            double scaleY = height / originalHeight;
            
            svg.setScaleX(width / svg.prefWidth(-1));
            svg.setScaleY(height / svg.prefHeight(originalWidth));
        }
        
        public String getString() { return svg; }
        
        public Double getFrontRotation() { return frontRotation; }
    }
    
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
    @FXML private TextField idEntity;
    @FXML private TextField rotationEntity;
    
    
    private void addToEntityListView() {
    
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
