import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Defines the class for unit testing the Grid class.
 */
public class GridTest {
	private static HashSet<Integer> FULL_CANDIDATE_LIST;
	
	@BeforeClass
	/**
	 * Initializes the static fields of the GridTest class.
	 */
	public static void initStatics() {
		FULL_CANDIDATE_LIST = new HashSet<Integer>();
		for (int i = 1; i <= Grid.GRID_SIZE; ++i) {
			FULL_CANDIDATE_LIST.add(i);
		}
	}
	
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
				assertEquals(FULL_CANDIDATE_LIST, cellAt.getCandidates());
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
			
			// Assert all cells in the row and column do not have the cell in
			// their candidate list.
			int r = cells[i].getRow();
			int c = cells[i].getColumn();
			int v = cells[i].getContents();
			for (int j = 0; j < Grid.GRID_SIZE; ++j) {
				assertFalse(grid.getCell(r, j).getCandidates().contains(v));
				assertFalse(grid.getCell(j, c).getCandidates().contains(v));
			}
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
	
	@Test
	/**
	 * Tests the write method of the Grid.
	 */
	public void doesWrite() {
		Grid grid = new Grid();
		
		// Array containing the expected row, column and contents of each 
		// cell defined above.
		int[] expectedRow      = { 1, 2, 5, 5, 5, 5, 5, 7, 8, 9 };
		int[] expectedColumn   = { 4, 4, 1, 3, 5, 7, 9, 6, 6, 8 };
		int[] expectedContents = { 2, 8, 1, 3, 5, 7, 9, 2, 8, 2 };
		
		// Set the grid's row, column and contents according to above arrays
		for (int i = 0; i < expectedRow.length; ++i) {
			grid.setCellValue(expectedRow[i] - 1, 
					expectedColumn[i] - 1, 
					expectedContents[i], false);
		}
		
		// For this test, let's write to a string but in reality we would
		// probably write to a file.
		StringWriter sW   = new StringWriter();
		BufferedWriter bW = new BufferedWriter(sW);
		try {
			grid.write(bW);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Read the result string line by line, make sure each line has the
		// correct expected row, column and contents.
		String result = sW.toString();
		String lines[] = result.split("\n");
		assertEquals(expectedRow.length, lines.length);
		for (int i = 0; i < lines.length; ++i) {
			String line = lines[i];
			String[] lineContents = line.split(" ");
			
			int row = Integer.parseInt(lineContents[0]);
			int col = Integer.parseInt(lineContents[1]);
			int con = Integer.parseInt(lineContents[2]);
			
			assertEquals(expectedRow[i],      row);
			assertEquals(expectedColumn[i],   col);
			assertEquals(expectedContents[i], con);
		}
	}
}
