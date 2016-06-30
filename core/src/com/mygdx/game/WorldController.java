package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.asset.AssetPlayerAnimation;
import com.mygdx.game.objects.AbstractGameObject;
import com.mygdx.game.objects.Block;
import com.mygdx.game.objects.Land;
import com.mygdx.game.objects.Player;
import com.mygdx.game.objects.Player.JUMP_STATE;
import com.mygdx.game.util.CameraHelper;
import com.mygdx.game.util.CollisionHandler;
import com.mygdx.game.util.Constants;

public class WorldController implements Disposable
{
	private static final String TAG = WorldController.class.getName();
	
	public CameraHelper cameraHelper;
	
	public Array<AbstractGameObject> objectsToRemove;
	
	public Level level;
	public int lives;
	public int score;
	
	// Non-Box2D Collisions
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	// Box2D Collisions
	public World myWorld;
	
	public WorldController()
	{
		init();	
	}
	
	private void init()
	{
		objectsToRemove = new Array<AbstractGameObject>();
		lives = Constants.LIVES_START;
		cameraHelper = new CameraHelper();
		initLevel();
	}
	
	private void initLevel()
	{
		score = 0;
		level = new Level(Constants.LEVEL_01);
		cameraHelper.setTarget(level.player);
		initPhysics();
	}
	
	private void initPhysics()
	{
		if (myWorld != null)
			myWorld.dispose();
		myWorld = new World(new Vector2(0, -9.81f), true);
		myWorld.setContactListener(new CollisionHandler(this));
		Vector2 origin = new Vector2();
		for (Land l : level.land)
		{
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.KinematicBody;
			bodyDef.position.set(l.position);
			Body body = myWorld.createBody(bodyDef);
			body.setUserData(l);
			l.body = body;
			PolygonShape polygonShape = new PolygonShape();
			origin.x = l.bounds.width / 2.0f;
			origin.y = l.bounds.height / 2.0f;
			polygonShape.setAsBox(l.bounds.width / 2.0f, l.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
		
		// For PLayer
		Player player = level.player;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(player.position);
		bodyDef.fixedRotation = true;
		
		Body body = myWorld.createBody(bodyDef);
		body.setType(BodyType.DynamicBody);
		body.setGravityScale(0.0f);
		body.setUserData(player);
		player.body = body;
		
		PolygonShape polygonShape = new PolygonShape();
		origin.x = player.bounds.width / 2.0f;
		origin.y = player.bounds.height / 2.0f;
		polygonShape.setAsBox(player.bounds.width / 2.0f, player.bounds.height / 2.0f, origin, 0);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		// fixtureDef.friction = 0.5f;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
	}
	

	
	private void handleInputGame(float deltaTime)
	{
		if (cameraHelper.hasTarget(level.player))
		{
			Player player = level.player;
			if (Gdx.input.isKeyPressed(Keys.LEFT))
			{
				player.velocity.x = -player.terminalVelocity.x;
			}
			else if (Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				player.velocity.x = player.terminalVelocity.x;
			}
            
			if (Gdx.input.isKeyPressed(Keys.SPACE))
			{
				player.setJumping(true);
			}
			else
			{
				player.setJumping(false);
			}
		}
	}
	
	private void spawnBlocks(Vector2 pos, int numBlocks, float radius)
	{
		float blockShapeScale = 0.5f;
		for (int i = 0; i<numBlocks; i++)
		{
			Block block = new Block();
			float x = MathUtils.random(-radius,radius);
			float y = MathUtils.random(5.0f, 15.0f);
			//float rotation = MathUtils.random(0.0f, 360.0f) * MathUtils.degreesToRadians;
			float blockScale = MathUtils.random(0.5f, 1.5f);
			block.scale.set(blockScale, blockScale);
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.position.set(pos);
			bodyDef.position.add(x, y);
			bodyDef.angle = 0; // rotation;
			Body body = myWorld.createBody(bodyDef);
			body.setType(BodyType.DynamicBody);
			body.setUserData(block);
			block.body = body;
			
			PolygonShape polygonShape = new PolygonShape();
			float halfWidth = block.bounds.width / 2.0f * blockScale;
			float halfHeight = block.bounds.height / 2.0f * blockScale;
			polygonShape.setAsBox(halfWidth * blockShapeScale, halfHeight * blockShapeScale);
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.density = 50;
			fixtureDef.restitution = 0.5f;
			fixtureDef.friction = 0.5f;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
			level.blocks.add(block);
		}
	}
	
	public void flagForRemoval(AbstractGameObject obj)
	{
		objectsToRemove.add(obj);
	}
	
	public void update(float deltaTime)
	{
		if (objectsToRemove.size > 0)
		{
			for (AbstractGameObject obj : objectsToRemove) 
			{
				if (obj instanceof Block) 
				{
					int index = level.blocks.indexOf((Block) obj, true);
					if (index != -1)
					{
					    level.blocks.removeIndex(index);
					    myWorld.destroyBody(obj.body);
					}
				}
			}
			objectsToRemove.removeRange(0, objectsToRemove.size - 1);
		}
		
		handleInputGame(deltaTime);
		
		if (MathUtils.random(0.0f, 2.0f) < deltaTime)
		{
		    // Temp Location to Trigger Blocks
		    Vector2 centerPos = new Vector2(level.player.position);
		    centerPos.x += level.player.bounds.width;
		    spawnBlocks(centerPos, Constants.BLOCKS_SPAWN_MAX, Constants.BLOCKS_SPAWN_RADIUS);
		}
		
		level.update(deltaTime);
		checkForCollisions();
		myWorld.step(deltaTime, 8, 3);
		cameraHelper.update(deltaTime);
	}
	
	private void onCollisionPlayerWithLand(Land land)
	{
		Player player = level.player;
		float heightDifference = Math.abs(player.position.y - (land.position.y + land.bounds.height));
		if (heightDifference > 0.25f)
		{
			boolean hitRightEdge = player.position.x > (land.position.x + land.bounds.width / 2.0f);
			if (hitRightEdge)
			{
				player.position.x = land.position.x + land.bounds.width;
			}
			else
			{
				player.position.x = land.position.x - player.bounds.width;
			}
			return;
		}
		
		switch (player.jumpState)
		{
			case GROUNDED:
				break;
			case FALLING:
			case JUMP_FALLING:
				player.position.y = land.position.y + player.bounds.height;
				player.jumpState = JUMP_STATE.GROUNDED;
				break;
			case JUMP_RISING:
				player.position.y = land.position.y + player.bounds.height;
				break;
		}
	}
	
	private void checkForCollisions()
	{
		r1.set(level.player.position.x, level.player.position.y, level.player.bounds.width, level.player.bounds.height);
		
		for (Land l : level.land)
		{
			r2.set(l.position.x, l.position.y, l.bounds.width, l.bounds.height);
			if (!r1.overlaps(r2)) continue;
			onCollisionPlayerWithLand(l);
		}
	}
	
	@Override
	public void dispose()
	{
		if (myWorld != null)
			myWorld.dispose();
	}

}
