import java.util.*;
import java.io.*;

public class Main
{
   public static void main (String[] args)
   throws Exception
   {
	   Scanner kbd = new Scanner (System.in);
	   System.out.println("Welcome to Tetris!");
	   System.out.println("When the game loads, use your mouse to click the game board. This will allow you to use the keyboard (up, down, left, and right keys) to rotate the pieces.");
	   System.out.println("Please enter 'yes' now to start the game.");
	   String temp = kbd.next();
	   temp = temp.toLowerCase();
	   if (temp.equals("yes"))
	   {
		   System.out.println("Please enter the number of rows (Tetris boards usually have 20 rows): ");
		   int rows = kbd.nextInt();
		   
		   System.out.println("Please enter the number of columns (Tetris boards usually have 10 column): ");
		   int cols = kbd.nextInt();
		   
	       ProgramWindow task = new ProgramWindow (rows, cols);
	   }
	   else
	   {
		   System.out.println("Please re-run the program and review the instructions above.");
		   System.exit(0);
	   }
   }
 
}