package core.uitlity;

import java.security.SecureRandom;

public class Zobrist {

	public Zobrist(int size, int pieceNumber) {
		// TODO Auto-generated constructor stub
		SecureRandom rand = new SecureRandom();
		zobristSeeds = new int[size][];
		for (int i = 0; i < size; i++) {
			zobristSeeds[i] = new int[pieceNumber];
			for (int j = 0; j < pieceNumber; j++) {
				zobristSeeds[i][j] = rand.nextInt();
			}
		}
		//System.out.println(size);
		this.size = size;
		this.pieceNumber = pieceNumber;
	}
	
	//初始化一个16*16的随机数数组
	private int[][] zobristSeeds;
	
	//根据随机数组，计算某个状态的Zobrist值
	public int hash(int[] status) {
		int h = 0;
		for (int i = 0; i < size; i++) {
			if (status[i] != 0)
				h ^= zobristSeeds[i][status[i]-1];
		}
		return h;
	}
	
	//在当前状态下（其zobrist值为value）, 将位置srcIndex上的代号为srcPiece的棋子，移动到destIndex，
	//   destPiece为当前在目标位置destIndex上的棋子的代号，可能为空格（目标位置没有棋子）。
	public int newHash(int value, int srcIndex, int srcPiece, int destIndex, int destPiece) {		
		value ^= zobristSeeds[srcIndex][srcPiece-1];	//异或掉要移动的子
		if (destPiece != 0)	// 如果目标位置有棋子，则异或掉该棋子。吃子的情况 
			value ^= zobristSeeds[destIndex][destPiece];
		value ^= zobristSeeds[destIndex][srcPiece-1];	//将要移动的棋子，异或到棋盘的目标位置。
		
		return value;
	}
	
	int size = 0;			//棋盘的大小,看作一维的数组
	int pieceNumber = 0;	//棋子的种类个数，每个棋子的代号从1到pieceNumber,空格用0表示
}
