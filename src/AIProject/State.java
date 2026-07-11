package AIProject;

import java.util.*;

public class State {

	public static final int SIZE = 6;

	private final List<Block> blocks;

	public State(List<Block> blocks) {
		this.blocks = deepCopy(blocks);
	}

	public List<Block> getBlocks() {
		return deepCopy(blocks);
	}

	private List<Block> deepCopy(List<Block> blocks) {
		List<Block> copy = new ArrayList<>();
		for (Block b : blocks) {
			copy.add(new Block(b.getId(), b.getRow(), b.getCol(), b.getLength(), b.getOrientation()));
		}
		return copy;
	}

	public String encode() {
		StringBuilder sb = new StringBuilder();
		for (Block b : blocks) {
			sb.append(b.getId()).append(":").append(b.getRow()).append(",") 
					.append(b.getCol()).append(";");
		}
		return sb.toString();
	}
	
	

	
	public boolean isGoal() {
	    Block red = blocks.get(0);
	    int row = red.getRow();
	    int endCol = red.getCol() + red.getLength();

	   
	    for (int c = endCol; c < SIZE; c++) {
	        for (Block b : blocks) {
	            if (b.getId() != 0 && b.occupiesCell(row, c)) {
	                return false; 
	            }
	        }
	    }

	    return true; 
	}


	
	public List<State> getSuccessors() {
		List<State> successors = new ArrayList<>();

		for (Block block : blocks) {
			for (int delta : new int[] { -1, +1 }) {

				if (canMove(block, delta)) {
					List<Block> newBlocks = new ArrayList<>();

					for (Block b : blocks) {
						if (b.getId() == block.getId())
							newBlocks.add(b.move(delta));
						else
							newBlocks.add(b);
					}

					successors.add(new State(newBlocks));
				}
			}
		}
		return successors;
	}

	
	private boolean canMove(Block block, int delta) {

		int newRow = block.getRow();
		int newCol = block.getCol();

		if (block.getOrientation() == Block.Orientation.HORIZONTAL)
			newCol += delta;
		else
			newRow += delta;

		
		if (newRow < 0 || newCol < 0)
			return false;

		if (block.getOrientation() == Block.Orientation.HORIZONTAL) {
			if (newCol + block.getLength() > SIZE)
				return false;
		} else {
			if (newRow + block.getLength() > SIZE)
				return false;
		}

		// Collision check
		for (Block other : blocks) {
			if (other.getId() == block.getId())
				continue;

			if (overlap(block, other, delta))
				return false;
		}

		return true;
	}

	private boolean overlap(Block moving, Block other, int delta) {
		Set<String> movingCells = getCells(moving, delta);
		Set<String> otherCells = getCells(other, 0);

		for (String c : movingCells)
			if (otherCells.contains(c))
				return true;

		return false;
	}

	private Set<String> getCells(Block b, int delta) {
		Set<String> set = new HashSet<>();

		for (int i = 0; i < b.getLength(); i++) {

			int r;
			if (b.getOrientation() == Block.Orientation.HORIZONTAL)
				r = b.getRow();
			else
				r = b.getRow() + i;

			int c;
			if (b.getOrientation() == Block.Orientation.HORIZONTAL)
				c = b.getCol() + i;
			else
				c = b.getCol();

			if (b.getOrientation() == Block.Orientation.HORIZONTAL)
				c += delta;
			else
				r += delta;

			set.add(r + "," + c);
		}

		return set;
	}
	
	public void printBoard() {
	    char[][] board = new char[SIZE][SIZE];

	    
	    for (int r = 0; r < SIZE; r++) {
	        for (int c = 0; c < SIZE; c++) {
	            board[r][c] = '.';
	        }
	    }

	    
	    for (Block b : blocks) {
	        char symbol = (b.getId() == 0) ? 'R' : (char) ('A' + b.getId() - 1);
	        for (int i = 0; i < b.getLength(); i++) {
	            int r = (b.getOrientation() == Block.Orientation.HORIZONTAL) ? b.getRow() : b.getRow() + i;
	            int c = (b.getOrientation() == Block.Orientation.HORIZONTAL) ? b.getCol() + i : b.getCol();
	            board[r][c] = symbol;
	        }
	    }

	    
	    for (int r = 0; r < SIZE; r++) {
	        for (int c = 0; c < SIZE; c++) {
	            System.out.print(board[r][c] + " ");
	        }
	        System.out.println();
	    }
	    System.out.println();
	}

}

