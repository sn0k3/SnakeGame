package window;

import game.GameState;
import game.Main;
import input.KeyInputListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

import scene.GameScene;
import scene.MenuScene;
import scene.Scene;

public class Window extends JFrame implements Runnable {
	
	private String title;
	private int width;
	private int height;

	public GameState currentGameState;
	
	public boolean isRunning;
	public Scene currentScene;

	public KeyInputListener keyListener = new KeyInputListener();
	public static Window window;
	
	public Window(int width, int height, String title) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		this.currentGameState = GameState.MENU;
		initComponents();
	}

	public void initComponents() {
		this.setTitle(title);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		isRunning = true;
		changeState(GameState.MENU); // Menu State default
		this.addKeyListener(keyListener);
		
		this.setVisible(true);	
	}
	
	public static Window getWindow() {
		if(Window.window == null) {
			Window.window = new Window(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Main.TITLE);
		}
		
		return Window.window;
	}
	
	public void changeState(GameState state) {
		currentGameState = state;
		
		if(currentGameState == GameState.MENU) {			
			currentScene = new MenuScene(keyListener);
			System.out.println("Menu state");
			
		} else if(currentGameState == GameState.PLAY) {			
			currentScene = new GameScene(keyListener);
			System.out.println("Game state");
			
		} else if(currentGameState == GameState.EXIT) {
			close();
			System.out.println("Exit state");
		
		} else {
			System.out.println("Unknown game state");
			currentScene = null;
		}
	}
	
	public void update(double deltaTime) {
		Image dbImage = this.createImage(width, height);
		Graphics dGraphics = dbImage.getGraphics();

		this.render(dGraphics);
		getGraphics().drawImage(dbImage, 0, 0, this);
		
		currentScene.update(deltaTime);
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		currentScene.render(g);
	}
	
	@Override
	public void run() {
		double lastFrameTime = 0.0;
		try {
			while(isRunning) {
				double time = Time.getTime();
				double deltaTime = time - lastFrameTime;
				lastFrameTime = time;
				
				update(deltaTime);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		isRunning = false;
		this.dispose();
	}
}
