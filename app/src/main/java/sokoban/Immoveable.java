package sokoban;

public class Immoveable extends Placeable {
	
	public Immoveable(int x, int y, char symbol, int image) {
		super(x, y, symbol, image);
	}

	 public boolean isWall(){
		if (symbol == '#') {
		return true;}
		 else return false;
}
}
