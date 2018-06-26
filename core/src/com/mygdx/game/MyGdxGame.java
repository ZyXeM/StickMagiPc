package com.mygdx.game;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.IInputHandeler;
import Logic.Model.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    IDrawManager iDrawManager;
    OrthographicCamera camera;
    private WorldMap worldMap;

    private Player yourPlayer;
    private IInputHandeler iInputHandeler;
    private int selectedSlot = 0;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;


    @Override
    public void create() {
        batch = new SpriteBatch();
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        iDrawManager = new DrawManager(this.spriteBatch,shapeRenderer);
        worldMap = new WorldMap(this.iDrawManager);
        worldMap.getISessionManager().login(new Account("mitch","mitch"));
        this.iInputHandeler = new InputHandeler(worldMap);
        Gdx.graphics.setVSync(true);
        createTests();

    }

    public void createTests() {
        Player p = new Player(this.worldMap,worldMap.getISessionManager());
        p.setLocation(new Vector2D(1, 1));
        worldMap.addInteractable(p);
        worldMap.setFocusedPlayer(p);
        Spell spell = new Spell(p.getWorldMap(),p.getSessionManager());
        p.getSpellList().add(spell);
        InGameObject object = new InGameObject(this.worldMap,worldMap.getISessionManager());
        object.setSize(new Vector2D(800,20));
        object.setLocation(new Vector2D(-400,-64));
        object.getHitBoxes().add(new Rectangle2D.Float(0,0,800,20));

        InGameObject object2 = new InGameObject(this.worldMap,worldMap.getISessionManager());
        object2.setSize(new Vector2D(20,100));
        object2.setLocation(new Vector2D(-400,-44));
        object2.getHitBoxes().add(new Rectangle2D.Float(0,0,20,100));

        InGameObject object3 = new InGameObject(this.worldMap,worldMap.getISessionManager());
        object3.setSize(new Vector2D(40,40));
        object3.setLocation(new Vector2D(-200,-44));
        object3.getHitBoxes().add(new Rectangle2D.Float(0,0,40,40));
        worldMap.addInteractable(object3);
        worldMap.addInteractable(object);
        worldMap.addInteractable(object2);
        this.yourPlayer = p;
        this.worldMap.getISessionManager().addInteractable(p);


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldMap.updateGameLogic(Gdx.graphics.getDeltaTime());
        camera.position.set((float) yourPlayer.getLocation().x, (float) yourPlayer.getLocation().y, 0);
        camera.update();
        updateInput();



        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        shapeRenderer.begin();
        worldMap.render(Gdx.graphics.getDeltaTime());
        spriteBatch.end();
        shapeRenderer.end();


    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }


    public void updateInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.iInputHandeler.walk(EDirection.LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.iInputHandeler.walk(EDirection.RIGHT);
        } else {
            if (yourPlayer.getWalkingDirection() != EDirection.NONE)

                this.iInputHandeler.walk(EDirection.NONE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.iInputHandeler.jump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            this.iInputHandeler.rotate(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.iInputHandeler.rotate(true);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector2D v1 = new Vector2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            Vector2D v2 = new Vector2D(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            v2.subtract(v1);
            this.iInputHandeler.use(this.selectedSlot, v2);
        }
    }
}
