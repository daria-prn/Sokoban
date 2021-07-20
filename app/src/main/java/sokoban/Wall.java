package sokoban;

import nz.ac.arastudent.dariap.sokoban.R;

public class Wall extends Immoveable {
	
	public Wall(int x, int y) {
		super(x, y, '#', R.drawable.wall);
		//this.setImageResource( R.drawable.wall);
	}
}

