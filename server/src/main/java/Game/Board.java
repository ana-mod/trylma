package Game;

import java.util.ArrayList;

public class Board {
	
	public final int size=4; //dlugosc boku promienia
	public int rownum = 4*size+1;
	public int colnum = 3*size+1;
	public int numberOfPlayers;
	public ArrayList<ArrayList<Point>> home = new ArrayList<ArrayList<Point>>(6);
//	public int piecesPerPlayer = 10; // 4+3+2+1
	
	public boolean[][] board = new boolean[rownum][colnum];
	public ArrayList<Piece> pieces = new ArrayList<Piece>();

	public Board() {
		initBoard();
	}
	/*
    private static volatile Board instance;

    public static Board getInstance() {
        if (instance == null) {
            synchronized (Board.class) {
                if (instance == null) {
                    instance = new Board(numberOfPlayers);
                }
            }
        }
        return instance;
    }*/
	
		public void initBoard(){

			for (int i=size; i<rownum-size; i++)
				for (int j=0; j<colnum; j++)
				{
					board[i][j] = true;
				}

			for (int i=0; i<size; i++){
				for (int j=0; j<i+1; j++ )
					board[i][((colnum-i+1)/2)-1+j]=true;
			}

			for (int i=0; i<size; i++){
				for (int j=0; j<i+1; j++ )
					board[rownum - i-1][((colnum-i+1)/2)-1+j]=true;
			}

			for(int i=0; i<=size; i++)
			{
				for(int j=0;j<(i+1)/2-i%2;j++)
					board[size+i][j]=false;

				for(int j=colnum-1; j>=colnum-(i+1)/2; j--)
					board[size+i][j]=false;
			}

			for(int i=0; i<=size; i++)
			{
				for(int j=0;j<(i+1)/2-i%2;j++)
					board[rownum - size-i -1][j]=false;

				for(int j=colnum-1; j>=colnum-(i+1)/2; j--)
					board[rownum - size-i-1][j]=false;
			}

		}
	
	
		public void initHome(){
			for(int i=0;i<6;i++)
				home.add(new ArrayList<Point>());
			
			
			for (int i=0; i<size; i++)
				for(int j=0; j<colnum; j++)
					if(board[i][j]) 
						home.get(0).add(new Point(i,j));
			
			for (int i=rownum-size; i<rownum; i++)
				for(int j=0; j<colnum; j++)
					if(board[i][j]) 
						home.get(5).add(new Point(i,j));

			for (int i=size; i<2*size; i++)
			{
				int j=0;
				int index=0;
				while(j<2*size-i)
				{
					if(board[i][index])
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
					if(board[i][index])
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
					if(board[i][index])
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
					if(board[i][index])
					{
						home.get(4).add(new Point(i,index));
						j++;
					}
					index--;
				}
				
				
			}
			
		}
	
	
	public void createPieces(){
		
		createPiece(null, 0, 6);
		createPiece(null, 0, 7);
	/*	createPiece(0, 1, 5);
		createPiece(0, 0, 5);
		createPiece(0, 2, 2);
		createPiece(0, 1, 1);
		createPiece(0, 1, 2);
		createPiece(0, 2, 1);
		createPiece(0, 2, 3);
		createPiece(0, 3, 1);
		createPiece(0, 3, 2);
		createPiece(0, 2, 4); // pieces created just for testing 
	*/}

	public void createPiece(Player Pl, int row, int col){
		Piece piece = new Piece(Pl, row, col); 
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
