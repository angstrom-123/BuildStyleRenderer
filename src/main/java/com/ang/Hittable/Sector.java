package com.ang.Hittable;

import com.ang.Maths.*;
import com.ang.Graphics.Colour;

public class Sector extends HittableList {
	// TODO: implement sector heights / light level
	private double floorHeight = 0.0;
	private double ceilHeight = 2.0;
	private double lightLevel = 1.0;

	public Sector(Vec2[] corners) {
		super(corners.length + 1);
		for (int i = 0; i < corners.length; i++) {
			Edge wall;
			if (i < corners.length - 1) {
				wall = new Edge(corners[i], corners[i + 1],
						new Colour(1.0, 1.0, 1.0));
			} else {
				wall = new Edge(corners[i], corners[0],
						new Colour(1.0, 1.0, 1.0));
			}
			super.addHittable(wall);
		}
	}
}
