package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.screens.MenuScreen;

public class MyGdxGame extends Game 
{
	private static final String TAG = MyGdxGame.class.getName();
	
    private WorldController worldController;
    private WorldRenderer worldRenderer;
	
	@Override
	public void create() 
	{
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		setScreen(new MenuScreen(this));
		
	}
}
