package Logic.Que;

import Logic.Messages.MessagePackage;

import java.net.InetSocketAddress;
import java.util.ArrayList;


public class QObject {
   private MessagePackage msg;
   private ArrayList<InetSocketAddress> playerList;

    public QObject(MessagePackage msg) {
        this.msg = msg;
        playerList = new ArrayList<InetSocketAddress>();

    }

    public MessagePackage getMsg() {
        return msg;
    }

    public void setMsg(MessagePackage msg) {
        this.msg = msg;
    }


    public ArrayList<InetSocketAddress> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<InetSocketAddress> playerList) {
        this.playerList = playerList;
    }
}
