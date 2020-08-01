import java.util.*;
import java.awt.*;
import javax.swing.JOptionPane;

public class Tetris 
{
	//Instance fields for drawing the board
	int rows; //number of rows indicated by users
	int bufferRows; //number of rows + 5
	int cols; //number of columns indicated by users
	int bufferCols; //number of columns + 4
	int width; //width of board
	int height; //height of board
	int size; //size of each square
	Point topLeft = new Point(); //the position of the top left corner each time a square is drawn
	public int [][] board; //The tetris board, with both the displayed region and the buffer regions
	public int [][] previousBoard; //holds the colours of the previous board, to preserve colour of original pieces
	public ProgramPanel pan; //Object of ProgramPanel, used for method repaint in ProgramPanel
	public Color [] colorArray = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}; //Holds an array of colour, used to generate colour for each Tetris piece
	Piece p; //Current piece that is falling
	BankOfPieces pieceBank; //Object of BankOfPieces, which holds all of the seven Tetris pieces

	//Instance fields for using the timer
	private long delay = 0; //The delay before the program starts.
	private long period = 400; //How fast the tetris piece is falling (e.g. time in between each time the piece moves, in milliseconds)
	   
	public int linesCleared = 0; //Holds the number of lines the user cleared in total in the game
	public int level = 1; //Holds the user's current level
	public int score = 0; //Holds the user's current score
	
	
	/**Constructor, in order of parameters: Width of the board, 
	  *height of the board, number of rows indicated by user, number of cols indicated by user, 
	  *object of ProgramPanel (used for method repaint in ProgramPanel)
	  */

	public Tetris (int width, int height, int rows, int cols, ProgramPanel pan)
	{
		
		//Constructors for drawing the board
		this.pan = pan;
		this.rows = rows;
		this.cols = cols;
		this.width = width;
		this.height = height;
		bufferRows = rows + 5; //4 on top to fill the buffer, 1 on bottom to stop piece
		bufferCols = cols + 4; //2 on each side
		board = new int [bufferRows][bufferCols];
		previousBoard = new int [bufferRows][bufferCols];
		
		size = Math.min(width / bufferCols, height / bufferRows);
		topLeft.x = (width - size * bufferCols)/2;
		topLeft.y = (height - size * bufferRows)/2;
		
		pieceBank = new BankOfPieces(board);
		
		//Fill the board with 0s
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				board[i][j] = 0;
			}
		}
		
		//Filling left side buffer
		for (int i = 0; i < bufferRows; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				board[i][j] = 9; //9 means filled for buffer
			}
		}
		
		//Filling right side buffer
		for (int i = 0; i < bufferRows; i++)
		{
			for (int j = bufferCols-2; j < bufferCols; j++)
			{
				board[i][j] = 9; //9 means filed for buffer
			}
		}
		
		//Filling bottom buffer
		for (int i = 0; i < bufferCols; i++)
		{
			board[board.length-1][i] = 9; //9 means filled for buffer
		}
		
		//copy everything on current board to previousBoard to preserve colour of past pieces
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				previousBoard[i][j] = board[i][j];
			}
		}
		
		startGame();
	}
	
	public void update (Graphics g)
	{
		//copy everything on board to previousBoard before a new piece is called	
		for (int i = 0; i < board.length; i++)
		 {
			 for (int j = 0; j < board[0].length; j++)
			 {
				 previousBoard[i][j] = board[i][j];
			}
		 }
		
		//Draw the Tetris board and the contents on it
		for (int i = 4; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
		  	{
				g.setColor(Color.BLACK); //reset everything to black
				
				if (board[i][j] == 0) //if it's an empty block
				{
					g.drawRect(topLeft.x, topLeft.y, size, size);
			  		g.setColor(Color.WHITE);
			  		g.fillRect(topLeft.x, topLeft.y, size, size);
			  		topLeft.x = topLeft.x + size + 1;
				}
				else if (board[i][j] == 8) //if the piece is currently falling
				{
					g.drawRect(topLeft.x, topLeft.y, size, size);
			  		g.setColor(colorArray[p.rand - 1]);
			  		g.fillRect(topLeft.x, topLeft.y, size, size);
			  		topLeft.x = topLeft.x + size + 1;
				}
				else if (board[i][j] > 0 && board[i][j] < 7) //So that pieces preserve their original colour
				{
					g.drawRect(topLeft.x, topLeft.y, size, size);
					int temp = previousBoard[i][j];
			  		g.setColor(colorArray[temp-1]);
			  		g.fillRect(topLeft.x, topLeft.y, size, size);
			  		topLeft.x = topLeft.x + size + 1;
				}
				else //if it's a buffer-region wall, paint it black
				{
					g.setColor(Color.BLACK);
					g.drawRect(topLeft.x, topLeft.y, size, size);
			  		g.fillRect(topLeft.x, topLeft.y, size, size);
			  		topLeft.x = topLeft.x + size + 1;
				}
		  	}
			
			topLeft.x = topLeft.x - size * bufferCols - bufferCols; //bring x coordinate back to initial value
		  	topLeft.y = topLeft.y + size + 1; //bring y value down by 1 square
		}
		
		topLeft.x = (width - size * bufferCols)/2;  //reset location of topLeft corner
		topLeft.y = (height - size * bufferRows)/2;  //reset location of topLeft corner
		
		/**The current level and score are displayed*/
		g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		g.setColor(Color.BLUE); 
		g.drawString("Current Level: " + level, 10, 20);
		g.drawString("Current Score: " + score, 10, 50);
	}
	
	/**Creates a new timer and timerTask. TimerTask determines whether a new piece should be 
	  *added to the top of the board or whether the current piece should be shifted down. In addition, 
	  *the timer checks if rows can be cleared, and if topping out occurs (the user's pieces have 
	  *reached the top of the board), a message will be sent out telling the user that the game has   
	  *ended. Start game also calls panel to repaint.
	  */

	public void startGame ()
	{
		Timer tm = new Timer();
	
	   // Creates a new TimerTask
		TimerTask motion = new TimerTask()
	      {
	         public void run ()
	         {
	            if (p == null)
	            {
	               p = pieceBank.nextPiece();
	            }
	            else if (p.canShiftDown())
	            {
	              p.shiftDown();
	            }
	            else
	            {
	            		if (toppingOut() == false)
	            		{
	            			boolean temp = checkClearRow();
			            	p = pieceBank.nextPiece();
			            	
			            	if (temp == true)
			            	{
		            			for (int i = 0; i < rows; i++)
		            			{
		            				temp = checkClearRow();
		            				p = pieceBank.nextPiece();
		            				if (temp == false)
		            				{
		            					break;
		            				}
		            			}
			            	}
	            		}
	            		else
	            		{
	            			tm.cancel();
	            			System.out.println("The end!");
	            			JOptionPane.showMessageDialog(null, 
	                                "Try again to beat your score! Please exit the window now.", 
	                                "The end", 
	                                JOptionPane.PLAIN_MESSAGE);
	            		}
	            }
	            pan.repaint();
	         }
	      };
	      
	      tm.scheduleAtFixedRate(motion, delay, period);
	}
	
	/**Checks if piece can shift down. If so, the method shifts the current piece down by one unit. 
	   *Otherwise, the method returns false
	   */

	 public boolean shiftDown()
	 {
		 if (p == null)
		 {
			 return false;
		 }
		 else
		 {
			if ( p.canShiftDown() )
			{
				p.shiftDown();
				return true;
			}
		 }
		 return false;
   }
	 
	 /**Checks if piece can shift left. If so, the method shifts the current piece left by one unit. 
	    *Otherwise, the method returns false
	    */

	 public boolean shiftLeft()
	 {
		 if (p == null)
		 {
			 return false;
		 }
		 else
		 {
			if ( p.canShiftLeft())
			{
				p.shiftLeft();
				return true;
			}
		 }
		 return false;
   }
	 
	 /**Checks if piece can shift right. If so, the method shifts the current piece right by one unit. 
	   *Otherwise, the method returns false
	   */
	 public boolean shiftRight()
	 {
		 if (p == null)
		 {
			 return false;
		 }
		 else
		 {
			if ( p.canShiftRight())
			{
				p.shiftRight();
				return true;
			}
		 }
		 return false;
   }
	 
	 /**Checks if piece can rotate. If so, the method rotates the current piece by 90 degrees clockwise. 
	   *Otherwise, the method returns false
	   */
	 public boolean rotate()
	 {
		if (p != null && p.canRotate())
		{
				p.rotate();
				return true;
		}
		 
		return false;
	 }
	 
	/**Checks to see if any rows on the board can be cleared. If so, checkClearRow() calls method 
	  *clearRow() with the full row as the parameter
	  */
	 public boolean checkClearRow()
	 {
		 boolean complete = true;
		 
		 for (int i = board.length - 2; i > 3; i--)
		 {
			 complete = true ;
			 for (int j = 2; j < board[i].length - 2; j++)
			 {
				 if ( board[i][j] == 0)
				 {
					 complete = false;
				 }
			 }
			 
			 if ( complete )
			 {
				 clearRow(i);
			 }
			
		 }
		 
		 return complete;
	 }

	 /**This method clears the row by pulling down everything above it by 1 unit. Each time 
	   *clearRow is called, linesCleared increases by 1 and the score increases by 10 times the level. 
	   *Each time 5 levels are cleared, the player levels up.
	   */

	   public void clearRow(int row)
	   {
		   //Pull down everything above 
		   for (int i = row; i > 4; i--)
		   {
			   for (int j = 2; j < board[i].length - 2; j++)
			   {
				   board[i][j] = board[i-1][j];
			   }
		   }
			   
		   //Calculates score and level
		   linesCleared++;
		  
		   score += 10 * level;
		   
		   if (linesCleared % 5 == 0)
		   {
			   level++;
			   period = period - 50;
			   if (period < 100)
			   {
				   period = 100;
			   }
		   }
	   }
	   
	   /**Checks to see if topping out occurs. Returns true if topping out occurs, returns false otherwise.
	    */
	   public boolean toppingOut()
	   {
		   if ((p.row <= 4) && (p.canShiftDown() == false))
		   {
			   return true;
		   }
		   return false;
	   }
 
}
