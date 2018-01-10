package Game;

import java.util.ArrayList;

public class Play {

	private int numberOfPlayers;
	public ArrayList<Player> players = new ArrayList<Player>(); 
	private Board board;

	private boolean isStarted = false;
	private String title;

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
	
		players.add(player);
		if(players.size() == numberOfPlayers)
			start();
	}
	
	public void start(){
		if(numberOfPlayers!=players.size()) return;
		isStarted=true;
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
	}
	
	public Board getBoard(){
		return board;
	}
}
