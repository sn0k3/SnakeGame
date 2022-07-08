package scene;

import game.Main;
import game.Snake;
import game.SnakeDir;
import game.SnakeFood;
import input.KeyInputListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class GameScene extends Scene {
	private KeyInputListener keyListener;
	
	private Rectangle background;
	private Rectangle foreground;
	
	private Snake snake;
	private SnakeFood snakeFood;
	public static int playerScore = 0;
	
	public GameScene(KeyInputListener keyListener) {
		this.keyListener = keyListener;
		
		this.background = new Rectangle(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);		
		this.foreground = new Rectangle(24, 48, 24 * 31, 24 * 22);
		
		this.snake = new Snake(background, 6, 48, 48 + 24, 24, 24);
		this.snakeFood = new SnakeFood(foreground, snake, 12, 12, Color.RED);
		this.snakeFood.spawn();

		playerScore = 0;
	}
	
	@Override
	public void update(double deltaTime) {
		if(keyListener.isKeyPressed(KeyEvent.VK_UP)) {
			snake.changeSnakeDir(SnakeDir.UP);
		} else if(keyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
			snake.changeSnakeDir(SnakeDir.LEFT);
		} else if(keyListener.isKeyPressed(KeyEvent.VK_RIGHT)){
			snake.changeSnakeDir(SnakeDir.RIGHT);
		} else if(keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
			snake.changeSnakeDir(SnakeDir.DOWN);
		}
				
		if(snakeFood.isFoodEntitySpawned != true) {
			snakeFood.spawn();			
		}
		
		snakeFood.update(deltaTime);
		snake.update(deltaTime);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(background.x, background.y, background.width, background.height);
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(foreground.x, foreground.y, foreground.width, foreground.height);
		
		snake.draw(g2d);
		snakeFood.render(g2d);
		
		// Display score
		g2d.setColor(new Color(99, 51, 131));
		g2d.drawString("Score: " + playerScore, 700, 80);
	}

}
