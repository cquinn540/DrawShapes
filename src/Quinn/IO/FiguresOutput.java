package Quinn.IO;

import java.io.*;
import java.util.ArrayList;
import Quinn.Shapes.*;

/**
 * @version 1.0
 * 
 * class writes length of list of figures to file 
 * 
 * @author Colin Quinn
 *
 */
public class FiguresOutput {
	/**
	 * Writes length of list of figures to file and then writes list of figures itself
	 * 
	 * @param figures			ArrayList of figures
	 * @param fileName			Name of file to be written to
	 * @throws IOException
	 */
	public void write (ArrayList<Figure> figures, String fileName) throws IOException {
		try {
			FileOutputStream fileOuput = new FileOutputStream(fileName);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOuput);
			
			int size = figures.size();
			objectOutput.writeInt(size);
			
			for(int i = 0; i < size; i++) {
				objectOutput.writeObject(figures.get(i));
			}
			objectOutput.close();
		}
		catch (IOException exception) {
			throw exception;
		}
	}
}
