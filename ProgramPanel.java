import java.awt.*;
import java.awt.event.*;

public class ProgramPanel extends Panel implements KeyListener
{
	//Instance fields
	int width; //width of the tetris board
	int height; //height of the tetris board
	int rows; //number of rows on the tetris board
	int cols; //number of columns on the tetris board
	Tetris t; //new tetris game

	//Constructor
	/**Constructs a program panel where KeyListener is added and the background of the panel is set 
	  *to light gray. In addition, the constructor instantiates instance fields width, height, rows, cols, 
	  *and t.
	  */
	public ProgramPanel(int width, int height, int rows, int cols) //width, height, rows, cols
	{
		setBackground( Color.LIGHT_GRAY) ;
		addKeyListener(this);
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		t = new Tetris(width, height, rows, cols, this);
	}
	  
	/**This method updates the content displayed on the screen*/
	public void paint(Graphics g)
	{
		t.update(g);
	}
	
	/**If user presses left arrow, the piece will shift down by one unit. If user presses right arrow, the 
	    *piece will shift right by one unit. If user presses down arrow, the piece will shift down one 
	    *unit. If user presses up arrow, the piece will rotate 90 degrees clockwise.
	    */
	   public void keyPressed(KeyEvent ke)
	   {
		   switch (ke.getKeyCode())
		   {
			   case KeyEvent.VK_LEFT: 	if (t.shiftLeft())
			   								repaint();
			   							break;
			   case KeyEvent.VK_RIGHT:	if (t.shiftRight())
			   								repaint();
			   							break;
			   case KeyEvent.VK_DOWN:	if (t.shiftDown())
			   								repaint();
			   							break;

			   case KeyEvent.VK_UP:		if (t.rotate())
			   								repaint();
			   							break;
		   }
	   }

	   /**Unimplemented methods of interface KeyListener*/
	   public void keyTyped(KeyEvent keyEvent){}
	   public void keyReleased(KeyEvent keyEvent){}

}
