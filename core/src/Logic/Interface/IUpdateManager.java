package Logic.Interface;


import Logic.Messages.AddInteractableMsg;
import Logic.Messages.MessagePackage;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Logic.Model.InGameObject;
import Logic.Model.Player;
import Logic.Model.Spell;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUpdateManager extends Remote {
   void addInteractableUpdate(AddInteractableMsg interactableMsg) throws RemoteException;
   void updateLocation(UpdateLocationMsg locationMsg)  throws RemoteException;
   void updateRotation(UpdateRotationMsg rotationMsg) throws RemoteException;
}
