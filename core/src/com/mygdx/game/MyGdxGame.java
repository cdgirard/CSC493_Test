package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.asset.Assets;

public class MyGdxGame extends ApplicationAdapter 
{
	private static final String TAG = MyGdxGame.class.getName();
	
    private WorldController worldController;
    private WorldRenderer worldRenderer;
	
	@Override
	public void create() 
	{
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		
	}

	@Override
	public void render() 
	{
	    worldController.update(Gdx.graphics.getDeltaTime());	
        Gdx.gl.glClearColor(0x64/255.0f,0x95/255.0f,0xed/255.0f,0xff/255.0f);  
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
	}
	
	@Override
	public void resize( int width, int height)
	{
		worldRenderer.resize(width, height);
	}
	
	@Override
	public void dispose()
	{
		worldController.dispose();
		worldRenderer.dispose();
		Assets.instance.dispose();
	}
}
