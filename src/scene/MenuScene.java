package scene;

import game.GameState;
import game.Main;
import input.KeyInputListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import window.Window;

public class MenuScene extends Scene {
	
	public KeyInputListener keyListener;
	
	public MenuScene(KeyInputListener keyListener) {
		this.keyListener = keyListener;
	}
	
	@Override
	public void update(double deltaTime) {
		if(keyListener.isKeyPressed(KeyEvent.VK_SPACE) == true) {
			Window.getWindow().changeState(GameState.PLAY);
		}
		
		if(keyListener.isKeyPressed(KeyEvent.VK_Q) == true) {
			Window.getWindow().changeState(GameState.EXIT);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(233, 128, 55));
		g.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		
		g.setColor(Color.white);
		g.drawString("---MENU---", 340, 150);
		g.drawString("Press SPACE to Start Game", 300, 200);
		g.drawString("Press Q to Exit", 325, 250);
	}

}
