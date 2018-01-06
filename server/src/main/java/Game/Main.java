package Game;

import Game.Board.Point;

public class Main {

	private static BasicPlayer player = new BasicPlayer(1);
	private static BasicPlayer player2 = new BasicPlayer(2);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Play pl = new Play(2);
		pl.addPlayer(player);
		pl.addPlayer(player2);
		//System.out.println(pl.players);
		Board b = new Board(pl.players.size());
		b.setPlay(pl);
		b.createPieces();
		for(int i=0;i<6;i++)
		{
			for(Point p : b.home.get(i))
				System.out.print(p.x + " " + p.y + " ");
			System.out.println();
		}
	System.out.println(b.getPieces());	
	System.out.println(b.getPiece(0, 6));
	System.out.println(b.getPiece(2, 7));
	b.getPiece(2, 7).move(player, 4, 8);
	System.out.println(b.getPiece(2, 7));
	System.out.println(b.getPiece(4, 8));
	System.out.println(b.getBoard()[0][6]);
	}

}
