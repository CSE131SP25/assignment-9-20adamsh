package assignment9;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class BodySegment {

	// Coordinates (x, y) for the segment's position and the size of the segment
	private double x, y, size;
	// Color of the segment
	private Color color;
	
	/**
	 * Constructs a new BodySegment with specified position and size.
	 * The segment is initially colored green.
	 * 
	 * @param x the x-coordinate of the segment
	 * @param y the y-coordinate of the segment
	 * @param size the size (width/height) of the segment
	 */
	public BodySegment(double x, double y, double size) {
		// Initialize position and size of the segment
		this.x = x;
		this.y = y;
		this.size = size;
		// Set the default color to green
		color = Color.GREEN;
	}
	
	// Setter for the x-coordinate of the segment
	public void setX(double x) {
		this.x = x;
	}
	
	// Setter for the y-coordinate of the segment
	public void setY(double y) {
		this.y = y;
	}
	
	// Getter for the x-coordinate of the segment
	public double getX() {
		return x;
	}
	
	// Getter for the y-coordinate of the segment
	public double getY() {
		return y;
	}
	
	// Getter for the size of the segment
	public double getSize() {
		return size;
	}
	
	
	/**
	 * Draws the body segment as a filled square with the given color.
	 * 
	 * @param color the color used to draw the segment
	 */
	public void draw(Color color) {
		// Set the drawing color
		StdDraw.setPenColor(color);
		// Draw the segment as a filled square with the center at (x, y) and half the size
		StdDraw.filledSquare(x, y, size / 2);
	}
	
}
