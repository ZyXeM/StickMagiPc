package Logic.Messages;

public class UpdateRotationMsg extends MessagePackage {
    private int rotation;
    private int interactableId;

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getInteractableId() {
        return interactableId;
    }

    public void setInteractableId(int interactableId) {
        this.interactableId = interactableId;
    }
}
