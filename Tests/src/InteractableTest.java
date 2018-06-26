import Logic.Interface.ISessionManager;
import Logic.Model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class InteractableTest {
    WorldMap worldMap;
    ISessionManager iSessionManager;
    @BeforeEach
    public void generateTestEnvironment(){
        //Generate a begin scenario with a world and a player
        worldMap = new WorldMap();
        //Generate fake SessionManager to prevent crashes
        iSessionManager = new TestSessionManager(worldMap);
        worldMap.setISessionManager(this.iSessionManager);
        Player p = new Player(this.worldMap,this.iSessionManager);
        ArrayList<Shape> hitboxes = new ArrayList<>();
        hitboxes.add(new Rectangle2D.Float(0,0,32,64));
        p.setHitBoxes(hitboxes);
        worldMap.addInteractable(p);
        worldMap.setFocusedPlayer(p);
    }

    @Test
    public void testAddInteractable(){
        //Tests if the interactable will be added properly
        Interactable interactable = new InGameObject(worldMap,iSessionManager);
        worldMap.addInteractable(interactable);
        Interactable test = worldMap.getInteractables().get(0);
        Assertions.assertEquals(interactable,test,"Interactable is not properly added");
    }
    @Test
    public void testApplyPhysicsForces(){
        //Adding force
        worldMap.getFocusedPlayer().addForce(new Vector2D(0,-6),"Random Force",true);
        worldMap.getFocusedPlayer().setLocation(new Vector2D(0,0));
        //Updating the gamelogic with 1 tick
        worldMap.updateGameLogic(1);
        Vector2D test = worldMap.getFocusedPlayer().getLocation();
        Vector2D location = new Vector2D(0,-6+worldMap.getFocusedPlayer().getGravity());
        Assertions.assertTrue(location.equals(test),"Gravity does not work like it should");

    }
    @Test
    public void testApplyPhysicsCollide(){
        //Adding of the force
        worldMap.getFocusedPlayer().addForce(new Vector2D(0,-6),"Random Force",true);
        worldMap.getFocusedPlayer().setLocation(new Vector2D(0,0));
        //Adding of in game obstacle
        InGameObject i = new InGameObject(this.worldMap,this.iSessionManager);
        i.setLocation(new Vector2D(-50,-50));
        i.setSize(new Vector2D(100,50));
        //Adding hitboxes for obstacle
        ArrayList<Shape> hitboxes = new ArrayList<>();
        hitboxes.add(new Rectangle2D.Float(0,0,100,50));
        i.setHitBoxes(hitboxes);
        worldMap.addInteractable(i);
        worldMap.updateGameLogic(1);
        Vector2D test = worldMap.getFocusedPlayer().getLocation();
        //the object should not have moved so should be equal to 0
        Vector2D location = new Vector2D(0,0);
        Assertions.assertTrue(location.equals(test),"There is an incorrect collision");

    }

    @Test
    public void testChangeRotation(){
        //rotating through 360 degree
        worldMap.getFocusedPlayer().changeRotation(350);
        Assertions.assertEquals(worldMap.getFocusedPlayer().getRotation(),350);
        worldMap.getFocusedPlayer().changeRotation(20);
        Assertions.assertEquals(worldMap.getFocusedPlayer().getRotation(),10);
        worldMap.getFocusedPlayer().changeRotation(-30);
        Assertions.assertEquals(worldMap.getFocusedPlayer().getRotation(),340);

    }


}
