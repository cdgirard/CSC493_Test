package com.mygdx.game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLand 
{
	public static TextureRegion land;
    
    public AssetLand(Texture tex)
    {
   	    land = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
        land.flip(false, false);
    }

}
