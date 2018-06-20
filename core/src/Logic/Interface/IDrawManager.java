package Logic.Interface;


import Logic.Model.InGameObject;
import Logic.Model.Player;
import Logic.Model.Spell;

public interface IDrawManager {
    /**
     * draws the player on the screen
     * @param player
     */
    void drawPlayer(Player player);



    /**
     * draws the spell on the screen
     * @param spell
     */
    void drawSpell(Spell spell);


    /**
     * draws object to the screen
     * @param inGameObject
     */
    void drawInGameObject(InGameObject inGameObject);
}
