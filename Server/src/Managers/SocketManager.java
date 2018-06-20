package Managers;

import Controllers.IMapController;
import Logic.Messages.MessagePackage;
import Logic.Que.PackageBundle;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class SocketManager extends Thread {



    private InetSocketAddress address;
    private IMapController mapController;


    public SocketManager(InetSocketAddress address, IMapController mapController) {
        this.address = address;
        this.mapController = mapController;
    }

    public void run() {

        byte[] receiveData = new byte[1024];
        DatagramSocket datagramSocket;

        while (true) {
            try {
                System.out.println("SocketManagerCreated");
                datagramSocket = new DatagramSocket(null);
                datagramSocket.bind(address);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                datagramSocket.receive(receivePacket);
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));
                MessagePackage msg = (MessagePackage) iStream.readObject();
                iStream.close();
                System.out.println("2ndPackage");
                this.mapController.handlePacket(new PackageBundle(address,msg));


            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException classNotFoundException) {

            }
        }
    }
}
