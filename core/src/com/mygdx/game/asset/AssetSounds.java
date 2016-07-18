package com.mygdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class AssetSounds
{
	public final Sound jump;
	public final Sound jumpWithFeather;
	public final Sound pickupCoin;
	public final Sound pickupFeather;
	public final Sound liveLost;
	
	public AssetSounds(AssetManager am)
	{
		jump = am.get("sounds/jump.wav", Sound.class);
		jumpWithFeather = am.get("sounds/jump_with_feather.wav", Sound.class);
		pickupCoin = am.get("sounds/pickup_coin.wav", Sound.class);
		pickupFeather = am.get("sounds/pickup_feather.wav", Sound.class);
		liveLost = am.get("sounds/live_lost.wav", Sound.class);
	}
	

}
