package Logic.Interface;

import Logic.Interface.IUpdateManager;
import Logic.Messages.LoginMsg;
import Logic.Que.PackageBundle;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote{
    int rmiLogin(LoginMsg loginMsg, IUpdateManager updateManager) throws RemoteException;
    void handlePackage(PackageBundle packet) throws  RemoteException;


}
