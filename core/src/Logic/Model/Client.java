package Logic.Model;

import Logic.Interface.IUpdateManager;
import Logic.Messages.AddInteractableMsg;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IUpdateManager {
  transient  private WorldMap worldMap;

    public Client(WorldMap worldMap) throws RemoteException {
        super();
        this.worldMap = worldMap;
    }

    @Override
    public void addInteractableUpdate(AddInteractableMsg interactableMsg) throws RemoteException {
        worldMap.addInteractableUpdate(interactableMsg);
    }

    @Override
    public void updateLocation(UpdateLocationMsg locationMsg) throws RemoteException {
        worldMap.updateLocation(locationMsg);
    }

    @Override
    public void updateRotation(UpdateRotationMsg rotationMsg) throws RemoteException {
        worldMap.updateRotation(rotationMsg);

    }
}
