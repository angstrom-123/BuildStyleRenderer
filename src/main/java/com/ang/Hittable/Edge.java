package com.ang.Hittable;

import com.ang.Maths.*;
import com.ang.Graphics.Colour;

public class Edge extends Hittable {
	private Vec2 p1;
	private Vec2 p2;
	private Colour albedo;

	public Edge(Vec2 p1, Vec2 p2, Colour albedo) {
		this.p1 = p1;
		this.p2 = p2;
		this.albedo = albedo;
	}

	public void flip() {
		Vec2 temp = p1;
		p1 = p2;
		p2 = temp;
	}

	@Override
	public boolean hit(Ray r, Interval tInterval, HitRecord rec) {
	//	TODO: Make intersections work well from both sides
		Vec2 v1 = r.origin().sub(p1);
		Vec2 v2 = p2.sub(p1);
		Vec2 v3 = new Vec2(-r.direction().y(), r.direction().x());
		double t1 = Vec2.cross(v2, v1) / Vec2.dot(v2, v3);
		double t2 = Vec2.dot(v1, v3) / Vec2.dot(v2, v3);
		if ((t1 >= 1E-8) && (t2 >= 1E-8) && (t2 <= 1.0)) {
			rec.setT(t1);
			rec.setColour(albedo);
			tInterval.setMax(t1);
			return true;

		}
		return false;

	}
}
