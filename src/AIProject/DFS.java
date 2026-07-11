package AIProject;
import java.util.*;

public class DFS {

    private Set<String> visited;
    int moves=0;

    public DFS() {
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
        Stack<Node> stack = new Stack<>();
        Node root = new Node(initialState, null, null, 0, 0, 0);
        stack.push(root);
        visited.add(initialState.encode()); 

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            
            if (current.state.isGoal())
                return current;

            for (State succ : current.state.getSuccessors()) {
                if (!visited.contains(succ.encode())) {
                    visited.add(succ.encode());
                    Move move = findMove(current.state, succ);
                    Node child = new Node(succ, current, move, current.depth + 1, 0,0);
                    stack.push(child);
                }
            }
        }

        return null; 
    }

   
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

   
    private void printPath(State initialState, List<Move> path) {
        State current = initialState;
        System.out.println("Initial state:");
        current.printBoard();

        
        for (Move m : path) {
            System.out.println("Move: " + m);
            System.out.println("Moves: "+ ++moves);
            List<Block> newBlocks = current.getBlocks().stream()
                    .map(b -> (b.getId() == m.blockId) ? b.move(m.delta) : b)
                    .toList();

            current = new State(newBlocks);
            current.printBoard();
        }

        
        Block red = current.getBlocks().get(0);
        int escapeDistance = State.SIZE - (red.getCol() + red.getLength());
        if (escapeDistance > 0) {
            Move finalMove = new Move(red.getId(), escapeDistance);

            
            System.out.println("Move: " + finalMove);

            
            List<Block> newBlocks = current.getBlocks().stream()
                    .map(b -> (b.getId() == finalMove.blockId) ? b.move(finalMove.delta) : b)
                    .toList();
            current = new State(newBlocks);

           
            current.printBoard();

           
            System.out.println("Red block escapes the remaining " + escapeDistance + " cells!");

           
            System.out.println("Goal reached after " + (path.size() + 1) + " moves!\n\n");
        } else {
           
            System.out.println("Goal reached after " + path.size() + " moves!\n\n");
        }
    }

}
