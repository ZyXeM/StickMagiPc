package Logic.Model;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Player extends Interactable {
    private  float useCoolDown = 5;
    private float currentCoolDown = 10;
    private List<Spell> spellList;
    private int targetDegree;
    private float movementSpeed = 8;
    private EDirection walkingDirection = EDirection.NONE;
    private boolean canJump = true;
    private int selectedSlot = 0;
    private boolean colliding = false;
    private int jumpHeigt = 40;


    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     */
    public Player(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager iSessionManager) {
        super(location, rotation, maxHealth, hitBoxes, worldMap, iSessionManager);
        this.spellList = new ArrayList<>();
    }

    public Player(WorldMap worldMap,ISessionManager iSessionManager) {
        super(worldMap,iSessionManager);
        this.spellList = new ArrayList<>();
        this.getHitBoxes().add(new Rectangle2D.Float(0,0,32, 64));

    }

    @Override
    public boolean collide(Interactable interactable) {
        if (interactable instanceof InGameObject) {
            canJump = true;
  //          setGoingCollide(true);
            return true;
//            getForceOnName("Gravity").getForce().set(0, 0);
//            if(getForceOnName("Jump")!= null)
//                getForceOnName("Jump").getForce().set(0, 0);

        }
    return false;
    }


    public void jump() {

        if (canJump) {
            Force f = getForceOnName("Jump");
            if(getForceOnName("Jump") == null)
             this.getForces().add(new Force("Jump",new Vector2D(0,jumpHeigt),true));
            else{
                f.getForce().set(0,jumpHeigt);
            }
            canJump = false;

        }
    }


    @Override
    public void draw(IDrawManager iDrawManager) {
        iDrawManager.drawPlayer(this);
    }

    @Override
    public void update(float deltaTime) {

        walk();
        applyPhysics(deltaTime);
        if(isGoingCollide()){
            if(getForceOnName("Jump")!= null)
             getForceOnName("Jump").getForce().set(0, 0);

        }
        colliding = false;
        currentCoolDown += deltaTime;
     //   checkCollide();





    }

    public void walk() {
        boolean present = false;
        Force x = null;
        for (Force f : this.getForces()) {
            if (f.getName().equals("Walk")) {
                x = f;
                present = true;
            }
        }

        if (!present) {
            x = new Force("Walk", new Vector2D(0, 0), true);
            this.getForces().add(x);
        }
        switch (this.walkingDirection) {
            case LEFT:
                x.getForce().set(-1, 0);
                x.getForce().multiply(this.movementSpeed);
                break;
            case RIGHT:
                x.getForce().set(1, 0);
                x.getForce().multiply(this.movementSpeed);
                break;
            case NONE:
                x.getForce().set(0, 0);
                break;
            case UP:
                break;
            case DOWN:
                break;
        }

    }

    public void use(Vector2D direction) {
        if(currentCoolDown > useCoolDown){
            currentCoolDown = 0;
            this.getSpellList().get(this.selectedSlot).use(this, direction);
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

    private boolean isCanJump() {
        return canJump;
    }

    private void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public void setSpellList(List<Spell> spellList) {
        this.spellList = spellList;
    }
}
