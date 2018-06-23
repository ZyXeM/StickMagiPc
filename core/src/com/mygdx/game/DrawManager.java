package com.mygdx.game;


import Logic.Enummeration.ETexture;
import Logic.Interface.IDrawManager;
import Logic.Model.InGameObject;
import Logic.Model.Player;
import Logic.Model.Spell;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DrawManager implements IDrawManager {

    private AssetLoader assets;
    private AssetManager assetManager;
    String p = "core/assets/";
    TextureRegion character,spell;
    private ShapeRenderer shapeRenderer;

  private SpriteBatch spriteBatch;
    public DrawManager(SpriteBatch spriteBatch,ShapeRenderer shapeRenderer) {
        this.spriteBatch = spriteBatch;
       this.shapeRenderer = shapeRenderer;

        load();
        this.character = new TextureRegion((Texture) assetManager.get(p+"Character.jpg"));
        this.spell = new TextureRegion((Texture) assetManager.get(p+"Spell.png"));

    }

    @Override
    public void drawPlayer(Player player) {
            spriteBatch.draw(character,(int)player.getLocation().x,(int)player.getLocation().y,16,32,32,64,1,1, player.getRotation());
    }



    @Override
    public void drawSpell(Spell spell) {
            spriteBatch.draw(this.spell,(int)spell.getLocation().x,(int)spell.getLocation().y,16,16,32,32,1,1, spell.getRotation());

    }

    @Override
    public void drawInGameObject(InGameObject inGameObject) {


        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect((float)inGameObject.getLocation().x,(float)inGameObject.getLocation().y, (float)inGameObject.getSize().x, (float)inGameObject.getSize().y);



    }

    public void load(){
        assetManager = new AssetManager();

        assetManager.load(p+"Character.jpg", Texture.class);
        assetManager.load(p+"Spell.png", Texture.class);
        assetManager.finishLoading();

    }
}
