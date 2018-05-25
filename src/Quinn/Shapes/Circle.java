package Quinn.Shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.awt.Point;

/**
 * @version 1.0
 * 
 * Defines a circle based on center point, radius, and color
 * 
 * @author Colin Quinn
 *
 */
public class Circle extends Figure implements Serializable {
	
	private Point center;
	private Point edge;
	private double radius;
	/**
	 * draws solid circle with color from parent
	 * 
	 * @param graphics
	 */
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		graphics.fillOval(
				/*
				 * fill oval draws from top right of rectangular space
				 * around oval so must shift center point to top right
				 */
				(int) (center.x - radius),
				(int) (center.y - radius),
				(int) this.getDiameter(),
				(int) this.getDiameter()
			);
	}
	/**
	 * Creates a circle based on two clicks by user
	 * first click is center and second is a point on
	 * circumference (referred to as edge)
	 * 
	 * @param aColor	Color selected by user
	 * @param aCenter	coordinates of center
	 * @param aEdge		coordinates of edge point
	 */
	public Circle(Color aColor, Point aCenter, Point aEdge) {
		color = aColor;
		center = aCenter;
		edge = aEdge;
		radius = center.distance(edge);
	}
	/**
	 * 
	 * @return center
	 */
	public Point getCenter() {
		return center;
	}
	/**
	 * 
	 * @return edge (point on circumference)
	 */
	public Point getEdge() {
		return edge;
	}
	/**
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}
	/**
	 * 
	 * @return diameter
	 */
	public double getDiameter() {
		return radius * 2;
	}
	/**
	 * concatenates fields
	 */
	@Override
	public String toString() {
		return "Circle [x=" + center.x + ", y=" + center.y
		+ ", radius=" + (int) radius + ", color=" + color + "]";
	}

}
