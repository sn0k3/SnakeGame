package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import scene.GameScene;
import window.Window;

public class Snake {
	public Rectangle[] body = new Rectangle[100];
	public int bodyWidth, bodyHeight;
	
	public int size;
	public int snakeTail = 0;
	public int snakeHead = 0;
	
	public double originalWaitBetweenUpds = 0.1;
	public double waitBetweenUpdates = 0;
	
	public SnakeDir currentSnakeDirection = SnakeDir.RIGHT;
	public SnakeDir prevSnakeDirection;
	
	public Rectangle background;
	
	public Snake(Rectangle background, int size, int startX, int startY, int bodyWidth, int bodyHeight) {
		this.size = size;
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
		
		for(int i = 0; i <= size; i++) {
			Rectangle bodyPiece = new Rectangle(startX + i * bodyWidth, startY, bodyWidth, bodyHeight);
			body[i] = bodyPiece;
			snakeHead++;
		}
		snakeHead--;
		
		this.background = background;
	}
	
	public void grow() {
		int posX = 0;
		int posY = 0;
		
		if(currentSnakeDirection == SnakeDir.UP) {
			posX = body[snakeTail].x;
			posY = body[snakeTail].y + bodyWidth;
		} else if(currentSnakeDirection == SnakeDir.LEFT) {
			posX = body[snakeTail].x + bodyWidth;
			posY = body[snakeTail].y;			
		} else if(currentSnakeDirection == SnakeDir.RIGHT) {
			posX = body[snakeTail].x - bodyWidth;
			posY = body[snakeTail].y;			
		} else if(currentSnakeDirection == SnakeDir.DOWN) {
			posX = body[snakeTail].x;
			posY = body[snakeTail].y + bodyWidth;
		}
		
		Rectangle bodyPiece = new Rectangle(posX, posY, bodyWidth, bodyHeight);
		
		snakeTail = (snakeTail - 1) % body.length;
		body[snakeTail] = bodyPiece;

		GameScene.playerScore++;
	}
	
	public boolean collisionWithSnakeBody() {
		Rectangle sHead = body[snakeHead];
		
		for(int i = snakeTail; i != snakeHead; i = (i + 1) % body.length) {
			if(collisionDetection(sHead, body[i]) == true) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkCollide(Rectangle rect) {		
		for(int i = snakeTail; i != snakeHead; i = (i + 1) % body.length) {
			if(collisionDetection(rect, body[i]) == true) {
				return true;
			}
		}
		
		return false;		
	}
	
	public boolean collisionWithScreen(Rectangle rect1) {
		return rect1.x < background.x || (rect1.x + rect1.width) > background.x + background.width ||
				rect1.y < background.y || (rect1.y + rect1.height) > background.y + background.height;
	}
	
	public boolean collisionDetection(Rectangle r1, Rectangle r2) {
		
		return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width && 
				r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
	}
	
	public void changeSnakeDir(SnakeDir snakeDir) {
		prevSnakeDirection = currentSnakeDirection;
	
		if(snakeDir == SnakeDir.UP && currentSnakeDirection != SnakeDir.DOWN && (prevSnakeDirection != SnakeDir.LEFT || prevSnakeDirection != SnakeDir.RIGHT)) {
			currentSnakeDirection = snakeDir;
		} else if(snakeDir == SnakeDir.LEFT && currentSnakeDirection != SnakeDir.RIGHT) {
			currentSnakeDirection = snakeDir;
		} else if(snakeDir == SnakeDir.RIGHT && currentSnakeDirection != SnakeDir.LEFT) {
			currentSnakeDirection = snakeDir;
		} else if(snakeDir == SnakeDir.DOWN && currentSnakeDirection != SnakeDir.UP && (prevSnakeDirection != SnakeDir.LEFT || prevSnakeDirection != SnakeDir.RIGHT)) {
			currentSnakeDirection = snakeDir;
		}
	}
	
	public void update(double deltaTime) {
		if(waitBetweenUpdates > 0) {
			waitBetweenUpdates = waitBetweenUpdates - deltaTime;

			return;
		}
		
		waitBetweenUpdates = originalWaitBetweenUpds;
		
		if(collisionWithSnakeBody() == true || collisionWithScreen(body[snakeHead])) {
			Window.getWindow().changeState(GameState.MENU);
		}
		
		int posX = 0;
		int posY = 0;
		
		if(currentSnakeDirection == SnakeDir.UP) {
			posX = body[snakeHead].x;
			posY = body[snakeHead].y - bodyWidth;
		} else if(currentSnakeDirection == SnakeDir.LEFT) {
			posX = body[snakeHead].x - bodyWidth;
			posY = body[snakeHead].y;			
		} else if(currentSnakeDirection == SnakeDir.RIGHT) {
			posX = body[snakeHead].x + bodyWidth;
			posY = body[snakeHead].y;			
		} else if(currentSnakeDirection == SnakeDir.DOWN) {
			posX = body[snakeHead].x;
			posY = body[snakeHead].y + bodyWidth;
		} 
		
		body[(snakeHead + 1) % body.length] = body[snakeTail];
		body[snakeTail] = null;
		snakeHead = (snakeHead + 1) % body.length;
		snakeTail = (snakeTail + 1) % body.length;
		
		body[snakeHead].x = posX;
		body[snakeHead].y = posY;
	}
	
	public void draw(Graphics2D g) {
		for(int i = snakeTail; i != snakeHead; i = (i + 1) % body.length) {
			Rectangle piece = body[i];
			int subWidth = (piece.width - 6) / 2;
			int subHeight = (piece.height - 6) / 2;

//			g.setColor(new Color(99, 51, 131));
			g.setColor(new Color(74, 131, 44));
			g.fillRect(piece.x + 2, piece.y + 2, subWidth, subHeight);
			g.fillRect(piece.x + 4 + subWidth, piece.y + 2, subWidth, subHeight);

			g.fillRect(piece.x + 2, piece.y + 4 + subHeight, subWidth, subHeight);
			g.fillRect(piece.x + 4 + subWidth, piece.y + 4 + subHeight, subWidth, subHeight);			
		}
	}
}
