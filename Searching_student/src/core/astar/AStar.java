package core.astar;

import core.problem.Action;
import core.problem.Problem; //操作
import core.problem.State;

import java.util.ArrayList;

public class AStar {
	
	public AStar(Problem problem) {
		super();
		this.problem = problem;
	}
	
	public Node childNode(Node parent, Action action) {
		State state = problem.result(parent.getState(), action);
		//getPathCost 初始到当前的实际代价   stepCost 从父级到后续任务的路径成本
		int pathCost = parent.getPathCost() + problem.stepCost(parent.getState(), action);
		int heuristic = problem.heuristic(state);  //估计从状态到目标状态最便宜路径的成本
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
		
		Fringe fringe = new Fringe();
		fringe.insert(node);
		
		Explored explored = new Explored();
		
		while (true) {
			
			if (fringe.isEmpty())	return null;	//失败
			
			node = fringe.pop(); //choose the lowest-cost node in frontier
			if (problem.goalTest(node.getState())) return node;
			explored.insert(node.getState());

			for (Action action : problem.Actions(node.getState())) {
				Node child = childNode(node, action);
				//child.getState()不再在扩展节点的集合(Close表中）且fringe(Open表）中不存在状态为state的节点  则将节点插入fringe中
				if (!explored.contains(child.getState()) && !fringe.contains(child.getState())) {
					fringe.insert(child);
				}
				else {
					/**
					如果邻接结点N’在CLOSED表中，比较CLOSED表中的g(N')值和当前的g(N')值，如果当前的g(N')更小，那么删除CLOSED表中的N'，把N设成N'的父节点，重新将N'插入OPEN表。
   	如果邻接结点N’在OPEN表中，比较OPEN表中的g(N')值和当前的g(N')值，如果当前的g(N')更小，那么删除OPEN表中的N'，把N设成N'的父节点，重新将N'插入OPEN表。
				***/
					Node revisited = fringe.revisited(child.getState());
					if (revisited != null && revisited.evaluation() > child.evaluation()) {
						fringe.replace(revisited, child);//用child节点代替Fringe中的 revisited节点
					}
				}
			}

		}	
	}
	
	//用动画展示问题的解路径
	public void solution(Node node)
	{
		// Fix me
		// 调用Problem的drawWorld方法，和simulateResult方法
		problem.drawWorld();
	}
	
	private Problem problem;
}
