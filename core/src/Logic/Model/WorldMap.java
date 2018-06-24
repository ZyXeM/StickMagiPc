package Logic.Model;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.ISessionManager;
import Logic.Messages.AddInteractableMsg;
import Logic.Messages.UpdateLocationMsg;
import Logic.Messages.UpdateRotationMsg;
import Session.SessionManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldMap {
    private List<Player> players;
    private List<InGameObject> inGameObjects;
    private List<Spell> spellList;
    private List<Interactable> drawables;
    private IDrawManager drawManager;
    private ISessionManager iSessionManager;
    private Player focusedPlayer;
    private static int idRange;

    public WorldMap(IDrawManager drawManager) {
        this.drawManager = drawManager;
        this.iSessionManager = new SessionManager(this);
        players = new ArrayList<>();
        inGameObjects = new ArrayList<>();
        spellList = new ArrayList<>();
        drawables = new ArrayList<>();

    }

    public WorldMap() {

        players = new ArrayList<>();
        inGameObjects = new ArrayList<>();
        spellList = new ArrayList<>();
        drawables = new ArrayList<>();

    }

    public int getNexzID(){
        idRange ++;
        return idRange;
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

        Iterator<Spell> spellIterator = spellList.iterator();
        while(spellIterator.hasNext()){
            Spell spell = spellIterator.next();
            spell.update(deltaTime);
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
        list.addAll(this.drawables);
        return list;
    }

    public void addInteractable(Interactable interactable){
        interactable.setSessionManager(this.iSessionManager);
        interactable.setWorldMap(this);
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

        for (InGameObject i : this.inGameObjects){
            i.draw(this.drawManager);
        }
        for (Spell i : this.spellList){
            i.draw(this.drawManager);
        }
        for (Player p: this.players){
            p.draw(this.drawManager);
        }
        for (Interactable p: this.drawables){
            p.draw(this.drawManager);
        }
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public void setSpellList(List<Spell> spellList) {
        this.spellList = spellList;
    }


    public void addInteractableUpdate(AddInteractableMsg interactableMsg) {
           // addInteractable(interactableMsg.getInteractable());
            drawables.add(interactableMsg.getInteractable());

    }


    public void updateLocation(UpdateLocationMsg locationMsg) {

        for(Interactable i : this.getInteractables()){
            if(i.getID() == locationMsg.getInteractableId()){
                i.setLocation(locationMsg.getLocation());
            }
        }

    }


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

    public ISessionManager getISessionManager() {
        return iSessionManager;
    }
    public void setISessionManager(ISessionManager iSessionManager) {
        this.iSessionManager = iSessionManager;
    }

    public void setIdRange(int idRange) {
        this.idRange = idRange;
    }

    public int getIdRange() {
        return idRange;
    }

    public void rotate(boolean leftRight) {
        if(leftRight){
            getFocusedPlayer().changeRotation(2);

        }
        else{
            getFocusedPlayer().changeRotation(-2);
        }

    }
}
