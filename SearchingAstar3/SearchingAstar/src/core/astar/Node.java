package core.astar;

import core.problem.Action;
import core.problem.State;

public class Node implements Comparable<Node>{
	public Node(State state, Node parent, Action action, int pathCost, int heuristic) {
		super();
		this.state = state;
		this.parent = parent;
		this.action = action;
		this.pathCost = pathCost;
		this.heuristic = heuristic;
	}
		
	private State state;	// the state in the state space to which the node corresponds
	private Node parent;	// the node in the search tree that generated this node
	private Action action;	// the action that was applied to the parent to generate the node 搴旂敤浜庣埗绾т互鐢熸垚鑺傜偣鐨勬搷浣�
	private int pathCost;	// the cost of the path from the initial state to the node 锛沢(n):浠庡垵濮嬬姸鎬佸埌褰撳墠鐘舵�佺殑瀹為檯浠ｄ环銆�
	private int heuristic;  // estimated cost of the cheapest path from the state at node to a goal state

	//f(n)=g(n)+h(n) evalution 浼颁环
	public int evaluation()
	{
		return getPathCost() + getHeuristic();
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public int getPathCost() {
		return pathCost;
	}
	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}
	public int getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public int compareTo(Node another) {
		// TODO Auto-generated method stub
		return this.evaluation() - another.evaluation();
		
		// Fix me when necessary.
	}
	
	public void draw() {
		state.draw();
	//	System.out.println("                  gn:" + this.getPathCost() + ", " +  "hn:" + this.getHeuristic() );

	}
	@Override
	public int hashCode(){
		return state.hashCode();
	}
}
