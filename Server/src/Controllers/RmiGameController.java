package Controllers;

import Logic.Interface.IGameController;
import Logic.Interface.IUpdateManager;
import Logic.Messages.LoginMsg;
import Logic.Model.WorldMap;
import Logic.Que.PackageBundle;

import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class RmiGameController extends UnicastRemoteObject implements IGameController {
    private GameController gameController;

    public RmiGameController(GameController gameController) throws RemoteException {
        super();
        this.gameController = gameController;
    }


    @Override
    public int rmiLogin(LoginMsg loginMsg, IUpdateManager updateManager) {

          return  gameController.handleLogin(loginMsg, getAdress(), updateManager);

    }

    @Override
    public void handlePackage(PackageBundle packet) throws RemoteException {
        packet.setAddress(getAdress());
        this.gameController.handlePackage(packet);
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public InetSocketAddress getAdress() {

        try {

            String host = null;
            host = getClientHost();
            InetSocketAddress s = new InetSocketAddress(host, 2001);
            return s;
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        return null;
    }
}
