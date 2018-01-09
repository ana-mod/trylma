package Game;

import java.util.ArrayList;

public class Board {
	
	private final int size=4; //dlugosc boku promienia
	private int rownum = 4*size+1;
	private int colnum = 3*size+1;
	public ArrayList<ArrayList<Point>> home = new ArrayList<ArrayList<Point>>(6);
	private boolean[][] board = new boolean[rownum][colnum];
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	public ArrayList<Player> players;
	private int numberOfPlayers; 
	public ArrayList<ArrayList<Piece>> piecesPerPlayer = new ArrayList<ArrayList<Piece>>();
	ArrayList<Piece> temp = new ArrayList<Piece>();

	public int getRownum(){
		return rownum;
	}
	
	public int getColnum(){
		return colnum;
	}
	
	public Board(ArrayList<Player> players) {
		this.players = players;
		initBoard();
		initHome();
		numberOfPlayers = players.size();
	}

		public void initBoard(){

			for (int i=size; i<rownum-size; i++)
				for (int j=0; j<colnum; j++)
				{
					getBoard()[i][j] = true;
				}

			for (int i=0; i<size; i++){
				for (int j=0; j<i+1; j++ )
					getBoard()[i][((colnum-i+1)/2)-1+j]=true;
			}

			for (int i=0; i<size; i++){
				for (int j=0; j<i+1; j++ )
					getBoard()[rownum - i-1][((colnum-i+1)/2)-1+j]=true;
			}

			for(int i=0; i<=size; i++)
			{
				for(int j=0;j<(i+1)/2-i%2;j++)
					getBoard()[size+i][j]=false;

				for(int j=colnum-1; j>=colnum-(i+1)/2; j--)
					getBoard()[size+i][j]=false;
			}

			for(int i=0; i<=size; i++)
			{
				for(int j=0;j<(i+1)/2-i%2;j++)
					getBoard()[rownum - size-i -1][j]=false;

				for(int j=colnum-1; j>=colnum-(i+1)/2; j--)
					getBoard()[rownum - size-i-1][j]=false;
			}

		}
	
	
		public void initHome(){
			for(int i=0;i<6;i++)
				home.add(new ArrayList<Point>());
			
			
			for (int i=0; i<size; i++)
				for(int j=0; j<colnum; j++)
					if(getBoard()[i][j]) 
						home.get(0).add(new Point(i,j));
			
			for (int i=rownum-size; i<rownum; i++)
				for(int j=0; j<colnum; j++)
					if(getBoard()[i][j]) 
						home.get(5).add(new Point(i,j));

			for (int i=size; i<2*size; i++)
			{
				int j=0;
				int index=0;
				while(j<2*size-i)
				{
					if(getBoard()[i][index])
					{
						home.get(1).add(new Point(i,index));
						j++;
					}
					index++;
				}
				
				j=0;
				index=colnum-1;
				while(j<2*size-i)
				{
					if(getBoard()[i][index])
					{
						home.get(2).add(new Point(i,index));
						j++;
					}
					index--;
				}
				
				
			}
			
			for (int i=rownum-size-1; i>=rownum-2*size; i--)
			{
				int j=0;
				int index=0;
				while(j<i-2*size)
				{
					if(getBoard()[i][index])
					{
						home.get(3).add(new Point(i,index));
						j++;
					}
					index++;
				}
				
				j=0;
				index=colnum-1;
				while(j<i-2*size)
				{
					if(getBoard()[i][index])
					{
						home.get(4).add(new Point(i,index));
						j++;
					}
					index--;
				}		
				
			}
			
		}
	
	public void createPieces(){
		
		switch(numberOfPlayers)
		{

			case 2:
				for(Point p : home.get(0)) createPiece(players.get(0), p.x, p.y, home.get(5));
				for(Point p : home.get(5)) createPiece(players.get(1), p.x, p.y, home.get(0));
				break;
				
			case 3:
				for(Point p : home.get(0)) createPiece(players.get(0), p.x, p.y, home.get(5));
				for(Point p : home.get(3)) createPiece(players.get(1), p.x, p.y, home.get(2));
				for(Point p : home.get(4)) createPiece(players.get(2), p.x, p.y, home.get(1));
				break;
				
			case 4:
				for(Point p : home.get(0)) createPiece(players.get(0), p.x, p.y, home.get(5));
				for(Point p : home.get(5)) createPiece(players.get(1), p.x, p.y, home.get(0));
				for(Point p : home.get(1)) createPiece(players.get(2), p.x, p.y, home.get(4));
				for(Point p : home.get(4)) createPiece(players.get(3), p.x, p.y, home.get(1));
				break;
				
			case 6:
				for (int i=0; i<6; i++)
				{
					for(Point p : home.get(i)) createPiece(players.get(i), p.x, p.y, home.get(5-i));
				}
				break;
		}
		
	}

	public void createPiece(Player player, int row, int col, ArrayList<Point> dest){
		Piece piece = new Piece(player, row, col, dest); 
		piece.setBoard(this);
		pieces.add(piece);
	}
	
	public Piece getPiece(int row, int col){
		
		for(Piece piece : pieces) {
			if (piece.getRow()==row && piece.getCol()==col) return piece.getInstance(row, col);
		}
		return null;	
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public boolean isOccupied(int row, int col){
	/*	for(Piece piece : pieces) {
			if (piece.getInstance(row, col)!=null) return true;
		}
		return false;
	*/
		for(Piece piece : pieces) {
			if (piece.getRow()==row && piece.getCol()==col) return true;
		}
		return false;
	}
	
	public boolean[][] getBoard() {
		return board;
	}

/*	public void setBoard(boolean[][] board) {
		this.board = board;
	}
	
	
*/
/*	public ArrayList<Piece> getPiecesPerPlayer(Player player) {
		
		if(piecesPerPlayer.size()==10) return piecesPerPlayer;
		
		for (Piece piece : pieces)
		{
			if(piece.getOwner()==player) piecesPerPlayer.add(piece);
		}
		return piecesPerPlayer;
		
	}
*/
	public void addPiecesPerPlayer(){
		
		for(int i=0;i<players.size();i++)
			piecesPerPlayer.add(new ArrayList<Piece>());
		
		for(int i=0; i<players.size(); i++)
		{
			for (Piece piece : pieces)
			{
				if(piece.getOwner().equals(players.get(i))) piecesPerPlayer.get(i).add(piece);
			}
			
		}
	}
	
	
	public ArrayList<Piece> getPiecesPerPlayer(Player player){
		
		return piecesPerPlayer.get(players.indexOf(player));
	}
	
	public boolean end(Player player) { //needs mastering probably

		temp = this.getPiecesPerPlayer(player);
		for (Piece piece : temp)		//or for(Piece piece : this.getPiecesPerPlayer(player) and then there's no need to create new ArrayList temp
		{
			if(!piece.isInDest()) return false;
		}
		
		return true;
	}
	
	public boolean end () {
		
		for (Player player : players)
		{
			if(!end(player)) return false;
		}
		
		return true;
	}
	
	class Point
	{
		int x, y;
		
		protected Point(int x, int y)
		{
			this.x=x;
			this.y=y;
		}
	}
	
	
	
}
