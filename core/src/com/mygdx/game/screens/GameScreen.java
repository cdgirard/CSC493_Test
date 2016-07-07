package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.WorldController;
import com.mygdx.game.WorldRenderer;

public class GameScreen extends AbstractGameScreen
{
    private static final String TAG = GameScreen.class.getName();
    
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    
    private boolean paused;
    
	public GameScreen(Game g)
	{
		super(g);
	}
	
	@Override
	public void render(float deltaTime)
	{
		worldController.update(deltaTime);

		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRenderer.render();
	}
	
	@Override
	public void resize(int width, int height)
	{
		worldRenderer.resize(width, height);
	}
	
	@Override
	public void show()
	{
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide()
	{
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}


	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
		super.resume();
	}
	

}
