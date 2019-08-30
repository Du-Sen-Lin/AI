package g07.problem.npuzzle;

import core.problem.State;

public class PuzzleState extends State {
	private int side;
	private int hash;
	private int[] states;
	private int g=0; //Ŀǰ�Ĵ���
	private int zeroX=0;
	private int zeroY=0;


	public int[] getStates() {
		return states;
	}

	public void setStates(int[] states) {
		this.states = states;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
	}

	private PuzzleState(int side) {
		// TODO Auto-generated constructor stub
		this.side = side;
	}
	
	public PuzzleState(int side, int[] status) {
		// TODO Auto-generated constructor stub
		this(side);
		setStatus(status);
	}
	
	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}


	
	public int getInversions() {		
		return 0;
	}
	
	//����λ���Ƶĸ���
	public int misplaced() {
		return 0;
	}
		
	public int getManhattan() {
		return manhattan;
	}

	public void setManhattan(int manhattan) {
		this.manhattan = manhattan;
	}
	
	public long getZobrist() {
		return zobrist;
	}

	public void setZobrist(long zobristValue) {
		this.zobrist = zobristValue;
	}

	@Override
	public void draw() {
		for (int i = 0; i < side; i++) {
			drawRow(i);
		}
		drawLine(); // ������
	}
	
	private void drawRow(int row) {
		drawLine();
		for (int j = 0; j < side; j++) {
			int index = row * side + j;
			if (status[index] != 0)
				System.out.printf("|" + "%2d" + " ", status[index]);
			else
				System.out.print("| # ");
		}
		System.out.println("|");
	}

	private void drawLine() {
		for (int j = 0; j < side; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	private void setBlankPos() {
	
	}

	public int getBlankPos() {
		return blankPos;
	}

	@Override
	public int hashCode() {
		
		return 0;
	}
		
	@Override
	public boolean equals(Object obj) {
		PuzzleState s = (PuzzleState) obj;
		return this.zobrist == s.zobrist;
	}
	
	//private int side; 		// 9, 16
	private int[] status; 	// һά�����Ž���
	private int blankPos; 	// �ո��λ��,��1��ʼ����
	
	private long zobrist;
	
	private int manhattan;	//��ǰ״̬��Ŀ��״̬�������پ���
}
