package Logic.Messages;

import Logic.Model.Interactable;

public class AddInteractableMsg extends MessagePackage {
    private Interactable interactable;

    public Interactable getInteractable() {
        return interactable;
    }

    public void setInteractable(Interactable interactable) {
        this.interactable = interactable;
    }
}
