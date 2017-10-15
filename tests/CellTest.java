import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Defines the class for unit testing the Cell class.
 */
public class CellTest {
	@Test
	/**
	 * Tests the first constructor.
	 */
	public void doesConstruct1() {
		Cell cell1 = new Cell(4, 6);
		assertEquals(4, cell1.getRow());
		assertEquals(6, cell1.getColumn());
		assertEquals(0, cell1.getContents());
		assertFalse(cell1.getReadOnly());
		
		Cell cell2 = new Cell(8, 2);
		assertEquals(8, cell2.getRow());
		assertEquals(2, cell2.getColumn());
		assertEquals(0, cell2.getContents());
		assertFalse(cell2.getReadOnly());
	}
	
	@Test
	/**
	 * Tests the second constructor.
	 */
	public void doesConstruct2() {
		Cell cell1 = new Cell(3, 2, 7, false);
		assertEquals(3, cell1.getRow());
		assertEquals(2, cell1.getColumn());
		assertEquals(7, cell1.getContents());
		assertFalse(cell1.getReadOnly());
		
		Cell cell2 = new Cell(1, 9, 5, true);
		assertEquals(1, cell2.getRow());
		assertEquals(9, cell2.getColumn());
		assertEquals(5, cell2.getContents());
		assertTrue(cell2.getReadOnly());
	}
	
	@Test
	/**
	 * Tests the setContents method works for read only cells.
	 */
	public void doesSetContentsForRO() {
		Cell cell1 = new Cell(3, 2, 1, true);
		
		int oldContents = cell1.setContents(9);
		assertEquals(1, oldContents);
		assertEquals(1, cell1.getContents());
	}
	
	@Test
	/**
	 * Tests the setContents method works for read/write cells.
	 */
	public void doesSetContentsForRW() {
		Cell cell1 = new Cell(3, 2, 1, false);
		
		int oldContents = cell1.setContents(9);
		assertEquals(1, oldContents);
		assertEquals(9, cell1.getContents());
	}
}
