/**
 *
 */
package g07.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.uitlity.Zobrist;

import java.util.ArrayList;

/**
 * @author Jianliang
 *
 */
public class NPuzzle extends Problem {
	public NPuzzle() {
		super();
	}

	private NPuzzle(PuzzleState initialState, PuzzleState goal, int side) {
		super(initialState, goal);
		this.side = side;
		zobrist = new Zobrist(side * side, side * side);
	}

	//初始状态 目标状态
	public NPuzzle(int side, int[] status) {
		this(new PuzzleState(side, status), defaultGoal(side), side);
	}

	private static PuzzleState defaultGoal(int side) {
		int[] status = new int[side * side];
		for (int i = 0; i < side * side - 1; i++)
			status[i] =  (i + 1);
		status[side * side - 1] = 0;
		PuzzleState goal = new PuzzleState(side, status);
		return goal;
	}
	/* (non-Javadoc)
	 * @see core.problem.Problem#result(core.problem.State, core.problem.Action)
	 */
	@Override
	public State result(State parent, Action action) {
		return null;
	}

	@Override
	public void drawWorld() {

	}

	@Override
	public void simulateResult(State parent, Action action) {

	}

	/* (non-Javadoc)
	 * @see core.problem.Problem#stepCost(core.problem.State, core.problem.Action)
	 */
	@Override
	public int stepCost(State parent, Action action) {
		// TODO Auto-generated method stub
		return 1;
	}

	/* (non-Javadoc)
	 * @see core.problem.Problem#heuristic(core.problem.State)
	 */
	@Override
	public int heuristic(State state) {
		return 0;
	}

	//是否可解
	static int cnt;
	static int[] b;
	static int flag;
	public static void sort(int[] a,int l,int r){
		if(r-l>0){
			int mid=(r+l)/2;
			int i=l;
			int p=l;
			int q=mid+1;
			sort(a,l,mid);
			sort(a,mid+1,r);
			while (p<=mid||q<=r){
				if(q>r||(p<=mid&&a[p]<=a[q])){
					b[i++]=a[p++];
				}
				else{
					b[i++]=a[q++];
					cnt+=mid-p+1;
				}
			}
			for(i=l;i<=r;i++){
				a[i]=b[i];
			}
		}
	}

	/* (non-Javadoc)
	 * @see core.problem.Problem#Actions(core.problem.State)
	 */
	@Override
	public ArrayList<Action> Actions(State state) {
		// TODO Auto-generated method stub

		return null;
	}

	private int side;	//边长：3, 4

	private Zobrist zobrist;

	@Override
	public boolean solvable() {
		// TODO Auto-generated method stub
		int num=0;
		int[] c = new int[side*side];
		int[] ss=new int[side*side];
		for(int i=0;i<side*side;i++){
			c[num++]=((PuzzleState)getInitialState()).getStates()[i];
		}

		for(int i=0;i<side*side;i++){
			if(c[i]==0){
				flag=i;
			}
		}
		for(int i=0;i<flag;i++){
			ss[i]=c[i];
		}
		for(int i=flag;i<side*side-1;i++){
			ss[i]=c[i+1];
		}
		cnt=0;
		sort(ss,0,7);
		if(cnt%2==0) {
			return true;
		}
		else
		{
			return false;
		}
	}

}
