package com.ang;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
	private InputInterface ii;

	public InputListener(InputInterface ii) {
		this.ii = ii;	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			ii.pressed('w');
		}
		if (key == KeyEvent.VK_A) {
			ii.pressed('a');
		}
		if (key == KeyEvent.VK_S) {
			ii.pressed('s');
		}
		if (key == KeyEvent.VK_D) {
			ii.pressed('d');
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			ii.released('w');
		}
		if (key == KeyEvent.VK_A) {
			ii.released('a');
		}
		if (key == KeyEvent.VK_S) {
			ii.released('s');
		}
		if (key == KeyEvent.VK_D) {
			ii.released('d');
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
