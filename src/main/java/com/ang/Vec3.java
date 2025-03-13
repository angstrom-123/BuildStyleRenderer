package com.ang;

public class Vec3 {
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;

	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double x() {
		return this.x;

	}

	public double y() {
		return this.y;

	}
	
	public double z() {
		return this.z;

	}

	public Vec3 add(Vec3 v) {
		return new Vec3(this.x + v.x(), this.y + v.y(), this.z + v.z());

	}

	public Vec3 sub(Vec3 v) {
		return new Vec3(this.x - v.x(), this.y - v.y(), this.z - v.z());

	}

	public Vec3 mul(double t) {
		return new Vec3(this.x * t, this.y * t, this.z * t);

	}

	public Vec3 div(double t) {
		return mul(1 / t);

	}

	public Vec3 neg() {
		return new Vec3(-this.x, -this.y, -this.z);

	}

	public double lengthSquared() {
		return (this.x * this.x) 
				+ (this.y * this.y) 
				+ (this.z * this.z);

	}

	public double length() {
		return Math.sqrt(lengthSquared());

	}

	public Vec3 unitVector() {
		return div(length());

	}

	public boolean nearZero() {
		double min = 1E-8;
		return (Math.abs(this.x) < min)
				&& (Math.abs(this.y) < min)
				&& (Math.abs(this.z) < min);

	}

	public static double dot(Vec3 u, Vec3 v) {
		return (u.x() * v.x()) + (u.y() * v.y()) + (u.z() * v.z());

	}

	public static Vec3 cross(Vec3 u, Vec3 v) {
		return new Vec3(u.y() * v.z() - u.z() * v.x(),
				u.z() * v.x() - u.x() * v.z(),
				u.z() * v.x() - u.x() * v.z());

	}
}
