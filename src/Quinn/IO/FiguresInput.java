package Quinn.IO;

import java.io.*;
import java.util.ArrayList;
import Quinn.Shapes.*;

/**
 * @version 1.0
 * 
 * class reads list of figures from file 
 * 
 * @author Colin Quinn
 *
 */

public class FiguresInput {
	/**
	 * reads length of list of figures from file and then reads list of figures
	 * itself from file
	 * 
	 * @param fileName		String name of file
	 * @return				ArrayList of figures
	 * @throws Exception	IOException or ClassNotFoundException
	 */
	public ArrayList<Figure> read (String fileName) throws Exception {
		try {
			FileInputStream fileInput = new FileInputStream(fileName);
			ObjectInputStream figuresInput = new ObjectInputStream(fileInput);
			
			int size = figuresInput.readInt();
			ArrayList<Figure> figures = new ArrayList<Figure>();
			
			for (int i = 0; i < size; i++) {
				figures.add( (Figure) figuresInput.readObject() );
			}
			
			return figures;
		}
		catch (IOException exception) {
			throw exception;
		}
		catch (ClassNotFoundException exception) {
			System.out.println("Incompatible file");
			throw exception;
		}
		
	}
}
