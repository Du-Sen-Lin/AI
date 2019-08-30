package g07.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;

import java.security.SecureRandom;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class NpuzzleProblem extends Problem {

    private  int size; //size*size
    private static int[][] Zobrist;  //棋盘每个位置对应的随机数
    //构造函数
    public NpuzzleProblem(int size,NpuzzleState init,NpuzzleState goal){
        //super(new NpuzzleState(size,status).setStatus(status),new NpuzzleState(size,goal).setStatus(goal));
        super(init,goal);
        this.size = size;
        SecureRandom rand = new SecureRandom();
        Zobrist = new int[size*size][];
        for(int i=0;i<size*size;i++){
            Zobrist[i]= new int[size*size];
            for(int j=0;j<size*size;j++){
                Zobrist[i][j]=rand.nextInt();
            }
        }
    }

    public static int[][] getZobrist() {
        return Zobrist;
    }

    public static void setZobrist(int[][] zobrist) {
        Zobrist = zobrist;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
    @Override
    public boolean solvable() {
        // TODO Auto-generated method stub
        int num=0;
        int[] c = new int[size*size];
        int[] ss=new int[size*size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                c[num++]=((NpuzzleState)getInitialState()).getStates()[i][j];
            }
        }

        for(int i=0;i<size*size;i++){
            if(c[i]==0){
                flag=i;
            }
        }
        for(int i=0;i<flag;i++){
            ss[i]=c[i];
        }
        for(int i=flag;i<size*size-1;i++){
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

    /**
     *
     * @param parent
     * @param action
     * @return
     */
    //从父级到操作的结果状态。
    //The resulting state from parent through action.
    //getEmptyX() EmptyY() 0的 x,y 坐标  在State里面写构造函数
    //getBoard() [][] x,y坐标上的值是什么

    @Override
    public  State result(State parent, Action action){
        //
        NpuzzleState tmp = new NpuzzleState ((NpuzzleState) parent);
        NpuzzleAction tmpaction =  (NpuzzleAction) action;
        switch (tmpaction.getDir()){
            case 1:
                //up
                if(tmp.getZeroX()==size-1) return null;
                else {
                 //   System.out.println("UP");
                    tmp.AddG(1);
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()] = tmp.getStates()[tmp.getZeroX() + 1][tmp.getZeroY()];
                    tmp.setZeroX(tmp.getZeroX() + 1); //0
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()]=0;
                    return tmp;
                }
            case 2:
                //down
                if(tmp.getZeroX()==0) return null;
                else{
                  //  System.out.println("DOWN");
                    tmp.AddG(1);
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()] = tmp.getStates()[tmp.getZeroX() - 1][tmp.getZeroY()];
                    tmp.setZeroX(tmp.getZeroX() - 1); //0
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()]=0;
                    return tmp;
                }
            case 3:
                //LEFT
                if(tmp.getZeroY()==size-1) return null;
                else{
                 //   System.out.println("LEFT");
                    tmp.AddG(1);
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()] = tmp.getStates()[tmp.getZeroX() ][tmp.getZeroY()+1];
                    tmp.setZeroY(tmp.getZeroY() + 1); //0
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()]=0;
                    return tmp;
                }
            case 4:
                //RIGHT
                if(tmp.getZeroY()==0) return null;
                else{
                //    System.out.println("RIGHT");
                    tmp.AddG(1);
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()] = tmp.getStates()[tmp.getZeroX() ][tmp.getZeroY()-1];
                    tmp.setZeroY(tmp.getZeroY() - 1); //0
                    tmp.getStates()[tmp.getZeroX()][tmp.getZeroY()]=0;
                    return tmp;
                }
        }
        return  null;
    }

    //从父级到后续任务的路径成本
    //The cost of the path from parent through action to its successors.
    @Override
    public int stepCost(State parent,Action action){
        return 1;
    }

    //启发式的heuristic
    //估计从状态到目标状态最便宜路径的成本
    //estimated cost of the cheapest path from the state to a goal state
    @Override
    public  int heuristic(State state){

        NpuzzleState s = (NpuzzleState) state;
        //System.out.println("h:" + s.heuristic());
        return s.heuristic();
    }

    //当前状态下的所有可能操作。
    //all the possible actions from current state.
    @Override
    public ArrayList<Action> Actions(State state){
        ArrayList<Action> list = new ArrayList<Action>();
        NpuzzleState temp=null;

        for(int i=1;i<=4;i++){
            temp = (NpuzzleState) result(state, (Action) new NpuzzleAction(i));
            if(temp!=null){
                list.add((Action) new NpuzzleAction(i));
            }
        }
        return list;
    }

    //描画出该问题的World State，最好能对细节进行渲染
    @Override
    public  void drawWorld(){
        this.getInitialState().draw();
    }

    //描画从parent状态经Action的变化过程
    public void simulateResult(State parent,Action action){
        State child = result(parent, action);
        action.draw();
        child.draw();
    }

}
