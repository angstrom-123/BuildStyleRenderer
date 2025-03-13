package com.ang;

public class Game implements ThreadInterface {
	private final int SCREEN_WIDTH = 300;
	private final double ASPECT_RATIO = 16.0 / 9.0;
	private final int FRAME_MS = 100;

	private Camera cam = new Camera(SCREEN_WIDTH, ASPECT_RATIO);

	public Game() {
		Global.uw = new UpdateWorker(FRAME_MS);
		Global.uw.setInterface(this);
	}

	public void startGame() {
		Global.uw.run();
	}

	@Override
	public void update() {
		System.out.println("Update");
	}
}
