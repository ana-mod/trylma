package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Game.Play;

public class BoardTest {

	@Test
	public void testFor2Players() {
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		Play pl = new Play(2);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		pl.createBoard();
		
		assertFalse(pl.getBoard().getBoard()[0][5]);
		assertTrue(pl.getBoard().getBoard()[0][6]);
		assertEquals(pl.getBoard().getPieces().size(), 20);
		assertTrue(pl.getBoard().isOccupied(0, 6));
		assertNotNull(pl.getBoard().getPiece(0, 6));
		assertFalse(pl.getBoard().isOccupied(0, 5));
		assertNull(pl.getBoard().getPiece(0, 5));
		assertFalse(pl.getBoard().isOccupied(6, 6));
		assertNotNull(pl.getBoard().getPiece(14, 6));
		assertFalse(pl.getBoard().end(player1));
		assertFalse(pl.getBoard().end());
	}

	@Test
	public void testFor3Players() {
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		BasicPlayer player3 = new BasicPlayer(3);
		Play pl = new Play(3);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		pl.addPlayer(player3);
		pl.createBoard();
		
		assertFalse(pl.getBoard().getBoard()[0][5]);
		assertTrue(pl.getBoard().getBoard()[0][6]);
		assertEquals(pl.getBoard().getPieces().size(), 30);
		assertTrue(pl.getBoard().isOccupied(0, 6));
		assertNotNull(pl.getBoard().getPiece(0, 6));
		assertFalse(pl.getBoard().isOccupied(0, 5));
		assertNull(pl.getBoard().getPiece(0, 5));
		assertFalse(pl.getBoard().isOccupied(6, 6));
		assertNotNull(pl.getBoard().getPiece(11, 1));
		assertFalse(pl.getBoard().end(player1));
		assertFalse(pl.getBoard().end());
	}
	
	@Test
	public void testFor4Players() {
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		BasicPlayer player3 = new BasicPlayer(3);
		BasicPlayer player4 = new BasicPlayer(4);
		Play pl = new Play(4);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		pl.addPlayer(player3);
		pl.addPlayer(player4);
		pl.createBoard();
		
		assertFalse(pl.getBoard().getBoard()[0][5]);
		assertTrue(pl.getBoard().getBoard()[0][6]);
		assertEquals(pl.getBoard().getPieces().size(), 40);
		assertTrue(pl.getBoard().isOccupied(0, 6));
		assertNotNull(pl.getBoard().getPiece(0, 6));
		assertFalse(pl.getBoard().isOccupied(0, 5));
		assertNull(pl.getBoard().getPiece(0, 5));
		assertFalse(pl.getBoard().isOccupied(6, 6));
		assertNull(pl.getBoard().getPiece(11, 1));
		assertNotNull(pl.getBoard().getPiece(11, 10));
		assertFalse(pl.getBoard().end(player1));
		assertFalse(pl.getBoard().end());
	}
		
	@Test
	public void testFor5Players() {
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		BasicPlayer player3 = new BasicPlayer(3);
		BasicPlayer player4 = new BasicPlayer(4);
		BasicPlayer player5 = new BasicPlayer(5);
		
		Play pl = new Play(5);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		pl.addPlayer(player3);
		pl.addPlayer(player4);
		pl.addPlayer(player5);
		pl.createBoard();
		
		assertTrue(pl.getBoard().getPieces().isEmpty());
		
	}
	
	@Test
	public void testFor6Players() {
		BasicPlayer player1 = new BasicPlayer(1);
		BasicPlayer player2 = new BasicPlayer(2);
		BasicPlayer player3 = new BasicPlayer(3);
		BasicPlayer player4 = new BasicPlayer(4);
		BasicPlayer player5 = new BasicPlayer(5);
		BasicPlayer player6 = new BasicPlayer(6);
		
		Play pl = new Play(6);
		pl.addPlayer(player1);
		pl.addPlayer(player2);
		pl.addPlayer(player3);
		pl.addPlayer(player4);
		pl.addPlayer(player5);
		pl.addPlayer(player6);
		pl.createBoard();
		
		assertEquals(pl.getBoard().getPieces().size(), 60);
		
	}
	
}

