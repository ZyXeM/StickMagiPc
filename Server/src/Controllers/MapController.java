package Controllers;

import Logic.Messages.*;
import Logic.Model.Interactable;
import Logic.Model.WorldMap;
import Logic.Que.ObjectQueue;
import Logic.Que.PackageBundle;
import Server.PlayerConnection;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapController extends Thread implements IMapController {
    private ArrayList<PlayerConnection> playerList;
    private ArrayList<PlayerConnection> playerListQueue;
    private WorldMap worldMap;
    private ArrayList<PackageBundle> packetQ;
    transient ExecutorService e;
    private ObjectQueue objectQueue;
    private Lock lock = new ReentrantLock();


    public MapController() {
        worldMap = new WorldMap();
        this.playerList = new ArrayList<>();
        this.playerListQueue = new ArrayList<>();
        ArrayList<InetSocketAddress> list = new ArrayList<>();
        for (PlayerConnection p : this.playerList) {
            list.add(p.getAddress());
        }
        this.objectQueue = new ObjectQueue(list);
        objectQueue.start();
        this.packetQ = new ArrayList<>();
        e = Executors.newCachedThreadPool();
    }

    /**
     * Updates the location on the server map and broadcasts it to the other players
     * @param packet
     */
    public void updateLocation(PackageBundle packet) {
        worldMap.updateLocation((UpdateLocationMsg) packet.getMsg());
        this.broadcastMessage(packet);
    }


    /**
     * Updates the rotation on the server map and broadcasts it to the other players
     * @param packet
     */
    public void updateRotation(PackageBundle packet) {
        worldMap.updateRotation((UpdateRotationMsg) packet.getMsg());
        this.broadcastMessage(packet);
    }


    /**
     * adds the Interactable to the server world and sends it to the clients
     * @param packet
     */
    public void addInteractable(PackageBundle packet) {
        worldMap.addInteractable(((AddInteractableMsg)packet.getMsg()).getInteractable());
        for (PlayerConnection p : playerList
                ) {
            try {
                if (packet.getMsg().getPlayerId() == p.getAccount().getID())
                    continue;
                p.getUpdateManager().addInteractableUpdate((AddInteractableMsg) packet.getMsg());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void handlePacket(PackageBundle packet) {
        System.out.println("Handle map package");
        MessagePackage messagePackage = packet.getMsg();
        if (messagePackage instanceof ConfirmationMsg) {
            this.objectQueue.confirmQUpdate(packet);
        }
        if (messagePackage instanceof AddInteractableMsg) {
            this.addInteractable(packet);
        } else if (messagePackage instanceof UpdateLocationMsg) {
            this.updateLocation(packet);
        } else if (messagePackage instanceof UpdateRotationMsg) {
            this.updateRotation(packet);
        } else {
            return;
        }
    }

    public void addPacketQ(PackageBundle msg) {
        try {
            this.lock.lock();
            this.packetQ.add(msg);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void addPlayerConnection(PlayerConnection playerConnection) {

        //sends all current objects in the world to the new player
        for(Interactable i :worldMap.getInteractables()){
            AddInteractableMsg msg = new AddInteractableMsg();
            msg.setInteractable(i);
            try {
                playerConnection.getUpdateManager().addInteractableUpdate(msg);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }

        this.playerList.add(playerConnection);
    }

    @Override
    public void run() {
        System.out.println("Game runs");

        //  Logic.Que.QSendRunnable manager = new Logic.Que.QSendRunnable(this.objectQ);
        // manager.run();
        while (true) {
            try {
                lock.lock();


                Iterator<PackageBundle> it = this.packetQ.iterator();
                while (it.hasNext()) {
                    PackageBundle pk = it.next();
                    this.handlePacket(pk);
                    it.remove();
                }
            } finally {
                lock.unlock();
            }
        }

    }

    public ArrayList<PlayerConnection> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<PlayerConnection> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<PackageBundle> getPacketQ() {
        return packetQ;
    }

    public void setPacketQ(ArrayList<PackageBundle> packetQ) {
        this.packetQ = packetQ;
    }

    /**
     * broadcasts the message to all player except the sender
     *
     * @param packageBundle
     */
    public void broadcastMessage(PackageBundle packageBundle) {
        Runnable run = () -> {
            try {
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                packageBundle.getMsg().setId(8);
                oo.writeObject(packageBundle.getMsg());
                oo.close();
                byte[] serializedMessage = bStream.toByteArray();
                for (PlayerConnection p : this.playerList) {
                    if (p.getAccount().getID() == packageBundle.getMsg().getPlayerId())
                        continue;
                    DatagramSocket datagramSocket = new DatagramSocket(null);
                    DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, p.getAddress().getAddress(), p.getAddress().getPort());
                    datagramSocket.send(packet);
                }


            } catch (Exception e) {
                System.out.println(e.toString());
            }


        };
        e.submit(run);


    }
}
