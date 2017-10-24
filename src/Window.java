import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
/**
 * @author Abdul Rehman
 *
 */
public class Window extends JFrame{
	//board buttons
	private Button buttons[][];
	//Toggle Buttons
	private Button togButtons[];
	//outer grid
	private Container oContainer;
	//inner grid
	private Container iContainer[];
	//display window
	private Container window;
	//grid format
	private GridLayout gridLayout;
	//menu
	private Menu menu;
	
	private Grid grid;
	//set up GUI
	public Window() {
		super ("Sudoku");
		// set up the layout,(both inner and outer are the same type of layout)
		this.gridLayout = new GridLayout(3, 3, 2, 2);
		//create back-end grid
		try {
			this.grid = new Grid(new File("res/proj3data1.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(this.grid);
		//this.limits = new GridBagConstraints();
		
		//create the outer grid
		//this.oContainer = getContentPane();
		this.oContainer = new Container();
		this.oContainer.setLayout(new GridLayout(3, 3, 10, 10));
		
		//create the inner grids
		this.iContainer = new Container[10];
		for(int i = 0; i < 9; ++i){
			this.iContainer[i] = new Container();
			this.iContainer[i].setLayout(gridLayout);
		}
		//TOGGLE BUTTONS
		this.togButtons = new Button[10];
		this.iContainer[9] = new Container();
		this.iContainer[9].setLayout(new BoxLayout(this.iContainer[9], BoxLayout.PAGE_AXIS));
		for (int i = 0; i < 9; ++i){
			this.togButtons[i] = new Button(Integer.toString((i+1)), i+1, false);
			this.togButtons[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Button temp = (Button) e.getSource();
					turnOffAll();
					temp.toggleSwitch();
					
					if (temp.getToggle()){
						temp.setBackground(Color.GREEN);
					}
					else {
						temp.setBackground(null);
					}
				}
			});
			this.togButtons[i].setPreferredSize(new Dimension (90, 90));
			this.togButtons[i].setFont(new Font("Arial", 1, 90));
			this.iContainer[9].add(this.togButtons[i]);
		}
		this.togButtons[9] = new Button("x", 0, false);
		this.togButtons[9].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Button temp = (Button) e.getSource();
				turnOffAll();
				temp.toggleSwitch();
				
				if (temp.getToggle()){
					temp.setBackground(Color.GREEN);
				}
				else {
					temp.setBackground(null);
				}
			}
		});
		this.togButtons[9].setPreferredSize(new Dimension (90, 90));
		this.togButtons[9].setFont(new Font("Arial", 1, 90));
		this.iContainer[9].add(this.togButtons[9]);
		
		//create the buttons for the board
		//then add buttons to the container then add 
		//the container to the larger grid
		buttons = new Button[Grid.GRID_SIZE][Grid.GRID_SIZE];
		initBoard();
		for (int i = 0; i< Grid.GRID_SIZE; ++i){
			this.oContainer.add(this.iContainer[i]);
		}
		display();
		//Add menu into the list
		this.menu = new Menu(this);
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(menu.getFileMenu());
		bar.add(menu.getHintMenu());
		bar.add(menu.getHelpMenu());
		
		setResizable(false);
		//setSize( 750, 750 );
		pack();
		setVisible( true );
		
	}//end of constructor Window
	/**
	 * @return finds the buttons that are toggled on and returns their value
	 **/
	public int findToggleInt(){
		int ret = -1;
		for (int i = 0; i < 10; ++i){
			if (this.togButtons[i].getToggle()){
				ret = this.togButtons[i].getNum();
				break;
			}
		}
		return ret;
	}
	/**
	 * turns off all the toggles and resets their colors
	 **/
	public void turnOffAll(){
		for (int i = 0; i < 10; ++i){
			if (this.togButtons[i].getToggle()){
				this.togButtons[i].toggleSwitch();
				this.togButtons[i].setBackground(null);
			}
		}
	}

	public void initBoard(){
		for (int r = 0; r < Grid.GRID_SIZE; ++r){
			for (int c = 0; c < Grid.GRID_SIZE; ++c){
				int contents = this.grid.getCell(r, c).getContents();
				String text = String.valueOf(contents);
				this.buttons[r][c] = new Button(contents == 0 ? "" : text, contents, r, c);
				
				int containerR = r / 3;
				int containerC = c / 3;
				int containerI = 3 * containerR + containerC; 
				
				this.buttons[r][c].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						Button temp = (Button) e.getSource();
						int val = findToggleInt();
						if (val == -1){
							return;
						}
						else if (val == 0){
							boolean changed = Window.this.grid.setCellValue(temp.getButtonRow(), temp.getButtonCol(), val, true);
							if (changed){
								temp.setNum(val);
								temp.setText(" ");
							}
						}
						else {
							boolean changed = Window.this.grid.setCellValue(temp.getButtonRow(), temp.getButtonCol(), val, true);
							if (changed){
								temp.setNum(val);
								temp.setText(Integer.toString(val));
							}
						}
						System.out.println(grid);
					}
				});
				
				this.buttons[r][c].setPreferredSize(new Dimension (100, 100));
				this.buttons[r][c].setFont(new Font("Arial", 0, 100));
				
				this.iContainer[containerI].add(this.buttons[r][c]);
				
					
			}
		}
	}
	
	public void setGrid(Grid g){
		this.grid = g;
		for (int r = 0; r < Grid.GRID_SIZE; ++r) {
			for (int c = 0; c < Grid.GRID_SIZE; ++c) {
				int cell = this.grid.getCellValue(r, c);
				this.buttons[r][c].setText(cell == 0 ? "" : String.valueOf(cell));
			}
		}
		
	}
	public Grid getGrid(){
		return this.grid;
	}
	
	public void display(){
		//window that gets displayed
		this.window = getContentPane();
		this.window.setLayout(new BoxLayout(this.window, BoxLayout.LINE_AXIS));
		this.window.add(this.oContainer);
		this.window.add(Box.createRigidArea(new Dimension(10,0)));
		this.window.add(this.iContainer[9]);
	}
}
