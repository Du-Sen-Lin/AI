package core.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import core.problem.State;

//The set of all leaf nodes available for expansion at any given point
public class Fringe {

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

    public void insert(Node node){
        //Fix me
        nodes.add(node);
        hashMap.put(node.getState().hashCode(), node);
    }

    public boolean contains(State state) {
        return hashMap.get(state.hashCode())!=null;
    }

	//返回Fringe中，其状态与state相同的节点；如果不存在，则返回null
    public Node revisited(State state) {
        return hashMap.get(state.hashCode()); //Fix me
    }

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
