package Game;

import java.util.ArrayList;

public class Play {

	private int numberOfPlayers;
	public ArrayList<Player> players = new ArrayList<Player>(); 
	private Board board;

	private boolean isStarted = false;
	private String title;

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
	}
	
	public Board getBoard(){
		return board;
	}
}
