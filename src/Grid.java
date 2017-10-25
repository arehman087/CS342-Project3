import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Defines the class representing the Sudoku grid.
 */
public class Grid {
	public static final int GRID_SIZE = 9;
	
	private Cell[][] m_cells;
	
	/**
	 * Instantiates a GRID_SIZE x GRID_SIZE grid where each cell is set to a
	 * read/write empty cell (contents of zero).
	 */
	public Grid() {
		this.m_cells = new Cell[GRID_SIZE][GRID_SIZE];
		
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				this.m_cells[r][c] = new Cell(r, c);
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
			this.setCellValue(row, col, con, false);
			this.m_cells[row][col].setReadOnly(true);
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
	 * Returns a list of all of the cells in the specified region.
	 * @param reg The region number in the range [0, 8].
	 * @return The list of the cells in the region.
	 */
	public ArrayList<Cell> getRegion(int reg) {
		assert reg >= 0 && reg <= 8;
		
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				Cell cell = this.getCell(r, c);
				if (cell.getRegion() == reg) {
					cells.add(cell);
				}
			}
		}
		
		return cells;
	}
	
	/**
	 * Returns a list of all of the cells in the specified row.
	 * @param row The row number in the range [0, 8].
	 * @return The list of cells in the row.
	 */
	public ArrayList<Cell> getRow(int row) {
		assert row >= 0 && row <= 8;
		
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				Cell cell = this.getCell(r, c);
				if (cell.getRow() == row) {
					cells.add(cell);
				}
			}
		}
		
		return cells;
	}
	
	/**
	 * Returns a list of all of the cells in the specified column.
	 * @param row The column number in the range [0, 8].
	 * @return The list of cells in the column.
	 */
	public ArrayList<Cell> getColumn(int col) {
		assert col >= 0 && col <= 8;
		
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				Cell cell = this.getCell(r, c);
				if (cell.getColumn() == col) {
					cells.add(cell);
				}
			}
		}
		
		return cells;
	}
	
	/**
	 * Sets the value of the cell at the specified row and column if the cell
	 * is not read-only.
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @param contents The new contents of the cell.
	 * @param check Checks if the value of the cell can be set to the new
	 *              contents before setting the value. If the value cannot be
	 *              set due to the candidates list, false is returned and the
	 *              grid is not modified.
	 * @return True if the value was set (cell was not read only), false 
	 *         otherwise.
	 */
	public boolean setCellValue(int r, int c, int contents, boolean check) {
		Cell at = this.getCell(r, c);
		
		// Check the candidates list if necessary
		if (check && !at.getCandidates().contains(contents)) {
			return false;
		}
		
		// Try to set the changes in the cell. If failed, return false
		int oldContents = at.getContents();
		boolean changed = at.setContents(contents);
		if (!changed) return false;
		
		// Otherwise, for each cell in the same row and column, remove the
		// new value from the candidate lists; add the old value to the
		// candidate lists (only if the old value is not zero/uninit).
		for (int i = 0; i < GRID_SIZE; ++i) {
			Integer oldContentsObj = Integer.valueOf(oldContents);
			Integer newContentsObj = Integer.valueOf(contents);
			
			// Remove cell from candidate lists of all cells with the same
			// row, column and region.
			ArrayList<Cell> regionCells = this.getRegion(at.getRegion());
			this.getCell(r, i).getCandidates().remove(newContentsObj);
			this.getCell(i, c).getCandidates().remove(newContentsObj);
			for (Cell cell : regionCells) {
				cell.getCandidates().remove(newContentsObj);
			}
			
			// Add old cell to candidate lists of all cells with the same
			// row, column, region.
			if (oldContents != 0 && oldContents != contents) {
				this.getCell(r, i).getCandidates().add(oldContentsObj);
				this.getCell(i, c).getCandidates().add(oldContentsObj);
				for (Cell cell : regionCells) {
					cell.getCandidates().add(oldContentsObj);
				}
			}
		}
		return true;
	}
	
	/**
	 * Solves all cells in the grid which fit the single cell criteria.
	 */
	public void solveSingle() {
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				// Get the current cell. Skip if read-only or if the cell
				// is already initialized.
				Cell at = this.getCell(r, c);
				if (at.getReadOnly() || at.getContents() != 0) continue;
				
				HashSet<Integer> candidates = at.getCandidates();
				Integer candidate = candidates.iterator().next(); 
				
				if (candidates.size() == 1) {
					this.setCellValue(r, c, candidate, true);
				}
			}
		}
	}
	
	// CS 440, 450, 422, 480 (hummel)
	
	/**
	 * Solves all cells in the grid which fit the hidden single cell criteria.
	 */
	public void solveHiddenSingle() {
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				Cell at = this.getCell(r, c);

				if (at.getReadOnly() || at.getContents() != 0) continue;
				
				ArrayList<Cell> row = this.getRow(at.getRow());
				ArrayList<Cell> col = this.getColumn(at.getColumn());
				ArrayList<Cell> reg = this.getRegion(at.getRegion());

				int rowCandidate = hiddenSingleHelper(row);
				if (rowCandidate > 0) {
					this.setCellValue(r, c, rowCandidate, true);
					continue;
				}
				
				int colCandidate = hiddenSingleHelper(col);
				if (colCandidate > 0) {
					this.setCellValue(r, c, colCandidate, true);
					continue;
				}

				int regCandidate = hiddenSingleHelper(reg);
				if (regCandidate > 0) {
					this.setCellValue(r, c, regCandidate, true);
					continue;
				}
			}
		}
	}
	
	/**
	 * Returns a String representation of the Grid.
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		// Append the top of the grid box
		stringBuilder.append("+");
		for (int i = 0; i < GRID_SIZE; ++i) {
			stringBuilder.append("---+");
		}
		stringBuilder.append('\n');
		String lineSep = stringBuilder.toString();
		
		// Append each row of the grid. Separate each row of the grid by the
		// same line as used for the top of the grid
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				int contents = this.m_cells[r][c].getContents();
				
				stringBuilder.append("| ");
				stringBuilder.append(String.format("%d", contents));
				stringBuilder.append(" ");
			}
			stringBuilder.append("|\n");
			stringBuilder.append(lineSep);
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Writes the contents of the Grid to the specified Buffered Writer 
	 * instance. For each initialized cell: the row, column and contents of
	 * the cell are written to a single line and flushed to the writer.
	 * @param bW The writer to which the grid is to be written to.
	 * @throws IOException If the buffered writer instance cannot be written
	 *                     to.
	 */
	public void write(BufferedWriter bW) throws IOException {
		for (int r = 0; r < GRID_SIZE; ++r) {
			for (int c = 0; c < GRID_SIZE; ++c) {
				Cell at = this.getCell(r, c);
				
				// Skip the cell if it is not initialized
				if (at.getContents() == 0) continue;
				
				// Write the cell in the format "Row Column Contents\n". Add
				// one to the row and column since the file indices should be 
				// in range [1, 9], not [0, 8].
				bW.write(String.valueOf(at.getRow() + 1));
				bW.write(" ");
				bW.write(String.valueOf(at.getColumn() + 1));
				bW.write(" ");
				bW.write(String.valueOf(at.getContents()));
				bW.newLine();
			}
		}
		
		bW.flush();
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
		
		return this.m_cells[r][c];
	}
	
	/**
	 * Checks if the candidates list of each cell has exactly one occurrence of
	 * some cell. If so, the sole value is returned.
	 * @param cells The list of cells.
	 * @return The sole value in the candidates list, or zero if no such value
	 *         exists.
	 */
	private int hiddenSingleHelper(ArrayList<Cell> cells) {
		int candidates[] = new int[GRID_SIZE];
		
		for (Cell cell : cells) {
			if (cell.getReadOnly()) continue;
			
			for (int i : cell.getCandidates()) {
				candidates[i - 1]++;
			}
		}
		for (int i = 0; i < candidates.length; ++i) {
			if (candidates[i] == 1) {
				return i + 1;
			}
		}
		
		return 0;
	}
}
