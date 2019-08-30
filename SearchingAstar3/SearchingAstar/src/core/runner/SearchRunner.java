package core.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;

import core.astar.AStar;
import core.astar.Node;
import core.problem.Problem;
import core.uitlity.Stopwatch;
import g07.problem.blocks.BlocksState;
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
	public static int[] init;
	public static char[] dir={'u','d','l','r'};
	public static char[] redir=new char[100];
	public SearchRunner() {
		// TODO Auto-generated constructor stub
	}
    public static char[][] reb= new char[1000][35];

    public static char[][] getReb() {
        return reb;
    }

    public static void setReb(char[][] reb) {
        SearchRunner.reb = reb;
    }

    public static void main(String[] args) throws Exception{
		System.out.println("This is a test..");
//		//获取n数码问题的所有实例
//		ArrayList<Problem> problems = ProblemFactory.getProblems(1);
//		test2(problems);

		//获取滑动积木块问题的所有实例
		/**
		 * 滑动积木块问题：
		 * 30阶 大概 100 秒左右  代价：987
		 * 29阶 大概 70 秒左右  代价：925
		 * 28阶 大概 50 秒左右	 代价：865
		 * 27阶 大概 50 秒左右  代价：807
		 * 26阶 大概 27 秒左右  代价：751
		 * 12    		177    0.303s
		 * 13			205	   0.731s
		 * 14			235	   0.235s
		 * 15  			267	   1.567s
		 *
		 */
		ArrayList<Problem> problems2 = ProblemFactory.getProblems(2);
		test(problems2);

		
	}
	
	//测试某个问题的所有实例
	public static void test(ArrayList<Problem> problems) throws Exception{
   //     PrintStream out = new PrintStream("E:/University/AI/AIDraw/map/slide.txt");
    //   System.setOut(out);
		double time1 = 0;
		for (Problem problem : problems) {
			Stopwatch timer1 = new Stopwatch();
			if (!problem.solvable()) {
				System.out.println("No Solutions.");
			}
			else {

                    AStar a = new AStar(problem);
                    Node node = a.Search();
                    if(node!=null)	System.out.println(String.valueOf(node.getPathCost()));
                    time1 = timer1.elapsedTime();
                    if (node != null) a.solution(node);

			}
			
			System.out.printf("执行了 %.3f 秒\n", time1);
			System.out.println("-------------------------------------------------");
		}
	}

    public int[][] reblocks = new int[1000][30];


	public static void test2(ArrayList<Problem> problems) {


		for (Problem problem : problems) {

			Rstep=0;
			flg=0;
			result = new int[200][20];
			PuzzleState state = (PuzzleState) problem.getInitialState();
			side = state.getSide();
			tmp = state.getStatus();   //tmp为初始状态
			System.out.println();
			xx = new int[20];
			yy = new int[20];
			init = new int[20];
//			for (int i = 0; i < side * side; i++) {
//				if (tmp[i] == 0) pos = i;
//				else {
//					xx[tmp[i]] = (i / side); //初始状态的（x,y)坐标
//					yy[tmp[i]] = (i % side);
//				}
//			}
//			if (!check(tmp)) System.out.println("No"); //是否是可行解
//			mat = new int[20];
//			for (int i = 0; i < side * side; i++) mat[i] = i + 1;  //目标状态
//			mat[side * side - 1] = 0;
//			pos = side * side - 1;

			//从初始状态找结束状态 tmp为初始状态  mat 目标状态			init
			for (int i = 0; i < side * side; i++) {
				init[i]=tmp[i];
				if (tmp[i] == 0) pos = i;
				else {
					xx[tmp[i]] = (i / side); //初始状态的（x,y)坐标
					yy[tmp[i]] = (i % side);
				}
			}

//			for(int i=0;i<side*side;i++){
//				System.out.print(xx[i]+",");
//			}
//			System.out.println();
//			for(int i=0;i<side*side;i++){
//				System.out.print(yy[i]+",");
//			}
			if (!check(tmp)) System.out.println("No"); //是否是可行解
			mat = new int[20];
			for (int i = 0; i < side * side; i++) mat[i] = i + 1;  //目标状态
			mat[side * side - 1] = 0;
			//pos = side * side - 1;



			double time1 = 0;
			Stopwatch timer1 = new Stopwatch();
//			for(int i=0;i<100;i++){
//				layer[i]=-1;
//			}
			//System.out.println(H(mat));
			//	System.out.println(H());
			for (bound = H(); bound <= 100; bound = dfs(0, H(), 4))
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

	public static int H() {
		int h = 0;
		for (int i = 0; i < side*side-1; i++) {
			h += abs(xx[i] - (i / side)) + abs(yy[i] - (i % side));
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
		if (step + h > bound) return step + h; // 从目标开始找 f(n)刚开始最小 如果有更大的则更新 f(n) 反方向找
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
			tmp[pos1] = tmp[tar]; // 0的坐标等于扩展出来的点的坐标 a.mat[10]=11;
			tmp[tar] = 0;//相当于swap()
			pos = tar;
			//如果换一个评估函数呢
			//阶段三  使用不相交模式数据库得到评估函数 ht值为分为的块的h的和加起来
			//

			ht = getH(tmp);
			//ht = h - (Math.abs(xx[mat[pos1]] - dx) + Math.abs(yy[mat[pos1]] - dy))  + Math.abs(xx[mat[pos1]] - x) + Math.abs(yy[mat[pos1]] - y);
			if (step + ht <= bound) {
				for (int k = 0; k < side * side; k++) {
					result[step][k] = tmp[k];
				}
				//System.out.println(i);
				if(i==0){
					redir[step]='r';
				}
				else if(i==1){
					redir[step]='l';
				}
				else if(i==2){
					redir[step]='d';
				}
				else{
					redir[step]='u';
				}
			}
			temp = dfs(step + 1, ht, i);
			if (flg == 1)  return temp;
			if (ret > temp) ret = temp;
			tmp[tar] = tmp[pos1];
			tmp[pos1] = 0;
			pos = pos1;
		}
		return ret;
	}


	static final int[] tilePositions = {-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};
	static final int[] tileSubsets = {-1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
	public static int getH(int [] tmp) {

		int index0 = 0, index1 = 0, index2 = 0;
		for (int pos = 15; pos >= 0; --pos) {
			final int tile = tmp[pos];
			if (tile != 0) {
				final int subsetNumber = tileSubsets[tile];
				switch (subsetNumber) {
					case 2:
						index2 |= pos << (tilePositions[tile] << 2);
						break;
					case 1:
						index1 |= pos << (tilePositions[tile] << 2);
						break;
					default:
						index0 |= pos << (tilePositions[tile] << 2);
						break;
				}
			}
		}
		return PuzzleConfiguration.costTable_15_puzzle_0[index0] +
				PuzzleConfiguration.costTable_15_puzzle_1[index1] +
				PuzzleConfiguration.costTable_15_puzzle_2[index2];
	}



	public static void Ans_show() {
		try {
			File writename = new File("log.txt");
			writename.createNewFile();  //这里是创建一个新的文件，如已有则不需要新建
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			if(side==4){
				out.write('4');
			}
			if(side==3){
				out.write('3');
			}
			out.write("\n");
			for (int i = 0; i <side*side; ++i) {
				out.write(init[i]+" ");
			}
			out.write("\n");

			for(int i=0;i<=Rstep-1;++i){
				//	System.out.println(i+1);
				for(int j=0;j<side*side;++j){
					out.write(result[i][j]+" ");
				}
				out.write("\n");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


//	public static void Ans_show()
//	{
//		//tmp[]
//		System.out.println("+-----+-----+-----+-----+");
//		for(int i = 0; i < 4; i ++){
//			System.out.print("|");
//			for(int j = 0; j < 4; j ++){
//				if(init[4 * i + j] > 9){
//					System.out.print(" ");
//				}
//				else{
//					System.out.print("  ");
//				}
//				System.out.print(init[4 * i + j]);
//				System.out.print("  |");
//			}
//			System.out.println("\n+-----+-----+-----+-----+");
//		}
//		int i;
//		//	System.out.println(Rstep);
//		for( i=0;i<=Rstep-1;i++){
//			System.out.println(i+1);
//			System.out.println(redir[i]);
//			for(int j=0;j<side*side;j++){
//				if(j % side == 0) Show();
//				System.out.print("| ");
//				if(result[i][j] > 9) System.out.print(result[i][j] + "  ");
//				else System.out.print(" " + result[i][j]+"  ");
//				if(j % side == side - 1) System.out.print("|\n");
//			}Show();
//		}
//	}
//
//
//	public static void Show()
//	{
//		System.out.print("+");
//		for(int qqq = 0; qqq < side; qqq ++){
//			System.out.print("-----+");
//		}
//		System.out.print("\n");
//	}











}
