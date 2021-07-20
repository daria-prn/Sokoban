package sokoban;

public abstract class Placeable {

	public final Point location;
	protected final char symbol;
	public int image;
	
	public Placeable(int x, int y, char symbol, int image) {
		this.location = new Point( x, y);
		Character ch = symbol;
		this.symbol = ch;
		this.image = image;
	}

    public Point where() {
		return this.location;
	}
	public int x() {
		return this.location.x(); //was y
	}
	public int y() {
		return this.location.y();
	}
	
	public String toString () {
		return "" + this.symbol;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public boolean hasCrate() {
		return false;
	}
	
	public boolean isTarget() {
		return false;
	}

    public boolean isWall() {return false;}
}


