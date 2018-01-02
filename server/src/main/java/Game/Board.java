package Game;

import java.util.ArrayList;

public class Board {
	
	public final int size=4; //dlugosc boku promienia
	public int rownum = 4*size+1;
	public int colnum = 3*size+1;
//	public int numberOfPlayers= 2;
//	public int piecesPerPlayer = 10; // 4+3+2+1
	
	public boolean[][] board = new boolean[rownum][colnum];
	public static ArrayList<Piece> pieces = new ArrayList<Piece>();

	private Board() {
		initBoard();
	}
	
    private static volatile Board instance;

    public static Board getInstance() {
        if (instance == null) {
            synchronized (Board.class) {
                if (instance == null) {
                    instance = new Board();
                }
            }
        }
        return instance;
    }
	
	public void initBoard(){
		for (int i=0; i<rownum; i++)
			for (int j=0; j<colnum; j++)
			{
				board[i][j] = true;
			}		
	}
	
	
	
	public static void createPieces(){
		
		createPiece(0, 0, 6);
		createPiece(0, 0, 7);
		createPiece(0, 1, 5);
		createPiece(0, 0, 5);
		createPiece(0, 2, 2);
		createPiece(0, 1, 1);
		createPiece(0, 1, 2);
		createPiece(0, 2, 1);
		createPiece(0, 2, 3);
		createPiece(0, 3, 1);
		createPiece(0, 3, 2);
		createPiece(0, 2, 4); // pieces created just for testing 
	}
	
	public static void createPiece(int Pl, int row, int col){
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
		for(Piece piece : pieces) {
			if (piece.getRow()==row && piece.getCol()==col) return true;
		}
		return false;
	}
	
}
