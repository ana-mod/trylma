package Game;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {
	
	@Test
	public void testMovingAndJumping(){
	
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
		assertTrue(pl.getBoard().getPiece(13, 5).isInDest());
		pl.getBoard().getPiece(9, 10).move(player3, 8, 10);
		pl.getBoard().getPiece(8, 10).move(player3, 7, 10);
		pl.getBoard().getPiece(7, 10).move(player3, 8, 10);
		assertNotNull(pl.getBoard().getPiece(8, 10));
		assertFalse(pl.getBoard().getPiece(8, 10).isInDest());
	}

}