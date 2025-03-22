package com.ang;

public class ParsingData {
	private int worldTypeLineNumber;
	private int positionLineNumber;
	private int facingLineNumber;
	private int coloursLineNumber;
	private WorldType worldType;

	public ParsingData() {}

	public void setWorld(WorldType type) {
		worldType = type;
	}

	public void setWorldLineNumber(int num) {
		worldTypeLineNumber = num;
	}

	public void setPositionLineNumber(int num) {
		positionLineNumber = num;
	}

	public void setFacingLineNumber(int num) {
		facingLineNumber = num;
	}

	public void setColoursLineNumber(int num) {
		coloursLineNumber = num;
	}

	public WorldType worldType() {
		return worldType;

	}

	public int worldLineNumber() {
		return worldTypeLineNumber;

	}

	public int positionLineNumber() {
		return positionLineNumber;

	}

	public int facingLineNumber() {
		return facingLineNumber;

	}

	public int coloursLineNumber() {
		return coloursLineNumber;

	}
}
