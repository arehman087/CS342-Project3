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
	 * Returns the reference to the cell at (r, c).
	 * @param The cell row.
	 * @param The cell column.
	 * @return The cell at the position (r, c).
	 */
	public Cell getCell(int r, int c) {
		assert r >= 0 && r < GRID_SIZE;
		assert c >= 0 && c < GRID_SIZE;

		return this.cells[r][c];
	}
}
