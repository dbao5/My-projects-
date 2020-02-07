import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
			for (int i =0; i < maze.getNoOfRows();i++){
				for (int j = 0; j < maze.getNoOfCols();j++){
					explored[i][j]=false;
				}
			}
		// set up the whole maze that all the node that not exposed is false
		State StartNode = new State(maze.getPlayerSquare(), null,0,0);
			State pop;
		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		queue.add(StartNode);//start size is 1
		noOfNodesExpanded++;
		while (!queue.isEmpty()) {
			pop = queue.pop();
			explored[pop.getX()][pop.getY()]=true;
			maxSizeOfFrontier = Math.max(queue.size(),maxSizeOfFrontier);
			List<State> suc = pop.getSuccessors(explored,maze);
			for (int i =0; i<suc.size();i++){
				if (suc.get(i).isGoal(maze)){
					noOfNodesExpanded++;
					maxDepthSearched++;
					State path = suc.get(i);//from the goal to trace back
					while (!path.getParent().equals(StartNode)){
						maze.setOneSquare(new Square(path.getX(), path.getY()),'.');
						maxDepthSearched++;
						path = path.getParent();
					}
					if (StartNode.getSquare().X==maze.getGoalSquare().X){
						maxSizeOfFrontier++;
					}
					maze.setOneSquare(new Square(path.getX(),path.getY()),'.');
					maze.setOneSquare(new Square(maze.getGoalSquare().X, maze.getGoalSquare().Y),'G');
					cost = maxDepthSearched;
					return true;
				}
						if (!check_path(suc.get(i),queue)){
							noOfNodesExpanded++;
						}
						queue.add(suc.get(i));
					}
			}
		return false;
	}
	private boolean check_path(State current, LinkedList<State> queue){
		for (int i =0; i < queue.size();i++){
			if (current.getX()==queue.get(i).getX()&&current.getY()==
					queue.get(i).getY()){
				return true;
			}
		}
		return false;
	}

}
