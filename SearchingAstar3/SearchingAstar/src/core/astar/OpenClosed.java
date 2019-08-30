package core.astar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import core.problem.State;

public class OpenClosed {
    private Comparator<Node> comparator = new Comparator<Node>(){
        @Override
        public int compare(Node s1, Node s2) {
            return s1.compareTo(s2);
        }
    };

    PriorityQueue<Node> nodes = new PriorityQueue<Node>(comparator);
    HashMap<Integer, Integer> hashMap=new HashMap<Integer, Integer>();
    
    public Node pop() {
        Node ans=nodes.poll();
        //hashMap.remove(ans.getState().hashCode());
        return ans;
    }

    public void insert(Node node){
        nodes.add(node);
        hashMap.put(node.getState().hashCode(), node.getPathCost());
    }

    public boolean contains(State state) {
        return hashMap.get(state.hashCode())!=null;
    }

    public int revisited(State state) {
        return hashMap.get(state.hashCode()); //Fix me
    }

    public boolean isEmpty() {
        return nodes.isEmpty(); //Fix me
    }

}
