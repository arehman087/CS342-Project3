import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class Menu extends JFrame {
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenu hintMenu;
	private Window window;
	
	/**
	 * Creates menu that's used to load or store puzzles,
	 * inform the player how to play and creators, or give hints to 
	 * the user
	 * */
	public Menu(Window w) {
		this.window = w;
		//set up menu 
		this.fileMenu = new JMenu("File");
		this.fileMenu.setMnemonic('F');
		
		//set up menu item
		/**
		 * loadItem - Takes in a .txt file and loads the board with numbers
		 * storeItem - Saves the board in a .txt file
		 * exitItem - closes the GUI 
		 */
		JMenuItem loadItem = new JMenuItem("Load a Puzzle");
		this.fileMenu.add(loadItem);
		loadItem.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent open){
						JFileChooser fileOpen = new JFileChooser();
						fileOpen.showOpenDialog(loadItem);
						fileOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
						File load = fileOpen.getSelectedFile();
						try {
							Menu.this.window.setGrid(new Grid(load));
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}); 
		
		JMenuItem storeItem = new JMenuItem("Store a Puzzle");
		this.fileMenu.add(storeItem);
		storeItem.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JFileChooser fileOpen = new JFileChooser();
						fileOpen.showOpenDialog(storeItem);
						fileOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
						File save = fileOpen.getSelectedFile();
						try {
							FileWriter fW = new FileWriter(save);
							BufferedWriter bW = new BufferedWriter(fW);
							Menu.this.window.getGrid().write(bW);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
		}); // need to implement later
		
		JMenuItem exitItem = new JMenuItem("Exit");
		this.fileMenu.add(exitItem);
		exitItem.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
					
				});
		
		//Help Menu
		/**
		 * ruleItem - Displays the rules for Sudoku
		 * playItem - Displays the controls to the player
		 * aboutItem - Displays the authors name and netIDs
		 */
		this.helpMenu = new JMenu("Help");
		this.helpMenu.setMnemonic('?');
		
		//set Help menu items
		JMenuItem ruleItem = new JMenuItem("Rules");
		this.helpMenu.add(ruleItem);
		ruleItem.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e){
				 JOptionPane.showMessageDialog(null, "Each of the nine blocks "
				 		+ "has to contain all the numbers 1-9 within its squares. "
				 		+ "Each number can only appear once in a row, column o"
				 		+ "r box.", "Rules", JOptionPane.INFORMATION_MESSAGE);
			}
		}); 
		
		JMenuItem playItem = new JMenuItem("How to play");
		this.helpMenu.add(playItem);
		playItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				 JOptionPane.showMessageDialog(null, "Use the toggle buttons located on the right "
				 		+ "to select a number. Then select a space on the board to place your "
				 		+ "number.", "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JMenuItem aboutItem = new JMenuItem("About...");
		this.helpMenu.add(aboutItem);
		aboutItem.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e){
				 JOptionPane.showMessageDialog(null, "Team Members: \n Anatoly"
				 		+ " Tverdovsky - atverd2 \n Abdul Rehman - arehma7"
				 		, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//Hint Menu
		/**
		 * checker- stops the user from inputing the wrong number into
		 * the space and displays an error message
		 * 
		 * sAlgItem-search through the blank cells and see if the Single Algorithm can be applied to that cell to
		 * resolve it. Once one cell has been resolved, stop processing
		 * 
		 * hAlgItem- search through the blank cells and see if the Hidden Algorithm can be applied to that cell to
		 * resolve it. Once one cell has been resolved, stop processing
		 * 
		 * LCAlgItem-search through the blank cells and see if the Locked Candidate Algorithm can be applied to that cell to
		 * resolve it. Once one cell has been resolved, stop processing
		 * 
		 * NPAlgItem-search through the blank cells and see if the Naked Pairs Algorithm can be applied to that cell to
		 * resolve it. Once one cell has been resolved, stop processing
		 * 
		 * FillItem- repeatedly process all of the blank cells until none of the algorithms can resolve any
		 * blank cell. Note that once a single blank cell has been resolved, your program should
		 * restart this process with the first blank cell. 
		 */
		this.hintMenu = new JMenu("Hint");
		this.hintMenu.setMnemonic('H');
		
		//set Hint menu items
		JCheckBoxMenuItem checker = new JCheckBoxMenuItem("Check");
		this.hintMenu.add(checker);
		checker.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AbstractButton temp = (AbstractButton) e.getSource();
				boolean selected = temp.getModel().isSelected();
				Menu.this.window.setHint(selected);
			}
		});
		
		JMenuItem sAlgItem = new JMenuItem("Single");
		this.hintMenu.add(sAlgItem);
		sAlgItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Menu.this.window.getGrid().solveSingle();
				Grid update = Menu.this.window.getGrid();
				Menu.this.window.setGrid(update);
			}
		});
		
		JMenuItem hAlgItem = new JMenuItem("Hidden");
		this.hintMenu.add(hAlgItem);
		hAlgItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Menu.this.window.getGrid().solveHiddenSingle();
				Grid update = Menu.this.window.getGrid();
				Menu.this.window.setGrid(update);
				System.out.println(update);
			}
		});
		
		JMenuItem LCAlgItem = new JMenuItem("locked Candidate");
		this.hintMenu.add(LCAlgItem);
		LCAlgItem.addActionListener(null);
		
		JMenuItem NPAlgItem = new JMenuItem("Naked Pairs");
		this.hintMenu.add(NPAlgItem);
		NPAlgItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Menu.this.window.getGrid().solveNaked();
				Grid update = Menu.this.window.getGrid();
				Menu.this.window.setGrid(update);
				System.out.println(update);
			}
		});
		
		JMenuItem fillItem = new JMenuItem("Fill");
		this.hintMenu.add(fillItem);
		fillItem.addActionListener(null);
	}
	/**
	 * @return gets the file menu contents
	 */
	public JMenu getFileMenu(){
		return this.fileMenu;
	}
	/**
	 * @return gets the help menu contents
	 */
	public JMenu getHelpMenu(){
		return this.helpMenu;
	}
	/**
	 * @return gets the hint menu contents
	 */
	public JMenu getHintMenu(){
		return this.hintMenu;
	}
}
