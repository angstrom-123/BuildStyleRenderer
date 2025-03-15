package com.ang;

public class World {
	private final double cubeSize = 1.5;
// private int[][] map = new int[][]{
//		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//		{1, 0, 1, 1, 1, 1, 1, 0, 0, 1},
//		{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
//		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//		{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
//		{1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
//		{1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
//		{1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
//		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
//	};
	private int[][] map = new int[][] {
		{1, 1, 1, 1, 1},
		{1, 0, 0, 0, 1},
		{1, 0, 0, 0, 1},
		{1, 0, 0, 0, 1},
		{1, 1, 1, 1, 1}
	};
	private Cube[] worldArray;

	public World() {
		worldArray = toCubeArray(map);
	}

	public boolean hit(Ray r, Interval tInterval, HitRecord rec) {
		for (Cube c : worldArray) {
			if (c.hit(r, tInterval, rec)) {
				return true;

			}
		}
		return false;

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
