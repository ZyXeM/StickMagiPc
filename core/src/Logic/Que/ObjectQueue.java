package Logic.Que;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class ObjectQueue {
    private ArrayList<QObject> objectQ;
    private ArrayList<InetSocketAddress> playerList;

    public ObjectQueue(ArrayList<InetSocketAddress> playerList) {
        this.objectQ = objectQ;
        this.playerList = playerList;
    }

    /**
     * confirmes a player has received a piece of information
     * @param packet
     */
    public void confirmQUpdate(PackageBundle packet) {
        Iterator<QObject> it = objectQ.iterator();
        while(it.hasNext()){
            QObject q = it.next();
            if(q.getMsg().getId() == packet.getMsg().getId()){
                it.remove();
            }
        }
    }

    /**
     * checks if the packet is already in the q else places it;
     * @param packet
     */
    public void checkQ(PackageBundle packet) {
        for (QObject q : objectQ){
            if(packet.getMsg().getId() == q.getMsg().getId()){
                return;
            }
        }
        QObject qObject = new QObject(packet.getMsg());
        for (InetSocketAddress p : this.playerList){
            if(p != packet.getAddress())
                qObject.getPlayerList().add(p);
        }
        this.objectQ.add(qObject);


    }

    public  void start(){
        Thread t = new Thread(new QSendRunnable(this.objectQ));
        t.start();
    }

}
