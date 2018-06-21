package com.mygdx.game;

import Logic.Enummeration.ETexture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    private TextureRegion character,spell;
    String p = "core/assets/";
    AssetManager asset;

    public void load(){
        asset = new AssetManager();
       // asset.
        //character = new TextureAtlas(p+"Character");
        //spell = new TextureAtlas(p+"Spell");
    }

    public TextureRegion getTexture(ETexture eTexture) {
       switch (eTexture){
           case Character:
               return character;
           case Spell:
               return spell;

               default:
                   return null;

       }
    }
}
