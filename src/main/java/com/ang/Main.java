package com.ang;

public class Main {
    public static void main(String[] args) {
		Game g = new Game(parseArgs(args));
		g.startGame();
    }

	private static String parseArgs(String[] args) {
		if (args.length == 0) {
			return "testMap.txt";

		}
		return args[1];
	
	}
}
