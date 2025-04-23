package assignment9;

import java.util.LinkedList;
import java.awt.Color;

public class Snake {

	private static final double SEGMENT_SIZE = 0.05;  // Size of each body segment
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 0.5;  // Movement increment per direction change
	private LinkedList<BodySegment> segments;  // List holding the body segments of the snake
	private double deltaX;  // Change in X position per move
	private double deltaY;  // Change in Y position per move
	
	public Snake() {
		// Initializes the snake with a single body segment at the center (0.5, 0.5)
		segments = new LinkedList <BodySegment>();
		segments.add(new BodySegment(0.5, 0.5, SEGMENT_SIZE));
		deltaX = 0;  // Initial horizontal movement (no movement)
		deltaY = 0;  // Initial vertical movement (no movement)
	}
	
	/**
	 * Changes the direction of the snake's movement.
	 * @param direction the direction to move: 1 for up, 2 for down, 3 for left, 4 for right
	 */
	public void changeDirection(int direction) {
		if(direction == 1) { // up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2) { // down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3) { // left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4) { // right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
	}
	
	/**
	 * Gets the list of segments that make up the snake.
	 * @return the LinkedList of body segments
	 */
	public LinkedList<BodySegment> getSegments() {
		return segments;
	}
	
	/**
	 * Moves the snake by updating the position of each segment based on the current direction.
	 * The snake grows when it eats food, and food is removed from the game.
	 * @param foods the list of food objects that the snake may eat
	 */
	public void move(LinkedList<Food> foods) {
		// Shift each body segment to the position of the segment ahead of it
		for (int i = segments.size() - 1; i > 0; i--) {
			BodySegment behind = segments.get(i);
			BodySegment ahead = segments.get(i - 1);
			behind.setX(ahead.getX());
			behind.setY(ahead.getY());
		}
		
		// Move the head by the current deltaX and deltaY values
		BodySegment head = segments.get(0);
		head.setX(head.getX() + deltaX);
		head.setY(head.getY() + deltaY);
		
		// Check if the snake eats any food and grow the snake if it does
		for (Food f : foods) {
			if (eatFood(f)) {
				// Add a new segment to the tail if food is eaten
				BodySegment tail = segments.getLast();
				double x = tail.getX();
				double y = tail.getY();
				segments.add(new BodySegment(x, y, SEGMENT_SIZE));
				foods.remove(f);  // Remove the food from the game
				break;
			}
		}
	}
	
	/**
	 * Draws the snake by rendering each segment in green color.
	 * The head of the snake is rendered in a darker shade of green.
	 */
	public void draw() {
		// Draw all segments in the snake
		for (BodySegment segment: segments) {
			segment.draw(Color.GREEN);
		}
		
		// Draw the head in a darker green to distinguish it
		BodySegment head = segments.get(0);
		Color darkGreen = new Color(0, 100, 0);  // Dark green color for the head
		head.draw(darkGreen);
	}
	
	/**
	 * The snake attempts to eat the given food.
	 * The snake grows if it successfully eats the food.
	 * @param f the food to be eaten
	 * @return true if the snake successfully ate the food, false otherwise
	 */
	public boolean eatFood(Food f) {
		// Get the head's position and the food's position
		BodySegment head = segments.get(0);
		double headX = f.getX();
		double headY = f.getY();
		
		// Get the radius of the food and the head of the snake
		double foodRadius = Food.FOOD_SIZE;
		double foodX = f.getX();
		double foodY = f.getY();
		
		// Calculate the closest point on the food to the head of the snake
		double closestX = Math.max(headX, Math.min(headX, foodX + head.getSize()));
		double closestY = Math.max(headY, Math.min(headY, foodY + head.getSize()));
		
		// Calculate the distance between the snake's head and the food
		double distance = Math.sqrt(Math.pow(head.getX() - foodX, 2) + Math.pow(head.getY() - foodY, 2));
		
		// If the distance between the head and food is less than the sum of their radii, the snake eats the food
		if (distance <= head.getSize() / 2 + Food.FOOD_SIZE / 2) {
			return true;
		}
		
		return false;  // The snake didn't eat the food
	}
	
	/**
	 * Checks if the head of the snake is within the bounds of the game window.
	 * The head must remain inside the window at all times.
	 * @return true if the snake's head is within the window bounds, false otherwise
	 */
	public boolean isInbounds() {
		// Get the head's position
		BodySegment head = segments.get(0);
		double headX = head.getX();
		double headY = head.getY();
		
		// Check if the head is within the bounds of the window (taking segment size into account)
		return headX >= SEGMENT_SIZE / 4 && headX <= 1 - (SEGMENT_SIZE / 4) &&
				headY >= SEGMENT_SIZE / 4 && headY <= 1 - (SEGMENT_SIZE / 4);
	}
}
