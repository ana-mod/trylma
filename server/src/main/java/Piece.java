
public class Piece {

	private int owner;
	private int row, col;
	
	public Piece(int owner, int row, int col) {
		this.owner = owner;
		this.row = row;
		this.col = col;
	}
	
	public void setRow(int row){
		this.row = row;	
	}

	public void setCol(int col){
		this.col = col;	
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public int getOwner(){
		return owner;
	}
	
	
	public void move(int row, int col){
		
		if(isMovePossible(row, col))
		{
			setRow(row);
			setCol(col);
		}
	}
	
	public boolean isMovePossible(int row, int col){
		if ((this.col==col+1 || this.col==col-1) && this.row==row) return true; //move horizontally (1 field left or 1 field right)
		if (this.row==row+1 || this.row==row-1) {
			if(col==this.col+(this.row)%2 || col==this.col+(this.row)%2-1) return true; //move diagonally up or down and left or right (depends on the row's parity)
		}
		return false;
	}
}
