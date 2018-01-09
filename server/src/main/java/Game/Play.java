package Game;

import java.util.ArrayList;

public class Play {

	private int numberOfPlayers;
	public ArrayList<Player> players = new ArrayList<Player>(); 
	private Board board;
	
	public void addPlayer(Player player) {
	
		players.add(player);
	
	}
	
	public void start(){
		if(numberOfPlayers!=players.size()) return;
		
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
}
