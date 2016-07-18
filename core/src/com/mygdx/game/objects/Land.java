package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.asset.AssetLand;

public class Land extends AbstractGameObject 
{
	public static final String TAG = Land.class.getName();
	private final float FLOAT_CYCLE_TIME = 2.0f;
	
	private TextureRegion land;
	
	private int length;
	
	// Floating
	private float floatCycleTimeLeft;
	private boolean floatingDownwards;
	
	public Land()
	{
		init();
	}
	
	private void init()
	{
		dimension.set(1.0f,1.0f);
		land = AssetLand.land;
		bounds.set(0, 0, dimension.x, dimension.y);
		
		setLength(1);
		
		floatingDownwards = false;
		floatCycleTimeLeft = MathUtils.random(0, FLOAT_CYCLE_TIME / 2);
		velocity.y = MathUtils.random(1, 5);
	}
	
	public void setLength(int len)
	{
		length = len;
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}
	
	public void increaseLength(int amount)
	{
		setLength(length + amount);
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		float relX = 0;
		float relY = 0;
		Gdx.app.log(TAG, ""+position.y);
		// Center
		relX = 0;
		for (int i=0;i < length; i++)
		{
			batch.draw(land.getTexture(), position.x+relX, position.y+relY, origin.x, origin.y, 
					dimension.x, dimension.y, scale.x, scale.y, rotation, land.getRegionX(), 
					land.getRegionY(), land.getRegionWidth(), land.getRegionHeight(), false, false);
			relX += dimension.x;
		}
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		floatCycleTimeLeft -= deltaTime;
		
		if (floatCycleTimeLeft <= 0)
		{
			floatCycleTimeLeft = FLOAT_CYCLE_TIME;
			floatingDownwards = !floatingDownwards;
			velocity.y = -velocity.y;
		}
		body.setLinearVelocity(velocity);		
		position.set(body.getPosition());
		//position.lerp(floatTargetPosition, deltaTime);
	}

}
