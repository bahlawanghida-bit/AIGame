# Rush Hour Puzzle Solver — AI Search Algorithms

A Java implementation of the classic **Rush Hour** sliding-block puzzle, solved using four fundamental AI search strategies: **Breadth-First Search (BFS)**, **Depth-First Search (DFS)**, **Iterative Deepening Search (IDS)**, and **A\*** with a custom heuristic. The project randomly generates solvable puzzle boards and benchmarks how each algorithm navigates the state space to free the red block from the grid.

## Overview

The puzzle is played on a 6×6 grid containing a set of horizontal and vertical blocks of varying lengths, plus a designated **red block**. The goal is to slide the surrounding blocks out of the way so the red block can escape off the right edge of the board. Each search algorithm explores the space of possible block moves differently, offering a hands-on comparison of uninformed vs. informed search techniques.

## Features

- **Randomized puzzle generation** — produces a new, non-overlapping board configuration (4–8 blocks of length 2 or 3, horizontal or vertical) on every run
- **Four search algorithms**, each implemented from scratch:
  - **BFS** — explores level by level, guaranteeing the shortest solution path
  - **DFS** — explores deeply along a single branch before backtracking
  - **IDS** — repeated depth-limited DFS with increasing depth bounds, combining BFS's completeness with DFS's low memory footprint
  - **A\*** — best-first search guided by a heuristic that counts the number of blocking pieces between the red block and the exit
- **State representation** — each board state is encoded into a unique string signature to detect and avoid revisiting duplicate states
- **Move tracking** — reconstructs and prints the full sequence of moves from the initial board to the solved state
- **Visual board output** — prints an ASCII representation of the grid at each step, with the red block marked `R` and other blocks labeled `A`, `B`, `C`, …

## Project Structure

```
AiProject/
└── src/AIProject/
    ├── Main.java     # Entry point — runs DFS, A*, BFS, and IDS on a generated puzzle
    ├── Puzzle.java   # Generates a random, non-overlapping starting board
    ├── State.java    # Board state: goal check, successor generation, encoding, rendering
    ├── Block.java    # Represents a single block (position, length, orientation)
    ├── Move.java     # Represents a single block move (block id + direction)
    ├── Node.java     # Search tree node (state, parent, move, depth, cost, heuristic)
    ├── BFS.java       # Breadth-first search implementation
    ├── DFS.java       # Depth-first search implementation
    ├── IDS.java       # Iterative deepening search implementation
    └── Astar.java     # A* search implementation with blocking-piece heuristic
```

## Requirements

- Java Development Kit (JDK) 11 or higher (uses `module-info.java` and `Stream.toList()`)
- Eclipse IDE (project includes `.project` / `.classpath` files) — or any IDE/CLI of your choice

## Build & Run

### Using Eclipse
1. Import the project via **File → Import → Existing Projects into Workspace**
2. Select the `AiProject` folder
3. Run `Main.java`

### Using the command line
```bash
cd AiProject/src
javac AIProject/*.java -d ../bin
java -cp ../bin AIProject.Main
```

## How It Works

1. `Puzzle` generates a random starting board with a red block and several obstacle blocks.
2. Each search algorithm treats board configurations as **states** in a graph, where an **edge** represents a legal single-cell move of one block.
3. `State.getSuccessors()` generates all valid next states by attempting to move every block one cell in each direction, respecting grid boundaries and collisions.
4. The search terminates when `State.isGoal()` confirms the red block has a clear path to the right edge of the grid.
5. The solution is reconstructed by walking back up the `Node` parent chain and printed move-by-move with the board state at each step.

## Sample Output

```
Running BFS...
BFS Solution found in 6 moves.
Printing BFS solution path:
Step 0:
Node (depth=0, cost=0, state=0:2,1;1:0,4;...)
--------------------
Move block 1 by 1
...
Goal reached after 6 moves!
```

## Possible Improvements

- Add a graphical (GUI) board visualization instead of console output
- Implement additional heuristics for A* (e.g., weighted blocker count, distance-based cost)
- Track and compare nodes expanded / memory used across algorithms for a formal performance analysis
- Guarantee puzzle solvability at generation time rather than relying on random placement

## License

Add your preferred license here (e.g., MIT).
