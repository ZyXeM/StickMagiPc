package Server;

import Controllers.IMapController;
import Logic.Model.Account;

import java.net.InetSocketAddress;

public class PlayerConnection {
    private InetSocketAddress address;
    private Account account;
    private IMapController game;

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public IMapController getGame() {
        return game;
    }

    public void setGame(IMapController game) {
        this.game = game;
    }
}
