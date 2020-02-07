import java.util.ArrayList;

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
	// You may not need it for the BFS but you will
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
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		ArrayList<State> state = new ArrayList<State>();//create the arraylist to store the succors

		int[] moveY = {-1, 0, 1, 0};//left and right
		int[] moveX = {0, 1, 0, -1};//up and down

		for (int i = 0; i < 4; i++){
			int X = this.getSquare().X;//current cod
			int Y = this.getSquare().Y;
			int Fx = maze.getGoalSquare().X;//goal cod
			int Fy = maze.getGoalSquare().Y;
			int Y1 = this.getSquare().Y + moveY[i];//next cod
			int X1 = this.getSquare().X + moveX[i];

			if (maze.getSquareValue(X1,Y1) != '%' &&
							explored[X1][Y1]==false){
				State neighbour;
				neighbour = new State(new Square(X1,Y1), this, this.getGValue()+1, this.getDepth() );
				depth = Math.max(this.depth, neighbour.getDepth());
				state.add(neighbour);
			}
		}
		return state;
		// TODO check all four neighbors in left, down, right, up order
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
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
	 * You may not need g() value in the BFS but you will need it in A-star
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
}
