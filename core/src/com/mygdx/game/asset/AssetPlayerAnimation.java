package com.mygdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetPlayerAnimation 
{
    public static TextureRegion[] rightChar;
    public static TextureRegion[] downChar;
    public static TextureRegion[] leftChar;
    public static TextureRegion[] upChar;

    public static Animation downAnimation;
    public static Animation leftAnimation;
    public static Animation rightAnimation;
    public static Animation upAnimation;

	
    public AssetPlayerAnimation(TextureAtlas charAtlas)
    {
// Now set the graphic for our cell to our newly created region
        
        downChar = new TextureRegion[8];
        
        for (int x=0;x<8;x++)
        {
            downChar[x] = charAtlas.findRegion("000"+x);
            downChar[x].flip(false, true);
            
        }
        downAnimation = new Animation(0.06f, downChar);
        downAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        leftChar = new TextureRegion[8];
        
        for (int x=8;x<16;x++)
        {
            String xStr = ""+x;
            if (x < 10)
                xStr = "0"+x;
            leftChar[x-8] = charAtlas.findRegion("00"+xStr);
            leftChar[x-8].flip(false, true);
        }
        leftAnimation = new Animation(0.06f, leftChar);
        leftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        rightChar = new TextureRegion[8];
        
        for (int x=16;x<24;x++)
        {
            rightChar[x-16] = charAtlas.findRegion("00"+x);
            rightChar[x-16].flip(false, true);
        }
        rightAnimation = new Animation(0.06f, rightChar);
        rightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        upChar = new TextureRegion[8];
        
        for (int x=24;x<32;x++)
        {
            upChar[x-24] = charAtlas.findRegion("00"+x);
            upChar[x-24].flip(false, true);
        }
        upAnimation = new Animation(0.06f, upChar);
        upAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }


}
