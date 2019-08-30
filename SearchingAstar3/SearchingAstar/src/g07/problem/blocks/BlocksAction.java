package g07.problem.blocks;

import core.problem.Action;

public class BlocksAction extends Action {
	int steps;//跳多少格 负数表示向右，正数 表示向左
	
	public BlocksAction(int steps) {
		super();
		this.steps = steps;
	}
	


	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
