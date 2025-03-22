package com.ang;

public enum MapFileDataType {
	VEC2,
	INT,
	INTS,
	COLOUR;

	private int length;

	private MapFileDataType() {}

	public void setLength(int length) {
		if (this != MapFileDataType.INTS) {
			return;

		}
		this.length = length;
	}

	public int length() {
		if (this != MapFileDataType.INTS) {
			return -1;

		}
		return length;

	}
}
