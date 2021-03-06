package Logic.Interface;


import Logic.Messages.AddInteractableMsg;
import Logic.Messages.MessagePackage;

import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Logic.Model.Account;
import Logic.Model.Interactable;


public interface ISessionManager {

    /**
     * Sends the rotation of an object to other players
     * @param rotation
     */
    void sendRotation(Interactable rotation);

    /**
     * Sends the location of an object to other players
     * @param location
     */
    void sendLocation(Interactable location);

    /**
     * Adds an interactable to the server
     * @param interactable
     */
    void addInteractable(Interactable interactable);


    void handleMessage(MessagePackage arg);

    void login(Account account);

    /**
     * Sends the rotation of an object to other players
     * @param rotation
     */
    void rotationUpdate(UpdateRotationMsg rotation);

    /**
     * Sends the location of an object to other players
     * @param location
     */
    void locationUpdate(UpdateLocationMsg location);

    /**
     * Adds an interactable to the server
     * @param interactable
     */
    void addInteractableUpdate(AddInteractableMsg interactable);
}
