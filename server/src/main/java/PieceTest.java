import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {


	@Test
	public void testIsMovePossible() {
	Piece piece1 = new Piece(0, 2, 6);

		piece1.move(2, 7);
		//assertEquals(piece1.getRow(), 2);
		/*assertEquals(piece1.getCol(), 7);
		
		piece1.move(1, 6);
		piece1.move(2, 6);
		assertEquals(piece1.getRow(), 2);
		
		piece1.move(3, 4);
		assertNotEquals(piece1.getRow(), 3);
		
		piece1.move(3, 5);
		piece1.move(2, 5);
		assertEquals(piece1.getCol(), 5);
		*/
		//piece1.move(1,4);
		
		piece1.move(0, 5);
		assertEquals(piece1.getCol(), 5);
		assertEquals(piece1.getRow(), 0);
	}
	
	@Test
	public void test(){
		Board board = new Board();
		board.createPieces();
		assertNotNull(board.getPiece(0, 6));
		assertEquals(board.getPieces().size(), 3);
		board.getPiece(0, 6).move(2, 5);
		assertNull(board.getPiece(0, 6)); 	
		assertNotNull(board.getPiece(2, 5));
		
	//	assertTrue(board.isOccupied(2, 5));

	}

}
