import java.util.HashSet;

/**
 * Defines a single cell of the Sudoku Grid.
 */
public class Cell {
	private boolean m_readonly;              // Is Cell changeable? 
	private int m_contents;                  // Number in the Cell
	
	private int m_row;                       // Row in Grid
	private int m_col;                       // Row in Column
	
	private HashSet<Integer> m_candidates; // List of candidates
	
	/**
	 * Instantiates a new empty Cell instance. The contents of the set are
	 * automatically set to zero and the cell is not marked as read/write.
	 * @param row The row of the cell.
	 * @param col The column of the cell.
	 */
	public Cell(int row, int col) {
		this(row, col, 0, false);
	}
	
	/**
	 * Instantiates a new Cell instance with the specified contents. The
	 * candidates list is automatically filled in with all possible
	 * candidates for the cell.
	 * @param row The row of the cell.
	 * @param col The column of the cell.
	 * @param contents The contents of the cell. 
	 * @param ro Are the cell contents changeable after the cell is 
	 *           instantiated?
	 */
	public Cell(int row, int col, int contents, boolean ro) {
		this.m_readonly = ro;
		this.m_contents = contents;
		this.m_row = row;
		this.m_col = col;
		
		this.m_candidates = new HashSet<Integer>();
		for (int i = 1; i <= Grid.GRID_SIZE; i++) {
			m_candidates.add(i);
		}
	}
	
	/**
	 * @return A list of all of the candidates for this cell.
	 */
	public HashSet<Integer> getCandidates() {
		return this.m_candidates;
	}
	
	/**
	 * @return The numeric value in this cell.
	 */
	public int getContents() {
		return this.m_contents;
	}
	
	/**
	 * @return The column of the cell in the grid.
	 */
	public int getColumn() {
		return this.m_col;
	}
	
	/**
	 * @return The row of the cell in the grid.
	 */
	public int getRow() {
		return this.m_row;
	}
	
	/**
	 * @return A boolean representing whether the Cell is read only or not.
	 */
	public boolean getReadOnly() {
		return this.m_readonly;
	}
	
	/**
	 * Sets the contents of the cell, if the cell is not read only.
	 * @param contents The new contents of the cell.
	 * @return True if the cell was changed, false otherwise.
	 */
	public boolean setContents(int contents) {
		if (this.m_readonly) return false;
		
		this.m_contents = contents;
		return true;
	}
	
	/**
	 * Sets the read only flag of the cell.
	 * @param ro Should the cell be read only?
	 */
	public void setReadOnly(boolean ro) {
		this.m_readonly = ro;
	}
}

