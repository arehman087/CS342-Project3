import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * @author Abdul Rehman
 *
 */
public class Window extends JFrame{
	//board buttons
	private Button buttons[];
	//Toggle Buttons
	private Button togButtons[];
	//outer grid
	private Container oContainer;
	//inner grid
	private Container iContainer[];
	//grid format
	private GridLayout Grid;

	
	//set up GUI
	public Window() {
		super ("Sudoku");
		// set up the layout,(both inner and outer are the same type of layout)
		this.Grid = new GridLayout(3, 3);
		//this.limits = new GridBagConstraints();
		
		//create the outer grid
		this.oContainer = getContentPane();
		this.oContainer.setLayout(Grid);
		
		//create the inner grids
		this.iContainer = new Container[9];
		for(int i = 0; i < 9; ++i){
			this.iContainer[i] = new Container();
			this.iContainer[i].setLayout(Grid);
		}
		/*create the buttons for the board
		 *then add buttons to the container then add 
		 *the container to the larger grid
		 */
		buttons = new Button[81];
		for (int i = 0; i < 9; ++i){
			for (int j = 0; j < 9; ++j){
				this.buttons[(i+1)*j] = new Button(Integer.toString((j+1)));
				this.iContainer[i].add(buttons[(i+1)*j]);
			}
			this.oContainer.add(((Component)this.iContainer[i]));
		}
		
		setSize( 750, 750 );
		setVisible( true );
		
	}//end of constructor Window

	
	/*main method just used for running GUI
	 */
	public static void main( String args[] )
	   {
		Window application = new Window();
		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   } 
}
