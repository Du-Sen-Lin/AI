package g07.problem.blocks;


import java.util.ArrayList;
import java.util.Arrays;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.uitlity.Zobrist;




public class BlocksProblem extends Problem{
	private int size;
	private int capacity=2;//最多能够隔着capacity个将牌跳
	private Zobrist zobrist;
		
	private BlocksState defaultInit(int size) {
		int[] status = new int[size * 2 + 1];
		for (byte i = 0; i < size; i++) {
			status[i] = 1;
			status[i + size] = 2;
		}		
		status[size * 2] = 0;
		this.zobrist=new Zobrist(2*size+1,2);
		BlocksState init = new BlocksState(size, status,zobrist.hash(status));
		return init;
	}
	
	private BlocksState defaultGoal(int size) {
		int[] status = new int[size * 2 + 1];
		for (byte i = 0; i < size; i++) {
			status[i] = 2;
			status[i + size] = 1;
		}		
		status[size * 2] = 0;
		BlocksState goal = new BlocksState(size, status,zobrist.hash(status));
		return goal;
	}
	
	public BlocksProblem(int size, boolean classical) {
		this.setInitialState(defaultInit(size));
		this.setGoal(defaultGoal(size));
		if (!classical) capacity = (size + 1) / 2 ;   
		this.size=size;
	}
	
//	public BlocksProblem(BlocksState initialState, BlocksState goal, int size) {
//		super(initialState, goal);
//		this.size = size;
//	}
//	

	@Override
	public int stepCost(State parent, Action action) {
		// TODO Auto-generated method stub
		int gn = 1;
		int steps=Math.abs(((BlocksAction)action).getSteps());
        if (steps >= 2 )
            gn = steps - 1;
        return gn;
	}

	@Override
	public int heuristic(State state) {		
		// TODO Auto-generated method stub
		
		BlocksState s = (BlocksState) state;
		return s.heuristic();
	}
	
	private boolean isSafe(int x) {
		return x>=0 && x<=size*2;
	}

	@Override
	public ArrayList<Action> Actions(State state) {
		// TODO Auto-generated method stub
		
		ArrayList<Action> actions = new ArrayList<>();
		
		int emptyPos = ((BlocksState) state).getEmptyPos();//得到空格的位置
		
        for (int i = emptyPos-capacity-1; i <= emptyPos+capacity+1; i ++){    //最多隔着capacity个牌跳

            if (isSafe(i)&& i != emptyPos) {
                BlocksAction action=new BlocksAction(i-emptyPos);
                actions.add(action);

            }
        }
		
		return actions;
	}
	
	@Override
	public State result(State parent, Action action) {
		int emptyPos = ((BlocksState) parent).getEmptyPos();
		
		int steps = ((BlocksAction) action).getSteps(); 
		
		int[] newStatus = Arrays.copyOf(((BlocksState) parent).getStatus(), ((BlocksState) parent).getStatus().length);
        int t = newStatus[emptyPos+steps];
        newStatus[emptyPos+steps] = newStatus[emptyPos];
        newStatus[emptyPos] = t;
      //计算zonbrist
        int zob1 = ((BlocksState) parent).getZobrist();
        int zob2=zobrist.newHash(zob1,emptyPos+steps,((BlocksState) parent).getStatus()[emptyPos+steps],emptyPos,((BlocksState) parent).getStatus()[emptyPos]);
        return new BlocksState(size,newStatus,zob2);
		
	}
	
//	@Override
//	public boolean goalTest(State state) {
//		return ((BlocksState)state).heuristic()==0&&((BlocksState)state).getEmptyPos()==size*2;
//	}

	@Override
	public void drawWorld() {
		// TODO Auto-generated method stub
		this.getInitialState().draw();
	}

	@Override
	public void simulateResult(State parent, Action action) {
		// TODO Auto-generated method stub
	
		State child = result(parent, action);
		
		//action.draw();
		child.draw();

	}

	
	@Override
	public boolean solvable() {
		// TODO Auto-generated method stub
		return true;
	}
}
