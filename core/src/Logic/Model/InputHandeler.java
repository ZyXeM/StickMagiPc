package Logic.Model;


import Logic.Enummeration.EDirection;
import Logic.Interface.IInputHandeler;




public class InputHandeler implements IInputHandeler {
    private WorldMap worldMap;

    public InputHandeler(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public boolean walk(EDirection direction) {
        worldMap.walk(direction);
        return true;
    }

    @Override
    public boolean rotate(boolean LeftRight) {
        return false;
    }

    @Override
    public boolean use(int inventorySlot, Vector2D direction) {
        worldMap.getFocusedPlayer().use(direction);
        return true;
    }


    @Override
    public boolean jump() {
        worldMap.getFocusedPlayer().jump();
        return true;
    }


    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
}
