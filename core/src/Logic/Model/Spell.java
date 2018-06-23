package Logic.Model;


import Logic.Enummeration.EType;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import Logic.Interface.IUseable;
import Session.SessionManager;
import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm;

import java.awt.*;
import java.util.List;


public class Spell extends Interactable implements IUseable {

    private int damage;
    private EType spellType;

    private int speed;


    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     */
    public Spell(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager iSessionManager) {
        super(location, rotation, maxHealth, hitBoxes, worldMap,iSessionManager);
        setGravity(0);
        getForceOnName("Gravity").getForce().set(0,0);

    }

    public Spell(int damage, EType spellType, WorldMap worldMap, ISessionManager sessionManager) {
        super(worldMap,sessionManager);
        this.damage = damage;
        this.spellType = spellType;

        getForceOnName("Gravity").getForce().set(0,0);
        setGravity(0);
    }

    public Spell(WorldMap worldMap,ISessionManager sessionManager){
        super(worldMap,sessionManager);

    }

    @Override
    public void draw(IDrawManager iDrawManager) {
        iDrawManager.drawSpell(this);
    }

    @Override
    public  void update(float deltaTime) {
        applyPhysics(deltaTime);


    }


    @Override
    public void use(Player player,Vector2D direction) {



        Spell spell = new Spell(10,EType.FIRE,this.getWorldMap(),this.getSessionManager());
        spell.setSpeed(1);
        Vector2D v = new Vector2D(direction);
        v.normalize();
        v.multiply(spell.getSpeed());
        spell.setLocation(player.getLocation().clone());
        spell.getForces().add(new Force("direction",v,true));
        player.getWorldMap().addInteractable(spell);
        getSessionManager().addInteractable(spell);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public EType getSpellType() {
        return spellType;
    }

    public void setSpellType(EType spellType) {
        this.spellType = spellType;
    }



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
