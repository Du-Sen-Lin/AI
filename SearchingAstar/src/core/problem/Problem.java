package core.problem;


import java.util.ArrayList;

import java.util.Random;

import core.uitlity.Zobrist;

public abstract class Problem {
	private State initialState;
	private State goal;
	
	public Problem() {
		
	}
	
	public Problem(State initialState) {
		super();
		
		this.initialState = initialState;
	}
	
	public Problem(State initialState, State goal) {
		super();
		this.initialState = initialState;
		this.goal = goal;
		
	}
	
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	
	public State getInitialState() {
		return initialState;
	}
	
	
	public State getGoal() {
		return goal;
	}

	public void setGoal(State goal) {
		this.goal = goal;
	}
	
	//all the possible actions from current state.�ӵ�ǰ�ڵ㿪ʼ��ִ�еĶ�������
	public abstract ArrayList<Action> Actions(State state);
	
	//The cost of the path from parent through action to its successors.�����ɢֵ
	public abstract int stepCost(State parent, Action action);
	
	//estimated cost of the cheapest path from the state to a goal state����״̬ state��goal����������h(state)
	public abstract int heuristic(State state);

	//test if the state is a goal.�Ƿ���Ŀ��
	public boolean goalTest(State state) {
		//System.out.println(state.hashCode()+" "+goal.hashCode());
		return state.hashCode()==goal.hashCode();
	}
	
	public abstract boolean solvable();

	//The resulting state from parent through action.��parent����action����Ľڵ�
	public abstract State result(State parent, Action action);	
	
	//�軭���������World State������ܶ�ϸ�ڽ�����Ⱦ
	public abstract void drawWorld();
	
	//�軭��parent״̬��Action�ı仯����
	public abstract void simulateResult(State parent, Action action);



}
