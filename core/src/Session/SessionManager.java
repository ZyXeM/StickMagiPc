package Session;

import Logic.Interface.IGameController;
import Logic.Interface.ISessionManager;
import Logic.Interface.IUpdateManager;
import Logic.Messages.*;
import Logic.Model.Account;
import Logic.Model.Interactable;
import Logic.Model.WorldMap;
import Logic.Que.ObjectQueue;
import Logic.Que.PackageBundle;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SessionManager implements ISessionManager {
    private  Client client;
    transient ExecutorService e;
    ObjectQueue objectQueue;
    private WorldMap worldMap;
    Thread listener;
    IGameController link;
    String hostIP = "192.168.2.14";
    private int playerID = 0;




    public SessionManager(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.e = Executors.newCachedThreadPool();
//        objectQueue = new ObjectQueue(getServerAdres());
//        objectQueue.start();
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry("192.168.2.14", 777);
            link = (IGameController) registry.lookup("IGameController");
            Client c = new Client(this);
            this.client = c;
        } catch (RemoteException ex) {
            System.out.println(ex.toString());
        } catch (NotBoundException ex) {
            System.out.println(ex.toString());
        }


    }

    /**
     * @return Server address
     */
    public ArrayList<InetSocketAddress> getServerAdres(){
        InetAddress IPAddress = null;
        try {
            IPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        InetSocketAddress inetSocketAddress = new InetSocketAddress(hostIP,2000);
        ArrayList<InetSocketAddress> list = new ArrayList<>();
        list.add(inetSocketAddress);
        return list;

    }

    @Override
    public void sendRotation(Interactable rotation) {
        UpdateRotationMsg rotationMsg = new UpdateRotationMsg();
        rotationMsg.setRotation(rotation.getRotation());
        rotationMsg.setInteractableId(rotation.getID());
        broadcastToServer(rotationMsg);


    }

    @Override
    public void sendLocation(Interactable location) {
        UpdateLocationMsg locationMsg = new UpdateLocationMsg();
        locationMsg.setLocation(location.getLocation());
        locationMsg.setId(3);
        locationMsg.setInteractableId(location.getID());
        broadcastToServer(locationMsg);
    }

    @Override
    public void addInteractable(Interactable interactable) {
        AddInteractableMsg interactableMsg = new AddInteractableMsg();
        interactableMsg.setInteractable(interactable);
        PackageBundle packageBundle = new PackageBundle(null,interactableMsg);
        packageBundle.getMsg().setPlayerId(this.playerID);
        try {
            this.link.handlePackage(packageBundle);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
//        objectQueue.checkQ(packageBundle);

    }

    @Override
    public void handleMessage(MessagePackage messagePackage) {


        if(messagePackage instanceof ConfirmationMsg){
            this.objectQueue.confirmQUpdate(new PackageBundle(getServerAdres().get(0),messagePackage));
        }
        if(messagePackage instanceof AddInteractableMsg){
           this.worldMap.addInteractableUpdate((AddInteractableMsg)messagePackage);
        }else
        if(messagePackage instanceof UpdateLocationMsg){
           worldMap.updateLocation((UpdateLocationMsg) messagePackage);
        }else
        if(messagePackage instanceof UpdateRotationMsg){
            this.worldMap.updateRotation((UpdateRotationMsg) messagePackage);
        }else{
            return;
        }

    }

    @Override
    public void login(Account account) {
        LoginMsg loginMsg = new LoginMsg();
        loginMsg.setId(5);
        loginMsg.setAccount(account);
        try {
           int idRange = link.rmiLogin(loginMsg,(IUpdateManager) client);
           this.worldMap.setIdRange(idRange*1000);
            this.playerID = idRange;
           ClientListener l = new ClientListener(this);
           l.setPort(2001+idRange);
            listener = new Thread(l);
            listener.start();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        // this.broadcastToServer(loginMsg);


    }

    @Override
    public void rotationUpdate(UpdateRotationMsg rotation) {
        this.worldMap.updateRotation(rotation);
    }

    @Override
    public void locationUpdate(UpdateLocationMsg location) {
        this.worldMap.updateLocation(location);
    }

    @Override
    public void addInteractableUpdate(AddInteractableMsg interactable) {
        this.worldMap.addInteractableUpdate(interactable);
    }


    /**
     * Broadcasts the message to the server
     * @param messagePackage
     */
    private void broadcastToServer(MessagePackage messagePackage) {

        //Broadcast to the server
        Runnable run = () -> {
            try {
                messagePackage.setPlayerId(this.playerID);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(messagePackage);
                oo.close();
                byte[] serializedMessage = bStream.toByteArray();
                DatagramSocket datagramSocket = new DatagramSocket();
                DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length,getServerAdres().get(0).getAddress() , 2000);
                datagramSocket.send(packet);
            } catch (Exception e) {

            }


        };
        e.submit(run);


    }


}
