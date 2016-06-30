package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.asset.AssetBlock;

public class Block extends AbstractGameObject 
{
	private TextureRegion block;
	
	public Block()
	{
		init();
	}
	
	private void init()
	{
		dimension.set(0.25f,0.25f);
		
		block = AssetBlock.block;
		
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.set(dimension.x / 2, dimension.y / 2);
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		batch.draw(block.getTexture(), position.x-origin.x, position.y-origin.y, origin.x, origin.y, 
				dimension.x, dimension.y, scale.x, scale.y, rotation, block.getRegionX(), 
				block.getRegionY(), block.getRegionWidth(), block.getRegionHeight(), false, false);
	}

}
