package com.mygdx.game;


import Logic.Enummeration.ETexture;
import Logic.Interface.IDrawManager;
import Logic.Model.InGameObject;
import Logic.Model.Player;
import Logic.Model.Spell;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawManager implements IDrawManager {

    private AssetLoader assets;
    private AssetManager assetManager;
    String p = "core/assets/";
    TextureRegion character;

  private SpriteBatch spriteBatch;
    public DrawManager(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;

        load();
        this.character = new TextureRegion((Texture) assetManager.get(p+"Character.jpg"));

    }

    @Override
    public void drawPlayer(Player player) {
            spriteBatch.draw(character,(int)player.getLocation().x,(int)player.getLocation().y,16,32,32,64,1,1, player.getRotation());
    }



    @Override
    public void drawSpell(Spell spell) {

    }

    @Override
    public void drawInGameObject(InGameObject inGameObject) {

    }

    public void load(){
        assetManager = new AssetManager();

        assetManager.load(p+"Character.jpg", Texture.class);
        assetManager.load(p+"Spell.jpg", Texture.class);
        assetManager.finishLoading();

    }
}
