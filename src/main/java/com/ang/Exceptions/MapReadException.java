package com.ang.Exceptions;

public class MapReadException extends Exception {
	public MapReadException(String message) {
		super(message);	
		super.printStackTrace();
	}
}
