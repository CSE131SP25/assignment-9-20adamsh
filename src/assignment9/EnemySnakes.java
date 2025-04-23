package assignment9;
import java.util.LinkedList;
import java.awt.Color;

public class EnemySnakes {

	// Constants for segment size and movement size
	private static final double SEGMENT_SIZE = 0.05; // Size of each segment of the snake
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 0.5; // Movement increment per frame
	
	// Instance variables
	private LinkedList<BodySegment> segments;  // Linked list to store the snake's body segments
	private double deltaX;  // Horizontal movement speed
	private double deltaY;  // Vertical movement speed
	private int direction;  // Current direction of the snake

	// Constructor for initializing the snake
	public EnemySnakes() {
		// Initialize the linked list for the snake's segments
		// FIXME: Set up the segments list
		segments = new LinkedList<BodySegment>();
		// Add the first body segment at position (0.75, 0.75)
		segments.add(new BodySegment(0.75, 0.75, SEGMENT_SIZE));
		deltaX = 0;  // Initial horizontal speed is 0
		deltaY = 0;  // Initial vertical speed is 0
	}
	
	/**
	 * Changes the direction of the enemy snake based on the input direction
	 * @param direction the new direction for the snake (1 = up, 2 = down, 3 = left, 4 = right)
	 */
	public void changeDirection(int direction) {
		// Update the movement direction based on the input
		if (direction == 1) { // Moving up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2) { // Moving down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3) { // Moving left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4) { // Moving right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
	}
	
	/**
	 * Moves the enemy snake based on its current direction and checks for collisions with food
	 * @param foods the list of food objects to check for collisions
	 */
	public void move(LinkedList<Food> foods) {
		// Move each segment of the snake (except the head) by setting its position to the one in front of it
		for (int i = segments.size() - 1; i > 0; i--) {
			BodySegment behind = segments.get(i); // Current segment
			BodySegment ahead = segments.get(i - 1); // Next segment (ahead of the current one)
			behind.setX(ahead.getX());  // Move the segment horizontally
			behind.setY(ahead.getY());  // Move the segment vertically
		}
		
		// Move the head of the snake by updating its position based on deltaX and deltaY
		BodySegment head = segments.get(0);
		head.setX(head.getX() + deltaX);
		head.setY(head.getY() + deltaY);
		
		// Check if the snake eats any food
		for (Food f : foods) {
			if (eatFood(f)) { // If the snake eats the food
				// Add a new segment at the tail's position
				BodySegment tail = segments.getLast();
				double x = tail.getX();
				double y = tail.getY();
				segments.add(new BodySegment(x, y, SEGMENT_SIZE));
				// Remove the eaten food from the list
				foods.remove(f);
				break;
			}
		}
		
		// Automatically change direction if the snake is near the boundary of the screen
		if (1 - head.getX() <  0.1) { // If the snake head is near the right boundary
			changeDirection(3);  // Move left
		}
		if (1 - head.getY() <  0.1) { // If the snake head is near the top boundary
			changeDirection(2);  // Move down
		}
		if (head.getX() <  0.1) { // If the snake head is near the left boundary
			changeDirection(4);  // Move right
		}
		if (head.getY() <  0.1) { // If the snake head is near the bottom boundary
			changeDirection(1);  // Move up
		}
	}
	
	/**
	 * Returns the head segment of the snake
	 * @return the head segment of the snake
	 */
	public BodySegment getHead() {
		return segments.get(0);
	}
	
	/**
	 * Draws the enemy snake by drawing each segment with a specific color
	 * The head of the snake is drawn in a darker shade of red for distinction
	 */
	public void draw() {
		// Draw each segment of the snake using a red color
		for (BodySegment segment: segments) {
			segment.draw(Color.RED);
		}
		// Draw the head of the snake in dark red for distinction
		BodySegment head = segments.get(0);
		Color darkRed = new Color(139, 0, 0);
		head.draw(darkRed);
	}
	
	/**
	 * Checks if the snake eats the provided food
	 * @param f the food to be eaten
	 * @return true if the snake successfully eats the food, false otherwise
	 */
	public boolean eatFood(Food f) {
		// Get the coordinates of the snake's head
		BodySegment head = segments.get(0);
		double headX = f.getX();
		double headY = f.getY();
		
		// Get the radius of the food (size of the food object)
		double foodRadius = Food.FOOD_SIZE;
		
		// Calculate the closest point on the food to the snake's head
		double foodX = f.getX();
		double foodY = f.getY();
		
		// Calculate the distance between the snake's head and the food
		double distance = Math.sqrt(Math.pow(head.getX() - foodX, 2) + Math.pow(head.getY() - foodY, 2));
		
		// If the distance between the head and the food is small enough, consider it as "eating" the food
		if (distance <= head.getSize() / 2 + Food.FOOD_SIZE / 2) {
			return true;
		}
		
		return false; // If the food wasn't eaten, return false
	}
	
	/**
	 * Checks if the head of the snake is still within the bounds of the game window
	 * @return true if the snake's head is within bounds, false if it goes out of bounds
	 */
	public boolean isInbounds() {
		// Get the coordinates of the snake's head
		BodySegment head = segments.get(0);
		double headX = head.getX();
		double headY = head.getY();
		
		// Check if the head's position is within the window's bounds (allowing a small margin around the edges)
		return headX >= SEGMENT_SIZE / 4 && headX <= 1 - (SEGMENT_SIZE / 4) &&
				headY >= SEGMENT_SIZE / 4 && headY <= 1 - (SEGMENT_SIZE / 4);
	}
}
