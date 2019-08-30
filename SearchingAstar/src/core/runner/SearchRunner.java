package core.runner;

import java.util.ArrayList;

import core.astar.AStar;
import core.astar.Node;
import core.problem.Problem;
import core.uitlity.Stopwatch;
import g07.problem.npuzzle.PuzzleState;

import static java.lang.Math.abs;


public class SearchRunner {

	public static int bound, flg;
	public static int[] u = {0, 0, 1, -1};
	public static int[] p = {1, -1, 0, 0};
	public static int[][] result;
	public static int Rstep;
	public static int side;
	public static int[] xx;
	public static int[] yy;
	public static int[] tmp;

	public static int[] mat;
	public static int pos;
	public static int layer[]=new int[100];

	public SearchRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("This is a test..");
		//获取n数码问题的所有实例
//		ArrayList<Problem> problems = ProblemFactory.getProblems(1);
//		test2(problems);
		
		//获取滑动积木块问题的所有实例
		ArrayList<Problem> problems2 = ProblemFactory.getProblems(2);
		test(problems2);
//
	}
	
	//测试某个问题的所有实例
	public static void test(ArrayList<Problem> problems) {
		double time1 = 0;
		for (Problem problem : problems) {
			Stopwatch timer1 = new Stopwatch();
			if (!problem.solvable()) {
				System.out.println("No Solutions.");
			}
			else {
				AStar a = new AStar(problem);
				Node node = a.Search();
				if(node!=null)	System.out.println(node.getPathCost());
				time1 = timer1.elapsedTime();
				if (node != null) a.solution(node);
			}
			
			System.out.printf("执行了 %.3f 秒\n", time1);
			System.out.println("-------------------------------------------------");
		}
	}


	public static void test2(ArrayList<Problem> problems) {


		for (Problem problem : problems) {

			Rstep=0;
			flg=0;
			result = new int[200][20];
			PuzzleState state = (PuzzleState) problem.getInitialState();
			side = state.getSide();
			tmp = state.getStatus();
			System.out.println();
			xx = new int[20];
			yy = new int[20];
			for (int i = 0; i < side * side; i++) {
				if (tmp[i] == 0) pos = i;
				else {
					xx[tmp[i]] = (i / side);
					yy[tmp[i]] = (i % side);
				}
			}

			if (!check(tmp)) {System.out.println("No"); continue;}
			mat = new int[20];
			for (int i = 0; i < side * side; i++) mat[i] = i + 1;
			mat[side * side - 1] = 0;
			pos = side * side - 1;

			double time1 = 0;
			Stopwatch timer1 = new Stopwatch();
//			for(int i=0;i<100;i++){
//				layer[i]=-1;
//			}
			//System.out.println(H(mat));
			for (bound = H(mat); bound <= 100; bound = dfs(0, H(mat), 4))
				if (flg == 1) break;
			time1 = timer1.elapsedTime();
			Ans_show();
			System.out.printf("行走了 %s 步，执行了 %.3f 秒\n", Rstep, time1);
		}

	}

	public static boolean check(int[] a) {
		int tot=0;
		int flag=0;
		for(int i=0;i<side*side;i++){
			if(a[i]==0) {
				flag = i/side;
				continue;
			}
			for(int j=0;j<i;j++){
				if(a[j]>a[i]){
					tot++;
				}
			}
		}
		if(side%2==1) return tot%2==0;
		else{
			tot+= abs(flag-(side-1));
			if(tot%2==0) return true;
			else return false;
		}
	}

	public static int H(int[] mat) {
		int h = 0;
		for (int i = 0; i < side*side-1; i++) {
			h += abs(xx[mat[i]] - (i / side)) + abs(yy[mat[i]] - (i % side));
		}
		return h;
	}

	public static boolean ok(int x,int y){
		if(x>y){
			int ff=x;
			x=y;
			y=ff;
		}
		if(x==0&y==1) return false;  //与操作  1 1 为1
		if(x==2&&y==3) return false;
		return true;
	}

	public static int dfs(int step, int h, int las) {
		//g(n)+h(n)=f(n) 更新f(n) bound 最大限度  采用估值函数，剪掉f(n)大于depth的路径
		if (step + h > bound) return step + h; //
//		if(layer[h]==-1)layer[h]=step;
//		if(step>layer[h]+15){
//			System.out.printf("enter\n");
//			return step+h;
//		}
		if (h == 0) { // 到达最终状态 输出g(n)即可
			//		System.out.println(step);
			flg = 1;
			Rstep = step;
			return step;
		}
		int pos1 = pos;
		int ret = 127, x = pos1 / side, y = pos1 % side;
		int dx, dy, tar, ht, temp, i;
		for (i = 0; i < 4; i++) { //四个方向扩展
			dx = x + u[i];
			dy = y + p[i];
			if (dx < 0 || dy < 0 || dx > side - 1 || dy > side - 1 || !ok(i, las)) continue;
			tar = (dx * side) + dy; //计算出扩展出的新节点的一维坐标 2,2 2*4+2= 10
			mat[pos1] = mat[tar]; // 0的坐标等于扩展出来的点的坐标 a.mat[10]=11;
			mat[tar] = 0;//相当于swap()
			pos = tar;
			//计算新的h值
        //阶段三主要在这修改 用不相交模式数据库 h(n) 为当前节点的各点的曼哈顿距离和。
			ht = h - (Math.abs(xx[mat[pos1]] - dx) + Math.abs(yy[mat[pos1]] - dy))  + Math.abs(xx[mat[pos1]] - x) + Math.abs(yy[mat[pos1]] - y);
			if (step + ht <= bound)
				for (int k = 0; k < side*side; k++)
					result[step][k] = mat[k];
			temp = dfs(step + 1, ht, i);
			if (flg == 1)  return temp;
			if (ret > temp) ret = temp;
			mat[tar] = mat[pos1];
			mat[pos1] = 0;
			pos = pos1;
		}
		return ret;
	}

	public static void Ans_show()
	{
		int i;
		//	System.out.println(Rstep);
		for( i=Rstep-1;i>=0;i--){
			System.out.println(Rstep -i- 1);
			for(int j=0;j<side*side;j++){
				if(j % side == 0) Show();
				System.out.print("| ");
				if(result[i][j] > 9) System.out.print(result[i][j] + "  ");
				else System.out.print(" " + result[i][j]+"  ");
				if(j % side == side - 1) System.out.print("|\n");
			}Show();
		}
		System.out.println(Rstep);
		for(int j=0;j<side*side;j++){
			int t;

			t = (j + 1) % (side * side);
			if(j % side == 0) Show();
			System.out.print("| ");
			if(t > 9) System.out.print(t + "  ");
			else System.out.print(" " + t +"  ");
			if(j % side == side - 1) System.out.print("|\n");
		}Show();
	}

	public static void Show()
	{
		System.out.print("+");
		for(int qqq = 0; qqq < side; qqq ++){
			System.out.print("-----+");
		}
		System.out.print("\n");
	}











}
