package core.astar;

import java.util.ArrayList;
import java.util.HashMap;

import core.problem.State;
//记住每个扩展节点的集合
//The set that remembers every expanded node
public class Explored {
	
//	public void insert(State state){
//		nodes.add(state);
//		//Fix me 可修改
//	}
//
//	public boolean contains(State state) {
//		return nodes.contains(state); //Fix me
//	}
//
//	ArrayList<State> nodes = new ArrayList<>();
//	//Data Structures for Explored, implement it yourself.



    HashMap<Integer,State> hashMap=new HashMap<Integer,State>();

    public void insert(State state){
        hashMap.put(state.hashCode(),state);
        //Fix me
    }

    public boolean contains(State state) {
        return hashMap.get(state.hashCode())!=null;
    }

    //Data Structures for Explored, implement it yourself.



}
