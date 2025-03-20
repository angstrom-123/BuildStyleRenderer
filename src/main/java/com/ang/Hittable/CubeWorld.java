package com.ang.Hittable;

import com.ang.Maths.*;
import com.ang.Render.Colour;

public class CubeWorld extends HittableList {
	private final double cubeSize = 1.5;
	private Colour[] colours = new Colour[]{
		new Colour(1.0, 1.0, 1.0),
		new Colour(1.0, 0.0, 0.0),
		new Colour(0.0, 1.0, 0.0),
		new Colour(0.0, 0.0, 1.0)
	};

	public CubeWorld(int[][] map) {
		super(map.length * map[0].length);
		for (Hittable h : toHittables(map, cubeCount(map))) {
			super.addHittable(h);
		}
	}

	private int cubeCount(int[][] map) {
		int count = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] > 0) {
					count++;
				}
			}
		}
		return count;

	}

	private Hittable[] toHittables(int[][] map, int length) {
		Hittable[] out = new Hittable[length];
		int head = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				int mapVal = map[y][x];
				if (mapVal > 0) {
					Vec3 pos = new Vec3((double) x * cubeSize, 
							(double) y * cubeSize, cubeSize / 2);
					out[head++] = new Cube(pos, cubeSize, colours[mapVal - 1]);
				}
			}
		}
		return out;

	}
}
