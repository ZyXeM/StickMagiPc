package Logic.Model;

import Logic.Enummeration.EType;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Interactable implements Serializable{

    private int ID;
    private Vector2D movement;
    private List<Force> forces;
    private Vector2D location;
    private int rotation;
    private int maxHealth;
    private int currentHealth;
    private List<Shape> hitBoxes;
    private WorldMap worldMap;
    private ISessionManager sessionManager;
    private Vector2D zeroVector = new Vector2D(0,0);



    /**
     * @param location : current location of the interactable
     * @param rotation : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes : collection of shapes which form the hit-box relative to the origin
     * @param worldMap : map as reference to interact with other interactables
     */
    public Interactable(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager sessionManager) {
        this.location = location;
        this.rotation = rotation;
        this.rotation = 0;
        this.maxHealth = maxHealth;
        this.hitBoxes = hitBoxes;
        this.worldMap = worldMap;
        this.sessionManager = sessionManager;
        this.hitBoxes  = new ArrayList<>();
        this.forces = new ArrayList<>();
    }

    public Interactable() {
        this.hitBoxes  = new ArrayList<>();
        this.forces = new ArrayList<>();

    }

    /**
     * applies damage to the object
     * @param damage
     * @param damageType
     */
    public void hit(float damage, EType damageType){
        this.currentHealth -= damage;
    }


    /**
     * applies a directional force to an object
     * @param force : direction and amplitude of the force
     */
    public void addForce(Vector2D force){
        this.movement.add(force);
    }
    public abstract void draw(IDrawManager iDrawManager);


    /**
     * applies the current forces
     */
    public void applyPhysics(float deltaTime){
        movement = zeroVector.clone();
        Iterator<Force> it = this.forces.iterator();
        while(it.hasNext()){
            Force force = it.next();
            if(force.getForce().equals(zeroVector) && !force.isPermanent()){
                it.remove();
            }
            else{
                if(!force.getForce().equals(zeroVector)){
                     movement.add(force.getForce());
                }
            }

        }

        if(!movement.equals(zeroVector)){

        this.location.add(this.movement.x,this.movement.y);
        sessionManager.sendLocation(this);
        }
    }

    /**
     * checks if this interactable collides with another
     */
    public void checkCollide(){
        for (Player p: this.worldMap.getPlayers()){
                this.isColliding(p);
        }
        for (InGameObject i: this.worldMap.getInGameObjects()){
            this.isColliding(i);
        }
        for (Spell s: this.worldMap.getSpellList()){
            this.isColliding(s);
        }

    }

    /**
     * checks if the interactable is collding with target
     * @param interactable
     * @return true if the objects collide
     */
    public  boolean isColliding(Interactable interactable){
        for (Shape shape:this.hitBoxes){
            for (Shape shape2:interactable.hitBoxes){
                if(shape.contains(shape2.getBounds2D())){
                    this.collide(interactable);
                }
            }
        }
           return false;
    }



    /**
     * updates the objects internal state
     * @param deltaTime
     */
    abstract void update(float deltaTime);


    /**
     * handles the interaction between two colliding objects
     * @param interactable
     */
    public void collide(Interactable interactable){



    }


    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;

    }

    public Vector2D getMovement() {
        return movement;
    }

    public void setMovement(Vector2D movement) {
        this.movement = movement;
    }


    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;

    }

    public List<Shape> getHitBoxes() {
        return hitBoxes;
    }

    public void setHitBoxes(List<Shape> hitBoxes) {
        this.hitBoxes = hitBoxes;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void changeRotation(int rotate){
       int newRotation = (this.rotation + rotate);
        if(newRotation >= 360){
            setRotation(0+(newRotation-360));

        }else
        if(newRotation < 0){
            setRotation(360 + newRotation);
        }else
            this.setRotation(newRotation);
    }


    public ISessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public List<Force> getForces() {
        return forces;
    }

    public void setForces(List<Force> forces) {
        this.forces = forces;
    }
}
