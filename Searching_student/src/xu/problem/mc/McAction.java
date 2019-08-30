package xu.problem.mc;

import core.problem.Action;

public class McAction extends Action {
	
	public McAction(int m, int c, int d) {
		super();
		this.m = m;
		this.c = c;
		this.d = d;
	}
	//�ƶ�����
	public int cost(){
		return 1;
	}
	
	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	private int m; //���ϴ���ʿ������
	private int c; //����Ұ�˵�����
	private int d; //���еķ���, 1��ʾ���󰶻����Ұ���0��ʾ���Ұ�������
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		String s = (d == 1? "-->" : "<--");
		System.out.print("         ");
		System.out.println(s + "[" + m + ", " + c + "]" + s);	
	}
	
}