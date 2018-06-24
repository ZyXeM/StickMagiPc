package Session;

import Logic.Messages.MessagePackage;
import java.io.*;
import java.net.*;

public class ClientListener extends Thread {


    SessionManager sessionManager;
    String hostIP = "192.168.2.14";

    public ClientListener(SessionManager sessionManager) {

        this.sessionManager = sessionManager;
    }

    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(2001);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receiveData = new byte[1024];
        System.out.println("ClientListener is running");
        while (true) {

            try {
                InetSocketAddress i = new InetSocketAddress(hostIP,2001);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, i.getAddress(), 2001);
                datagramSocket.receive(receivePacket);
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));

                MessagePackage msg = (MessagePackage) iStream.readObject();
                iStream.close();

                this.sessionManager.handleMessage(msg);


            } catch (Exception e) {
                System.out.println(e.toString());
            }


        }
    }


}
