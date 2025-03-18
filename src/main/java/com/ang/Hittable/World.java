package com.ang.Hittable;

import com.ang.Maths.*;
import com.ang.HitRecord;
import com.ang.Render.Colour;

public class World {
	private final double cubeSize = 1.5;
	private Colour[] colours = new Colour[]{
		new Colour(1.0, 1.0, 1.0),
		new Colour(1.0, 0.0, 0.0),
		new Colour(0.0, 1.0, 0.0),
		new Colour(0.0, 0.0, 1.0)
	};
	int[][] map = new int[][]{
		{1, 2, 4, 4, 4, 4, 4, 4, 4, 1},
		{1, 0, 4, 4, 4, 4, 1, 0, 0, 2},
		{1, 0, 0, 0, 0, 0, 1, 0, 0, 2},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
		{3, 0, 0, 0, 0, 0, 3, 0, 0, 2},
		{3, 0, 4, 0, 0, 0, 3, 0, 0, 2},
		{2, 0, 4, 0, 0, 0, 3, 0, 0, 2},
		{2, 0, 4, 0, 0, 0, 3, 4, 0, 2},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	private Cube[] worldArray;

	public World() {
		worldArray = toCubeArray(map);
	}

	public int[][] map() {
		return map;
	}

	public boolean hit(Ray r, Interval tInterval, HitRecord rec) {
		boolean didHit = false;
		HitRecord tempRec = new HitRecord();
		double closestHit = tInterval.max();
		for (Cube c : worldArray) {
			Interval newBounds = new Interval(tInterval.min(), closestHit);
			if (c.hit(r, newBounds, tempRec)) {
				if (tempRec.t() > 0.0) {
					didHit = true;	
					closestHit = tempRec.t();
					rec.setT(tempRec.t());
					rec.setColour(tempRec.colour());
				}
			}
		}
		return didHit;

	}

	private Cube[] toCubeArray(int[][] map) {
		int count = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] > 0) {
					count++;
				}
			}
		}
		Cube[] out = new Cube[count];
		int outHead = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] > 0) {
					Vec3 pos = new Vec3((double) x * cubeSize, 
							(double) y * cubeSize, 
							cubeSize / 2);
					out[outHead++] = new Cube(pos, cubeSize, colours[map[y][x] - 1]);
				}
			}
		}
		return out;

	}
}
