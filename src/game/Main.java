package game;

import window.Window;

public class Main {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String TITLE = "Snake game";
	
	public static final int TILE_WIDTH = 12;
	
	public static void main(String[] args) {
		Window gameWindow = Window.getWindow();
		
		Thread th = new Thread(gameWindow);
		th.start();
	}
}

