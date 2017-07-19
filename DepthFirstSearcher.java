import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...

		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();
		State startState = new State(maze.getPlayerSquare(), null, 0, 0);
		stack.push(startState);

		while (!stack.isEmpty()) {


			//FOR DEBUGGING
//			System.out.println("The stack contains: ");
//			for (State v: stack) {
//				System.out.print("(" + v.getX() + "," + v.getY() + ")" + " ");
//			}
//			System.out.println();


			//POPPING A NODE FROM THE FRONTIER
			State currState = stack.pop();
			noOfNodesExpanded++;
			if (maxDepthSearched < currState.getDepth()) {
				maxDepthSearched = currState.getDepth();
			}


			//IF POPPED STATE IS A THE GOAL
			if (currState.isGoal(maze)) {
				currState = currState.getParent();
				cost++;
				while ((currState.getX() != maze.getPlayerSquare().X) || 
						(currState.getY() != maze.getPlayerSquare().Y)) {
					maze.setOneSquare(currState.getSquare(), '.');
					currState = currState.getParent();
					cost++;
				}
				return true;
			}

			//DECIDING WHETHER TO ADD SUCCESSORS TO FRONTIER
			ArrayList<State> successors = currState.getSuccessors(explored, maze);
			while (!successors.isEmpty()) {
				State successor = successors.remove(0);
				Square testSquare = successor.getSquare();
				if (stack.isEmpty()) {
					stack.push(successor);
					continue;
				}

				State currNode = currState;
				if (currNode.getParent() != null)  {
					ArrayList<Square> backPath = new ArrayList<Square>();
					do {
						backPath.add(currNode.getSquare());
						currNode = currNode.getParent();
					}
					while (!currNode.getSquare().equals(maze.getPlayerSquare()));
					backPath.add(maze.getPlayerSquare());

					boolean cycleFound = false;
					for (Square sq: backPath) {
						if (testSquare.equals(sq)) {
							cycleFound = true;
							//	System.out.println("CYCLE FOUND");
						}
					}
					if (!cycleFound) stack.push(successor);
					continue;
				}
				else stack.push(successor);

			}
			if (maxSizeOfFrontier < stack.size()) {
				maxSizeOfFrontier = stack.size();
			}
		}
		return false;
	}
}
