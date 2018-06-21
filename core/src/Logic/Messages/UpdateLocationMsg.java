package Logic.Messages;


import Logic.Model.Vector2D;

public class UpdateLocationMsg extends MessagePackage {
    private Vector2D location;
    private int interactableId;

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }

    public int getInteractableId() {
        return interactableId;
    }

    public void setInteractableId(int interactableId) {
        this.interactableId = interactableId;
    }
}
