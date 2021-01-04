package classes;

import javafx.geometry.Point2D;
import javafx.scene.shape.SVGPath;

public class Entity {
    private final SVGPath sprite;
    private Point2D coords;
    private double rotation;

    Entity(String svgPath, Point2D point, double rotation) {
        this(svgPath, point.getX(), point.getY(), rotation);
    }

    public Entity(final String svgPath, final double x, final double y, double rotation) {
        this.sprite = new SVGPath();
        this.sprite.setContent(svgPath);

        this.rotation = rotation % 360.0d;
        this.coords = new Point2D(x, y);
    }

    public Point2D getCoords() {
        return coords;
    }

    public void modifyCoords(double x, double y) {
        coords = coords.add(x, y);
    }

    public void modifyCoords(Point2D point) {
        coords = coords.add(point);
    }

    public void setCoords(Point2D point) {
        coords = point;
    }

    public void setCoords(double x, double y) {
        coords = new Point2D(x, y);
    }

    public SVGPath getSprite() {
        return sprite;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation % 360.0d;
    }

    public void modifyRotation(double rotation) {
        this.rotation = (this.rotation + rotation) % 360.0d;
    }
}
