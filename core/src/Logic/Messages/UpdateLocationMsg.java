package Logic.Messages;


import java.awt.geom.Point2D;

public class UpdateLocationMsg extends MessagePackage {
    private Point2D location;
    private int interactableId;

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public int getInteractableId() {
        return interactableId;
    }

    public void setInteractableId(int interactableId) {
        this.interactableId = interactableId;
    }
}
