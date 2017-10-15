import static org.junit.Assert.*;

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
}
