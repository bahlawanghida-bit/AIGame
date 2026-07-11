package AIProject;

import java.util.*;

public class Node {

    public State state;      
    public Node parent;      
    public Move move;       
    public int depth;        
    public int cost;        
    public int heuristic;

    
    public Node(State state, Node parent, Move move, int depth, int cost, int h) {
        this.state = state;
        this.parent = parent;
        this.move = move;
        this.depth = depth;
        this.cost = cost;
        this.heuristic=h;
    }

   
    public List<Move> getPath() {
        List<Move> path = new ArrayList<>();
        Node current = this;
        while (current.parent != null && current.move != null) {
            path.add(current.move);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public String toString() {
        return "Node (depth=" + depth + ", cost=" + cost + ", state=" + state.encode() + ")";
    }
}
