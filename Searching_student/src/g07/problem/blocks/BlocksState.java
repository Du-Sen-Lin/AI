package g07.problem.blocks;

import core.problem.State;

public class BlocksState extends State {
    private int size;//单色木块数
    private int status[]=new int[size*2+1];	//当前格局 -1代表白 1代表黑
    private int emptyPos;//空格的位置
    private int zobrist;
    
    public BlocksState(int size, int status[], int[][] zobrist) {
		super();
		this.size = size;
		this.status = status;
		setEmptyPos();
		setZobrist(zobrist);
	}
    
	public BlocksState(int size, int status[], int zobrist) {
		super();
		this.size = size;
		this.status = status;
		setEmptyPos();
		setZobrist(zobrist);
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
    public int[] getStatus() {
        return this.status;
    }
    public void setStatus(int status[]){
        this.status = status;
    }
    
    public void setEmptyPos() {
        for(int i=0; i<=2*size; i++) {
            if (this.status[i] == 0){///0是空格
                emptyPos = i;
                break;
            }
        }
    }
    
    public int getEmptyPos() {
    	return this.emptyPos;
    }
    
    public void setZobrist(int[][] zobrist) {
        ///计算hash
    	int zob = 0;
        for(int i=0; i<=2*size; i++) {

            if(status[i] == -1)
                zob ^= zobrist[1][i];
            if(status[i] == 1)
                zob ^= zobrist[0][i];
        }
        this.zobrist=zob;
    }
    
    public void setZobrist(int zobrist) {
        this.zobrist=zobrist;
    }
    public int getZobrist() {
    	return this.zobrist;
    }
    
    //评估函数的值是当前格局中每个白块前面黑块数目之和
	public int heuristic()
	{
        int ans = 0;
        int hn = 0;
        for (int i = 0;i < this.status.length;i++){
            if(this.status[i] == 1)
                ans++;
            if(this.status[i] == -1)
                hn += ans;
        }
        return hn;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.zobrist;
	}


    
    @Override
	public void draw() {
    	 for (int i = 0;i <=this.size*2;i++){
             if (this.status[i] == -1)
                 System.out.print("白");
             else if (this.status[i] == 1)
                 System.out.print("黑");
             else
                 System.out.print("空");
         }
	}
}
