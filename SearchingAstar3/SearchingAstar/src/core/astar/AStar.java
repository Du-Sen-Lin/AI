package core.astar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import g07.problem.blocks.BlocksState;

public class AStar {
	public int[] layer=new int[1000];
	int size,m;
	public AStar(Problem problem) {
		super();
		this.problem = problem;
		size=((BlocksState)problem.getInitialState()).getSize();
		m=size*size-1;
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
	
	
//	public Node Search()
//	{
//		//起始节点
//		State initState = problem.getInitialState();
//		Node node = new Node(initState, null, null, 0, problem.heuristic(initState));
//		
//		Fringe fringe = new Fringe();
//		fringe.insert(node);
//		
//		Explored explored = new Explored();
//		
//		while (true) {
//			
//			if (fringe.isEmpty())	return null;	//失败
//			
//			node = fringe.pop(); //choose the lowest-cost node in frontier
//			if (problem.goalTest(node.getState())) return node;
//			explored.insert(node.getState());
//
//			for (Action action : problem.Actions(node.getState())) {
//				Node child = childNode(node, action);
//				//child.getState()不再在扩展节点的集合且fringe中不存在状态为state的节点  则将节点插入fringe中
//				if (!explored.contains(child.getState()) && !fringe.contains(child.getState())) {
//					fringe.insert(child);
//				}
//				else {
//					Node revisited = fringe.revisited(child.getState());
//					if (revisited != null && revisited.evaluation() > child.evaluation()) {
//						fringe.replace(revisited, child);//用child节点代替Fringe中的 revisited节点
//					}
//				}
//			}
//
//		}	
//	}
//	

	public Node Search()
	{
		//起始节点
		State initState = problem.getInitialState();
		Node node = new Node(initState, null, null, 0, problem.heuristic(initState));
		init();
		OpenClosed openClosed = new OpenClosed();
		openClosed.insert(node);
		int c=0;   //标记
		while (true) {

			if (openClosed.isEmpty())	return null;	//失败
			node = openClosed.pop(); //choose the lowest-cost node in frontier
			if (problem.goalTest(node.getState())) return node;
			for (Action action : problem.Actions(node.getState())) {
				Node child = childNode(node, action);
				if (!openClosed.contains(child.getState()) ||openClosed.revisited(child.getState())>child.getPathCost()) {
					//剪枝
					c=cute(node.getHeuristic(), node.getPathCost());
					if(c==-1)continue;
					openClosed.insert(child);
				}
			}
		}	
	}

	/**
	 * 剪枝   开头与结尾搜索宽度设置一个较大w值  其他状态设置较小值
	 * 若到达某一个Heuristic值最小的代价与w的和小于pathCost ，则不插入扩展节点
	 * @param h		 Heuristic
	 * @param g		pathCost
	 * @return
	 */
	int cute(int h,int g){
		if(layer[h]==-1){
			layer[h]=g;  //到达h最小的代价
			return 1;
		}
		int w=1;
		if(h>m||h<5) {  //处理开头与结尾
			w = 20;
		}
		if(layer[h]+w<g){
			//System.out.printf("%d h=%d g=%d\n",layer[h],h,g);
			return -1;
		}
		return 1;
	}

	/**
	 * layer 初始化 记录Heuristic
	 */
	 void init(){
		for(int i=0,j=layer.length;i<j;i++){
			layer[i]=-1;
		}
	}
	//用动画展示问题的解路径
	public void solution(Node node) {
		int cnt = 0;
		ArrayList<Node> lists = new ArrayList<Node>();
		while (node != null) {
			lists.add(node);
			node = node.getParent();
			cnt++;
		}

			for (int i = cnt - 1; i >= 0; i--) {
				node = lists.get(i);
				node.draw();
				//System.out.println();
			//	System.out.println("|");
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
