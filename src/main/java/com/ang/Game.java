package com.ang;

import com.ang.Hittable.World;
import com.ang.Maths.Vec2;
import com.ang.Thread.*;

import java.awt.event.KeyEvent;

public class Game implements ThreadInterface, InputInterface, MouseInputInterface {
	private final int SCREEN_WIDTH = 600;
	private final double ASPECT_RATIO = 16.0 / 9.0;
	private final int FRAME_MS = 1000 / 60;
	private final double STEP_SIZE = 0.08;
	private final double TURN_STEP_SIZE = Math.PI / 50.0;
	private boolean[] keyInputs = new boolean[256];
	private InputListener il = new InputListener(this);
	private MouseInputListener mil = new MouseInputListener(this);
	private World world = new World();
	private Camera cam = new Camera(SCREEN_WIDTH, ASPECT_RATIO, world);

	public Game() {
		cam.setTransform(new Vec2(3.0, 3.0), new Vec2(0.0, 1.0)); 
		cam.init(il, mil);
		Global.uw = new UpdateWorker(FRAME_MS);
		Global.uw.setInterface(this);
	}

	public void startGame() {
		Global.uw.run();
	}

	@Override
	public void update() {
		cam.changePosition(getMovementInput());
		cam.changeFacing(getTurnInput());
		cam.update();
		cam.draw();
	}

	@Override
	public void pressed(int key) {
		keyInputs[key] = true;
	}

	@Override
	public void released(int key) {
		keyInputs[key] = false;
	}

	@Override
	public void mouseMoved(int x, int y) {
		// System.out.println(x + " " + y);
	}

	private double getTurnInput() {
		double theta = 0.0;
		if (keyInputs[KeyEvent.VK_LEFT]) { 
			theta += TURN_STEP_SIZE;
		}
		if (keyInputs[KeyEvent.VK_RIGHT]) { 
			theta -= TURN_STEP_SIZE;
		}
		return theta;

	}

	private Vec2 getMovementInput() {
		Vec2 out = new Vec2(0.0, 0.0);
		if (keyInputs[KeyEvent.VK_W]) {
			out = out.add(new Vec2(0.0, -STEP_SIZE));
		}
		if (keyInputs[KeyEvent.VK_A]) {
			out = out.add(new Vec2(STEP_SIZE, 0.0));
		}
		if (keyInputs[KeyEvent.VK_S]) {
			out = out.add(new Vec2(0.0, STEP_SIZE));
		}
		if (keyInputs[KeyEvent.VK_D]) {
			out = out.add(new Vec2(-STEP_SIZE, 0.0));
		}
		return out;

	}
}
