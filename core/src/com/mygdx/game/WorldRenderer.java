package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.util.Constants;

public class WorldRenderer implements Disposable
{
	// Camera that follows action in the game.
	private OrthographicCamera camera;
	// Camera that shows user interface information.
	private OrthographicCamera cameraUI;
	private SpriteBatch batch;
	private WorldController worldController;
	
	// For Box2D Debugging
	private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
	private Box2DDebugRenderer b2DebugRenderer;
	
	public WorldRenderer (WorldController worldController)
	{
		this.worldController = worldController;
		init();
	}
	
	private void init()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		worldController.cameraHelper.setPosition(20, 8);
		worldController.cameraHelper.applyTo(camera);
		
		cameraUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
		cameraUI.setToOrtho(true);
		cameraUI.update();
		
		b2DebugRenderer = new Box2DDebugRenderer();
	}
	
	public void render()
	{
		renderWorld();
		renderGui();
	}
	
	private void renderWorld()
	{
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		
		if (DEBUG_DRAW_BOX2D_WORLD)
		{
			b2DebugRenderer.render(worldController.myWorld, camera.combined);
		}
	}
	
	private void renderGui()
	{
		batch.setProjectionMatrix(cameraUI.combined);
		batch.begin();
		renderGuiScore(batch);
		batch.end();
	}
	
	private void renderGuiScore(SpriteBatch batch)
	{
		float x = -15;
		float y = -15;
		Assets.instance.fonts.defaultBig.draw(batch,""+worldController.score,x+75, y+37);
	}
	
	public void resize (int width, int height)
	{
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height) * width;
		camera.update();
		
		cameraUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
		cameraUI.position.set(cameraUI.viewportWidth / 2, cameraUI.viewportHeight / 2, 0);
		cameraUI.update();
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
	}

}
