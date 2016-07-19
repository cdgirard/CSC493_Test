package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.asset.AssetBlock;
import com.mygdx.game.asset.AssetPlayer;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.util.AudioManager;

public class Player extends AbstractGameObject 
{
	public static final String TAG = Player.class.getName();
	
	public ParticleEffect dustParticles = new ParticleEffect();
	
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;

	
	public enum VIEW_DIRECTION { LEFT, RIGHT }
	
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasPowerup;
	public float timeLeftPowerup;
	
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation restingAnimation;
	
	public Player()
	{
		init();
	}
	
	public void init()
	{
		dimension.set(1,1);
		
		leftAnimation = Assets.instance.player.leftAnimation;
		rightAnimation = Assets.instance.player.rightAnimation;
		restingAnimation = Assets.instance.player.restingAnimation;
		setAnimation(restingAnimation);
		
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
		
		// Particles
		dustParticles.load(Gdx.files.internal("effects/dust.pfx"), Gdx.files.internal("effects"));
	}
	
	public void setJumping(boolean jumpKeyPressed)
	{
		switch (jumpState)
		{
			case GROUNDED: 
				if (jumpKeyPressed)
				{
					AudioManager.instance.play(Assets.instance.sounds.jump);
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;
					//Gdx.app.log(TAG,"RISING");
				}
				else if (velocity.x != 0)
				{
					//Gdx.app.log(TAG, "starting particles");
					dustParticles.setPosition(position.x + dimension.x / 2, position.y+0.1f);
					dustParticles.start();
				}
				else if (velocity.x == 0)
				{
					dustParticles.allowCompletion();
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
			//Gdx.app.log(TAG, "stopping particles");
			dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}
    }
	
	@Override
	public void update(float deltaTime)
	{
		stateTime += deltaTime;
		//Gdx.app.log(TAG, "UP stateTime: "+stateTime);
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);

		if (body != null)
		{
			// Gdx.app.log(TAG, "velY: "+velocity.y+" state: "+jumpState);
			body.setLinearVelocity(velocity);
			position.set(body.getPosition());
		}
		if (velocity.x != 0)
		{
			//Gdx.app.log(TAG, "velX: "+velocity.x+" viewDir: "+viewDirection);
			if (velocity.x < 0)
			{
				if (animation != leftAnimation)
				{
					setAnimation(leftAnimation);
				}
				viewDirection = VIEW_DIRECTION.LEFT;
			}
			else if (velocity.x > 0)
			{
				if (animation != rightAnimation)
					setAnimation(rightAnimation);
				viewDirection = VIEW_DIRECTION.RIGHT;
			}
		}
		else
		{
			setAnimation(restingAnimation);
		}
		dustParticles.update(deltaTime);
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		dustParticles.draw(batch);
		
		TextureRegion reg = animation.getKeyFrame(stateTime, true);
		
		// Gdx.app.log(TAG, "frame: "+reg.getRegionX()+ "state: " + stateTime+" animation: "+animation.getKeyFrames().length);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, 
				scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
        batch.setColor(1, 1, 1, 1);
        
        
	}

}
