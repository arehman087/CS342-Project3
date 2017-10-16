import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JFrame {
	private JLabel disp;
	/**
	 * Creates menu that's used to load or store puzzles
	 * Creates menu that's used to inform the player how to play and 
	 * creators
	 * Creates menu that's used to give hints to the user
	 * */
	public Menu() {
		//set up menu 
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		//set up menu item
		/**
		 * loadItem - Takes in a .txt file and loads the board with numbers
		 * storeItem - Saves the board in a .txt file
		 * exitItem - closes the GUI 
		 */
		JMenuItem loadItem = new JMenuItem("Load a Puzzle");
		fileMenu.add(loadItem);
		loadItem.addActionListener(null); // need to implement later
		
		JMenuItem storeItem = new JMenuItem("Store a Puzzle");
		fileMenu.add(storeItem);
		loadItem.addActionListener(null); // need to implement later
		
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		exitItem.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}//inner class to close GUI
					
				});
		
		//Help Menu
		/**
		 * ruleItem - Displays the rules for Sudoku
		 * playItem - Displays the controls to the player
		 * aboutItem - Displays the authors name and netIDs
		 */
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('?');
		
		//set Help menu items
		JMenuItem ruleItem = new JMenuItem("Rules");
		helpMenu.add(ruleItem);
		ruleItem.addActionListener(null); // implement later
		
		JMenuItem playItem = new JMenuItem("How to play");
		helpMenu.add(playItem);
		playItem.addActionListener(null);
		
		JMenuItem aboutItem = new JMenuItem("About...");
		helpMenu.add(aboutItem);
		aboutItem.addActionListener(null);
		
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
		JMenu hintMenu = new JMenu("Hint");
		hintMenu.setMnemonic('H');
		
		//set Hint menu items
		JCheckBoxMenuItem checker = new JCheckBoxMenuItem("Check");
		hintMenu.add(checker);
		checker.addActionListener(null);
		
		JMenuItem sAlgItem = new JMenuItem("Single");
		hintMenu.add(sAlgItem);
		sAlgItem.addActionListener(null);
		
		JMenuItem hAlgItem = new JMenuItem("Hidden");
		hintMenu.add(hAlgItem);
		hAlgItem.addActionListener(null);
		
		JMenuItem LCAlgItem = new JMenuItem("locked Candidate");
		hintMenu.add(LCAlgItem);
		LCAlgItem.addActionListener(null);
		
		JMenuItem NPAlgItem = new JMenuItem("Naked Pairs");
		hintMenu.add(NPAlgItem);
		NPAlgItem.addActionListener(null);
		
		JMenuItem fillItem = new JMenuItem("Fill");
		hintMenu.add(fillItem);
		fillItem.addActionListener(null);
		
		//put the menus into the bar (Having some problems)
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(fileMenu);
		bar.add(hintMenu);
		bar.add(helpMenu);
		
	}

}
