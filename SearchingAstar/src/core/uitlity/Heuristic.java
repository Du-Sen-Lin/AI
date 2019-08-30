package core.uitlity;

import java.security.SecureRandom;

public class Heuristic {
	private long[] heuristic;
	private int size;
	public Heuristic(int size) {
		// TODO Auto-generated constructor stub
		heuristic=new long[size];
		long ans=1;
		for (int i = 0; i < size; i++) {
			heuristic[i] = ans;
			ans*=3;
			//System.out.print("  "+heuristic[i]);
		}
		//System.out.println("");
		this.size = size;
	}
	
	public long cal(int i,long num) {
		//System.out.println(num+" "+i);
		long res=num*heuristic[i];
		return res;
	}
}
