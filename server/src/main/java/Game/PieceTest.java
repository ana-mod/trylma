package Game;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {


	@Test
	public void testIsMovePossible() {
	//Piece piece1 = new Piece(null, 2, 6);

		//piece1.move(null, 3, 6);
		//assertEquals(piece1.getRow(), 3);
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
		
	//	piece1.move(2, 5);
		//assertEquals(piece1.getCol(), 5);
		//assertEquals(piece1.getRow(), 2);
	}
	
	@Test
	public void test(){
/*		Board board = new Board(2);
		assertNotNull(board.getPiece(0, 6));
		assertEquals(board.getPieces().size(), 12);
		board.getPiece(1, 1).move(null, 3, 2);
		assertNull(board.getPiece(1, 1)); 	
		assertNotNull(board.getPiece(3, 2));		
		board.getPiece(0, 6).move(null, 2, 7);
		assertNull(board.getPiece(0, 6));
		assertNotNull(board.getPiece(2, 7));
		assertTrue(board.isOccupied(2, 7));
*/		
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		Play pl = new Play(2);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		Board b = new Board(pl.players.size());
		b.setPlay(pl);
		b.createPieces();
		assertEquals(b.getPieces().size(), 20);
		assertNotNull(b.getPiece(0, 6));
		assertEquals(b.getPiece(0, 6).getOwner(), player1);
		assertFalse(b.isOccupied(4, 8));
		b.getPiece(2, 7).move(player1, 4, 8);
		assertNull(b.getPiece(2, 7));
		assertNotNull(b.getPiece(4, 8));
		
	}

}