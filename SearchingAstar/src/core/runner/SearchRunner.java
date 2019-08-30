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
		//��ȡn�������������ʵ��
//		ArrayList<Problem> problems = ProblemFactory.getProblems(1);
//		test2(problems);
		
		//��ȡ������ľ�����������ʵ��
		ArrayList<Problem> problems2 = ProblemFactory.getProblems(2);
		test(problems2);
//
	}
	
	//����ĳ�����������ʵ��
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
			
			System.out.printf("ִ���� %.3f ��\n", time1);
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
		if(x==0&y==1) return false;  //�����  1 1 Ϊ1
		if(x==2&&y==3) return false;
		return true;
	}

	public static int dfs(int step, int h, int las) {
		//g(n)+h(n)=f(n) ����f(n) bound ����޶�  ���ù�ֵ����������f(n)����depth��·��
		if (step + h > bound) return step + h; //
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
			mat[pos1] = mat[tar]; // 0�����������չ�����ĵ������ a.mat[10]=11;
			mat[tar] = 0;//�൱��swap()
			pos = tar;
			//�����µ�hֵ
        //�׶�����Ҫ�����޸� �ò��ཻģʽ���ݿ� h(n) Ϊ��ǰ�ڵ�ĸ���������پ���͡�
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
