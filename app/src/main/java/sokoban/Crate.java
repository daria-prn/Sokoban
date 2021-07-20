package sokoban;

import nz.ac.arastudent.dariap.sokoban.R;

public class Crate extends Moveable{

	public Crate (int x, int y) {
		super(x, y, 'x', R.drawable.crate);
		//this.setImageResource( R.drawable.crate);
	}
}
