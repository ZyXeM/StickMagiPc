package com.mygdx.game;


import Logic.Interface.IDrawManager;
import Logic.Model.InGameObject;
import Logic.Model.Player;
import Logic.Model.Spell;
import com.badlogic.gdx.graphics.g2d.Batch;

public class DrawManager implements IDrawManager {


    Batch batch;
    public DrawManager(Batch batch) {
        this.batch = batch;
    }

    @Override
    public void drawPlayer(Player player) {

    }



    @Override
    public void drawSpell(Spell spell) {

    }

    @Override
    public void drawInGameObject(InGameObject inGameObject) {

    }
}
