package Game;

public class Piece {

	private Player owner;
	private int row, col;
	
	Board board;// = Board.getInstance();

	
	/*public boolean isFree(int row, int col){
		return !board.isOccupied(row, col);
	}*/

	
	public Piece(Player player2, int row, int col) {
		this.owner = player2;
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
	
	public Player getOwner(){
		return owner;
	}
	
	public Piece getInstance(int row, int col){
		if(this.row==row & this.col==col) return this;
		return null;
	}
	
	
	public void move(Player player, int row, int col){
	
		if(board.getBoard()[row][col])
		{
			
			if(this.owner==player) //(this.owner).equals(owner) 
			{
				if(isMovePossible(row, col) && !board.isOccupied(row, col)) //board.isOccupied mozna dolaczyc do ismovepossible
				{ 
					setRow(row);
					setCol(col);
				}
			}
		}
	}
	
/*	public boolean isNeighbour(int row, int col){
		if ((this.col==col+1 || this.col==col-1) && this.row==row) return true; //move horizontally (1 field left or 1 field right)
		if (this.row==row+1 || this.row==row-1) {
			if(col==this.col+(this.row)%2 || col==this.col+(this.row)%2-1) return true; //move diagonally up or down and left or right (depends on the row's parity)
		}
		return false;
	}
*/
	public boolean isMovePossible(int row, int col){
	/*	if(col==this.col-1 && row==this.row)
			{
			if(!field.isOccupied(row, this.col-1)) return true; //jesli sasiad z lewej
			else if (col==this.col-2 && row==this.row) return true;
			} //jesli sasiad tamtego wczesniejszego sasiada z tej samej str i tamten sasiad zajety
		*/
		
		if(!board.isOccupied(row, this.col-1))
		{
			if(col==this.col-1 && row==this.row) return true;	//move one left
		}
		else if(col==this.col-2 && row==this.row) return true; //move two left if one left's occupied
		
		if(!board.isOccupied(row, this.col+1))
		{
			if(col==this.col+1 && row==this.row) return true;	//move one right
		}
		else if (col==this.col+2 && row==this.row) return true; // move two right if one right's occupied
		
		if(!board.isOccupied(this.row-1, this.col+(this.row)%2))
		{
			if(col==this.col+(this.row)%2 && row==this.row-1) return true; //move one right&down 
		}
		else if (col==this.col-1 && row==this.row-2) return true; //move two right&down if one right&down's occupied
		
		if(!board.isOccupied(this.row+1, this.col+(this.row)%2))
		{
			if(col==this.col+(this.row)%2 && row==this.row+1) return true; // move one right&up
		}
		else if (col==this.col-1 && row==this.row+2) return true;
		
		if(!board.isOccupied(this.row-1, this.col+(this.row)%2-1))
		{
			if(col==this.col+(this.row)%2-1 && row==this.row-1) return true; //move one left down
		}
		else if (col==this.col+1 && row==this.row-2) return true;
		
		if(!board.isOccupied(this.row+1, this.col+(this.row)%2-1))
		{
			if(col==this.col+(this.row)%2-1 && row==this.row+1) return true; //move one left up
		}
		else if (col==this.col+1 && row==this.row+2) return true;
		
	/*	if (col==this.col+1 && row==this.row && !board.isOccupied(row, col+1)) return true; // z prawej
		else if (col==this.col+2 && row==this.row && board.isOccupied(row, this.col+1)) return true; // z prawej prawego
	*/
		return false;
	}
	
	@Override
	public String toString(){
		return "Piece"+" "+"Player:"+owner+" "+"Row:"+row+" "+"Column:"+col;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
	}
}
