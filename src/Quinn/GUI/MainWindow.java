package Quinn.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;

import Quinn.Shapes.Circle;
import Quinn.Shapes.Figure;
import Quinn.IO.*;

/**
 * @version 1.0
 * 
 * This class creates a window that allows the user to draw shapes of different sizes and colors
 * and save those shapes
 * 
 * list of shapes is automatically restored from file when window starts up
 * 
 * @author Colin Quinn
 *
 */

public class MainWindow extends JFrame {
	
	public static final String FIGURES_FILE_NAME = "figures";
	
	protected JFrame frame;
	protected ShapeInformationPanel shapeInformationPanel;		// need reference for click handler
	
	protected ArrayList<Figure> figures;
	protected Figure currentFigure;			// Most recent figure object
	protected Color currentColor;			// Color of figure that will be drawn
	protected String currentShape;			// The type figure that will be drawn
	protected boolean firstClick;			// True if first click, false if second click
	
	/**
	 * Creates main window and reads in a list of figure objects
	 * from file figures.ser in project directory
	 * 
	 * @param args		not used
	 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow window = new MainWindow();
                try {
                	FiguresInput input = new FiguresInput();
                	window.setFigures( input.read(FIGURES_FILE_NAME) );
                }
                catch (Exception exception) {
                	System.out.println("Figures not found");
                	window.setFigures( new ArrayList<Figure>() );
                }                
                window.create();
            }
        });
    }
    /**
     * sets the list of figures
     * used to set figures to list of figures found in file
     * 
     * @param figureList
     */
    public void setFigures (ArrayList<Figure> figureList) {
    	figures = figureList;
    }
	
    /**
     * initializes and adds components to main window
     */
	public void create() {
		firstClick = true;
		currentShape = "rectangle";
		currentColor = Color.red;
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		shapeInformationPanel = new ShapeInformationPanel();
		
		frame.getContentPane().add( BorderLayout.EAST, shapeInformationPanel );
		frame.getContentPane().add( BorderLayout.WEST, new DrawPanel() );
		frame.getContentPane().add( BorderLayout.CENTER, new ButtonPanel() );
		frame.setSize(1000, 500);
		frame.pack();
		frame.setVisible(true);
		
	}
	/**
	 * @version 1.0
	 * 
	 * Panel for drawing shapes based on mouse clicks
	 * 
	 * @author Colin Quinn
	 *
	 */
	class DrawPanel extends JPanel {
		
		Point point1;
		Point point2;
		
		/**
		 * constructs drawing panel
		 * drawing panel allows user to create shapes with the coordinates from two mouse clicks
		 * displays current date in lower left
		 */
		public DrawPanel() {
			setBorder( BorderFactory.createLineBorder(Color.black) );
			setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS ));
			this.add( Box.createVerticalStrut(380) );
			this.add( Box.createHorizontalStrut(7) );
			
			// place date in lower left
			LocalDate date = LocalDate.now();
			JLabel dateLabel = new JLabel( DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(date) );
			this.add(dateLabel);
			this.add( Box.createHorizontalStrut(10) );
			
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent click) {
					if (firstClick == true) {
						point1 =  click.getPoint();
						firstClick = false;
					}
					else {
						point2 = click.getPoint();
						switch (currentShape) {
							case "circle":
								currentFigure = new Circle(currentColor, point1, point2);
								figures.add(currentFigure);
								repaint();
								shapeInformationPanel.printFigure(currentFigure);
								break;
							case "rectangle": 
								// prevents user from drawing flat rectangle
								if ( point1.x != click.getX() && point1.y != click.getY() ) {
									currentFigure = new Quinn.Shapes.Rectangle(
											currentColor, point1, point2
										);
									figures.add(currentFigure);
									repaint();
									shapeInformationPanel.printFigure(currentFigure);
								}
								break;
							default:
								System.out.println("No Shape Selected");
						}
						firstClick = true;
					}
				}
			});
		}
		/**
		 * set panel size
		 */
		public Dimension getPreferredSize() {
			return new Dimension(400, 400);
		}
		@Override
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			if ( !figures.isEmpty() ) {
				
				for (Figure figure : figures) {
					figure.draw(graphics);
				}
			}
		}
		
	}
	/**
	 * @version 1.0
	 * 
	 * This panel holds six buttons
	 * Three change the color shapes will be drawn (red, green, or blue)
	 * Two select shape to be drawn (rectangle, circle)
	 * One to save shapes to a file and exit
	 * 
	 * @author Colin Quinn
	 *
	 */
	class ButtonPanel extends JPanel {
		
		/**
		 * Constructs panel and places buttons
		 */
		public ButtonPanel() {
			
			// create buttons
			JButton redButton = new JButton("red");
			JButton greenButton = new JButton("green");
			JButton blueButton = new JButton("blue");
			
			JButton rectangleButton = new JButton("rectangle");
			JButton circleButton = new JButton("circle");
			JButton exitButton = new JButton("exit");
			
			// add listeners
			redButton.addActionListener( new ColorListener(Color.red) );
			greenButton.addActionListener( new ColorListener(Color.green) );
			blueButton.addActionListener( new ColorListener(Color.blue) );
			
			rectangleButton.addActionListener( new ShapeListener("rectangle") );
			circleButton.addActionListener( new ShapeListener("circle") );
			
			// exits program and saves list of figures
			exitButton.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					try {
						FiguresOutput output = new FiguresOutput();
						output.write(figures, FIGURES_FILE_NAME);
						System.out.println("Saved successfully");
					}
					catch (Exception exception){
						System.out.println("Save failed");
					}

					System.exit(0);

				}
			});
			
			// add buttons to frame
			this.setLayout( new GridLayout(2, 3) );
			this.add(redButton);
			this.add(greenButton);
			this.add(blueButton);
			this.add(rectangleButton);
			this.add(circleButton);
			this.add(exitButton);
		}
		
		
	}
	/**
	 * @version 1.0
	 * 
	 * This panel contains a text area which shows a list of
	 * shapes that have been drawn
	 * 
	 * @author Colin Quinn
	 *
	 */
	class ShapeInformationPanel extends JPanel {
		
		JTextArea textArea;
		/**
		 * constructs panel and creates and sets size of text area
		 */
		public ShapeInformationPanel() {
			
			textArea = new JTextArea(24, 35);
			textArea.setBorder( BorderFactory.createLineBorder(Color.black) );
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			this.add(textArea);
			if ( !figures.isEmpty() ) {
				
				for (Figure figure : figures) {
					this.printFigure(figure);
				}
			}
		}
		/**
		 * sets size of panel
		 */
		public Dimension getPreferredSize() {
			return new Dimension(400, 400);
		}
		/**
		 * appends a shape's information to the text area
		 * 
		 * @param figure	shape to be printed on text area
		 */
		public void printFigure(Figure figure) {
			textArea.append( figure.toString() + "\n" );
		}
	}
	/**
	 * @version 1.0
	 * 
	 * sets currentColor
	 * 
	 * @author Colin Quinn
	 *
	 */
	class ColorListener implements ActionListener {
		Color color;
		/**
		 * constructs listener that changes currentColor to 
		 * color specified in constructor
		 * 
		 * @param aColor
		 */
		public ColorListener(Color aColor) {
			super();
			color = aColor;
		}
		/**
		 * changes color
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			currentColor = color;
		}
	}
	/**
	 * @version 1.0
	 * 
	 * sets shape of currentFigure
	 * 
	 * @author Colin Quinn
	 *
	 */
	class ShapeListener implements ActionListener {
		String shape;
		/**
		 * constructs listener that changes shape of currentFigure
		 * shape specified in constructor
		 *   
		 * @param aShape	"rectangle" or "circle"
		 */
		public ShapeListener(String aShape) {
			super();
			shape = aShape;
		}
		/**
		 * changes shape
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			currentShape = shape;
		}
	}
}
