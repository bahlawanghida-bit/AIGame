package AIProject;

public class Move {

	public int blockId;
	public int delta; // +1 or -1

	public Move(int blockId, int delta) {
		this.blockId = blockId;
		this.delta = delta;
	}

	@Override
	public String toString() {
		return "Move block " + blockId + " by " + delta;
	}

}
