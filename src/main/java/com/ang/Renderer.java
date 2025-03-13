package com.ang;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Renderer {
	private JFrame frame = new JFrame();
	private int width;
	private int height;
	private BufferedImage img;
	private ImagePanel imgPanel;

	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.imgPanel = new ImagePanel(img);
		init();
	}
	
	private void init() {
		imgPanel.setPreferredSize(new Dimension(width, height));
		frame.getContentPane().add(imgPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Global.uw.doStop();
				frame.dispose();
			}
		});
		imgPanel.setFocusable(true);
		imgPanel.requestFocusInWindow();
	}

	public void writePixel(Vec3 colour, int x, int y) {
		if (colour.lengthSquared() - 1 > 1E-8) {
			colour = colour.unitVector();
		}
		// convert from linear to gamma space
		double r = colour.x() > 0 ? Math.sqrt(colour.x()) : 0.0;
		double g = colour.y() > 0 ? Math.sqrt(colour.y()) : 0.0;
		double b = colour.z() > 0 ? Math.sqrt(colour.z()) : 0.0;
		// normalize and round
		int rComponent = (int) Math.min(r * 255, 255);
		int gComponent = (int) Math.min(g * 255, 255);
		int bComponent = (int) Math.min(b * 255, 255);
		// convert to BufferedImage.TYPE_INT_RGB
		int col = (rComponent << 16) | (gComponent << 8) | (bComponent);
		img.setRGB(x, y, col);
	}

	public void repaint() {
		frame.repaint();
	}
}
