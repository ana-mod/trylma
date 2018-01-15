package Game;

import GameInfo.Move;

import java.util.ArrayList;
import java.util.Collections;

public class Play {

	private int numberOfPlayers;
	public ArrayList<Player> players = new ArrayList<Player>(); 
	private Board board;

	private boolean isStarted = false;
	private String title;
	private Piece previousMovedPiece = null;
	private int movesMade =0;

	public Player getActualPlayer ()
	{
		return actualPlayer;
	}

	private Player actualPlayer;

	public boolean isStarted ()
	{
		return isStarted;
	}

	public String getTitle ()
	{
		return title;
	}

	public void setTitle (String title)
	{
		this.title = title;
	}

	public void addPlayer(Player player) {
		if(players.size() < numberOfPlayers)
			players.add(player);

		if(players.size() == numberOfPlayers)
			start();
	}
	
	public void start(){
		isStarted=true;
		Collections.shuffle(players);
		actualPlayer = players.get(0);
		createBoard();
	}
	
	public Play(int number){
		this.numberOfPlayers = number;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void createBoard(){
		board = new Board(players);
		board.createPieces();
		board.addPiecesPerPlayer();
		
	}
	
	public Board getBoard(){
		return board;
	}

	public boolean move(Player player, Move move)
	{
		boolean isMoveMade = false;

		if(player!=actualPlayer)
			return false;

		Piece piece = board.getPiece(move.x1, move.y1);

		if(piece==null)
			return false;

		if(piece.getOwner()!=player)
			return false;

		if(previousMovedPiece==null)
		{
			isMoveMade = piece.move(player, move.x2, move.y2);
			if(isMoveMade)
			{
				previousMovedPiece = piece;
				movesMade++;
				return true;
			}
			else return false;
		}
		else if(previousMovedPiece.equals(piece))
		{
			if(piece.isJumpMade())
				return isMoveMade = piece.move(player, move.x2, move.y2);
			else
				return false;
		}

		else return false;
	}

	public void endOfMove()
	{
		actualPlayer = players.get((players.indexOf(actualPlayer)+1)%players.size());
		if(previousMovedPiece!=null)
			previousMovedPiece.resetJump();
		previousMovedPiece = null;
	}

	public void removePlayer(Player player)
	{
		if(actualPlayer.equals(player))
		{
			int index = players.indexOf(actualPlayer);
			players.remove(player);
			if (!players.isEmpty())
			{
				actualPlayer = players.get((index + 1) % players.size());
				if (previousMovedPiece != null)
					previousMovedPiece.resetJump();
				previousMovedPiece = null;
			}
		}
		else players.remove(player);
	}
}
