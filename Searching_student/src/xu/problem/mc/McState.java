package xu.problem.mc;

import core.problem.State;;
//Ұ���봫��ʿ�����״̬
public class McState extends State {

	//��һ����Ԫ��(m, c, b)����ʾ���ϵ�״̬
	//����m��c�ֱ�������ϴ���ʿ��Ұ�˵���Ŀ
	//b=LEFT��ʾ�����󰶣�RIGHT��ʾ������	
	public McState(int m, int c, int b) {
		super();
		this.m = m;
		this.c = c;
		this.b = b;
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
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}

	private int m;	
	private int c;
	private int b;
	
	private int size;
	
	public int getSize() {
		return size;
	}

	public McState setSize(int size) {
		this.size = size;
		return this;
	}

	@Override
	public void draw() {
//		// TODO Auto-generated method stub
//		int m = this.m, c = this.c;
//		
//		if (b == 0) {
//			m = size - m;
//			c = size - c;
//		}	
		
		int mr = size - m;
		int cr = size - c;
		int br = 1 - b;
		
		System.out.print("(" + m + ", " + c + ", " + b + ")");	
		System.out.print("~~~~~~~~~~~~");
		System.out.print("(" + mr + ", " + cr + ", " + br + ")");	
		System.out.println();
	}
	
	public int heuristic()
	{
		return m + c - 2 * b;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return m * 100 + c * 10 + b;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	} 
}
