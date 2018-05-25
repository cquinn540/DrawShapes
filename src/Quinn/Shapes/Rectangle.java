package Quinn.Shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.awt.Point;

/**
 * @version 1.0
 * 
 * Defines a rectangle based on top right corner, bottom left corner,
 * height and length
 * 
 * @author Colin Quinn
 *
 */
public class Rectangle extends Figure implements Serializable {
	
	private Point corner1;	//  top right corner
	private Point corner2;	//	bottom left corner
	private int height;
	private int length;
	/**
	 * draws	solid rectangle of color in parent
	 * @param
	 */
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		graphics.fillRect(
				corner1.x,
				corner1.y,
				this.getLength(),
				this.getHeight()
			);
	}
	/**
	 * Creates a rectangle object based on user's clicks
	 * first click is one corner and second click is opposite
	 * corner along diagonal
	 * 
	 * @param aColor		chosen color
	 * @param ClickPoint1	first click coordinates
	 * @param ClickPoint2	second click coordinates
	 */
	public Rectangle(Color aColor, Point ClickPoint1, Point ClickPoint2) {
		
		color = aColor;
		
		int x1 = ClickPoint1.x;
		int y1 = ClickPoint1.y;
		int x2 = ClickPoint2.x;
		int y2 = ClickPoint2.y;		
		
		/*
		 * makes sure corner1 is top right corner of rectangle regardless 
		 * of where user's second click is
		 */
		if (x1 < x2) {
			if (y1 < y2) {
				corner1 = new Point(x1, y1);
				corner2 = new Point(x2, y2);
			}
			else {
				corner1 = new Point(x1, y2);
				corner2 = new Point(x2, y1);
			}
		}
		if (x1 > x2) {
			if (y1 < y2) {
				corner1 = new Point(x2, y1);
				corner2 = new Point(x1, y2);
			}
			else {
				corner1 = new Point(x2, y2);
				corner2 = new Point(x1, y1);
			}
		}
		
		height = (int) Math.abs(y1 - y2);
		length = (int) Math.abs(x1 - x2);
		
	}
	/**
	 * 
	 * @return top right corner
	 */
	public Point getCorner1() {
		return corner1;
	}
	/**
	 * 
	 * @return top left corner
	 */
	public Point getCorner2() {
		return corner2;
	}
	/**
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * 
	 * @return length (width)
	 */
	public int getLength() {
		return length;
	}
	/**
	 * concatenates fields
	 */
	@Override
	public String toString() {
		return "Rectangle [x=" + corner1.x + ", y=" + corner1.y
		+ ", width=" + length + ", height " + height + ", color=" + color + "]";
	}

}
