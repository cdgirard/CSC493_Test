package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.AbstractGameObject;
import com.mygdx.game.objects.Block;
import com.mygdx.game.objects.Land;
import com.mygdx.game.objects.Player;

public class Level 
{
	public static final String TAG = Level.class.getName();
	
	public Player player;
	public Array<Land> land;
	public Array<Block> blocks;
	
	
	public enum BLOCK_TYPE 
	{
		EMPTY(0,0,0), // black
		LAND(0,255,0), // green
		PLAYER_SPAWNPOINT(255,255,255), // white
		BLOCK(255,0,255); // purple
		
		private int color;
		
		private BLOCK_TYPE(int r, int g, int b)
		{
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		public boolean sameColor(int color)
		{
			return this.color == color;
		}
		
		public int getColor()
		{
			return color;
		}
	}
	
	
	
	public Level(String fileName)
	{
		init(fileName);
	}

	private void init(String fileName)
	{
		land = new Array<Land>();
		blocks = new Array<Block>();
		
		player = null;
		
		Pixmap pixmap = new Pixmap(Gdx.files.internal(fileName));
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++)
		{
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++)
			{
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				float baseHeight = pixmap.getHeight() - pixelY;
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel))
				{
					// do nothing
				}
				else if (BLOCK_TYPE.LAND.sameColor(currentPixel))
				{
					if (lastPixel != currentPixel)
					{
						obj = new Land();
						float heightIncreaseFactor = 1.0f;
						offsetHeight = -3.0f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
						land.add((Land) obj);
					}
					else
					{
						land.get(land.size - 1).increaseLength(1);
					}
				}
				else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel))
				{
					obj = new Player();
					offsetHeight = -3.0f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y + offsetHeight);
					player = (Player)obj;
				}
				else if (BLOCK_TYPE.BLOCK.sameColor(currentPixel))
				{
					
				}
				else
				{
					int r = 0xff & (currentPixel >>> 24); // red
					int g = 0xff & (currentPixel >>> 16); // green
					int b = 0xff & (currentPixel >>> 8);  // blue
					int a = 0xff & currentPixel; // alpha
					Gdx.app.error(TAG, "Unknown object at x <"+pixelX+"> y<"+pixelY+">: r<"
							+r+"> g<"+g+"> b<"+b+"> a<"+a+">");
				}
				lastPixel = currentPixel; // Not using at the moment.
			}
		}
		
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '"+fileName+"' loaded");
	}
	

	
	public void render(SpriteBatch batch)
	{
		for (Land l : land)
			l.render(batch);
		
		for (Block block : blocks)
			block.render(batch);
		
		player.render(batch);
	}
	
	public void update(float deltaTime)
	{
		player.update(deltaTime);
		
		for (Land l: land)
			l.update(deltaTime);
		
		for (Block block: blocks)
			block.update(deltaTime);
	}
}
