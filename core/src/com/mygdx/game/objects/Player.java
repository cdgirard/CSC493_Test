package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.asset.AssetBlock;
import com.mygdx.game.asset.AssetPlayerAnimation;

public class Player extends AbstractGameObject 
{
	public static final String TAG = Player.class.getName();
	
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;

	
	public enum VIEW_DIRECTION { LEFT, RIGHT }
	
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	private TextureRegion player;
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasPowerup;
	public float timeLeftPowerup;
	
	public Player()
	{
		init();
	}
	
	public void init()
	{
		dimension.set(1,1);
		player = AssetPlayerAnimation.leftChar[0];
		origin.set(dimension.x/2,dimension.y/2);
		bounds.set(0,0,dimension.x,dimension.y);
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		viewDirection = VIEW_DIRECTION.RIGHT;
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		hasPowerup = false;
		timeLeftPowerup = 0;
	}
	
	public void setJumping(boolean jumpKeyPressed)
	{
		switch (jumpState)
		{
			case GROUNDED: 
				if (jumpKeyPressed)
				{
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;
					//Gdx.app.log(TAG,"RISING");
				}
				break;
			case JUMP_RISING:
				if (!jumpKeyPressed)
					jumpState = JUMP_STATE.JUMP_FALLING;
				break;
			case FALLING:
			case JUMP_FALLING:
				break;
		}
	}
	
	public void setPowerup(boolean pickedUp)
	{
		
	}
	
	public boolean hasPowerup()
	{
		return false;
	}
	
	@Override
    protected void updateMotionY(float deltaTime)
    {
		switch (jumpState)
		{
			case GROUNDED:
				//jumpState = JUMP_STATE.FALLING;
				break;
			case JUMP_RISING:
				timeJumping += deltaTime;
				if (timeJumping <= JUMP_TIME_MAX)
				{
					velocity.y = terminalVelocity.y;
				}
				else
					jumpState = JUMP_STATE.JUMP_FALLING;
				break;
			case FALLING:
				jumpState = JUMP_STATE.GROUNDED;
				break;
			case JUMP_FALLING:
				velocity.y = -terminalVelocity.y;
				break;
		}
		if (jumpState != JUMP_STATE.GROUNDED)
		{
			super.updateMotionY(deltaTime);
		}
    }
	
	@Override
	public void update(float deltaTime)
	{
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);

		if (body != null)
		{
			//Gdx.app.log(TAG, "velY: "+velocity.y+" state: "+jumpState);
	        body.setLinearVelocity(velocity);		
			position.set(body.getPosition());
		}
		if (velocity.x != 0)
		{
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = player;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, 
				scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.LEFT, false);
        batch.setColor(1, 1, 1, 1);
	}

}
