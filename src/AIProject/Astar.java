package AIProject;
import java.util.*;

public class Astar {

    private Set<String> visited;
    int moves=0;

    public Astar() {
        visited = new HashSet<>();
    }

   
    public void searchAndPrint(State initialState) {
        Node goalNode = search(initialState);

        if (goalNode != null) {
            printPath(initialState, goalNode.getPath());
        } else {
            System.out.println("No solution found.");
        }
    }

    
    private Node search(State initialState) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost + n.heuristic));

        
        Node root = new Node(initialState, null, null, 0, 0, heuristic(initialState));
        open.add(root);
        visited.add(initialState.encode());

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.state.isGoal())
                return current;

            for (State succ : current.state.getSuccessors()) {
                if (!visited.contains(succ.encode())) {
                    visited.add(succ.encode());
                    Move move = findMove(current.state, succ);
                    Node child = new Node(
                            succ,
                            current,
                            move,
                            current.depth + 1,             
                            current.cost + 1,              
                            heuristic(succ)                
                    );
                    open.add(child);
                }
            }
        }

        return null; 
    }

    // ------------------ Heuristic: number of blocking pieces in front of red block ------------------
    private int heuristic(State state) {
        Block red = state.getBlocks().get(0); // red block assumed ID 0
        int row = red.getRow();
        int colEnd = red.getCol() + red.getLength();
        int blockers = 0;

        for (int c = colEnd; c < State.SIZE; c++) {
            for (Block b : state.getBlocks()) {
                if (b.getId() != 0 && b.occupiesCell(row, c)) {
                    blockers++;
                    break;
                }
            }
        }
        return blockers; // fewer blockers = closer to goal
    }

    // ------------------ Determine which block moved ------------------
    private Move findMove(State from, State to) {
        List<Block> fromBlocks = from.getBlocks();
        List<Block> toBlocks = to.getBlocks();

        for (int i = 0; i < fromBlocks.size(); i++) {
            Block b1 = fromBlocks.get(i);
            Block b2 = toBlocks.get(i);

            if (b1.getRow() != b2.getRow() || b1.getCol() != b2.getCol()) {
                int delta = (b1.getOrientation() == Block.Orientation.HORIZONTAL)
                        ? b2.getCol() - b1.getCol()
                        : b2.getRow() - b1.getRow();

                return new Move(b1.getId(), delta);
            }
        }
        return null;
    }

    // ------------------ Print solution board after each move ------------------
    private void printPath(State initialState, List<Move> path) {
        State current = initialState;
        System.out.println("Initial state:");
        current.printBoard();

        // Apply moves in path
        for (Move m : path) {
            System.out.println("Move: " + m);
            System.out.println("Moves: "+ ++moves);
            List<Block> newBlocks = current.getBlocks().stream()
                    .map(b -> (b.getId() == m.blockId) ? b.move(m.delta) : b)
                    .toList();

            current = new State(newBlocks);
            current.printBoard();
        }

        // Handle final red block escape if path is free
        Block red = current.getBlocks().get(0);
        int escapeDistance = State.SIZE - (red.getCol() + red.getLength());
        if (escapeDistance > 0) {
            Move finalMove = new Move(red.getId(), escapeDistance);

            // 1️⃣ Print final move first
            System.out.println("Move: " + finalMove);

            // 2️⃣ Apply final move
            List<Block> newBlocks = current.getBlocks().stream()
                    .map(b -> (b.getId() == finalMove.blockId) ? b.move(finalMove.delta) : b)
                    .toList();
            current = new State(newBlocks);

            // 3️⃣ Print updated board
            current.printBoard();

            // 4️⃣ Red block escapes message
            System.out.println("Red block escapes the remaining " + escapeDistance + " cells!");

            // 5️⃣ Total moves including final escape
            System.out.println("Goal reached after " + (path.size() + 1) + " moves!");
        } else {
            // No extra move needed
            System.out.println("Goal reached after " + path.size() + " moves!");
        }
    }
}

