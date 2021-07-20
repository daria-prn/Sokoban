package sokoban;

import nz.ac.arastudent.dariap.sokoban.R;

public abstract class Enterable extends Immoveable {
	
	protected Moveable myWorker;
	protected Moveable myCrate; 
	protected boolean isEmpty = true;
	protected final int myImage;
	
	public Enterable(int x, int y, char symbol, int image) {
		super(x,y, symbol, image);
		myImage = image;
	}

	public Crate getCrate() {
		return (Crate) this.myCrate;
	}
	
	public void removeWorker() {
		this.myWorker = null;
		this.isEmpty = true;
		this.image = myImage;
	}
	
	public void addWorker(Moveable theWorker) {
		this.myWorker = theWorker;
		theWorker.set(this.location);
		this.isEmpty = false;
		this.image = this.getMovableImage(theWorker);
	}
	
	public void removeCrate() {
		this.myCrate = null;
		this.isEmpty = true;
		this.image = myImage;
	}
	
	public void addCrate(Crate aCrate) {
		this.myCrate = aCrate;
		this.isEmpty = false;
		if (this.isTarget()) {
		    this.image = R.drawable.crate2;
        } else {
		    this.image = this.getMovableImage(aCrate);
		}
	}

	protected int getMovableImage(Moveable theMovable ) {
		return theMovable.image;
	}

	//@Override 
	public boolean isEmpty() {
		return this.isEmpty;  // replace with test on content exists
	}

	public boolean hasCrate() {
		return this.myCrate != null;
	}
	
	
	 public boolean isTarget() { 
		 if (symbol == '+') {
			 return true;} 
		 else return false;
		 }
	 
	
	// override to modify a 'stacked' moveable's string
	protected String getMovableString(Moveable theMovable ) {
		return theMovable.toString();
	} 
	
	public String toString() {
		String symbol = "" + this.symbol;
		if (this.myWorker != null) {
			symbol = this.getMovableString(myWorker);
		}
		if (this.myCrate != null) {
			symbol = this.getMovableString(myCrate);
		}
		return symbol;
	}
}