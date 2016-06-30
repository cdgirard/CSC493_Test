package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.asset.AssetBlock;
import com.mygdx.game.asset.AssetLand;

public class Land extends AbstractGameObject 
{
	public static final String TAG = Land.class.getName();
	
	private TextureRegion land;
	
	public Land()
	{
		init();
	}
	
	private void init()
	{
		dimension.set(1.0f,1.0f);
		land = AssetLand.land;
		bounds.set(0, 0, dimension.x, dimension.y);
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		batch.draw(land.getTexture(), position.x, position.y, origin.x, origin.y, 
				dimension.x, dimension.y, scale.x, scale.y, rotation, land.getRegionX(), 
				land.getRegionY(), land.getRegionWidth(), land.getRegionHeight(), false, false);
	}

}
