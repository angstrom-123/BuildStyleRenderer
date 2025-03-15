package com.ang;

public class HitRecord {
	private Vec2 hitPoint;
	private double t;

	public HitRecord() {}

	public Vec2 hitPoint() {
		return hitPoint;

	}

	public double t() {
		return t;

	}

	public void setHitPoint(Vec2 hitPoint) {
		this.hitPoint = hitPoint;
	}

	public void setT(double t) {
		this.t = t;
	}

	public void set(HitRecord rec) {
		hitPoint = rec.hitPoint();
		t = rec.t();
	}
}
