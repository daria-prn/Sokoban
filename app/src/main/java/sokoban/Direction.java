package sokoban;

public enum Direction {
	UP(-1, 0),
	RIGHT(0,1),
	DOWN(1,0),
	LEFT(0,-1);
	
Direction(int x, int y) {
    this.xAdjust = x;
    this.yAdjust = y;
}

public final int xAdjust;
public final int yAdjust;

public Point adjust(Point where){
	return new Point( this.xAdjust + where.x(), this.yAdjust + where.y());
}

}