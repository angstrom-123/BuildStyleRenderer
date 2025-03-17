package com.ang;

import com.ang.Render.*;
import com.ang.Maths.*;
import com.ang.Hittable.*;

public class Camera {
	// +x = left
	// +y = forward
	// +z = up
	private Colour backgroundCol = new Colour(0.3, 0.4, 0.6);
	private Renderer renderer;
	private World world;
	private int imageWidth;
	private int imageHeight;
	private double viewportHeight = 2.0;
	private double viewportWidth;
	private double aspectRatio;
	private Vec2 position;
	private Vec2 facing;
	private Vec3 w, u, v;
	private Vec2 pixel0Position;
	private Vec2 pixelDeltaU;
	
	public Camera(int imageWidth, double aspectRatio, World world) {
		this.imageWidth = imageWidth;
		this.aspectRatio = aspectRatio;
		this.world = world;
	}

	public void setTransform(Vec2 position, Vec2 facing) {
		this.position = position;
		this.facing = facing;
	}

	public void changePosition(Vec2 positionDelta) {
		Vec2 xDelta = facing.mul(positionDelta.y());
		Vec2 yDelta = u.toVec2().neg().mul(positionDelta.x());
		position = position.add(xDelta).add(yDelta);
	}

	public void changeFacing(double theta) {
		double cosTheta = Math.cos(theta);
		double sinTheta = Math.sin(theta);
		double xPos = facing.x() * cosTheta - facing.y() * sinTheta;
		double yPos = facing.x() * sinTheta + facing.y() * cosTheta;
		facing = new Vec2(xPos, yPos).unitVector();
	}

	public void init(InputListener il, MouseInputListener mil) {
		imageHeight = (int) Math.round((double) imageWidth / aspectRatio);
		imageWidth = Math.max(imageWidth, 1);
		renderer = new Renderer(imageWidth, imageHeight, il, mil);
		viewportWidth = viewportHeight 
				* ((double) imageWidth / (double) imageHeight);
		update();
	}

	public void update() {
		// camera basis vectors
		v = new Vec3(0.0, 0.0, 1.0); // up 
		u = Vec3.cross(v, facing.toVec3()).unitVector(); // side
		w = facing.toVec3().unitVector(); // back
		// viewport basis vectors
		Vec2 viewportU = u.mul(viewportWidth).toVec2();
		pixelDeltaU = (u.mul(viewportWidth).div(imageWidth)).toVec2();
		Vec2 offset = w.add(viewportU.div(2.0)).toVec2();
		pixel0Position = position.sub((offset.add(pixelDeltaU)).div(2.0));
	}

	public long draw() {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < imageWidth; i++) {
			Ray r = getRay(i);	
			HitRecord rec = new HitRecord();
			if (world.hit(r, new Interval(0.001, Global.INFINITY), rec)) {
				Colour columnCol = getColumnColour(r, rec);
				int columnHeight = getColumnHeight(r, rec);
				renderer.writeColumn(columnCol, backgroundCol, i, columnHeight);
			} else {
				renderer.writeColumn(backgroundCol, backgroundCol, i, imageHeight); 
			}
		}
		renderer.repaint();
		return System.currentTimeMillis() - startTime;

	}

	private Colour getColumnColour(Ray r, HitRecord rec) {
		double distance = (r.at(rec.t()).sub(r.origin())).length();
		double value = 1.0 - (distance / 10.0);
		return new Colour(value, value, value);

	}

	// TODO: Columns should be larger than screen 
	// viewport is a cut-out of the centre.
	private int getColumnHeight(Ray r, HitRecord rec) {
		double distance = (r.at(rec.t()).sub(r.origin())).length();
		return (int) Math.round(
				(-imageHeight / 10) * distance + (imageHeight * 1.1));

	}

	private Ray getRay(int x) {
		Vec2 offsetX = pixelDeltaU.mul(x);
		Vec2 pixelPos = pixel0Position.add(offsetX);
		Vec2 rayDir = pixelPos.sub(position);
		return new Ray(position, rayDir);

	}
}
