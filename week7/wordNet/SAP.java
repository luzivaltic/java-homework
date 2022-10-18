import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private Digraph curGraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        this.curGraph = new Digraph(G);
    }
    
    private int[] singleSourceAncestor(int v , int w) {
        int[] ances = new int[2];      
        ances[0] = Integer.MAX_VALUE;
        ances[1] = -1;
        
        BreadthFirstDirectedPaths BFS_v = new BreadthFirstDirectedPaths(curGraph, v);
        BreadthFirstDirectedPaths BFS_w = new BreadthFirstDirectedPaths(curGraph, w);
        
        for(int i = 0 ;i < curGraph.V() ; i++){
            if( BFS_v.hasPathTo(i) && BFS_w.hasPathTo(i) ){
                int len = BFS_v.distTo(i) + BFS_w.distTo(i);       
                if( len < ances[0] ){
                    ances[0] = len;
                    ances[1] = i;
                }
            }
        }
        if( ances[0] == Integer.MAX_VALUE ){
            ances[0] = -1;
        }
        return ances;
    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        return singleSourceAncestor(v, w)[0];
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return singleSourceAncestor(v, w)[1];
    }

    private int[] multipleSourceAncestor(Iterable<Integer>v , Iterable<Integer> w) {
        int[] ances = new int[2];      
        ances[0] = Integer.MAX_VALUE;
        ances[1] = -1;

        if( v == null || w == null ){
            throw new IllegalArgumentException();
        }
        if( !v.iterator().hasNext() || !w.iterator().hasNext() ) {
            ances[0] = -1;
            return ances;
        }
        
        BreadthFirstDirectedPaths BFS_v = new BreadthFirstDirectedPaths(curGraph, v);
        BreadthFirstDirectedPaths BFS_w = new BreadthFirstDirectedPaths(curGraph, w);
        
        for(int i = 0 ;i < curGraph.V() ; i++){
            if( BFS_v.hasPathTo(i) && BFS_w.hasPathTo(i) ){
                int len = BFS_v.distTo(i) + BFS_w.distTo(i);       
                if( len < ances[0] ){
                    ances[0] = len;
                    ances[1] = i;
                }
            }
        }

        return ances;
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return multipleSourceAncestor(v, w)[0];   
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        return multipleSourceAncestor(v, w)[1];
    }
 
    // do unit testing of this class
    public static void main(String[] args){

    }
}