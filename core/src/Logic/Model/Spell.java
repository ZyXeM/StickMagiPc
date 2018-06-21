package Logic.Model;


import Logic.Enummeration.EType;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import Logic.Interface.IUseable;
import java.awt.*;
import java.util.List;


public class Spell extends Interactable implements IUseable {

    private int damage;
    private EType spellType;

    /**
     * @param location  : current location of the interactable
     * @param rotation  : current rotation of the interactable
     * @param maxHealth : initial health of the interactable
     * @param hitBoxes  : collection of shapes which form the hit-box relative to the origin
     */
    public Spell(Vector2D location, int rotation, int maxHealth, List<Shape> hitBoxes, WorldMap worldMap, ISessionManager iSessionManager) {
        super(location, rotation, maxHealth, hitBoxes, worldMap,iSessionManager);
    }


    @Override
    public void draw(IDrawManager iDrawManager) {
        iDrawManager.drawSpell(this);
    }

    @Override
    public  void update(float deltaTime) {

    }


    @Override
    public void Use(Player player) {

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
}
