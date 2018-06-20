package Logic.Interface;


import Logic.Model.Player;

public interface IUseable {
   /**
    * tries to use the usable object
    * @param player : player who invokes the spell
    */
   void Use(Player player);
}
