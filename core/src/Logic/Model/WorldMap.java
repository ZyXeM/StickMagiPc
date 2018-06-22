package Logic.Model;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import Logic.Interface.IUpdateManager;
import Logic.Messages.AddInteractableMsg;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Session.SessionManager;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldMap implements IUpdateManager {
    private List<Player> players;
    private List<InGameObject> inGameObjects;
    private List<Spell> spellList;
    private IDrawManager drawManager;
    private ISessionManager iSessionManager;
    private Player focusedPlayer;

    public WorldMap(IDrawManager drawManager) {
        this.drawManager = drawManager;
        this.iSessionManager = new SessionManager(this);
        players = new ArrayList<>();
        inGameObjects = new ArrayList<>();
        spellList = new ArrayList<>();
        this.iSessionManager.login(new Account("mitch","mitch"));
    }

    public WorldMap() {

        players = new ArrayList<>();
        inGameObjects = new ArrayList<>();
        spellList = new ArrayList<>();

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

//        Iterator<InGameObject> inGameObjectIterator = inGameObjects.iterator();
//        while(inGameObjectIterator.hasNext()){
//            InGameObject player = inGameObjectIterator.next();
//            player.update(deltaTime);
//        }




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

    public void addInteractable(Interactable interactable){
        interactable.setSessionManager(this.iSessionManager);
        if(interactable instanceof  Spell){
            this.spellList.add((Spell)interactable);
        }else

        if(interactable instanceof  Player){
            this.players.add((Player)interactable);

        }else
        if(interactable instanceof  InGameObject){
            this.inGameObjects.add((InGameObject)interactable);

        }

    }



    /**
     * updates graphics of the game
     * @param deltaTime
     */
    public void render(float deltaTime){
        for (Player p: this.players){
            p.draw(this.drawManager);
        }
        for (InGameObject i : this.inGameObjects){
            i.draw(this.drawManager);
        }
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public void setSpellList(List<Spell> spellList) {
        this.spellList = spellList;
    }

    @Override
    public void addInteractableUpdate(AddInteractableMsg interactableMsg) {
            addInteractable(interactableMsg.getInteractable());

    }

    @Override
    public void updateLocation(UpdateLocationMsg locationMsg) {

        for(Interactable i : this.getInteractables()){
            System.out.println("UpdateFor");
            if(i.getID() == locationMsg.getInteractableId()){
                System.out.println("Update");
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
        this.focusedPlayer.setWalkingDirection(direction);
    }

    public Player getFocusedPlayer() {
        return focusedPlayer;
    }

    public void setFocusedPlayer(Player focusedPlayer) {
        this.focusedPlayer = focusedPlayer;
    }
}
