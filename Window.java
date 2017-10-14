import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame{
	private Button buttons[];
	private Container container;

	public Window() {
		super ("Sudoku");
	}

	
	
	public static void main( String args[] )
	   {
		Window application = new Window();
		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   } 
}
