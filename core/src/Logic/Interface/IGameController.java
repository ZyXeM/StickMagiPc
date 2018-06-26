package Logic.Interface;

import Logic.Interface.IUpdateManager;
import Logic.Messages.LoginMsg;
import Logic.Model.Account;
import Logic.Que.PackageBundle;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote{
    /**
     * Handles the login and adds the player to the game
     * @param loginMsg
     * @param updateManager
     * @return range of available ID should be *1000
     * @throws RemoteException
     */
    int rmiLogin(LoginMsg loginMsg, IUpdateManager updateManager) throws RemoteException;


    /**
     * Handles the register of the account
     * @param account
     * @throws RemoteException
     */
    boolean rmiRegister(Account account) throws RemoteException;
    /**
     * Handles the current message
     * @param packet
     * @throws RemoteException
     */
    void handlePackage(PackageBundle packet) throws  RemoteException;


}
