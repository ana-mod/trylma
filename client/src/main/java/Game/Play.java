package Game;

import java.util.ArrayList;

public class Play {

	private int numberOfPlayers;
	public ArrayList<Player> players = new ArrayList<Player>(); 
	
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
	
}