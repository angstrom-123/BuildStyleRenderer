package com.ang;

import com.ang.Exceptions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
	private String[] fileData;
	@Test
	public void testMapParser() {
		MapFileReader reader = new MapFileReader("/mapData/");
		try {
			fileData = reader.readFile("testMap.txt");
		} catch (MapReadException e) {}
		MapFileParser parser = new MapFileParser("/mapData/testMap.txt");
		assertTrue(fileData[0].equals("TOP"));
		assertTrue(fileData[1].equals("-CUBEWORLD#INT9~9#"));
		assertTrue(parser.charsMatch(fileData[0], "TOP"));
		assertTrue(parser.charsMatch(fileData[1], "-CUBEWORLD*"));
		assertTrue(parser.charsMatch(fileData[1], "-CUBEWORLD#*#"));
		assertTrue(parser.charsMatch(fileData[1], "-CUBEWORLD#INT*~*#"));
		assertFalse(parser.charsMatch(fileData[0], "CUBEWORLD"));
		assertFalse(parser.charsMatch(fileData[0], "-CUBEWORLD"));
		assertFalse(parser.charsMatch(fileData[0], "-CUBEWORLD*f"));
		assertFalse(parser.charsMatch(fileData[0], "CUBEWORLD#g*#"));
	}
}
