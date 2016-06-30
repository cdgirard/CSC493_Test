package com.mygdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.Constants;

public class Assets implements Disposable, AssetErrorListener
{

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	public AssetPlayerAnimation player;
	public AssetBlock block;
	public AssetLand land;
	public AssetFonts fonts;
	private AssetManager assetManager;
	
	private Assets()
	{
		
	}
	
	public void init(AssetManager manager)
	{
		assetManager = manager;
		assetManager.setErrorListener(this);
		assetManager.load(Constants.TEXTURE_ATLAS_PLAYER, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_BLOCK,Texture.class);
		assetManager.load(Constants.TEXTURE_LAND,Texture.class);
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assests loaded: "+assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: "+a);
		
		fonts = new AssetFonts();
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_PLAYER);
		player = new AssetPlayerAnimation(atlas);
		
		Texture tex = assetManager.get(Constants.TEXTURE_BLOCK);
        block = new AssetBlock(tex);
        
        tex = assetManager.get(Constants.TEXTURE_LAND);
        land = new AssetLand(tex);
	}
	
	@Override
	public void dispose()
	{
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}
	
//	@Override
//	public void error(String filename, Class type, Throwable throwable)
//	{
//		Gdx.app.error(TAG, "Couldn't load asset '"+filename+"'", (Exception)throwable);
//	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable)
	{
		Gdx.app.error(TAG, "Couldn't load asset '"+asset.fileName+"'", (Exception)throwable);
	}
}


