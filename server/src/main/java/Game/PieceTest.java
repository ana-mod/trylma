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
		pl.createBoard();
		assertEquals(pl.getBoard().getPieces().size(), 20);
		assertNotNull(pl.getBoard().getPiece(0, 6));
		assertEquals(pl.getBoard().getPiece(0, 6).getOwner(), player1);
		assertFalse(pl.getBoard().isOccupied(4, 8));
		pl.getBoard().getPiece(2, 7).move(player1, 4, 8);
		assertNull(pl.getBoard().getPiece(2, 7));
		assertNotNull(pl.getBoard().getPiece(4, 8));
		assertTrue(pl.getBoard().getPiece(4, 8).isJumpMade());
		pl.getBoard().getPiece(4, 8).move(player1, 4, 9);
		assertFalse(pl.getBoard().getPiece(4, 9).isJumpMade());
		
	}
	
	@Test
	public void testDestinationLeaving(){
			
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		BasicPlayer player3 = new BasicPlayer(3);
		Play pl = new Play(3);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		pl.addPlayer(player3);
		pl.createBoard();
		pl.getBoard().getPiece(3, 5).move(player1, 4, 5);
		pl.getBoard().getPiece(4, 5).move(player1, 5, 5);
		pl.getBoard().getPiece(5, 5).move(player1, 6, 5);
		pl.getBoard().getPiece(6, 5).move(player1, 7, 5);
		pl.getBoard().getPiece(7, 5).move(player1, 8, 5);
		pl.getBoard().getPiece(8, 5).move(player1, 9, 5);
		pl.getBoard().getPiece(9, 5).move(player1, 10, 5);
		pl.getBoard().getPiece(10, 5).move(player1, 11, 5);
		pl.getBoard().getPiece(11, 5).move(player1, 12, 5);
		pl.getBoard().getPiece(12, 5).move(player1, 13, 5);
		pl.getBoard().getPiece(13, 5).move(player1, 12, 5);
		assertNotNull(pl.getBoard().getPiece(13, 5));

		
		
	}

}