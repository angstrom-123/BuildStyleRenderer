package com.ang.Maths;

public class Vec3 {
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;

	public Vec3() {}

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

	@Override
	public String toString() {
		return ("x: " + x + "\n"
				+ "y: " + y + "\n"
				+ "z: " + z + "\n");

	}

	public Vec2 toVec2() {
		return new Vec2(x, y);

	}

	public void setAxis(int axis, double val) {
		switch (axis) {
		case 0:
			x = val;
			break;

		case 1:
			y = val;
			break;
		case 2:
			z = val;
			break;

		default:
			break;

		}
	}

	public Vec3 add(Vec3 v) {
		return new Vec3(x + v.x(), y + v.y(), z + v.z());

	}

	public Vec3 add(Vec2 v) {
		return new Vec3(x + v.x(), y + v.y(), z);

	}

	public Vec3 sub(Vec3 v) {
		return new Vec3(x - v.x(), y - v.y(), z - v.z());

	}

	public Vec3 sub(Vec2 v) {
		return new Vec3(x - v.x(), y - v.y(), z);

	}

	public Vec3 mul(double t) {
		return new Vec3(x * t, y * t, z * t);

	}

	public Vec3 div(double t) {
		return mul(1 / t);

	}

	public Vec3 neg() {
		return new Vec3(-x, -y, -z);

	}

	public double lengthSquared() {
		return (x * x) + (y * y) + (z * z);

	}

	public double length() {
		return Math.sqrt(lengthSquared());

	}

	public Vec3 unitVector() {
		return div(length());

	}

	public boolean nearZero() {
		double min = 1E-8;
		return (Math.abs(this.x) < min)	&& (Math.abs(this.y) < min);

	}

	public double axis(int axis) {
		switch (axis) {
		case 0:
			return x;

		case 1:
			return y;

		case 2:
			return z;

		default:
			return 0.0;

		}
	}

	public static double dot(Vec3 u, Vec3 v) {
		return (u.x() * v.x()) + (u.y() * v.y()) + (u.z() * v.z()); 

	}

	public static Vec3 cross(Vec3 u, Vec3 v) {
		return new Vec3(u.y() * v.z() - u.z() * v.y(),
				u.z() * v.x() - u.x() * v.z(),
				u.x() * v.y() - u.y() * v.x());

	}
}
