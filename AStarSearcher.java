import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		Square goalSquare = maze.getGoalSquare();


		State root = new State(maze.getPlayerSquare(), null, 0, 0);
		double rootFValue = StateFValuePair.getFValue(root.getSquare(), goalSquare);
		StateFValuePair rootState = new StateFValuePair(root, rootFValue);

		frontier.add(rootState);

		while (!frontier.isEmpty()) {
			StateFValuePair curr = frontier.poll();
			State currState = curr.getState();
			int xPosition = currState.getX();
			int yPosition = currState.getY();
			explored[xPosition][yPosition] = true;

			noOfNodesExpanded++;
			if (maxDepthSearched < currState.getDepth()) {
				maxDepthSearched = currState.getDepth();
			}

			//IF POPPED ITEM IS GOAL STATE
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

			//ADDING NEW SUCCESSOR NODES TO FRONTIER

			//expanding current node
			ArrayList<State> successors = currState.getSuccessors(explored, maze);
			for (int i = successors.size() - 1; i > -1; i--) {
				State s = successors.remove(i);

			
				//turn each successor state into a StateFValuePair object
				StateFValuePair newNode = new StateFValuePair(s, 
						StateFValuePair.getFValue(s.getSquare(), goalSquare));

				//get State's coordinates on map
				int xPos = s.getX();
				int yPos = s.getY();

				//CHECK IF VISITED BEFORE
				if (explored[xPos][yPos] == true) {
					continue;
				}

				Iterator<StateFValuePair> itr = frontier.iterator();

				//CHECKING IF NODE IS CURRENTLY ON FRONTIER
				if (frontier.isEmpty()) {
					frontier.add(newNode);
				}
				else while (itr.hasNext()) {
					StateFValuePair fV = itr.next();
					if (s.getSquare().equals(fV.getState().getSquare())) {
						if (newNode.getFValue() < fV.getFValue()) {
							itr.remove();
							frontier.add(newNode);
							break;
						}
					}
				}
				frontier.add(newNode);
			}
			if (maxSizeOfFrontier < frontier.size()) {
				maxSizeOfFrontier = frontier.size();
			}
		}



		// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
		// maxDepthSearched, maxSizeOfFrontier during
		// the search

		// use frontier.poll() to extract the minimum stateFValuePair.
		// use frontier.add(...) to add stateFValue pairs

		// TODO return false if no solution
		return false;
	}

}
