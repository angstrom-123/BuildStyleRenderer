package com.ang;

import com.ang.Maths.Vec2;
import com.ang.Render.*;

import com.ang.Hittable.World;

public class Minimap {
	private final int SIZE = 100;
	private final int RESOLUTION = 10;
	private final int TILE_SIZE = (int) Math.floor(SIZE / RESOLUTION);
	private final int OFFSET = 20;
	private final Colour TILE_COLOUR = new Colour(0.9, 0.9, 0.9);
	private final Colour BACKGROUND_COLOUR = new Colour(0.1, 0.1, 0.1);
	private final Colour PLAYER_COLOUR = new Colour(1.0, 0.0, 0.0);
	private Renderer renderer;

	public Minimap(Renderer renderer) {
		this.renderer = renderer;
	}

	public void drawMinimap(World world, Vec2 centre, Vec2 facing) {
		int[][] map = world.map();
		int halfRes = RESOLUTION / 2;
		for (int j = RESOLUTION - 1; j >= 0; j--) {
			for (int i = RESOLUTION - 1; i >= 0; i--) {
				int xPos = OFFSET + (i * TILE_SIZE);
				int yPos = OFFSET + (j * TILE_SIZE);
				int xCoord = (int) Math.round(centre.x()) - halfRes + i; 
				int yCoord = (int) Math.round(centre.y()) - halfRes + j; 
				if ((xCoord < 0) || (xCoord >= map.length) 
						|| (yCoord < 0) || (yCoord >= map[0].length)) {
					renderer.writeTile(BACKGROUND_COLOUR, TILE_SIZE, xPos, yPos);
				} else {
					if (map[yCoord][xCoord] == 1) {
						renderer.writeTile(TILE_COLOUR, TILE_SIZE, xPos, yPos);
					} else {
						renderer.writeTile(BACKGROUND_COLOUR, TILE_SIZE, xPos, yPos);
					}
				}
			}
		}
		int playerOffset = OFFSET + (SIZE / 2);
		renderer.writeChevron(PLAYER_COLOUR, 20, 20, facing, 
				playerOffset, playerOffset);
	}	
}
