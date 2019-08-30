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
	
	//all the possible actions from current state.从当前节点开始能执行的动作集合
	public abstract ArrayList<Action> Actions(State state);
	
	//The cost of the path from parent through action to its successors.计算耗散值
	public abstract int stepCost(State parent, Action action);
	
	//estimated cost of the cheapest path from the state to a goal state计算状态 state到goal的评估代价h(state)
	public abstract int heuristic(State state);

	//test if the state is a goal.是否是目标
	public boolean goalTest(State state) {
		//System.out.println(state.hashCode()+" "+goal.hashCode());
		return state.hashCode()==goal.hashCode();
	}
	
	public abstract boolean solvable();

	//The resulting state from parent through action.从parent经过action到达的节点
	public abstract State result(State parent, Action action);	
	
	//描画出该问题的World State，最好能对细节进行渲染
	public abstract void drawWorld();
	
	//描画从parent状态经Action的变化过程
	public abstract void simulateResult(State parent, Action action);



}
