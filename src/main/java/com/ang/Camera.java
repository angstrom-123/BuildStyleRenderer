package com.ang;

import com.ang.Graphics.*;
import com.ang.Maths.*;
import com.ang.Hittable.*;
import com.ang.Inputs.InputListener;

public class Camera {
	private Colour 		backgroundCol 	= new Colour(0.3, 0.4, 0.6);
	private int 		imageWidth 		= 100;
	private double 		aspectRatio 	= 16.0 / 9.0;
	private double 		fov 			= Math.PI / 3.0;
	private Vec2 		position 		= new Vec2(0.0, 0.0);
	private Vec2 		facing 			= new Vec2(0.0, 1.0);
	private int 		imageHeight;
	private double 		viewportHeight;
	private double 		viewportWidth;
	private Vec3 		w, u, v;
	private Vec2 		pixel0Position;
	private Vec2 		pixelDeltaU;
	private Renderer 	renderer;
	
	public Camera(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setTransform(Vec2 position, Vec2 facing) {
		this.position = position;
		this.facing = facing;
	}

	public void changePosition(Vec2 positionDelta) {
		Vec2 xDelta = u.toVec2().neg().mul(positionDelta.x());
		Vec2 yDelta = facing.mul(positionDelta.y());
		position = position.add(xDelta).add(yDelta);
	}

	public void changeFacing(double theta) {
		double cosTheta = Math.cos(theta);
		double sinTheta = Math.sin(theta);
		double xPos = facing.x() * cosTheta - facing.y() * sinTheta;
		double yPos = facing.x() * sinTheta + facing.y() * cosTheta;
		facing = new Vec2(xPos, yPos).unitVector();
	}

	public void init(InputListener il) {
		imageHeight = (int) Math.round((double) imageWidth / aspectRatio);
		imageWidth = Math.max(imageWidth, 1);
		renderer = new Renderer(imageWidth, imageHeight, il);
		viewportHeight = 2.0 * Math.tan(fov / 2.0);
		viewportWidth = viewportHeight 
				* ((double) imageWidth / (double) imageHeight);
		update();
	}

	public void update() {
		// camera basis vectors
		v = new Vec3(0.0, 0.0, 1.0); // up 
		w = facing.neg().toVec3().unitVector(); // back
		u = Vec3.cross(v, w).unitVector(); // right
		// viewport basis vectors
		Vec2 viewportU = (u.mul(viewportWidth)).toVec2();
		pixelDeltaU = viewportU.div(imageWidth);
		Vec2 offset = w.add(viewportU.div(2.0)).toVec2();
		pixel0Position = position.sub(offset).add((pixelDeltaU).div(2.0));
	}

	public long draw(HittableList world) {
		long startTime = System.currentTimeMillis();
		// Drawing the world
		for (int i = 0; i < imageWidth; i++) {
			Ray r = getRay(i);	
			HitRecord rec = new HitRecord();
			if (world.hit(r, new Interval(0.001, Global.INFINITY), rec)) {
				int[] bounds = getColumnBounds(r, rec);
				renderer.writeColumn(rayColour(r, rec), backgroundCol, i, 
						bounds[0], bounds[1]);
			} else {
				renderer.writeColumn(backgroundCol, backgroundCol, i, 
						imageHeight); 
			}
		}
		// Refreshing the screen
		renderer.repaint();
		return System.currentTimeMillis() - startTime;

	}

	private Colour rayColour(Ray r, HitRecord rec) {
		Colour colour = rec.colour();	
		colour = colour.mul(getDepth(r, rec));
		return colour;

	}

	private double getDepth(Ray r, HitRecord rec) {
		double distance = (r.at(rec.t()).sub(r.origin())).length();
		double value = 1.0 - (distance / 15.0);
		return value;

	}

	private int[] getColumnBounds(Ray r, HitRecord rec) {
		double distance = (r.at(rec.t()).sub(r.origin())).length();
		int h = (int) (imageHeight / distance);
		int bottom = Math.max((imageHeight / 2) - (h / 2), 0);
		int top = Math.min((imageHeight / 2) + (h / 2), imageHeight - 1);
		return new int[]{bottom, top};

	}

	private Ray getRay(int x) {
		Vec2 offsetX = pixelDeltaU.mul(x);
		Vec2 pixelPos = pixel0Position.add(offsetX);
		Vec2 rayDir = pixelPos.sub(position);
		return new Ray(position, rayDir);

	}
}
