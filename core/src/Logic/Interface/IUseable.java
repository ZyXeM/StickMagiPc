package Logic.Interface;


import Logic.Model.Player;
import Logic.Model.Vector2D;

public interface IUseable {
   /**
    * tries to use the usable object
    * @param player : player who invokes the spell
    */
   void use(Player player, Vector2D direction);
}
