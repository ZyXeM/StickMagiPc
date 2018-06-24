package Logic.Model;

import Logic.Enummeration.EType;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Interactable implements Serializable {

    private int ID;
    private Vector2D movement;
    private List<Force> forces;
    private Vector2D location;
    private int rotation;
    private int maxHealth;
    private int currentHealth;
    private List<Shape> hitBoxes;
    transient WorldMap worldMap;
    transient ISessionManager sessionManager;
    private Vector2D zeroVector = new Vector2D(0, 0);
    private float gravity = -6;
    private boolean goingCollide = false;
    private boolean gravApplied = false;


    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     * @param worldMap  : map as reference to interact with other interactables
     */
    public Interactable(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager sessionManager) {
        this.location = location;
        this.rotation = rotation;
        this.rotation = 0;
        this.maxHealth = maxHealth;
        this.hitBoxes = hitBoxes;
        this.worldMap = worldMap;
        this.sessionManager = sessionManager;
        this.hitBoxes = new ArrayList<>();
        this.forces = new ArrayList<>();
        forces.add(new Force("Gravity", new Vector2D(0, gravity), true));
        this.setID(worldMap.getNexzID());
    }

    public Interactable(WorldMap worldMap, ISessionManager sessionManager) {
        this.worldMap = worldMap;
        this.sessionManager = sessionManager;
        this.hitBoxes = new ArrayList<>();
        this.forces = new ArrayList<>();
        forces.add(new Force("Gravity", new Vector2D(0, gravity), true));
        this.setID(worldMap.getNexzID());
    }

    /**
     * applies damage to the object
     *
     * @param damage
     * @param damageType
     */
    public void hit(float damage, EType damageType) {
        this.currentHealth -= damage;
    }


    /**
     * applies a directional force to an object
     *
     * @param force : direction and amplitude of the force
     */
    public void addForce(Vector2D force,String name,boolean perm) {
        this.forces.add(new Force(name,force,perm));
    }

    public abstract void draw(IDrawManager iDrawManager);


    /**
     * applies the current forces
     */
    public void applyPhysics(float deltaTime) {
      for (Force f : forces){
          checkObjectColl(f.getName());
      }
        if(gravApplied){
            Force f = getForceOnName("Gravity");
            if(!f.getForce().equals(zeroVector)) {
                f.getForce().set(f.getForce().x, f.getForce().y + gravity * deltaTime);
                Force g = getForceOnName("Jump");
                if(g!=null)
                    g.getForce().set(0,0);
            }
            gravApplied = false;

        }
        else{
            Force f = getForceOnName("Gravity");
            f.getForce().set(0,gravity);
        }



        sessionManager.sendLocation(this);









    }

    public boolean checkObjectColl(String name){
        Force f = getForceOnName(name);
        if(f != null){

            Vector2D move = f.getForce();

            Vector2D col2 = new Vector2D(location.x + move.x, location.y + move.y);
            boolean coll = false;
            boolean addVector2 = true;
            for (InGameObject i : this.worldMap.getInGameObjects()) {
                if (isColliding(i, col2)) {
                    addVector2 = false;
                }
                else {
                    coll = true;
                }
            }
            if(addVector2){
                if(name.equals("Gravity")){
                    gravApplied = true;
                }
                this.location.set(col2.x,col2.y);
            }
            return coll;
        }
       return false;
    }


    public Force getForceOnName(String name) {
        for (Force f : getForces()) {
            if (f.getName().equals(name))
                return f;
        }
        return null;
    }


    /**
     * checks if this interactable collides with another
     */
    public void checkCollide() {
        for (Player p : this.worldMap.getPlayers()) {
            if (p != this)
                this.isColliding(p);
        }

        for (Spell s : this.worldMap.getSpellList()) {
            if (s != this)
                this.isColliding(s);
        }
    }

    /**
     * checks if the interactable is collding with target
     *
     * @param interactable
     * @return true if the objects collide
     */
    public boolean isColliding(Interactable interactable) {
        boolean coll = false;
        for (Shape shape : this.hitBoxes) {

            for (Shape shape2 : interactable.hitBoxes) {
                if (getRect(shape, this.getLocation()).intersects(getRect(shape2, interactable.getLocation()))) {

                    this.collide(interactable);
                    coll = true;

                }
            }
        }
        return coll;
    }

    public boolean isColliding(Interactable interactable, Vector2D newMovement) {
        for (Shape shape : this.hitBoxes) {
            for (Shape shape2 : interactable.hitBoxes) {
                if (getRect(shape, newMovement).intersects(getRect(shape2, interactable.getLocation()))) {
                    this.collide(interactable);
                    return  true;
                }
            }
        }
        return false;
    }


    public Rectangle2D getRect(Shape shape, Vector2D location) {

        if (shape instanceof Rectangle2D.Float) {
            Rectangle2D.Float n = (Rectangle2D.Float) shape;
            n.x = (float) location.x;
            n.y = (float) location.y;

            return n;
        }
        return null;
    }


    /**
     * updates the objects internal state
     *
     * @param deltaTime
     */
    abstract void update(float deltaTime);


    /**
     * handles the interaction between two colliding objects
     *
     * @param interactable
     */
    public boolean collide(Interactable interactable) {

        return false;
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

    public void changeRotation(int rotate) {
        int newRotation = (this.rotation + rotate);
        if (newRotation >= 360) {
            setRotation(0 + (newRotation - 360));

        } else if (newRotation < 0) {
            setRotation(360 + newRotation);
        } else
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

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public boolean isGoingCollide() {
        return goingCollide;
    }

    public void setGoingCollide(boolean goingCollide) {
        this.goingCollide = goingCollide;
    }
}
