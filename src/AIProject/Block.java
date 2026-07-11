package AIProject;

public class Block {
	public enum Orientation {
		HORIZONTAL, VERTICAL
	}

	private int id;
	private int row; 
	private int col; 
	private int length;
	private Orientation orientation;

	public Block(int id, int row, int col, int length, Orientation orientation) {
		this.id = id;
		this.row = row;
		this.col = col;
		this.length = length;
		this.orientation = orientation;
	}

	public int getId() {
		return id;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLength() {
		return length;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	
	public Block move(int delta) {
		if (orientation == Orientation.HORIZONTAL) {
			return new Block(id, row, col + delta, length, orientation);
		} else {
			return new Block(id, row + delta, col, length, orientation);
		}
	}
	
	public boolean occupiesCell(int row, int col) {
	    if (orientation == Orientation.HORIZONTAL) {
	        return this.row == row && col >= this.col && col < this.col + length;
	    } else {
	        return this.col == col && row >= this.row && row < this.row + length;
	    }
	}

}
