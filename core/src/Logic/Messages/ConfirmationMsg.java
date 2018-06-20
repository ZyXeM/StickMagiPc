package Logic.Messages;

public class ConfirmationMsg extends MessagePackage {
    private boolean confirm = true;

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
