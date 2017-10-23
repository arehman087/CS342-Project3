import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Defines the class representing the Sudoku grid.
 */
public class Grid {
	public static final int GRID_SIZE = 9;
	
	private Cell[][] cells;
	
	/**
	 * Instantiates a GRID_SIZE x GRID_SIZE grid where each cell is set to a
	 * read/write empty cell (contents of zero).
	 */
	public Grid() {
		this.cells = new Cell[GRID_SIZE][GRID_SIZE];
		
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				this.cells[r][c] = new Cell(r, c);
			}
		}
	}
	
	/**
	 * Instantiates a new GRID_SIZE x GRID_SIZE grid where each cell is set to
	 * a read/write empty cell (contents of zero). Then, for each entry in the
	 * specified file, the cell is changed to a read-only cell with the 
	 * contents specified in the file.
	 * @param f The file to be used for the grid.
	 * @throws IOException If the file cannot be opened or read from.
	 */
	public Grid(File f) throws IOException {
		// Initialize all cells to empty R/W
		this();
		
		// Read the file into Buffered Reader
		FileReader fR = new FileReader(f);
		BufferedReader bR = new BufferedReader(fR);
	
		// Parse each line of the file
		String line;
		while ((line = bR.readLine()) != null) {
			String[] lineContents = line.split(" ");
			
			// Get the row, column and contents of each line and create a new
			// read-only cell with that data.
			int row = Integer.parseInt(lineContents[0]) - 1;
			int col = Integer.parseInt(lineContents[1]) - 1;
			int con = Integer.parseInt(lineContents[2]);
			this.cells[row][col] = new Cell(row, col, con, true);
		}
		
		bR.close();
	}
	
	/**
	 * Gets the value of the cell at the specified row and column.
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @return The cell value.
	 */
	public int getCellValue(int r, int c) {
		return this.getCell(r, c).getContents();
	}
	
	/**
	 * Sets the value of the cell at the specified row and column if the cell
	 * is not read-only.
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @param contents The new contents of the cell.
	 * @return True if the value was set (cell was not read only), false 
	 *         otherwise.
	 */
	public boolean setCellValue(int r, int c, int contents) {
		// TODO: Update Candidates List...
		
		return this.getCell(r, c).setContents(contents);
	}
	
	/**
	 * Returns the reference to the cell at the specified row and column.
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @return The cell reference.
	 */
	protected Cell getCell(int r, int c) {
		assert r >= 0 && r < GRID_SIZE;
		assert c >= 0 && c < GRID_SIZE;
		
		return this.cells[r][c];
	}
}
