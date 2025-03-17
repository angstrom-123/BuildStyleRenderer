package com.ang;

import com.ang.Hittable.World;
import com.ang.Maths.Vec2;
import com.ang.Thread.*;

public class Game implements ThreadInterface, InputInterface {
	private final int SCREEN_WIDTH = 600;
	private final double ASPECT_RATIO = 16.0 / 9.0;
	private final int FRAME_MS = 100;
	private final double STEP_SIZE = 0.1;
	private boolean[] keyInputs = new boolean[256];
	private InputListener il = new InputListener(this);
	private World world = new World();
	private Camera cam = new Camera(SCREEN_WIDTH, ASPECT_RATIO, world);

	public Game() {
		cam.setTransform(new Vec2(3.0, 3.0), new Vec2(0.0, 1.0)); 
		cam.init(il);
		Global.uw = new UpdateWorker(FRAME_MS);
		Global.uw.setInterface(this);
	}

	public void startGame() {
		Global.uw.run();
	}

	@Override
	public void update() {
		cam.changePosition(getMovementInput());
		cam.update();
		cam.draw();
	}

	@Override
	public void pressed(char c) {
		keyInputs[(int) c] = true;
	}

	@Override
	public void released(char c) {
		keyInputs[(int) c] = false;
	}

	private Vec2 getMovementInput() {
		Vec2 out = new Vec2(0.0, 0.0);
		if (keyInputs[(int) 'w']) {
			out = out.add(new Vec2(0.0, -STEP_SIZE));
		}
		if (keyInputs[(int) 'a']) {
			out = out.add(new Vec2(STEP_SIZE, 0.0));
		}
		if (keyInputs[(int) 's']) {
			out = out.add(new Vec2(0.0, STEP_SIZE));
		}
		if (keyInputs[(int) 'd']) {
			out = out.add(new Vec2(-STEP_SIZE, 0.0));
		}
		return out;

	}
}
