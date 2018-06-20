package Logic.Model;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import Logic.Interface.IUpdateManager;
import Logic.Messages.AddInteractableMsg;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Session.SessionManager;
import java.awt.geom.Point2D;
import java.util.*;

public class WorldMap implements IUpdateManager {
    private List<Player> players;
    private List<InGameObject> inGameObjects;
    private List<Spell> spellList;
    private IDrawManager drawManager;
    private ISessionManager iSessionManager;

    public WorldMap(IDrawManager drawManager) {
        this.drawManager = drawManager;
        this.iSessionManager = new SessionManager(this);
        players = new ArrayList<>();
        inGameObjects = new ArrayList<>();
        spellList = new ArrayList<>();
        this.iSessionManager.login(new Account("mitch","mitch"));
    }

    /**
     * updates the game logic of the game
     * @param deltaTime
     */
    public void updateGameLogic(float deltaTime){
        Iterator<Player> playerIterator = players.iterator();
        while(playerIterator.hasNext()){
           Player player = playerIterator.next();
           player.update(deltaTime);
        }

        Iterator<InGameObject> inGameObjectIterator = inGameObjects.iterator();
        while(inGameObjectIterator.hasNext()){
            InGameObject player = inGameObjectIterator.next();
            player.update(deltaTime);
        }




    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<InGameObject> getInGameObjects() {
        return inGameObjects;
    }

    public void setInGameObjects(List<InGameObject> inGameObjects) {
        this.inGameObjects = inGameObjects;
    }

    public IDrawManager getDrawManager() {
        return drawManager;
    }

    public void setDrawManager(IDrawManager drawManager) {
        this.drawManager = drawManager;
    }

    public ArrayList<Interactable> getInteractables(){
      ArrayList<Interactable>  list  = new ArrayList<Interactable>();
        list.addAll(this.inGameObjects);
        list.addAll(this.players);
        list.addAll(this.spellList);
        return list;
    }





    /**
     * updates graphics of the game
     * @param deltaTime
     */
    public void render(float deltaTime){
//        for (Player p: this.players){
//            p.draw(this.drawManager);
//        }
//        for (InGameObject i : this.inGameObjects){
//            i.draw(this.drawManager);
//        }
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public void setSpellList(List<Spell> spellList) {
        this.spellList = spellList;
    }

    @Override
    public void addInteractable(AddInteractableMsg interactableMsg) {
        if(interactableMsg.getInteractable() instanceof  Spell){
            this.spellList.add((Spell)interactableMsg.getInteractable());
        }else

        if(interactableMsg.getInteractable() instanceof  Player){
            this.players.add((Player)interactableMsg.getInteractable());

        }else
        if(interactableMsg.getInteractable() instanceof  InGameObject){
            this.inGameObjects.add((InGameObject)interactableMsg.getInteractable());

        }

    }

    @Override
    public void updateLocation(UpdateLocationMsg locationMsg) {
        for(Interactable i : this.getInteractables()){
            if(i.getID() == locationMsg.getInteractableId()){
                i.setLocation(locationMsg.getLocation());
            }
        }

    }

    @Override
    public void updateRotation(UpdateRotationMsg rotationMsg) {
        for(Interactable i : this.getInteractables()){
            if(i.getID() == rotationMsg.getInteractableId()){
                i.setRotation(rotationMsg.getRotation());
            }
        }

    }

    public void walk(EDirection direction) {
        System.out.println("Walk");
        Player dummy = new Player();
        dummy.setLocation(new Point2D.Float(1,1));
        this.iSessionManager.sendLocation(dummy);
    }
}
