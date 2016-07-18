package com.mygdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class AssetMusic
{
	public final Music song01;
	
	public AssetMusic(AssetManager am)
	{
		song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
	}

}
