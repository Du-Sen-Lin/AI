package g07.problem.npuzzle;

import core.problem.State;

import static java.lang.Math.abs;

public class NpuzzleState extends State {

    private int size;
    private int hash;
    private int[][] states;
    private int g=0; //目前的代价
    private int zeroX=0;
    private int zeroY=0;

    public void AddG(int g){
        this.g+=g;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public int[][] getStates() {
        return states;
    }

    public void setStates(int[][] states) {
        this.states = states;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getZeroX() {
        return zeroX;
    }

    public void setZeroX(int zeroX) {
        this.zeroX = zeroX;
    }

    public int getZeroY() {
        return zeroY;
    }

    public void setZeroY(int zeroY) {
        this.zeroY = zeroY;
    }

    public NpuzzleState(int[][] board){
        this.states=board;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]==0){
                    zeroX=i;
                    zeroY=j;
                }
            }
        }
    }

    //拷贝构造函数
    public NpuzzleState(NpuzzleState state){
        this.size=state.getSize();
        this.states=new int[size][];
        this.g=state.getG();
        this.zeroX=state.getZeroX();
        this.zeroY=state.getZeroY();
        for(int i=0;i<state.getSize();i++){
            this.states[i]=new int[size];
            for(int j=0;j<state.getSize();j++){
                this.states[i][j]=state.getStates()[i][j];
            }
        }

    }

    public NpuzzleState(int size,int[] board){
        this.size=size;
        this.states=new int[size][];
        for(int i=0;i<size;i++){
            this.states[i]=new int[size];
            for(int j=0;j<size;j++){
                this.states[i][j]=board[i*size+j];
                if(this.states[i][j]==0){
                    zeroX=i;
                    zeroY=j;
                }
            }
        }
    }


    //行列错位法 当前9个数字位（包含0）与目标节点对应数字位行列错位相差的距离和

    public int heuristic()
    {
        int ans=0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(states[i][j]==0){
                    ans += (size-(i+1)) + (size-(j+1));
                }
                else{
                    if(states[i][j]%size==0){
                        ans += abs(states[i][j]/size-(i+1)) + abs(size-(j+1));
                    }
                    else{
                        ans += abs(states[i][j]/size+1-(i+1)) + abs(states[i][j]%size-(j+1));
                    }
                }
            }
        }
//        for(int i=0;i<size;i++){
//            for(int j=0;j<size;j++){
//                if(states[i][j]!=0 ){
//                    ans+=(abs( (states[i][j] -1 )/size-i) + abs( states[i][j] - 1 )%size -j );
//                }
//            }
//        }
        return ans;
    }


    //绘图输出
    @Override
    public void draw(){
//        for(int i=0;i<9;i++){
//            System.out.print(status[i]+" ");
//            if((i+1)%3==0){
//                System.out.println();
//            }
//        }
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(states[i][j]!=0) {
                    System.out.print(states[i][j] + " ");
                }
                else{
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object obj){
        return this.hashCode() == obj.hashCode();
    }
    //Zobrist Hash
    @Override
    public int hashCode(){
        if(hash!=0) return hash;
        int h=0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                //System.out.println(j);
               // System.out.println(board[i][j]);
                if (states[i][j] != 0) {
                    h ^= NpuzzleProblem.getZobrist()[i * size + j][states[i][j]];
                }
            }
        }
        return  h;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
