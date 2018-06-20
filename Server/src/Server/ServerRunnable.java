package Server;

import Managers.SessionManagerServer;

public class ServerRunnable{

   static private SessionManagerServer sessionManagerServer;

    public static void main (String[] arg) {

        sessionManagerServer = new SessionManagerServer();
        try {
            sessionManagerServer.createServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
