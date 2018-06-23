package Logic.Interface;

import Logic.Enummeration.EDirection;
import Logic.Model.Vector2D;


public interface IInputHandeler {


    /**
     * tries to move the player into a certain direction
     * @param direction
     * @return Successful
     */
    boolean walk(EDirection direction);


    /**
     * tries to rotate the player towards a side
     * @param LeftRight : false = left, true = right
     * @return Successful
     */
    boolean rotate(boolean LeftRight);


    /**
     * tries to use the item in the specific inventory slot
     * @param inventorySlot : number slot to use within your actionbar starting from the left at 1
     * @return Successful
     */
    boolean use(int inventorySlot, Vector2D direction);
    /**
     * @return
     */
    boolean jump();



}
