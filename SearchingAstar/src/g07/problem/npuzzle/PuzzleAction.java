package g07.problem.npuzzle;

import core.problem.Action;

public class PuzzleAction extends Action {
	public PuzzleAction() {
		// TODO Auto-generated constructor stub
	}

	public PuzzleAction(int blankPos, Direction dir) {
		super();
		this.blankPos = blankPos;
		this.dir = dir;
	}

	public int getBlankPos() {
		return blankPos;
	}

	public void setBlankPos(int blankPos) {
		this.blankPos = blankPos;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	@Override
	public void draw() {
		
	}
	
	int blankPos = 0;	//空格位置
	Direction dir;      //空格移动的方向
}
