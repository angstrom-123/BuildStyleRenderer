package com.ang;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseInputListener implements MouseMotionListener {
	private MouseInputInterface mii;

	public MouseInputListener(MouseInputInterface mii) {
		this.mii = mii;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mii.mouseMoved(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {}
}
