package com.ang.Hittable;

import com.ang.Maths.*;
import com.ang.HitRecord;

public class World {
	private final double cubeSize = 1.5;
	private int[][] map = new int[][]{
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
//	private int[][] map = new int[][] {
//		{1, 1, 1, 1, 1},
//		{1, 0, 0, 0, 1},
//		{1, 0, 0, 0, 1},
//		{1, 0, 0, 0, 1},
//		{1, 1, 1, 1, 1}
//	};
	private Cube[] worldArray;

	public World() {
		worldArray = toCubeArray(map);
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
				}
			}
		}
		return didHit;

	}

	private Cube[] toCubeArray(int[][] map) {
		int count = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 1) {
					count++;
				}
			}
		}
		Cube[] out = new Cube[count];
		int outHead = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 1) {
					Vec3 pos = new Vec3((double) x * cubeSize, 
							(double) y * cubeSize, 
							cubeSize / 2);
					out[outHead++] = new Cube(pos, cubeSize);
				}
			}
		}
		return out;

	}
}
