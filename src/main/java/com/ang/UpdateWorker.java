package com.ang;

public class UpdateWorker implements Runnable {
	private boolean stop = false;
	private int frameMs;
	private ThreadInterface ti;

	public UpdateWorker(int frameMs) {
		this.frameMs = frameMs;
	}

	public void setInterface(ThreadInterface ti) {
		this.ti = ti;
	}

	public void doStop() {
		stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				ti.update();
				Thread.sleep(frameMs);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
