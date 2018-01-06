package Game;

public class BasicPlayer implements Player {


	private int side; //1stPlayer=1, 2ndPlayer=2 ...

	public BasicPlayer(int side) {
		setSide(side);
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}
	

	public boolean isOwnPiece(Piece piece) {
		if (piece.getOwner() == this) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString(){
		return side+"";
	}

}
