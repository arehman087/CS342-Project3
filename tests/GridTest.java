import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Defines the class for unit testing the Grid class.
 */
public class GridTest { 
	@Test
	/**
	 * Tests the default constructor of the Grid.
	 */
	public void doesDefaultConstruct() {
		Grid grid = new Grid();
		
		for (int r = 0; r < Grid.GRID_SIZE; ++r) {
			for (int c = 0; c < Grid.GRID_SIZE; ++c) {
				Cell cellAt = grid.getCell(r, c);
				
				assertEquals(r, cellAt.getRow());
				assertEquals(c, cellAt.getColumn());
				assertEquals(0, cellAt.getContents());
				assertFalse(cellAt.getReadOnly());
			}
		}
	}
	
	@Test
	/**
	 * Tests the file constructor of the Grid.
	 */
	public void doesFileConstruct() {
		File file;
		Grid grid = new Grid();
		
		try {
			file = new File("res/proj3data4.txt");
			grid = new Grid(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Get all of the cells which should have been defined in the file
		Cell[] cells = {
				grid.getCell(1 - 1, 4 - 1),
				grid.getCell(2 - 1, 4 - 1),
				grid.getCell(5 - 1, 1 - 1),
				grid.getCell(5 - 1, 3 - 1),
				grid.getCell(5 - 1, 5 - 1),
				grid.getCell(5 - 1, 7 - 1),
				grid.getCell(5 - 1, 9 - 1),
				grid.getCell(7 - 1, 6 - 1),
				grid.getCell(8 - 1, 6 - 1),
				grid.getCell(9 - 1, 8 - 1)
		};
		
		// Array containing the expected row, column and contents of each 
		// cell defined above.
		int[] expectedRow      = { 0, 1, 4, 4, 4, 4, 4, 6, 7, 8 };
		int[] expectedColumn   = { 3, 3, 0, 2, 4, 6, 8, 5, 5, 7 };
		int[] expectedContents = { 2, 8, 1, 3, 5, 7, 9, 2, 8, 2 };
		
		// Assert all cells from the file were correctly initialized
		for (int i = 0; i < cells.length; ++i) {
			assertTrue(cells[i].getReadOnly());
			
			assertEquals(expectedRow[i],      cells[i].getRow());
			assertEquals(expectedColumn[i],   cells[i].getColumn());
			assertEquals(expectedContents[i], cells[i].getContents());
		}
		
		// Go through all other cells, assert they are R/W zero initialized
		for (int r = 0; r < Grid.GRID_SIZE; ++r) {
			for (int c = 0; c < Grid.GRID_SIZE; ++c) {
				// Skip this cell if it is defined in the file
				boolean skip = false;
				for (int i = 0; i < expectedRow.length; ++i) {
					if (r == expectedRow[i] && c == expectedColumn[i]) {
						skip = true;
						break;
					}
				}
				if (skip) continue;
				
				// Else, assert zero initialized R/W cell
				Cell cell = grid.getCell(r, c);
				assertFalse(cell.getReadOnly());
				assertEquals(r, cell.getRow());
				assertEquals(c, cell.getColumn());
				assertEquals(0, cell.getContents());
			}
		}
	}
}
