package com.ang;

import com.ang.Hittable.*;
import com.ang.Maths.Vec2;
import com.ang.Thread.*;

import java.awt.event.KeyEvent;

public class Game implements ThreadInterface, InputInterface {
	private final int 		IMAGE_WIDTH 	= 600;
	private final int 		FRAME_MS 		= 1000 / 60;
	private boolean[] 		keyInputs 		= new boolean[256];
	private InputListener 	il 				= new InputListener(this);
	private Camera 			cam 			= new Camera(IMAGE_WIDTH);
	private CameraMover		controller  	= new CameraMover(cam);
	private HittableList	world;

	public Game(int selection) {
		cam.setTransform(new Vec2(2.5, 2.5), new Vec2(0.0, 1.0)); 
		cam.init(il);
		Global.uw = new UpdateWorker(FRAME_MS);
		Global.uw.setInterface(this);
		switch(selection) {
		case 0:
			// TODO: make map editor
			Vec2[] points = new Vec2[]{
				new Vec2(4.0, 4.0),
				new Vec2(4.0, -4.0),
				new Vec2(-4.0, -4.0),
				new Vec2(-4.0, 4.0)
			};
			world = new Sector(points);
			break;

		case 1:
			int[][] map = new int[][]{
				{1, 2, 4, 4, 4, 4, 4, 4, 4, 1},
				{1, 0, 4, 4, 4, 4, 1, 0, 0, 2},
				{1, 0, 0, 0, 0, 0, 1, 0, 0, 2},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
				{3, 0, 0, 0, 0, 0, 3, 0, 0, 2},
				{3, 0, 4, 0, 0, 0, 3, 0, 0, 2},
				{2, 0, 4, 0, 0, 0, 3, 0, 0, 2},
				{2, 0, 4, 0, 0, 0, 3, 4, 0, 2},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
			};
			world = new CubeWorld(map);
			break;

		default:
			System.out.println("Invalid option selected");
			break;

		}
	}

	public void startGame() {
		if (world != null) {
			Global.uw.run();
		}
	}

	@Override
	public void update() {
		controller.update(keyInputs);
		cam.update();
		cam.draw(world);
	}

	@Override
	public void pressed(int key) {
		keyInputs[key] = true;
	}

	@Override
	public void released(int key) {
		keyInputs[key] = false;
	}

}
