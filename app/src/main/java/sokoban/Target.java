package sokoban;

import nz.ac.arastudent.dariap.sokoban.R;

public class Target extends Enterable {
	
	public Target(int x, int y) {
		super(x, y, '+', R.drawable.target);
		//this.setImageResource( R.drawable.target);
	}
	
	protected String getMovableString(Moveable theMovable ) {
		return theMovable.toString().toUpperCase();
	} 

}
