package Logic.Model;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Player extends Interactable {
    private List<Spell> spellList;
    private int targetDegree;
    private float movementSpeed;

    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     */
    public Player(Point2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager iSessionManager) {
        super(location, rotation, maxHealth, hitBoxes, worldMap,  iSessionManager );
        this.spellList = new ArrayList<>();
    }

    public Player(){

    }



    @Override
    public void draw(IDrawManager iDrawManager) {
        iDrawManager.drawPlayer(this);
    }

    @Override
   public void update(float deltaTime) {

    }

    public void walk(EDirection eDirection){
        switch (eDirection){
            case LEFT:
                break;
            case RIGHT:
                break;
            case UP:
                break;
            case DOWN:
                break;
        }

    }




    public int getTargetDegree() {
        return targetDegree;
    }

    public void setTargetDegree(int targetDegree) {
        this.targetDegree = targetDegree;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
}
