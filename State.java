import java.util.ArrayList;
import java.util.Iterator;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the DFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 *            <<EXPLORED IS ONLY UPDATED IN A* SEARCH, NOT IN DFS>>
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {

		// FILL THIS METHOD
		ArrayList<State> successors = new ArrayList<State>();

		int x = square.X;
		int y = square.Y;
		int newGValue = gValue + 1;
		int newDepth = depth + 1;

		int maxY = maze.getNoOfRows();
		int maxX = maze.getNoOfCols();

		State parent = this;

		//FOR DEBUGGING
		//		System.out.println("Expanding the state " + x + "," + y);
		//		System.out.println("Which contains :" + maze.getSquareValue(x,  y) + ".");

		//LEFT (_, x) is decreasing
		if (y > 0) {

			if (maze.getSquareValue(x,  y - 1) != '%') {
				Square left = new Square(getX(), getY() - 1);	
				State moveLeft = new State(left, parent, newGValue, newDepth);
				successors.add(moveLeft);
			}
		}

		//DOWN (y, _) is increasing
		if (x < maxY) {
			
			if (maze.getSquareValue(x + 1,  y) != '%') {
				Square down = new Square(getX() + 1, getY());
				State moveDown = new State(down, parent, newGValue, newDepth);
				successors.add(moveDown);
			}
		}

		//RIGHT (_, x) is increasing
		if (y < maxX) {

			if (maze.getSquareValue(x,  y + 1) != '%') {
				Square right = new Square(getX(), getY() + 1);
				State moveRight = new State(right, parent, newGValue, newDepth);
				successors.add(moveRight);
			}
		}

		//UP (y, _) is decreasing
		if (x > 0) {

			if (maze.getSquareValue(x - 1,  y) != '%') {
				Square up = new Square(getX() - 1, getY());
				State moveUp = new State(up, parent, newGValue, newDepth);
				successors.add(moveUp);
			}
		}
		//System.out.println("Found " + successors.size() + " successors");
		return successors;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the DFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}

	public boolean equal(State a) {
		if (this.getX() == a.getX()) {
			if (this.getY() == a.getY()) {
				if (this.getDepth() == a.getDepth()) {
					if (this.getGValue() == a.getGValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
