package core.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import core.problem.State;

//任何给定点上可用于扩展的所有叶节点集
//The set of all leaf nodes available for expansion at any given point
public class Fringe {
//	//从Fringe中取出耗散值最小的节点
//	public Node pop() {
//
//		Node min = nodes.get(0);
//		for (Node node : nodes)	{
//			if (min.compareTo(node) > 0) min = node;
//		}
//
//		nodes.remove(min);
//		return min; //Fix me
//	}
//
//	//将一个节点插入到Fringe中
//	public void insert(Node node){
//		//Fix me
//		nodes.add(node);
//	}
//
//	//判断Finge中是否存在状态为state的节点
//	public boolean contains(State state) {
//		for (Node node : nodes)	{
//			if (node.getState().equals(state)) return true;
//		}
//		return false;
//	}
//
//	//返回Fringe中，其状态与state相同的节点；如果不存在，则返回null
//	public Node revisited(State state) {
//		for (Node node : nodes)	{
//			if (node.getState().equals(state)) return node;
//		}
//		return null; //Fix me
//	}
//
//	//Fringe是否为空
//	public boolean isEmpty() {
//		return nodes.isEmpty(); //Fix me
//	}
//
//	//用节点to代替Finge中的from节点
//	public void replace(Node from, Node to) {
//		nodes.remove(from);
//		nodes.add(to);
//		//Fix me
//	}
//	//ArrayList 动态数组
//	//Data Structures for Fringe, implement it yourself.
//	ArrayList<Node> nodes = new ArrayList<>();



    //重写比较器
    private Comparator<Node> comparator = new Comparator<Node>(){
        @Override
        public int compare(Node s1, Node s2) {
            return s1.compareTo(s2);
        }
    };

    PriorityQueue<Node> nodes = new PriorityQueue<Node>(comparator);
    HashMap<Integer, Node> hashMap=new HashMap<Integer, Node>();
    //从Fringe中取出耗散值最小的节点
    public Node pop() {
        Node ans=nodes.poll();
        hashMap.remove(ans.getState().hashCode());
        return ans;
    }

    //将一个节点插入到Fringe中
    public void insert(Node node){
        //Fix me
        nodes.add(node);
        hashMap.put(node.getState().hashCode(), node);
    }

    //判断Finge中是否存在状态为state的节点
    public boolean contains(State state) {
        return hashMap.get(state.hashCode())!=null;
    }

    //返回Fringe中，其状态与state相同的节点；如果不存在，则返回null
    public Node revisited(State state) {
        return hashMap.get(state.hashCode()); //Fix me
    }

    //Fringe是否为空
    public boolean isEmpty() {
        return nodes.isEmpty(); //Fix me
    }

    //用节点to代替Finge中的from节点
    public void replace(Node from, Node to) {
        hashMap.put(from.getState().hashCode(),to);
        nodes.add(to);
        //Fix me
    }


}
