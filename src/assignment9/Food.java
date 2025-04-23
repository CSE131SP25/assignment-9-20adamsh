package assignment9;

import java.awt.Color;
import java.util.LinkedList;

import edu.princeton.cs.introcs.StdDraw;

public class Food {

	// The constant size of the food
	public static final double FOOD_SIZE = 0.02;
	private double x, y;

	
	/**
	 * Creates a new Food object at a random location within the bounds of the game window
	 */
	public Food() {
		// Initialize the food's position with random x and y coordinates between 0 and 1
		this.x = Math.random();
		this.y = Math.random();
	}
	
	// Setter for x-coordinate of food
	public void setX(double x) {
		this.x = x;
	}
	
	// Setter for y-coordinate of food
	public void setY(double y) {
		this.y = y;
	}
	
	// Getter for x-coordinate of food
	public double getX() {
		return x;
	}
	
	// Getter for y-coordinate of food
	public double getY() {
		return y;
	}
	
	
	/**
	 * Draws the food object on the screen using a filled circle.
	 * The color of the food is set to a sandy brown.
	 */
	public void draw() {
		// Set the pen color for drawing the food (sandy brown)
		StdDraw.setPenColor(210, 180, 140);
		// Draw the filled circle representing the food at (x, y) with the specified size
		StdDraw.filledCircle(x, y, FOOD_SIZE);
	}
	
}
