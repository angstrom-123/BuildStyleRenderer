package com.ang;

public class Main {
    public static void main(String[] args) {
		Game g = new Game(parseArgs(args));
		g.startGame();
    }

	private static int parseArgs(String[] args) {
		if (args.length == 0) {
			return 0;

		}
		return Integer.valueOf(args[0]);
	
	}
}
