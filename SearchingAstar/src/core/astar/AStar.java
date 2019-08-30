package core.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;

public class AStar {

	public AStar(Problem problem) {
		super();
		this.problem = problem;
	}
	
	//返回parent经过action到达的节点
	public Node childNode(Node parent, Action action) {
		State state = problem.result(parent.getState(), action);
		int pathCost = parent.getPathCost() + problem.stepCost(parent.getState(), action);
		int heuristic = problem.heuristic(state);
		return new Node(state, parent, action, pathCost, heuristic);
	}
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	
	

	public Node Search()
	{
		//起始节点
		State initState = problem.getInitialState();
		Node node = new Node(initState, null, null, 0, problem.heuristic(initState));
		
		OpenClosed openClosed = new OpenClosed();
		openClosed.insert(node);
				
		while (true) {
			
			if (openClosed.isEmpty())	return null;	//失败
			
			node = openClosed.pop(); //choose the lowest-cost node in frontier

			if (problem.goalTest(node.getState())) return node;

			for (Action action : problem.Actions(node.getState())) {
				Node child = childNode(node, action);
				if (!openClosed.contains(child.getState())) {
					openClosed.insert(child);

				}
			}
		}	
	}
	
	//用动画展示问题的解路径
	public void solution(Node node)
	{
		int cnt=0;
         while (node != null) {
             node.draw();
             System.out.println("|");
             node = node.getParent();
             cnt++;
         }
         
         System.out.println((cnt-1)+"   ");
		// Fix me
		// 调用Problem的drawWorld方法，和simulateResult方法
	}
	
	private Problem problem;
	
//	//DoubleBFS
//	 private Comparator<Node> comparator = new Comparator<Node>(){
//	        @Override
//	        public int compare(Node s1, Node s2) {
//	            return s1.compareTo(s2);
//	        }
//	    };
//	PriorityQueue<Node>[] queue=new PriorityQueue[2];
//	int[] size=new int[] {0,0};
//	HashMap<Integer, Node >[] hashMap=new HashMap[2];
//
//	
//	void output(Node n1,Node n2) {
//		ArrayList<Node> lists=new ArrayList<Node>();
//		Node node=n1;
//		int i=0;
//		while(node!=null) {
//			lists.add(i++,node);
//			node=node.getParent();
//		}
//		for ( int j = i-1; j >=0; j -- ){
//            Node print = lists.get(j);
//            print.draw();
//        }
//		node=n2;
//		while(node!=null) {
//			node.draw();;
//			node=node.getParent();
//		}
//		
//	}
//	
//	public void insert(int i,Node node) {
//		queue[i].add(node);
//		hashMap[i].put(node.hashCode(), node);
//		size[i]++;
//	}
//	
//	public Node poll(int i) {
//		Node node=queue[i].poll();
//		hashMap[i].remove(node.hashCode());
//		size[i]--;
//		return node;
//	}
//	
//	public boolean isContains(int i,State state) {
//		return hashMap[i].get(state.hashCode())!=null;
//	}
//	
//	public Node isIntersect(int i,State state) {
//		return hashMap[Math.abs(i-1)].get(state.hashCode());
//	}
//	
//	public boolean expand(int i) {
//		Node node=poll(i);
//		for (Action action : problem.Actions(node.getState())) {
//			Node child = childNode(node, action);
//			if (!isContains(i,child.getState())) {
//				Node other=isIntersect(i,child.getState());
//				if(other!=null) {
//					if(i==1) {
//						output(other,child);
//					}
//					else {
//						output(child,other);
//					}
//					return true;
//				}
//				else {
//					insert(i,child);
//				}
//			}
//
//		}
//		return false;
//	}
//	public boolean DoubleSearch() {
//		queue[0]=new PriorityQueue<Node>(comparator);
//		queue[1]=new PriorityQueue<Node>(comparator);
//		hashMap[0]=new HashMap<Integer, Node >();
//		hashMap[1]=new HashMap<Integer, Node >();
//		State initState = problem.getInitialState();
//		Node start = new Node(initState, null, null, 0, problem.heuristic(initState));
//		State goalState = problem.getGoal();
//		Node end = new Node(goalState, null, null, 0, problem.heuristic(goalState));
//		insert(0,start);
//		insert(1,end);
//		while(!queue[0].isEmpty()&&!queue[1].isEmpty()) {
//			if(size[0]>=size[1]) {
//				if(expand(0))
//					return true;
//			}
//			else {
//				if(expand(1))
//					return true;
//			}
//		}
//		while(!queue[0].isEmpty()) {
//			if(expand(0))
//				return true;
//		}
//		while(!queue[1].isEmpty()) {
//			if(expand(1))
//				return true;
//		}
//		return false;
//	
//	}
	
}
