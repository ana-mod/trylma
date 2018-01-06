package Game;

import java.util.ArrayList;

public class Board {
	
	public final int size=4; //dlugosc boku promienia
	public int rownum = 4*size+1;
	public int colnum = 3*size+1;
//	public int numberOfPlayers= 2;
//	public int piecesPerPlayer = 10; // 4+3+2+1
	
	public boolean[][] board = new boolean[rownum][colnum];
	public ArrayList<Piece> pieces = new ArrayList<Piece>();	
	
	public Board() {
		initBoard();
	}
	
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
				board[rownum-i-1][((colnum-i+1)/2)-1+j]=true;
		}
		for (int i=0; i<)
		
	}
	
	
	public void createPieces(){
		
		createPiece(0, 0, 6);
		createPiece(0, 0, 7);
		createPiece(0, 1, 5);
	}
	
	public void createPiece(int Pl, int row, int col){
		Piece piece = new Piece(Pl, row, col); 
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
		return true;
	}
	
}
