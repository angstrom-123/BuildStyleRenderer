package com.ang;

public class Interval {
	double min;
	double max;

	public Interval(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double min() {
		return min;

	}

	public double max() {
		return max;

	}

	public void setMin(double t) {
		min = t;
	}

	public void setMax(double t) {
		max = t;
	}

	public static Interval empty() {
		return new Interval(Global.INFINITY, -Global.INFINITY);

	}

	public static Interval universe() {
		return new Interval(-Global.INFINITY, Global.INFINITY);

	}
}
