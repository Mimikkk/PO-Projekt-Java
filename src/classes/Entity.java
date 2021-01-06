package classes;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;

import java.util.Set;

public class Entity {
    private final SVGPath sprite;
    private final int id;
    private final TYPE type;
    
    private void resize(SVGPath svg, double width, double height) {
        double originalWidth = svg.prefWidth(-1);
        double originalHeight = svg.prefHeight(originalWidth);
        double scaleX = width / originalWidth;
        double scaleY = height / originalHeight;
        
        svg.setScaleX(scaleX);
        svg.setScaleY(scaleY);
    }
    
    public Entity(final TYPE type) {
        var svg = new SVGPath();
        svg.setContent(type.getSvg());
        sprite = svg;
        
        if (Set.of(TYPE.MILITARYSHIP, TYPE.CIVILIANSHIP).contains(type)) {
            svg.setRotationAxis(new Point3D(0, 1, 0));
        }
        
        this.id = 1; // TODO ID generator
        this.type = type;
    }
    
    public int getID() { return this.id; }
    public TYPE getType() { return this.type; }
    public SVGPath getSprite() { return sprite; }
    
    @Override public String toString() { return type + " id: " + id; }
}
