package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SnakeFood {
	public Rectangle background;
	public Snake snake;
	public int width;
	public int height;
	public Color color;
	
	public Rectangle rect;
	public boolean isFoodEntitySpawned;
	
	public SnakeFood(Rectangle background, Snake snake, int width, int height, Color color) {
		this.background = background;
		this.snake = snake;
		this.width = width;
		this.height = height;
		this.color = color;
	
		this.rect = new Rectangle(0, 0, width, height);
	}
	
	public void spawn() {
		do {
			int posX = (int)(Math.random() * (int)background.width / Main.TILE_WIDTH) * Main.TILE_WIDTH + background.x;
			int posY = (int)(Math.random() * (int)background.height / Main.TILE_WIDTH) * Main.TILE_WIDTH + background.y;
			
			this.rect.x = posX;
			this.rect.y = posY;
			
		} while(snake.checkCollide(rect));
		this.isFoodEntitySpawned = true;
	}
	
	public void update(double deltaTime) {
		if(snake.checkCollide(this.rect)) {
			snake.grow();
			this.rect.x = -100;
			this.rect.y = -100;
			this.isFoodEntitySpawned = false;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect(this.rect.x, this.rect.y, width, height);
	}
}
