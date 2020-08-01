import java.awt.*;

public class ProgramWindow extends Frame
{
		public int width = 1000; //Used to store the width of the Program Window
		public int height = 800; //Used to store the height of the Program Window
		public int rows; //Used to store the number of rows in the Tetris board (not including buffer rows)
		public int cols; //Used to store the number of columns in the Tetris board (not including buffer columns)
		
		/**Constructor adds properties such as resizability and location, creates a new object of 
		  *program panel, and sets dimensions and title of ProgramWindow. The two parameters 
		  *hold the values of the number of rows and columns the user chosen in class Main 
		  */

		public ProgramWindow(int rows, int cols)
		{
			this.rows = rows; //Passed down from what the user entered in class Main
			this.cols = cols; //Passed down from what the user entered in class Main
			
			ProgramPanel panel = new ProgramPanel(width, height, rows, cols); //Creates a new object of ProgramPanel
					
			add(panel); //Attach the "panel" to the frame
			
			setTitle("Tetris"); //Set title of tab
			
			setSize(width, height); //Set dimensions of the window
			
			setLocation(0, 100); //Set the position of the top left corner of the frame
			
			setResizable(true); //Users will be able to change the size of the frame
			
			setVisible(true); //Show the window on the monitor
			
		}
	
		
}
