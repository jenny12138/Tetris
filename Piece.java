import java.awt.Color;

class Piece
{
	//Instance Fields
	public int row; //Row number of the top left corner of piece on Tetris board
	public int col; //Column number of the top left corner of piece on Tetris board
	public int [][] structure; //what the tetris piece looks like
	public int [][] board; //Holds the tetris board
	public Color [] colorArray = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}; //Holds the possible colours for tetris pieces
	int rand = (int)(Math.random() * colorArray.length + 1); //Generates a random number from 1 to colorArray.length inclusive
	
	/**Constructor. The parameters are as follows: row = row number of the top left corner of piece 
	  *on tetris board, col = Column number of the top left corner of piece on tetris board, int[][] 
	  *structure holds what the tetris piece looks like, and int[][]board holds the Tetris board. 
	  */
	public Piece(int row, int col, int[][] structure, int[][] board)
	{
		this.row = row;
		this.col = col;
		this.structure = structure;
		this.board = board;
	}

	/**Moves the current piece down by one unit. Uses draw and undraw methods.*/
   public void shiftDown()
   {
	   unDraw();
	   row++;
	   Draw();
   }
   
   /**Moves the current piece left by one unit. Uses draw and undraw methods.*/
   public void shiftLeft()
   {
	   unDraw();
	   col--;
	   Draw();
   }
   
   /**Moves the current piece right by one unit. Uses draw and undraw methods.*/
   public void shiftRight()
   {
	   unDraw();
	   col++;
	   Draw();
   }
   
   /**Rotates the current piece by 90 degrees clockwise. Uses draw and undraw methods.*/
   public void rotate()
   { 
	   unDraw(); 
	   int [][] temp = new int[4][4];
		
		for (int row = 0; row < structure.length; row++)
		{
			for (int col = 0; col < structure[0].length; col++)
			{
				temp[col][3-row] = structure[row][col];
			
			}
			
		}
		
		for (int i = 0; i < structure.length; i++)
		{
			for (int j = 0; j < structure[0].length; j++)
			{
				structure[i][j] = temp[i][j];
			}
		}
		
		Draw();
		
   }
   
   /**Checks to see if the piece can shift down. The method returns true if the piece can shift down, and 
     *returns false otherwise.
     */
   public boolean canShiftDown()
   {
	   for (int c = 0; c < 4; c++)
	   {
		   int r;
		   for (r = 3; r >= 0; r--)
		   {
			   if (structure[r][c] != 0)
			   {
				   break;
			   }
		   }
		   if (r != -1)
		   {
			    if (board[r+row+1][c+col] != 0)
			    {
			    	 for (int i = 0; i < 4; i++)
			  	   {
			  		   for (int j = 0; j < 4; j++)
			  		   {
			  			   if (structure[i][j] != 0)
			  			   {
			  				   board[i+row][j+col] = rand;
			  			   }
			  		   }
			  	   }
			    		return false;
			    }
		   }
	   }
	   return true;

   }
   
   /**Checks to see if the piece can shift left. The method returns true if the piece can shift left, and 
     *returns false otherwise.
     */
   public boolean canShiftLeft()
   {
	   for (int r = 0; r < 4; r++)
	   {
		   int c;
		   for (c = 0; c < 4 ; c++)
		   {
			   if (structure[r][c] != 0)
			   {
				   break;
			   }
		   }
		   if (c != 4)
		   {
			    if (board[r+row][c+col-1] != 0)
			    {
			    		return false;
			    }
		   }
	   }
	   return true;
   }
   
   /**Checks to see if the piece can shift right. The method returns true if the piece can shift right, and 
     *returns false otherwise.
     */
   public boolean canShiftRight()
   {
	   for (int r = 0; r < 4; r++)
	   {
		   int c;
		   for (c = 3; c >= 0 ; c--)
		   {
			   if (structure[r][c] != 0)
			   {
				   break;
			   }
		   }
		   if (c != -1)
		   {
			    if (board[r+row][c+col+1] != 0)
			    {
			    		return false;
			    }
		   }
	   }
	   return true;
   }
   
   /**Checks to see if the piece can rotate. The method returns true if the piece can rotate, and 
    *returns false otherwise.
    */
   public boolean canRotate()
   { 
	   
	   //make temp array that is an empty version of the board
	   int[][] temp = new int [board.length][board[0].length];
	   int [][] smallTemp = new int[4][4];
	   
	   for (int i = 0; i < board.length; i++)
	   {
		   for (int j = 0; j < board[0].length; j++)
		   {
			   temp[i][j] = 0;
		   }
	   }
	   
	   //rotate the piece on the temp array
		for (int row = 0; row < structure.length; row++)
		{
			for (int col = 0; col < structure[0].length; col++)
			{
				smallTemp[col][3-row] = structure[row][col];
			}
			
		}
		
		for (int i = 0; i < 4; i++)
		   {
			   for (int j = 0; j < 4; j++)
			   {
				   if (smallTemp[i][j] != 0)
				   {
					   temp[i+row][j+col] = 7;
				   }
			   }
		   }
		
		//compare the two. If any two values are the same (not including values of the current Piece) then it cannot rotate
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				if ( (temp[i][j] != 0) && (board[i][j] != 0))
				{
						if (board[i][j] != 8)
						{
							return false;
						}
				}
			}
		}
	   return true;
   }
   
   /**Erases a 4 by 4 area on the Tetris board before a transformation such as shifting downwards has 
     *occurred. 
     */
   public void unDraw()
   {
	   for (int i = 0; i < 4; i++)
	   {
		   for (int j = 0; j < 4; j++)
		   {
			   if (structure[i][j] != 0)
			   {
				   board[i+row][j+col] = 0;
			   }
		   }
	   }
   }
   
   /**Draws the current piece back on the Tetris board after a transformation such as shifting downwards has 
     *occurred
     */
   public void Draw()
   {
	   for (int i = 0; i < 4; i++)
	   {
		   for (int j = 0; j < 4; j++)
		   {
			   if (structure[i][j] != 0)
			   {
				   board[i+row][j+col] = 8;
			   }
		   }
	   }
   }
	
}

