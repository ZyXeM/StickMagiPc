import Logic.Interface.ISessionManager;
import Logic.Messages.AddInteractableMsg;
import Logic.Messages.MessagePackage;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Logic.Model.Account;
import Logic.Model.Interactable;
import Logic.Model.WorldMap;

public class TestSessionManager implements ISessionManager {

    private WorldMap worldMap;

    public TestSessionManager(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void sendRotation(Interactable rotation) {

    }

    @Override
    public void sendLocation(Interactable location) {

    }

    @Override
    public void addInteractable(Interactable interactable) {

    }

    @Override
    public void handleMessage(MessagePackage arg) {

    }

    @Override
    public void login(Account account) {

    }

    @Override
    public void rotationUpdate(UpdateRotationMsg rotation) {

    }

    @Override
    public void locationUpdate(UpdateLocationMsg location) {

    }

    @Override
    public void addInteractableUpdate(AddInteractableMsg interactable) {

    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
}
