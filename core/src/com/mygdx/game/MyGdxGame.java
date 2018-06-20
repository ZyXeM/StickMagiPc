package com.mygdx.game;

import Logic.Enummeration.EDirection;
import Logic.Interface.IDrawManager;
import Logic.Interface.IInputHandeler;
import Logic.Model.InputHandeler;
import Logic.Model.Player;
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
	Texture img;
	IDrawManager iDrawManager;
	OrthographicCamera camera;
	private WorldMap worldMap;
	private Player yourPlayer;
	private IInputHandeler iInputHandeler;
	private  int selectedSlot= 0;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("core/assets/badlogic.jpg");
		camera = new OrthographicCamera();
		iDrawManager = new DrawManager(this.batch);
		worldMap = new WorldMap(this.iDrawManager);
		this.iInputHandeler = new InputHandeler(worldMap);

	}

	@Override
	public void render () {
		updateInput();
//		worldMap.updateGameLogic(Gdx.graphics.getDeltaTime());
//		camera.setToOrtho(false,(float)yourPlayer.getLocation().x(),(float)yourPlayer.getLocation().y());
//		worldMap.render(Gdx.graphics.getDeltaTime());
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public WorldMap getWorldMap() {
		return worldMap;
	}

	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	public Player getYourPlayer() {
		return yourPlayer;
	}

	public void setYourPlayer(Player yourPlayer) {
		this.yourPlayer = yourPlayer;
	}

	public void updateInput(){
		if(  Gdx.input.isButtonPressed(Input.Keys.A)){
			this.iInputHandeler.walk(EDirection.LEFT);
		}
		if(  Gdx.input.isButtonPressed(Input.Keys.D)){
			this.iInputHandeler.walk(EDirection.RIGHT);
		}
		if(Gdx.input.isButtonPressed(Input.Keys.SPACE)){
			this.iInputHandeler.jump();
		}
		if(  Gdx.input.isButtonPressed(Input.Keys.Q)){
			this.iInputHandeler.rotate(false);
		}
		if(  Gdx.input.isButtonPressed(Input.Keys.E)){
			this.iInputHandeler.rotate(true);
		}
		if(  Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			this.iInputHandeler.use(this.selectedSlot);
		}
	}
}
