package core.problem;

import java.util.ArrayList;
import java.util.Random;

public abstract class Problem {
	public Problem() {
		
	}
	//this与super的用法总结
	// this 是自身的一个对象，代表对象本身；super 指向自己超（父）类对象的一个指针。这个超类离的是离自己最近的一个父类
	//super()调用父类构造方法
	public static int[][] zobrist;
	public Problem(State initialState, State goal) {
		super();
		this.initialState = initialState;
		this.goal = goal;
	}
	public Problem(State initialState) {
		super();

		this.initialState = initialState;
	}

	public static void setZobrist(int row,int col) {
		zobrist = new int[row][col];
		Random random = new Random();
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				zobrist[i][j] = random.nextInt();

	}

	public abstract boolean solvable();
	//从父级到操作的结果状态。
	//The resulting state from parent through action.
	public abstract State result(State parent, Action action);
	//从父级到后续任务的路径成本
	//The cost of the path from parent through action to its successors.
	public abstract int stepCost(State parent, Action action);
	//启发式的heuristic
	//估计从状态到目标状态最便宜路径的成本
	//estimated cost of the cheapest path from the state to a goal state
	public abstract int heuristic(State state);
	//当前状态下的所有可能操作。
	//all the possible actions from current state.
	public abstract ArrayList<Action> Actions(State state);
	//测试状态是否为目标。
	//test if the state is a goal.
	public boolean goalTest(State state) {
		return state.equals(goal);
	}
	
	public State getInitialState() {
		return initialState;
	}
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	
	//描画出该问题的World State，最好能对细节进行渲染
	public abstract void drawWorld();
	
	//描画从parent状态经Action的变化过程
	public abstract void simulateResult(State parent, Action action);

	private State initialState;
	private State goal;


	public State getGoal() {
		return goal;
	}

	public void setGoal(State goal) {
		this.goal = goal;
	}
}
