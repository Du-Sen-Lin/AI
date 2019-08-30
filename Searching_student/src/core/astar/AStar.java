package core.astar;

import core.problem.Action;
import core.problem.Problem; //����
import core.problem.State;

import java.util.ArrayList;

public class AStar {
	
	public AStar(Problem problem) {
		super();
		this.problem = problem;
	}
	
	public Node childNode(Node parent, Action action) {
		State state = problem.result(parent.getState(), action);
		//getPathCost ��ʼ����ǰ��ʵ�ʴ���   stepCost �Ӹ��������������·���ɱ�
		int pathCost = parent.getPathCost() + problem.stepCost(parent.getState(), action);
		int heuristic = problem.heuristic(state);  //���ƴ�״̬��Ŀ��״̬�����·���ĳɱ�
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
		//��ʼ�ڵ�
		State initState = problem.getInitialState();
		Node node = new Node(initState, null, null, 0, problem.heuristic(initState));
		
		Fringe fringe = new Fringe();
		fringe.insert(node);
		
		Explored explored = new Explored();
		
		while (true) {
			
			if (fringe.isEmpty())	return null;	//ʧ��
			
			node = fringe.pop(); //choose the lowest-cost node in frontier
			if (problem.goalTest(node.getState())) return node;
			explored.insert(node.getState());

			for (Action action : problem.Actions(node.getState())) {
				Node child = childNode(node, action);
				//child.getState()��������չ�ڵ�ļ���(Close���У���fringe(Open���в�����״̬Ϊstate�Ľڵ�  �򽫽ڵ����fringe��
				if (!explored.contains(child.getState()) && !fringe.contains(child.getState())) {
					fringe.insert(child);
				}
				else {
					/**
					����ڽӽ��N����CLOSED���У��Ƚ�CLOSED���е�g(N')ֵ�͵�ǰ��g(N')ֵ�������ǰ��g(N')��С����ôɾ��CLOSED���е�N'����N���N'�ĸ��ڵ㣬���½�N'����OPEN��
   	����ڽӽ��N����OPEN���У��Ƚ�OPEN���е�g(N')ֵ�͵�ǰ��g(N')ֵ�������ǰ��g(N')��С����ôɾ��OPEN���е�N'����N���N'�ĸ��ڵ㣬���½�N'����OPEN��
				***/
					Node revisited = fringe.revisited(child.getState());
					if (revisited != null && revisited.evaluation() > child.evaluation()) {
						fringe.replace(revisited, child);//��child�ڵ����Fringe�е� revisited�ڵ�
					}
				}
			}

		}	
	}
	
	//�ö���չʾ����Ľ�·��
	public void solution(Node node)
	{
		// Fix me
		// ����Problem��drawWorld��������simulateResult����
		problem.drawWorld();
	}
	
	private Problem problem;
}
