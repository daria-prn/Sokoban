package sokoban;

public abstract class Moveable extends Placeable {

	public Moveable(int x, int y, char symbol, int image) {
		super(x,  y, symbol, image);
	}
	public void set( Point newLocation) {
		this.location.set(newLocation);
	}
}
