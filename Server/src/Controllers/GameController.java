package Controllers;

import Logic.Interface.IUpdateManager;
import Logic.Messages.LoginMsg;
import Logic.Messages.MessagePackage;
import Logic.Model.Account;
import Logic.Que.PackageBundle;
import Server.PlayerConnection;

import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GameController {

  private ArrayList<IMapController> gameList;
  private ArrayList<PlayerConnection> playerList;
  private Map<InetSocketAddress,IMapController> playerMap;
  private int numberOfMaxPlayers = 5;
  private RmiGameController gameController;

    public GameController() throws RemoteException {

        gameList = new ArrayList<>();
        playerList = new ArrayList<>();
        playerMap = new HashMap<>();
        gameController = new RmiGameController(this);
    }


    /**
     * Handles the log in request if its a new address it wil try to join a game
     * @param messagePackage
     * @param address
     */
    public int handleLogin(LoginMsg messagePackage, InetSocketAddress address, IUpdateManager iUpdateManager) {
        if(!this.login(messagePackage.getAccount())){
            //Show error message /feedback
            return 0;
        }

        //searches if you're logged in
        boolean loggedIn = false;
        for (PlayerConnection p : playerList){
            if(p.getAddress().equals(address)){
                loggedIn = true;
            }
        }
        //If you're not logged in it wil make a new playerconnection and find a match
        if(!loggedIn){
            PlayerConnection playerConnection = new PlayerConnection();
            playerConnection.setAddress(address);
            playerConnection.setAccount(messagePackage.getAccount());
            playerConnection.setUpdateManager(iUpdateManager);
            this.findMatch(playerConnection);
        }
       return playerMap.get(address).getPlayerList().size();

    }

    /**
     * finds a match for the player: if none are found creates a new match
     * @param playerConnection
     */
    private void findMatch(PlayerConnection playerConnection) {
        System.out.println("finding match");
        boolean newGame = true;
        for(IMapController map : this.gameList){
            if(map.getPlayerList().size() <= numberOfMaxPlayers){
                map.addPlayerConnection(playerConnection);
                newGame = false;
                this.playerMap.put(playerConnection.getAddress(), map);
            }
        }
        if(newGame){
            createGame(playerConnection);
        }
    }



    /**
     * creates game with the playerconnection
     * @param playerConnection
     * @return
     */
    private boolean createGame(PlayerConnection playerConnection) {
        System.out.println("Creating game");
        IMapController controller = new MapController();
        controller.addPlayerConnection(playerConnection);
        this.playerList.add(playerConnection);
        playerConnection.setGame(controller);
        this.gameList.add(controller);
        this.playerMap.put(playerConnection.getAddress(),controller);
        Thread t = new Thread((MapController)controller);
        t.start();

        return true;
    }

    /**
     * checkes if the account matches
     * @param account
     * @return
     */
    private boolean login(Account account) {
        //check if name and password are correct
        return true;
    }



    public ArrayList<PlayerConnection> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<PlayerConnection> playerList) {
        this.playerList = playerList;
    }



    public void handlePackage(PackageBundle packet) {
        MessagePackage messagePackage = packet.getMsg();
        if(messagePackage instanceof LoginMsg){
            this.handleLogin((LoginMsg)messagePackage,packet.getAddress(),null);
        }
        else{
            this.qMessage(packet);

        }
    }


    private void qMessage(PackageBundle packet) {
        try{
      IMapController object =  this.playerMap.get(packet.getAddress());
      if(object != null)
         object.addPacketQ(packet);

        }catch (Exception e){
            System.out.println("");
        }

    }

    public RmiGameController getGameController() {
        return gameController;
    }
}
