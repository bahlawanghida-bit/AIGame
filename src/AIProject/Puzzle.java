package AIProject;

import java.util.*;

public class Puzzle {

    private State initialState;
    private static final int SIZE = 6;
    private Random random = new Random();

    public Puzzle() {
        initialState = generateRandomPuzzle();
    }

    public State getInitialState() {
        return initialState;
    }

    private State generateRandomPuzzle() {
        List<Block> blocks = new ArrayList<>();

     
        int redCol = random.nextInt(SIZE - 2);  
        blocks.add(new Block(
                0,        
                2,        
                redCol,   
                2,       
                Block.Orientation.HORIZONTAL
        ));

        int numberOfBlocks = 4 + random.nextInt(5);

        int nextId = 1;

        while (nextId <= numberOfBlocks) {

            Block.Orientation orient = random.nextBoolean()
                    ? Block.Orientation.HORIZONTAL
                    : Block.Orientation.VERTICAL;

           
            int length = random.nextDouble() < 0.75 ? 2 : 3;

            int row, col;

            if (orient == Block.Orientation.HORIZONTAL) {
                row = random.nextInt(SIZE);
                col = random.nextInt(SIZE - length + 1);
            } else {
                row = random.nextInt(SIZE - length + 1);
                col = random.nextInt(SIZE);
            }

            Block newBlock = new Block(nextId, row, col, length, orient);

            
            if (!overlaps(newBlock, blocks)) {
                blocks.add(newBlock);
                nextId++;
            }
        }

        return new State(blocks);
    }

   
    private boolean overlaps(Block b, List<Block> blocks) {
        for (Block other : blocks) {
            if (isOverlapping(b, other)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOverlapping(Block a, Block b) {
        Set<String> cellsA = getOccupiedCells(a);
        Set<String> cellsB = getOccupiedCells(b);

        for (String c : cellsA) {
            if (cellsB.contains(c)) return true;
        }
        return false;
    }

    private Set<String> getOccupiedCells(Block b) {
        Set<String> cells = new HashSet<>();

        for (int i = 0; i < b.getLength(); i++) {
            int r = b.getOrientation() == Block.Orientation.HORIZONTAL
                    ? b.getRow()
                    : b.getRow() + i;

            int c = b.getOrientation() == Block.Orientation.HORIZONTAL
                    ? b.getCol() + i
                    : b.getCol();

            cells.add(r + "," + c);
        }
        return cells;
    }
}
