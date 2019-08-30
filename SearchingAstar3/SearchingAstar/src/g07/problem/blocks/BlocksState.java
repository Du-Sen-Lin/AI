package g07.problem.blocks;

import core.astar.AStar;
import core.astar.Node;
import core.problem.State;
import core.runner.SearchRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class BlocksState extends State{
    private int size;//单色木块数
    private int status[]=new int[size*2+1];	//当前格局 -1代表白 1代表黑
    private int emptyPos;//空格的位置
    private int zobrist;

	public BlocksState(int size, int[] status,int zob) {
		super();
		this.size = size;
		this.status = status;
		setEmptyPos();
		setZobrist(zob);
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
            if(this.status[i] == 2)
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


//    public  char[][] bbb = new char[1000][35];
//	public  int num=0;
    @Override
	public void draw() {
	   // num++;
//    	 for (int i = 0;i <=this.size*2;i++){
//             if (this.status[i] == 2)
//                 System.out.print("W");
//             else if (this.status[i] == 1)
//                 System.out.print("B");
//             else
//                 System.out.print("E");
//         }
//        for (int i = 0;i <=this.size*2;i++){
//            if (this.status[i] == 2)
//             bbb[num][i]='W';
//
//            else if (this.status[i] == 1)
//                bbb[num][i]='B';
//            else
//                bbb[num][i]='E';
//        }

	}




//		try {
//        File writename = new File("log.txt");
//        writename.createNewFile();  //这里是创建一个新的文件，如已有则不需要新建
//        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//        if(side==4){
//            out.write('4');
//        }
//        if(side==3){
//            out.write('3');
//        }
//        out.write("\n");
//        for (int i = 0; i <side*side; ++i) {
//            out.write(init[i]+" ");
//            //	out.write(j + "\n");
//            //	out.flush();
//        }
//        out.write("\n");
//
//        for(int i=0;i<=Rstep-1;++i){
//            //	System.out.println(i+1);
//            for(int j=0;j<side*side;++j){
//                out.write(result[i][j]+" ");
//            }
//            out.write("\n");
//        }
//        out.flush();
//        out.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}
