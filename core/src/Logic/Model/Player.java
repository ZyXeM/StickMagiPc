package Logic.Model;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Interactable {
    private List<Spell> spellList;
    private int targetDegree;
    private float movementSpeed = 8;
    private EDirection walkingDirection = EDirection.NONE;

    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     */
    public Player(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager iSessionManager) {
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
        walk();
       // checkCollide();
        applyPhysics(deltaTime);


    }

    public void walk(){
        boolean present = false;
        Force x = null;
        for (Force f : this.getForces()){
            if(f.getName().equals("Walk")){
                x = f;
                present = true;
            }
        }

        if(!present){
            x = new Force("Walk",new Vector2D(0,0),true);
            this.getForces().add(x);
        }
        switch (this.walkingDirection){
            case LEFT:
                System.out.println("walking left");
                x.getForce().set(-1,0);
                x.getForce().multiply(this.movementSpeed);
                break;
            case RIGHT:
                System.out.println("walking left");
                x.getForce().set(1,0);
                x.getForce().multiply(this.movementSpeed);
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

    public EDirection getWalkingDirection() {
        return walkingDirection;
    }

    public void setWalkingDirection(EDirection walkingDirection) {
        this.walkingDirection = walkingDirection;
    }
}
