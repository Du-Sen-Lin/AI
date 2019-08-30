package g07.problem.blocks;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;

import java.util.ArrayList;
import java.util.Arrays;


public class BlocksProblem extends Problem {
	private int size;//单色木块数
	private int capacity;//最多能够隔着capacity个将牌跳
	
	public BlocksProblem(BlocksState initialState, int size, int capacity) {
		super(initialState);
		this.size = size;
		this.capacity = capacity;
	}
	

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
        int zob2;
        if(((BlocksState) parent).getStatus()[emptyPos+steps] == -1)               
            zob2 = zob1^zobrist[1][emptyPos+steps]^zobrist[1][emptyPos];
        else
            zob2 = zob1^zobrist[0][emptyPos+steps]^zobrist[0][emptyPos];
        return new BlocksState(size,newStatus,zob2);
		
	}
	
	@Override
	public boolean goalTest(State state) {
		return ((BlocksState)state).heuristic()==0;
	}

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
