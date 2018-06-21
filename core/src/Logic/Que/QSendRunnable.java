package Logic.Que;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QSendRunnable extends Thread {


    private ArrayList<QObject> objectQ;
    transient ExecutorService e;

    public QSendRunnable(ArrayList<QObject> objectQ) {
        this.objectQ = objectQ;
        e = Executors.newCachedThreadPool();

    }

    public void run() {


        while (true) {
            try {
                this.sleep(200);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                Iterator<QObject> it = objectQ.iterator();
                while (it.hasNext()) {
                    QObject object = it.next();

                        try {
                            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                            ObjectOutput oo = new ObjectOutputStream(bStream);
                            oo.writeObject(object.getMsg());
                            oo.close();
                            byte[] serializedMessage = bStream.toByteArray();
                            for (InetSocketAddress p : object.getPlayerList()) {
                                DatagramSocket datagramSocket = new DatagramSocket(null);
                                //datagramSocket.bind(p);
                                DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length,p.getAddress(),p.getPort()+1);
                                datagramSocket.send(packet);
                            }


                        } catch (Exception e) {

                        }


                    }





            } catch (Exception e) {

            }
        }
    }

    public ArrayList<QObject> getObjectQ() {
        return objectQ;
    }

    public void setObjectQ(ArrayList<QObject> objectQ) {
        this.objectQ = objectQ;
    }
}
