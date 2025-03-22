package com.ang;

import com.ang.Hittable.HittableList;
import com.ang.Hittable.CubeWorld;
import com.ang.Exceptions.MapParseException;
import com.ang.Graphics.Colour;
import com.ang.Maths.Vec2;

public class MapFileParser {
	private String path;
	private String[] lines;

	public MapFileParser(String path) {
		this.path = path;
	}

	public MapData parseMapData(String[] lines) throws MapParseException {
		this.lines = lines;
		ParsingData parsingData = new ParsingData();
		if (!validFile(parsingData)) {
			throw new MapParseException(path + " Is not a valid map file");

		}
		MapData mapData = new MapData();
		mapData.setWorldType(mapData.worldType());
		mapData.setPosition(parseVec2(parsingData.positionLineNumber()));
		mapData.setFacing(parseVec2(parsingData.facingLineNumber()));
		if (parsingData.worldType() == WorldType.CUBEWORLD) {
			Colour[] colours = parseColours(parsingData.coloursLineNumber());
			HittableList world = parseCubeWorld(parsingData.worldLineNumber(), colours);
			mapData.setWorld(world);
		} else {
			mapData.setWorld(parseSectorWorld(parsingData.worldLineNumber()));
		}
		return mapData;

	}

	private boolean validFile(ParsingData pData) {
		if (!charsMatch(lines[0], "TOP") || !charsMatch(lines[lines.length - 1], "BOTTOM")) {
			return false;

		}
		boolean worldFound = false;
		boolean positionFound = false;
		boolean facingFound = false;
		boolean coloursFound = false;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			// TODO: Add support for sector world "-SECTORWORLD"
			if (charsMatch(line, "-CUBEWORLD")) {
				worldFound = true;
				pData.setWorld(WorldType.CUBEWORLD);
				pData.setWorldLineNumber(i);
			} else if (charsMatch(line, "-POSITION")) {
				positionFound = true;
				pData.setPositionLineNumber(i);
			} else if (charsMatch(line, "-FACING")) {
				facingFound = true;
				pData.setFacingLineNumber(i);
			} else if (charsMatch(line, "-COLOURS")) {
				coloursFound = true;
				pData.setColoursLineNumber(i);
			}
		}
		return worldFound && positionFound && facingFound && coloursFound;

	}
	
	private HittableList parseSectorWorld(int headLineNum) throws MapParseException {
		// TODO: Implement
		return new HittableList(0);

	}

	private HittableList parseCubeWorld(int headLineNum, Colour[] colours) throws MapParseException {
		MapFileDataType type = parseDataType(headLineNum);
		if (type != MapFileDataType.INTS) {
			throw new MapParseException(path, headLineNum);

		}
		int arrayLength = parseArrayLength(headLineNum);
		String[] arrayLines = extractDataUnderHeader(headLineNum);
		int[][] map = new int[arrayLines.length][arrayLength];
		for (int i = 0; i < arrayLines.length; i++) {
			String line = arrayLines[i];
			boolean didStart = false;
			boolean didEnd = false;
			int[] parsedLine = new int[arrayLength];
			int parsedLineHead = 0;
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if (!didStart && (c == '<')) {
					didStart = true;
					continue;

				}
				if (didStart && !didEnd && (c == '>')) {
					didEnd = true;
					continue;

				}
				if (didStart && !didEnd && (c != '|')) {
					parsedLine[parsedLineHead++] = c - '0'; 
				}
			}
			if (!didStart || !didEnd) {
				throw new MapParseException(path, headLineNum, i);

			}
			map[i] = parsedLine;
		}
		return new CubeWorld(map, colours); 	

	}

	private Vec2 parseVec2(int headLineNum) throws MapParseException {
		MapFileDataType type = parseDataType(headLineNum);
		if (type != MapFileDataType.VEC2) {
			throw new MapParseException(path, headLineNum);

		}
		String[] arrayLines = extractDataUnderHeader(headLineNum);
		if (arrayLines.length > 1) {
			throw new MapParseException(path, headLineNum);

		}
		String line = arrayLines[0];
		boolean didStart = false;
		boolean didEnd = false;
		Vec2 parsedLine = new Vec2();
		String dataString = "";
		int axisToAdd = 0;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (!didStart && (c == '<')) {
				didStart = true;
				continue;

			}
			if (didStart && (c == '>')) {
				didEnd = true;
				parsedLine.setAxis(axisToAdd++, Double.valueOf(dataString));
				dataString = "";
				continue;

			} 
			if (didStart && !didEnd) {
				if (c != '|') {
					dataString += c;	
				} else {
					parsedLine.setAxis(axisToAdd++, Double.valueOf(dataString));
					dataString = "";
				}
			}
		}
		if (!didStart || !didEnd) {
			throw new MapParseException(path, headLineNum);

		}
		return parsedLine;

	}

	private Colour[] parseColours(int headLineNum) throws MapParseException {
		MapFileDataType type = parseDataType(headLineNum);
		if (type != MapFileDataType.COLOUR) {
			throw new MapParseException(path, headLineNum);

		}
		String[] arrayLines = extractDataUnderHeader(headLineNum);
		Colour[] colours = new Colour[arrayLines.length];
		int coloursHead = 0;
		for (int i = 0; i < arrayLines.length; i++) {
			String line = arrayLines[i];
			boolean didStart = false;
			boolean didEnd = false;
			Colour parsedLine = new Colour();
			String dataString = "";
			int componentToAdd = 0;
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if (!didStart && (c == '<')) {
					didStart = true;
					continue;

				}
				if (didStart && (c == '>')) {
					didEnd = true;
					parsedLine.setComponent(componentToAdd++, Double.valueOf(dataString));
					dataString = "";
					continue;

				}
				if (didStart && !didEnd) {
					if (c != '|') {
						dataString += c;
					} else {
						parsedLine.setComponent(componentToAdd++, Double.valueOf(dataString));
						dataString = "";
					}
				}
			}
			if (!didStart || !didEnd) {
				throw new MapParseException(path, headLineNum, i);

			}
			colours[coloursHead++] = parsedLine;
		}
		return colours;

	}

	private int parseArrayLength(int headLineNum) throws MapParseException {
		boolean didStart = false;
		boolean didEnd = false;
		String dataString = "";
		String line = lines[headLineNum];
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (!didStart && (c == '~')) {
				didStart = true;
				continue;

			}
			if (didStart && (c == '#')) {
				didEnd = true;
				continue;

			}
			if (didStart && !didEnd) {
				dataString += c;	
			}
		}
		if (!didStart || !didEnd) {
			throw new MapParseException(path, headLineNum);

		}
		return Integer.valueOf(dataString);

	}

	private MapFileDataType parseDataType(int headLineNum) throws MapParseException {
		boolean didStart = false;
		boolean didEnd = false;
		String dataString = "";
		String line = lines[headLineNum];
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (!didStart && (c == '#')) {
				didStart = true;
				continue;

			} 
			if (didStart && (c == '~')) {
				didEnd = true;
				continue;

			}
			if (didStart && !didEnd) {
				dataString += c;	
			}
		}
		if (!didStart || !didEnd) {
			throw new MapParseException(path, headLineNum);

		}
		if (charsMatch(dataString, "INT*")) {
			int len = Integer.valueOf(dataString.substring(3));
			MapFileDataType temp = MapFileDataType.INTS;
			temp.setLength(len);
			return temp;

		} else if (charsMatch(dataString, "VEC2")) {
			return MapFileDataType.VEC2;

		} else if (charsMatch(dataString, "INT")) {
			return MapFileDataType.INT;

		} else if (charsMatch(dataString, "COLOUR")) {
			return MapFileDataType.COLOUR;

		} else {
			throw new MapParseException(path, headLineNum);

		}
	}

	private String[] extractDataUnderHeader(int headLineNum) throws MapParseException {
		int arrayLength = parseArrayLength(headLineNum);
		String[] arrayLines = new String[arrayLength];
		int arrayLinesHead = 0;
		boolean didStart = false;
		boolean didEnd = false;
		for (int i = headLineNum + 1; i < headLineNum + arrayLength + 3; i++) {
			String line = lines[i];
			if (!didStart && charsMatch(line, "START")) {
				didStart = true;
				continue;

			}
			if (didStart && !didEnd && charsMatch(line, "END")) {
				didEnd = true;
				continue;

			}
			if (didStart && !didEnd) {
				arrayLines[arrayLinesHead++] = line;
			}
		}
		if (!didStart || !didEnd) {
			throw new MapParseException();

		}
		return arrayLines;

	}

	public boolean charsMatch(String input, String pattern) {
		final char WILD_CARD = '*';
		char[] inp = input.toCharArray();
		char[] pat = pattern.toCharArray();
		int patternPtr = 0;
		char wildEnd = ' ';
		if (pat.length > inp.length) {
			return false;

		}
		for (int i = 0; i < inp.length; i++) {
			if (patternPtr >= pat.length) {
				return true;

			}
			if ((pat[patternPtr] == WILD_CARD) && (wildEnd == ' ')) {
				if (patternPtr >= pat.length - 1) {
					return true;

				}
				wildEnd = pat[patternPtr + 1];
				continue;

			}
			if (inp[i] == wildEnd) {
				wildEnd = ' ';
				patternPtr++;
			}
			if (pat[patternPtr] != WILD_CARD) {
				if (inp[i] != pat[patternPtr]) {
					return false;

				}
				patternPtr++;
			}
		}
		return true;

	}
}
