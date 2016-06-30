package com.mygdx.game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetBlock 
{
     public static TextureRegion block;
     
     public AssetBlock(Texture tex)
     {
    	 block = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
         block.flip(false, false);
     }
}
