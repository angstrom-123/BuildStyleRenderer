package com.ang;

public class Camera {
	private Renderer renderer;
	private int imageWidth;
	private int imageHeight;
	private double aspectRatio;
	private int[][] map = new int[][]{
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 1, 1, 1, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	public Camera(int imageWidth, double aspectRatio) {
		this.imageWidth = imageWidth;
		this.aspectRatio = aspectRatio;
		init();
	}

	public void draw() {
		// TODO: implement
	}

	private void init() {
		imageHeight = (int) Math.round((double) imageWidth / aspectRatio);
		imageWidth = Math.max(imageWidth, 1);
		renderer = new Renderer(imageWidth, imageHeight);
		double viewportHeight = 2.0;
		double viewportWidth = viewportHeight 
				* ((double) imageWidth / (double) imageHeight);
	}
}
