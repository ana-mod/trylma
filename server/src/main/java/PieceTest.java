import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {

	Piece piece1 = new Piece(0, 1, 6);

	@Test
	public void testIsMovePossible() {

		piece1.move(2, 7);
		//assertEquals(piece1.getRow(), 2);
		assertEquals(piece1.getCol(), 7);
		
		piece1.move(1, 6);
		piece1.move(2, 6);
		assertEquals(piece1.getRow(), 2);
		
		piece1.move(3, 4);
		assertNotEquals(piece1.getRow(), 3);
		
	}

}
