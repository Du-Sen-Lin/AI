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
//		//��ȡn�������������ʵ��
//		ArrayList<Problem> problems = ProblemFactory.getProblems(1);
//		test2(problems);

		//��ȡ������ľ�����������ʵ��
		/**
		 * ������ľ�����⣺
		 * 30�� ��� 100 ������  ���ۣ�987
		 * 29�� ��� 70 ������  ���ۣ�925
		 * 28�� ��� 50 ������	 ���ۣ�865
		 * 27�� ��� 50 ������  ���ۣ�807
		 * 26�� ��� 27 ������  ���ۣ�751
		 * 12    		177    0.303s
		 * 13			205	   0.731s
		 * 14			235	   0.235s
		 * 15  			267	   1.567s
		 *
		 */
		ArrayList<Problem> problems2 = ProblemFactory.getProblems(2);
		test(problems2);

		
	}
	
	//����ĳ�����������ʵ��
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
			
			System.out.printf("ִ���� %.3f ��\n", time1);
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
			tmp = state.getStatus();   //tmpΪ��ʼ״̬
			System.out.println();
			xx = new int[20];
			yy = new int[20];
			init = new int[20];
//			for (int i = 0; i < side * side; i++) {
//				if (tmp[i] == 0) pos = i;
//				else {
//					xx[tmp[i]] = (i / side); //��ʼ״̬�ģ�x,y)����
//					yy[tmp[i]] = (i % side);
//				}
//			}
//			if (!check(tmp)) System.out.println("No"); //�Ƿ��ǿ��н�
//			mat = new int[20];
//			for (int i = 0; i < side * side; i++) mat[i] = i + 1;  //Ŀ��״̬
//			mat[side * side - 1] = 0;
//			pos = side * side - 1;

			//�ӳ�ʼ״̬�ҽ���״̬ tmpΪ��ʼ״̬  mat Ŀ��״̬			init
			for (int i = 0; i < side * side; i++) {
				init[i]=tmp[i];
				if (tmp[i] == 0) pos = i;
				else {
					xx[tmp[i]] = (i / side); //��ʼ״̬�ģ�x,y)����
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
			if (!check(tmp)) System.out.println("No"); //�Ƿ��ǿ��н�
			mat = new int[20];
			for (int i = 0; i < side * side; i++) mat[i] = i + 1;  //Ŀ��״̬
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
			System.out.printf("������ %s ����ִ���� %.3f ��\n", Rstep, time1);
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
		if(x==0&y==1) return false;  //�����  1 1 Ϊ1
		if(x==2&&y==3) return false;
		return true;
	}

	public static int dfs(int step, int h, int las) {
		if (step + h > bound) return step + h; // ��Ŀ�꿪ʼ�� f(n)�տ�ʼ��С ����и��������� f(n) ��������
//		if(layer[h]==-1)layer[h]=step;
//		if(step>layer[h]+15){
//			System.out.printf("enter\n");
//			return step+h;
//		}
		if (h == 0) { // ��������״̬ ���g(n)����
			//		System.out.println(step);
			flg = 1;
			Rstep = step;
			return step;
		}
		int pos1 = pos;
		int ret = 127, x = pos1 / side, y = pos1 % side;
		int dx, dy, tar, ht, temp, i;
		for (i = 0; i < 4; i++) { //�ĸ�������չ
			dx = x + u[i];
			dy = y + p[i];
			if (dx < 0 || dy < 0 || dx > side - 1 || dy > side - 1 || !ok(i, las)) continue;
			tar = (dx * side) + dy; //�������չ�����½ڵ��һά���� 2,2 2*4+2= 10
			tmp[pos1] = tmp[tar]; // 0�����������չ�����ĵ������ a.mat[10]=11;
			tmp[tar] = 0;//�൱��swap()
			pos = tar;
			//�����һ������������
			//�׶���  ʹ�ò��ཻģʽ���ݿ�õ��������� htֵΪ��Ϊ�Ŀ��h�ĺͼ�����
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
			writename.createNewFile();  //�����Ǵ���һ���µ��ļ�������������Ҫ�½�
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
