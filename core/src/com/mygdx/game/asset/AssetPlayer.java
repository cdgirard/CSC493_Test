package com.mygdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetPlayer 
{
    public static TextureRegion player;

   // public final Animation downAnimation;
    public final Animation leftAnimation;
    public final Animation rightAnimation;
    public final Animation restingAnimation;
   // public final Animation upAnimation;

    
	public AssetPlayer(TextureAtlas atlas)
	{
		player = atlas.findRegion("player", 8);
		Array<AtlasRegion> regions = new Array<AtlasRegion>();

		for (int x = 8; x < 16; x++)
		{
			regions.add(atlas.findRegion("player", x));
		}

		leftAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);

		regions = new Array<AtlasRegion>();
		for (int x = 16; x < 24; x++)
		{
			regions.add(atlas.findRegion("player", x));
		}
		rightAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);

		regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("player",0));
		restingAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		
	}
	
//    public AssetPlayerAnimation(TextureAtlas charAtlas)
//    {
//// Now set the graphic for our cell to our newly created region
//        
//        downChar = new TextureRegion[8];
//        
//        for (int x=0;x<8;x++)
//        {
//            downChar[x] = charAtlas.findRegion("000"+x);
//            downChar[x].flip(false, true);
//            
//        }
//        downAnimation = new Animation(0.06f, downChar);
//        downAnimation.setPlayMode(Animation.PlayMode.LOOP);
//        
//        leftChar = new TextureRegion[8];
//        
//        for (int x=8;x<16;x++)
//        {
//            String xStr = ""+x;
//            if (x < 10)
//                xStr = "0"+x;
//            leftChar[x-8] = charAtlas.findRegion("00"+xStr);
//            leftChar[x-8].flip(false, true);
//        }
//        leftAnimation = new Animation(0.06f, leftChar);
//        leftAnimation.setPlayMode(Animation.PlayMode.LOOP);
//        
//        rightChar = new TextureRegion[8];
//        
//        for (int x=16;x<24;x++)
//        {
//            rightChar[x-16] = charAtlas.findRegion("00"+x);
//            rightChar[x-16].flip(false, true);
//        }
//        rightAnimation = new Animation(0.06f, rightChar);
//        rightAnimation.setPlayMode(Animation.PlayMode.LOOP);
//        
//        upChar = new TextureRegion[8];
//        
//        for (int x=24;x<32;x++)
//        {
//            upChar[x-24] = charAtlas.findRegion("00"+x);
//            upChar[x-24].flip(false, true);
//        }
//        upAnimation = new Animation(0.06f, upChar);
//        upAnimation.setPlayMode(Animation.PlayMode.LOOP);
//    }


}
