package Session;

import Logic.Interface.ISessionManager;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SessionManager implements ISessionManager {
    transient ExecutorService e;
    ObjectQueue objectQueue;
    private WorldMap worldMap;
    Thread listener;



    public SessionManager(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.e = Executors.newCachedThreadPool();


        objectQueue = new ObjectQueue(getServerAdres());
        objectQueue.start();
        listener = new Thread(new ClientListener(this));
        listener.start();
    }

    public ArrayList<InetSocketAddress> getServerAdres(){
        InetAddress IPAddress = null;
        try {
            IPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        InetSocketAddress inetSocketAddress = new InetSocketAddress(IPAddress,2000);
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
        System.out.println("Sendlocation");
        UpdateLocationMsg locationMsg = new UpdateLocationMsg();
        locationMsg.setLocation(location.getLocation());
        locationMsg.setId(3);
        locationMsg.setInteractableId(3);

        broadcastToServer(locationMsg);

    }

    @Override
    public void addInteractable(Interactable interactable) {
        AddInteractableMsg interactableMsg = new AddInteractableMsg();
        interactableMsg.setInteractable(interactable);
        PackageBundle packageBundle = new PackageBundle(null,interactableMsg);
        objectQueue.checkQ(packageBundle);

    }

    @Override
    public void handleMessage(MessagePackage messagePackage) {
        System.out.println("Handle map package");

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
        this.broadcastToServer(loginMsg);


    }

    /**
     * Broadcasts the message to the server
     * @param messagePackage
     */
    private void broadcastToServer(MessagePackage messagePackage) {
        System.out.println("broadcastClient");

        Runnable run = () -> {
            try {

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(messagePackage);
                oo.close();
                byte[] serializedMessage = bStream.toByteArray();
                DatagramSocket datagramSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName("localhost");
                DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, IPAddress, 2000);
                System.out.println("PackageSendClient");
                datagramSocket.send(packet);
                System.out.println("PackageSendClient");
                System.out.println(messagePackage.getId());


            } catch (Exception e) {

            }


        };
        e.submit(run);


    }


}
