package Controllers;

import Logic.Que.PackageBundle;
import Server.PlayerConnection;

import java.rmi.Remote;
import java.util.ArrayList;

public interface IMapController {

    void handlePacket(PackageBundle packet);

    void addPlayerConnection(PlayerConnection playerConnection);
    ArrayList<PlayerConnection> getPlayerList();

    ArrayList<PackageBundle> getPacketQ();

    void addPacketQ(PackageBundle packet);
}
