package AIProject;

public class Main {

	public static void main(String[] args) {
	
		
		
		Puzzle puzzle = new Puzzle(); 
        DFS dfs = new DFS();
        dfs.searchAndPrint(puzzle.getInitialState());
        Astar astar = new Astar();
        astar.searchAndPrint(puzzle.getInitialState());


        
        Puzzle p = new Puzzle();
        State initial = p.getInitialState();

        
        System.out.println("Running BFS...");
        BFS bfsSolver = new BFS();
        Node resultBFS = bfsSolver.solve(initial);

        if (resultBFS != null) {
            System.out.println("BFS Solution found in " + resultBFS.getPath().size() + " moves.");
            System.out.println("Printing BFS solution path:");
            bfsSolver.printSolution(resultBFS);
        } else {
            System.out.println("BFS did not find a solution.");
        }

        System.out.println("\n===============================\n");

        
        System.out.println("Running IDS...");
        IDS idsSolver = new IDS();
        Node resultIDS = idsSolver.solve(initial);

        if (resultIDS != null) {
            System.out.println("IDS Solution found in " + resultIDS.getPath().size() + " moves.");
            System.out.println("Printing IDS solution path:");
            idsSolver.printSolution(resultIDS);
        } else {
            System.out.println("IDS did not find a solution.");
        }
    }
}

	


