package Session;

import Logic.Interface.ISessionManager;
import Logic.Interface.IUpdateManager;
import Logic.Messages.AddInteractableMsg;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IUpdateManager {
  transient  private ISessionManager sessionManager;

    public Client(ISessionManager sessionManager) throws RemoteException {
        super();
       this.sessionManager = sessionManager;
    }

    @Override
    public void addInteractableUpdate(AddInteractableMsg interactableMsg) throws RemoteException {
        sessionManager.addInteractableUpdate(interactableMsg);
    }

    @Override
    public void updateLocation(UpdateLocationMsg locationMsg) throws RemoteException {
        sessionManager.locationUpdate(locationMsg);
    }

    @Override
    public void updateRotation(UpdateRotationMsg rotationMsg) throws RemoteException {
        sessionManager.rotationUpdate(rotationMsg);

    }
}
