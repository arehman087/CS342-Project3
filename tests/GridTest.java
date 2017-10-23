import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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
	 * Tests the getRegion() method of the Grid.
	 */
	public void doesGetRegion() {
		Grid grid = new Grid();
		
		ArrayList<Cell> cells0 = grid.getRegion(0);
		assertTrue(cells0.contains(grid.getCell(0, 0)));
		assertTrue(cells0.contains(grid.getCell(0, 1)));
		assertTrue(cells0.contains(grid.getCell(0, 2)));
		assertTrue(cells0.contains(grid.getCell(1, 0)));
		assertTrue(cells0.contains(grid.getCell(1, 1)));
		assertTrue(cells0.contains(grid.getCell(1, 2)));
		assertTrue(cells0.contains(grid.getCell(2, 0)));
		assertTrue(cells0.contains(grid.getCell(2, 1)));
		assertTrue(cells0.contains(grid.getCell(2, 2)));
		assertEquals(9, cells0.size());
		
		ArrayList<Cell> cells4 = grid.getRegion(4);
		assertTrue(cells4.contains(grid.getCell(3, 3)));
		assertTrue(cells4.contains(grid.getCell(3, 4)));
		assertTrue(cells4.contains(grid.getCell(3, 5)));
		assertTrue(cells4.contains(grid.getCell(4, 3)));
		assertTrue(cells4.contains(grid.getCell(4, 4)));
		assertTrue(cells4.contains(grid.getCell(4, 5)));
		assertTrue(cells4.contains(grid.getCell(5, 3)));
		assertTrue(cells4.contains(grid.getCell(5, 4)));
		assertTrue(cells4.contains(grid.getCell(5, 5)));
		assertEquals(9, cells4.size());
		
		ArrayList<Cell> cells7 = grid.getRegion(7);
		assertTrue(cells7.contains(grid.getCell(6, 3)));
		assertTrue(cells7.contains(grid.getCell(6, 4)));
		assertTrue(cells7.contains(grid.getCell(6, 5)));
		assertTrue(cells7.contains(grid.getCell(7, 3)));
		assertTrue(cells7.contains(grid.getCell(7, 4)));
		assertTrue(cells7.contains(grid.getCell(7, 5)));
		assertTrue(cells7.contains(grid.getCell(8, 3)));
		assertTrue(cells7.contains(grid.getCell(8, 4)));
		assertTrue(cells7.contains(grid.getCell(8, 5)));
		assertEquals(9, cells7.size());
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
			ArrayList<Cell> region = grid.getRegion(cells[i].getRegion());
			for (int j = 0; j < Grid.GRID_SIZE; ++j) {
				assertFalse(grid.getCell(r, j).getCandidates().contains(v));
				assertFalse(grid.getCell(j, c).getCandidates().contains(v));
			}
			for (Cell cell : region) {
				assertFalse(cell.getCandidates().contains(v));
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
	 * Tests the setCellValue for a case where there is no checking, and the
	 * change is valid and the cell is empty before the change.
	 */
	public void doesSetCellValue_Basic() {
		Grid grid = new Grid();
		grid.setCellValue(3, 5, 8, false);
		
		// Construct set with no eight
		HashSet<Integer> noEight = new HashSet<Integer>();
		for (int i = 1; i <= Grid.GRID_SIZE; ++i) {
			if (i != 8) noEight.add(i);
		}
		
		// Assert eight got removed from candidate lists of all cells on the
		// row and column.
		for (int i = 0; i < Grid.GRID_SIZE; ++i) {
			assertEquals(noEight, grid.getCell(3, i).getCandidates());
			assertEquals(noEight, grid.getCell(i, 5).getCandidates());
		}
		
		// Assert eight got removed from candidate lists of all cells in the
		// region.
		ArrayList<Cell> regions = grid.getRegion(
				grid.getCell(3, 5).getRegion());
		for (Cell cell : regions) {
			assertEquals(noEight, cell.getCandidates());
		}
	}
	
	@Test
	/**
	 * Tests the setCellValue for a case where the cell's value is reset to 
	 * zero.
	 */
	public void doesSetCellValue_ToEmpty() {
		Grid grid = new Grid();
		grid.setCellValue(3, 5, 8, false);
		
		grid.setCellValue(3, 5, 0, false);
		
		// Assert candidate list was reset to full for each row and column
		for (int i = 0; i < Grid.GRID_SIZE; ++i) {
			assertEquals(FULL_CANDIDATE_LIST, 
					grid.getCell(3, i).getCandidates());
			assertEquals(FULL_CANDIDATE_LIST, 
					grid.getCell(i, 5).getCandidates());
		}
		
		// Assert candidate list was reset to full for each cell in sub-grid
		ArrayList<Cell> regions = grid.getRegion(
				grid.getCell(3, 5).getRegion());
		for (Cell cell : regions) {
			assertEquals(FULL_CANDIDATE_LIST, cell.getCandidates());
		}
	}
	
	@Test
	/**
	 * Does solve single.
	 */
	public void doesSolveSingle() {
		Grid grid = new Grid();
		
		for (int i = 0; i < Grid.GRID_SIZE - 1; ++i) {
			grid.setCellValue(i, 0, i + 1, false);
			grid.setCellValue(0, i, i + 1, false);
		}
		
		assertEquals(0, grid.getCellValue(0, 8));
		assertEquals(0, grid.getCellValue(8, 0));
		
		System.out.println(grid);
		grid.solveSingle();
		System.out.println(grid);
		
		assertEquals(9, grid.getCellValue(0, 8));
		assertEquals(9, grid.getCellValue(8, 0));
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
