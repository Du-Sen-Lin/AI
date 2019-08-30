package g07.problem.npuzzle;


import core.problem.Action;

public class NpuzzleAction extends Action {


    //每一步的Action
    private  int dir;
    private static int count=1;

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        NpuzzleAction.count = count;
    }

    public NpuzzleAction(int dir){
        this.dir=dir;
    }

    @Override
    public void  draw(){
        switch (dir) {
            case 1:
                System.out.println("the  " + (count++) + " steps! direction: UP");
                break;
            case 2:
                System.out.println("the  " + (count++) +" steps! direction: DOWN");
                break;
            case 3:
                System.out.println("the " + (count++) +" steps! direction: LEFT");
                break;
            case 4:
                System.out.println("the " + (count++) +" steps! direction: RIGHT");
                break;
            default:
                break;

        }
    }

}
