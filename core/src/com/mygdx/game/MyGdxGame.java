package com.mygdx.game;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.IInputHandeler;
import Logic.Model.InputHandeler;
import Logic.Model.Player;
import Logic.Model.Vector2D;
import Logic.Model.WorldMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	IDrawManager iDrawManager;
	OrthographicCamera camera;
	private WorldMap worldMap;

	private Player yourPlayer;
	private IInputHandeler iInputHandeler;
	private  int selectedSlot= 0;
	private SpriteBatch spriteBatch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		iDrawManager = new DrawManager(this.spriteBatch);
		worldMap = new WorldMap(this.iDrawManager);
		this.iInputHandeler = new InputHandeler(worldMap);
		createTests();

	}

	public  void createTests(){
		Player p = new Player();
		p.setID(1);
		p.setLocation(new Vector2D(1,1));
		worldMap.addInteractable(p);
		worldMap.setFocusedPlayer(p);
		this.yourPlayer = p;

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateInput();
		worldMap.updateGameLogic(Gdx.graphics.getDeltaTime());
		camera.setToOrtho(false,(float)yourPlayer.getLocation().x,(float)yourPlayer.getLocation().y);
		spriteBatch.begin();
		worldMap.render(Gdx.graphics.getDeltaTime());
		spriteBatch.end();



	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	public WorldMap getWorldMap() {
		return worldMap;
	}

	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}



	public void updateInput(){
		if(  Gdx.input.isKeyPressed(Input.Keys.A)){
			this.iInputHandeler.walk(EDirection.LEFT);
		}
		else
		if(  Gdx.input.isKeyPressed(Input.Keys.D)){
			this.iInputHandeler.walk(EDirection.RIGHT);
		}
		else{
			if(yourPlayer.getWalkingDirection() != EDirection.NONE)

		   	 this.iInputHandeler.walk(EDirection.NONE);
        }

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			this.iInputHandeler.jump();
		}

		if(  Gdx.input.isKeyPressed(Input.Keys.Q)){
			this.iInputHandeler.rotate(false);
		}
		if(  Gdx.input.isKeyPressed(Input.Keys.E)){
			this.iInputHandeler.rotate(true);
		}
		if(  Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			this.iInputHandeler.use(this.selectedSlot);
		}
	}
}
