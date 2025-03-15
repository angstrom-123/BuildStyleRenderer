package com.ang;

public class Colour {
	double r;
	double g;
	double b;

	public Colour() {}

	public Colour(double r, double g, double b){
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public double r() {
		return r;

	}

	public double g() {
		return g;

	}
	
	public double b() {
		return b;

	}

	public Colour unitNormalize() {
		double scale = Math.sqrt(r * r + b * b + g * g);
		return new Colour(r / scale, g / scale, b / scale);

	}	
}
