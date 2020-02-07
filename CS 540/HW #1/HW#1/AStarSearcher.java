import java.util.*;

/**
 * A* algorithm search
 *
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 *
	 * @param maze initial maze.
	 * @see Searcher
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

		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		for (int i = 0; i < maze.getNoOfRows(); i++) {
			for (int j = 0; j < maze.getNoOfCols(); j++) {
				explored[i][j] = false;
			}
		}
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		int Gx = maze.getGoalSquare().X;
		int Gy = maze.getGoalSquare().Y;
		double initial = Math.sqrt((maze.getPlayerSquare().X - maze.getGoalSquare().X) * (maze.getPlayerSquare().X
				- maze.getGoalSquare().X) + (maze.getPlayerSquare().Y - maze.getGoalSquare().Y) * (maze.getPlayerSquare().Y
				- maze.getGoalSquare().Y));

		StateFValuePair root = new StateFValuePair(new State(maze.getPlayerSquare(), null, 0, 0), initial);
		frontier.add(root);
		StateFValuePair pop;
		while (!frontier.isEmpty()) {
			maxSizeOfFrontier = Math.max(frontier.size(), maxSizeOfFrontier);
			pop = frontier.poll();
			noOfNodesExpanded++;
			explored[pop.getState().getX()][pop.getState().getY()] = true;
			ArrayList<State> s = pop.getState().getSuccessors(explored, maze);
			for (int i = 0; i < s.size(); i++) {//loop through all the successors
				if (s.get(i).isGoal(maze)) {
					noOfNodesExpanded++;
					maxDepthSearched++;
					State Path = s.get(i); //goal
					while (!Path.getParent().equals(root.getState())) {
						maze.setOneSquare(new Square(Path.getX(), Path.getY()), '.');
						maxDepthSearched++;
						Path = Path.getParent();
					}
					maze.setOneSquare(new Square(Path.getX(), Path.getY()), '.');
					maze.setOneSquare(new Square(maze.getGoalSquare().X, maze.getGoalSquare().Y), 'G');
					cost = maxDepthSearched;
					return true;
				}

				double eu = Math.sqrt((s.get(i).getX() - Gx) * (s.get(i).getX()
						- Gx) + (s.get(i).getY() - Gy) * (s.get(i).getY() - Gy));

				double f = s.get(i).getParent().getGValue() +1 + eu;
				StateFValuePair st = new StateFValuePair(s.get(i), f);
				boolean p1 = true;
				for (StateFValuePair e : frontier) {
					if (st.getState().getX() == e.getState().getX() &&
							st.getState().getY() == e.getState().getY()) {
						if (st.compareTo(e) == 1) {
							frontier.remove(e);
							frontier.add(st);
							break;
						}
						p1 = false;
					}
				}
				if (p1 == true) {
					frontier.add(st);
				}
			}
		}
		return false;
	}
}
//				LinkedList<StateFValuePair> list = new LinkedList<>();
//				for (StateFValuePair e : frontier
//					 ) {
//					list.add(e);
//				}
//				for (int k =0; k< list.size();k++){
//					if (s.get(i).getSquare().X==list.get(k).getState().getSquare().X&&
//							s.get(i).getSquare().Y==list.get(k).getState().getSquare().Y){
//						if (s.get(i).getGValue()>=list.get(k).getState().getGValue()){
//							break;
//						}else{
//							frontier.remove(list.get(k));
//							frontier.add(new StateFValuePair(s.get(i), f));
//							break;
//						}
//					}
//				}

//				StateFValuePair sp = new StateFValuePair(s.get(i), f);
//				frontier.add(sp);
//				Iterator<StateFValuePair> iterator = frontier.iterator();
//				boolean judge = false;
//				while (iterator.hasNext())
//				{
//					StateFValuePair iterStatePair = iterator.next();
//					System.out.println("x " + iterStatePair.getState().getX() + " y" + iterStatePair.getState().getY());
//					if (sp.getState().getX() == iterStatePair.getState().getX()
//							&& sp.getState().getY() == iterStatePair.getState().getY())
//					{
//						if (sp.compareTo(iterStatePair) == -1) // comparing F value of two nodes
//						{
//							frontier.remove(iterator.next());
//							frontier.add(sp);
//							judge = true;
//						}
//					}
//				}
//				if (!judge){
//					noOfNodesExpanded++;
//				}


//				while(g_iterator.next().equals(s.get(i))){
//					if (s.get(i).getGValue()<next.getState().getGValue()){
//						frontier.add(new StateFValuePair(s.get(i),f));
//						noOfNodesExpanded++;
//					}else {
//						break;
//					}
//				}
//			LinkedList<State> queue=new LinkedList<>();
//			for (int k = 0; k<frontier.size();k++){
//				queue.add(k,frontier.poll().getState());
//				}

//				if (!frontier.contains(s.get(i))){
//					frontier.add(new StateFValuePair(s.get(i),f));
//					noOfNodesExpanded++;
//				}else{
//					PriorityQueue<StateFValuePair> com = frontier;
//					StateFValuePair check = com.poll();
//
//				}
//				LinkedList<StateFValuePair> queue = null;
//				StateFValuePair add = frontier.poll();
//				while (add != null && !queue.contains(add)) {
//					queue.add(add);
//				}
//				for (int k = 0; k < queue.size(); k++) {
//					if (s.get(i).getX() == queue.get(k).getX() &&
//							s.get(i).getY() == queue.get(k).getY()) {
//						if (s.get(i).getGValue() >= queue.get(k).getGValue()) {
//							break;
//						} else {
//							frontier.add(new StateFValuePair(s.get(i), f));
//							noOfNodesExpanded++;
//						}
//					}
//				}

//	private static class Node {
//		int data;
//		int priority;
//
//		Node next;
//
//	}
//	private static Node node = new Node();
//	private static Node newNode(int d, int p)
//	{
//		Node temp = new Node();
//		temp.data = d;
//		temp.priority = p;
//		temp.next = null;
//		return temp;
//	}
//
//	// Return the value at head
//	private static int peek(Node head)
//	{
//		return (head).data;
//	}
//
//	// Removes the element with the
//// highest priority form the list
//	private static Node pop(Node head)
//	{
//		Node temp = head;
//		(head)  = (head).next;
//		return head;
//	}
//
//	// Function to push according to priority
//	private static Node push(Node head, int d, int p)
//	{
//		Node start = (head);
//
//		// Create new Node
//		Node temp = newNode(d, p);
//
//		// Special Case: The head of list has lesser
//		// priority than new node. So insert new
//		// node before head node and change head node.
//		if ((head).priority > p) {
//
//			// Insert New Node before head
//			temp.next = head;
//			(head) = temp;
//		}
//		else {
//
//			// Traverse the list and find a
//			// position to insert new node
//			while (start.next != null &&
//					start.next.priority < p) {
//				start = start.next;
//			}
//
//			// Either at the ends of the list
//			// or at required position
//			temp.next = start.next;
//			start.next = temp;
//		}
//		return head;
//	}
//
//	// Function to check is list is empty
//	static int isEmpty(Node head)
//	{
//		return ((head) == null)?1:0;
//	}
//


