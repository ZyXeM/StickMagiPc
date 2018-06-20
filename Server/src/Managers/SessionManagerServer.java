package Managers;

import Controllers.GameController;
import Logic.Messages.MessagePackage;
import Logic.Que.PackageBundle;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionManagerServer {
    private static final int PORT_NUMBER_LOGIN = 2000;
    private static final int PORT_NUMBER_GAME = 2001;
    private GameController gameController;

    DatagramSocket datagramSocket;
    public boolean createServer() throws SocketException {
        this.gameController = new GameController();
        byte[] receiveData = new byte[1024];

        Logger.getAnonymousLogger().log( Level.INFO,"creating server");
        datagramSocket = new DatagramSocket(PORT_NUMBER_LOGIN);
        while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println("waiting");
                datagramSocket.receive(receivePacket);
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));
                MessagePackage msg = (MessagePackage) iStream.readObject();
                iStream.close();
                gameController.handlePackage(new PackageBundle(new InetSocketAddress(receivePacket.getAddress(),PORT_NUMBER_LOGIN),msg) );
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    }


