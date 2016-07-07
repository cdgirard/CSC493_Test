package com.mygdx.game.util;

public class Constants 
{
	// Visible game work is 5 meters wide
	public static final float VIEWPORT_WIDTH = 5.0f;
	
	// Visible game work is 5 meters tall
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800.f;
	
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	
	// Image Assets
	public static final String TEXTURE_ATLAS_PLAYER = "images/char_warrior_female2.atlas";
	public static final String TEXTURE_BLOCK = "images/arrow_down.png";
	public static final String TEXTURE_LAND = "images/ground.png";

	// Blocks
	public static final int BLOCKS_SPAWN_MAX = 2;
	public static final float BLOCKS_SPAWN_RADIUS = 3.5f;
	
	// Levels
	public static final String LEVEL_01 = "data/test_level.png";
	
	public static final int LIVES_START = 3;
	public static final String PREFERENCES = "game.prefs";
	
	// UI Data
	public static final String TEXTURE_ATLAS_UI = "ui/game-ui-pack.atlas";
	public static final String TEXTURE_ATLAS_LIBGDX_UI = "ui/uiskin.atlas";
	public static final String SKIN_LIBGDX_UI = "ui/uiskin.json";
	public static final String SKIN_GAME_UI = "ui/game-ui.json";
}
