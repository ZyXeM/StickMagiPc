package Logic.Interface;


import Logic.Messages.AddInteractableMsg;
import Logic.Messages.MessagePackage;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Logic.Model.InGameObject;
import Logic.Model.Player;
import Logic.Model.Spell;

public interface IUpdateManager {
   void addInteractable(AddInteractableMsg interactableMsg);
   void updateLocation(UpdateLocationMsg locationMsg);
   void updateRotation(UpdateRotationMsg rotationMsg);
}
