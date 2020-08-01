public class BankOfPieces
{
   public Piece p; //The piece that ends up getting chosen
   public int[][] board; //Holds the tetris board

   BankOfPieces(int [][] board)
   {
	  this.board = board; //Holds the tetris board
   }

   /**Contains all seven tetris pieces which are hardcoded, and a random number generator that 
     *selects one of the pieces at random
     */
   public Piece nextPiece()
   {
      int rand = (int)(Math.random() * 7); //Generates a random number between 0 and 6 inclusive
      
      if (rand==0) 
      {
    	  return new Piece(0, 3, new int [][] {{0,0,0,0}, {0,2,2,0}, {0,0,2,2},{0,0,0,0}}, board); //Z
      }
      
      else if (rand==1) 
      {
    	  return new Piece(0, 3, new int [][] {{0,0,0,0}, {0,2,2,0}, {0,2,2,0},{0,0,0,0}}, board); //square
      }
      
      else if (rand==2) 
      {
    	  return new Piece(0, 3, new int [][] {{0,2,0,0}, {0,2,0,0}, {0,2,0,0},{0,2,0,0}}, board); //straight line
      }
      
      else if (rand==3) 
      {
    	  return new Piece(0, 3, new int [][] {{0,0,0,0}, {0,0,2,2}, {0,2,2,0},{0,0,0,0}}, board); //S
      }
      
      else if (rand==4) 
      {
    	  return new Piece(0, 3, new int [][] {{0,2,0,0}, {0,2,0,0}, {0,2,2,0},{0,0,0,0}}, board); //L
      }
      
      else if (rand==5) 
      {
    	  return new Piece(0, 3, new int [][] {{0,0,2,0}, {0,0,2,0}, {0,2,2,0},{0,0,0,0}}, board); //J
      }
      
      else
      {
    	  return new Piece(0, 3, new int [][] {{0,0,0,0}, {0,2,2,2}, {0,0,2,0},{0,0,0,0}}, board); //T
      }

   }
}
