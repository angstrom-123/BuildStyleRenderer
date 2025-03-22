package com.ang.Loaders;

import com.ang.Hittables.HittableList;
import com.ang.Maths.Vec2;

public class MapData {
	private WorldType worldType;
	private HittableList world;
	private Vec2 position;
	private Vec2 facing;

	public MapData() {}

	public void setWorldType(WorldType worldType) {
		this.worldType = worldType;
	}

	public void setWorld(HittableList world) {
		this.world = world;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public void setFacing(Vec2 facing) {
		this.facing = facing;
	}

	public WorldType worldType() {
		return worldType;

	}

	public HittableList world() {
		return world;
	
	}

	public Vec2 position() {
		return position;

	}

	public Vec2 facing() {
		return facing;

	}

	public boolean isPopulated() {
		if ((worldType != null) && (world != null) && (position != null) && (facing != null)) {
			return true;

		}
		return false;

	}
}
