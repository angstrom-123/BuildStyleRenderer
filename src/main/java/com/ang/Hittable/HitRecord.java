package com.ang.Hittable;

import com.ang.Graphics.Colour;

public class HitRecord {
	private double t;
	private Colour colour;

	public HitRecord() {}

	public double t() {
		return t;

	}

	public Colour colour() {
		return colour;

	}

	public void setT(double t) {
		this.t = t;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}
}
