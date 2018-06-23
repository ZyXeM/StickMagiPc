package Logic.Model;


import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import java.awt.*;
import java.util.List;

public class InGameObject extends Interactable {
    private Vector2D size;


    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     */
    public InGameObject(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager iSessionManager) {
        super(location, rotation, maxHealth, hitBoxes, worldMap,iSessionManager);
    }
    public InGameObject(WorldMap worldMap,ISessionManager iSessionManager){
        super(worldMap,iSessionManager);
        this.setGravity(0);

    }

    @Override
    public void draw(IDrawManager iDrawManager) {
        iDrawManager.drawInGameObject(this);
    }

    @Override
    public void update(float deltaTime) {

    }


    public Vector2D getSize() {
        return size;
    }

    public void setSize(Vector2D size) {
        this.size = size;
    }
}
