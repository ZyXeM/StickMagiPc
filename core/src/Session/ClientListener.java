package Session;

import Logic.Messages.MessagePackage;
import Logic.Que.PackageBundle;
import Logic.Que.QObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientListener extends Thread {


    SessionManager sessionManager;

    public ClientListener(SessionManager sessionManager) {

        this.sessionManager = sessionManager;
    }

    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receiveData = new byte[1024];

        while (true) {

            try {

                InetAddress IPAddress = InetAddress.getByName("localhost");

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, 2000);
                datagramSocket.receive(receivePacket);
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));
                MessagePackage msg = (MessagePackage) iStream.readObject();
                iStream.close();
                this.sessionManager.handleMessage(msg);


            } catch (Exception e) {

            }


        }
    }


}
