package assignment9;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

import edu.princeton.cs.introcs.StdDraw;

public class Game {
	
	// Instance variables
	private Snake snake;
	private EnemySnakes enemySnake;
	private LinkedList<Food> foods;
	private double spawnTimer;
	
	public Game() {
		StdDraw.enableDoubleBuffering();
		
		// Initialize game objects
		// Create a new Snake, EnemySnakes, and Food objects
		foods = new LinkedList<Food>();
		snake = new Snake();
		enemySnake = new EnemySnakes();
		
		// Add initial food items to the game
		foods.add(new Food());
		foods.add(new Food());
	}
	
	/**
	 * Main game loop
	 * Continuously checks for player input, moves the snake and enemies, checks for collisions,
	 * and updates the game display. The loop continues until the game is over.
	 */
	public void play() {
		// Start the main game loop
		while (true) { 
			// Get the current direction input from the player
			int dir = getKeypress();
			
			// Randomly change the enemy snake's direction (10% chance per frame)
			if (Math.random() < 0.1) {  
                int enemyDir = (int) (Math.random() * 4) + 1;  // Random direction (1 to 4)
                enemySnake.changeDirection(enemyDir);
            }
			
			// Move the enemy snake
			enemySnake.move(foods);
			
			// Move the player's snake based on the current direction
			snake.changeDirection(dir);
			snake.move(foods);
			
			// Check if the player snake collides with the enemy snake
			BodySegment enemyHead = enemySnake.getHead();
			for (BodySegment segment : snake.getSegments()) {
				double dx = enemyHead.getX() - segment.getX();
				double dy = enemyHead.getY() - segment.getY();
				double distance = Math.sqrt(dx * dx + dy * dy);
				
				// If the enemy's head touches any of the player's segments, game over
				if (distance < (enemyHead.getSize() / 2) + (segment.getSize() / 2)) {
					 System.out.println("Game Over! You got bit.");
					 return; // End the game
				}
			}
			
			// Check if the snake is out of bounds
			if (!snake.isInbounds()) {
				System.out.println("Game Over!");
				break;  // Exit the game loop if out of bounds
			}
			
			// Occasionally spawn new food (5% chance) if less than 15 pieces of food exist
			if (Math.random() > 0.95 && foods.size() < 15) {
				foods.add(new Food());
			}
			
			// Update the game screen with new information
			updateDrawing();
		}
	}
	
	/**
	 * Checks which key is pressed to determine the player's movement direction
	 * @return the direction based on key press (1 = up, 2 = down, 3 = left, 4 = right, -1 if no key)
	 */
	private int getKeypress() {
		// Check for key presses and return the corresponding direction
		if (StdDraw.isKeyPressed(KeyEvent.VK_W)) {
			return 1;  // Move up
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
			return 2;  // Move down
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
			return 3;  // Move left
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
			return 4;  // Move right
		} else {
			return -1;  // No movement
		}
	}
	
	/**
	 * Updates the game display by:
	 * 1. Clearing the screen
	 * 2. Drawing the snake, enemy snake, and food items
	 * 3. Pausing briefly to control the speed of the game
	 * 4. Displaying the updated screen to the player
	 */
	private void updateDrawing() {
		// Clear the screen to prepare for the new frame
		StdDraw.clear();
		
		// Draw the player's snake and the enemy snake
		snake.draw();
		enemySnake.draw();
		
		// Draw all the food items on the screen
		for (Food f : foods) {
			f.draw();
		}
		
		// Pause for 50 milliseconds to control the speed of the game
		StdDraw.pause(50);
		
		// Show the updated content on the screen
		StdDraw.show();
	}
	
	/**
	 * Main method to start the game
	 * Initializes a new game object and begins the game loop
	 */
	public static void main(String[] args) {
		// Create a new Game instance and start the game
		Game g = new Game();
		g.play();
	}
}
