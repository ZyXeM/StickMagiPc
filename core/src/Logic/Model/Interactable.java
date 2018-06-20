package Logic.Model;

import Logic.Enummeration.EType;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;


import java.awt.*;
import java.io.Serializable;
import java.util.List;

public abstract class Interactable implements Serializable{

    private int ID;
    private java.awt.geom.Point2D movement;
    private java.awt.geom.Point2D location;
    private int rotation;
    private int maxHealth;
    private int currentHealth;
    private List<Shape> hitBoxes;
    private WorldMap worldMap;
    private ISessionManager sessionManager;



    /**
     * @param location : current location of the interactable
     * @param rotation : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes : collection of shapes which form the hit-box relative to the origin
     * @param worldMap : map as reference to interact with other interactables
     */
    public Interactable(java.awt.geom.Point2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap,ISessionManager sessionManager) {
        this.location = location;
        this.rotation = rotation;
        this.maxHealth = maxHealth;
        this.hitBoxes = hitBoxes;
        this.worldMap = worldMap;
        this.sessionManager = sessionManager;
    }

    public Interactable() {

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
    public void addForce(java.awt.geom.Point2D force){
       // this.movement.plus(force);
    }
    public abstract void draw(IDrawManager iDrawManager);


    /**
     * applies the current forces
     */
    public void applyPhysics(float deltaTime){

        checkCollide();


     //   this.location.translate(this.movement.x(),this.movement.y());
        this.sessionManager.sendLocation(this);
        this.sessionManager.sendRotation(this);
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
//        for (Shape2D shape:this.hitBoxes){
//            for (Shape2D shape2:interactable.hitBoxes){
//                if(shape.boundingBox().containsBounds(shape2)){
//                    this.collide(interactable);
//                }
//            }
//        }
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


    public java.awt.geom.Point2D getLocation() {
        return location;
    }

    public void setLocation(java.awt.geom.Point2D location) {
        this.location = location;

    }

    public java.awt.geom.Point2D getMovement() {
        return movement;
    }

    public void setMovement(java.awt.geom.Point2D movement) {
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


}
