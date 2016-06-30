package com.mygdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetFonts 
{
    public final BitmapFont defaultSmall;
    public final BitmapFont defaultNormal;
    public final BitmapFont defaultBig;
    
    public AssetFonts()
    {
    	defaultSmall = new BitmapFont(Gdx.files.internal("ui/default.fnt"),true);
    	defaultNormal = new BitmapFont(Gdx.files.internal("ui/default.fnt"),true);
    	defaultBig = new BitmapFont(Gdx.files.internal("ui/default.fnt"),true);
    	
    	defaultSmall.getData().setScale(0.75f);
    	defaultNormal.getData().setScale(1.0f);
    	defaultBig.getData().setScale(2.0f);
    	
    	defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
}
