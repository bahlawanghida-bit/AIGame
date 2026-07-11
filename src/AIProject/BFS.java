package AIProject;

import java.util.*;


public class BFS {

    public Node solve(State initialState) {

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        Node root = new Node(initialState, null, null, 0, 0, 0);
        queue.add(root);
        visited.add(initialState.encode());

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.state.isGoal()) {
                return current;
            }

            List<State> successors = current.state.getSuccessors();

            for (State nextState : successors) {
                String code = nextState.encode();
                if (visited.contains(code))
                    continue;

                visited.add(code);
                Move move = findMove(current.state, nextState);

                Node child = new Node(
                        nextState,
                        current,
                        move,
                        current.depth + 1,
                        0,
                        0
                );

                queue.add(child);
            }
        }

        return null;
    }

    private Move findMove(State oldState, State newState) {
        List<Block> oldBlocks = oldState.getBlocks();
        List<Block> newBlocks = newState.getBlocks();

        for (int i = 0; i < oldBlocks.size(); i++) {
            Block a = oldBlocks.get(i);
            Block b = newBlocks.get(i);

            if (a.getRow() != b.getRow() || a.getCol() != b.getCol()) {
                int delta;
                if (a.getOrientation() == Block.Orientation.HORIZONTAL)
                    delta = b.getCol() - a.getCol();
                else
                    delta = b.getRow() - a.getRow();

                delta = (delta > 0) ? 1 : -1;

                return new Move(a.getId(), delta);
            }
        }
        return null;
    }

    public void printSolution(Node goalNode) {
        if (goalNode == null) {
            System.out.println("No solution found.");
            return;
        }

        
        Stack<Node> path = new Stack<>();
        Node current = goalNode;
        while (current != null) {
            path.push(current);
            current = current.parent;
        }

        System.out.println("Solution path (total moves: " + (path.size() - 1) + "):");

        int step = 0;
        while (!path.isEmpty()) {
            Node node = path.pop();
            System.out.println("Step " + step + ":");
            System.out.println(node.state);  // make sure State has a toString() method
            if (node.move != null) {
                System.out.println("Move: " + node.move);
            }
            System.out.println("--------------------");
            step++;
        }
    }
}
