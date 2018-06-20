package Logic.Messages;

import java.io.Serializable;

public abstract class MessagePackage implements Serializable {
    private int id;
    private int playerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
