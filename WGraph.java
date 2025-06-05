import java.io.*;
import java.util.*;

class Edge{
    
    public int[] nodes = new int[2]; /*The nodes connected by the edge*/
    public Integer weight; /*Integer so we can use Comparator*/
    
    Edge(int i, int j, int w){
        this.nodes[0] = i;
        this.nodes[1] = j;
        this.weight = w;
    }

    @Override
    public String toString() {
        return String.format("Edge(%s,%s,%s)",this.nodes[0],this.nodes[0],this.weight);
    }

}

public class WGraph{

    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private ArrayList<Integer> nodes = new ArrayList<Integer>();
    private int nb_nodes = 0;
    private Integer source = 0;
    private Integer destination =0;

    WGraph() {
    }
    
    WGraph(WGraph graph) {
    	for(Edge e:graph.edges){
    		this.addEdge(new Edge(e.nodes[0],e.nodes[1],e.weight));
    	}
    	this.source = graph.source;
    	this.destination = graph.destination;
    }
    
    public void addEdge(Edge e) throws RuntimeException{
        /*Ensures that it is a new edge if both nodes already in the graph*/
        int n1 = e.nodes[0];
        int n2 = e.nodes[1];
        if (this.nodes.indexOf(n1) >= 0 && this.nodes.indexOf(n2) >= 0){
            for (int z = 0; z < this.edges.size(); z++){
                int[] n = this.edges.get(z).nodes;
                if ((n1 == n[0] && n2 == n[1])){
                    throw new RuntimeException("The edge (" + n1 + ", " + n2 + ") already exists");
                }
            }
        }

        /*Update nb_nodes if necessary*/
        if (this.nodes.indexOf(n1) == -1){
            this.nodes.add(n1);
            this.nb_nodes += 1;
        }
        if (this.nodes.indexOf(n2) == -1){
            this.nodes.add(n2);
            this.nb_nodes += 1;
        }

        this.edges.add(e);
    }
    
    public Edge getEdge(Integer node1, Integer node2){    	
    	for(Edge e:edges){
    		if(e.nodes[0]==node1 && e.nodes[1]==node2){
    			return e;
    		}
    	}
    	return null;
    }
    public void setSource(int source){
        this.source = source;
    }
    
    public void setDestination(int destination){
        this.destination = destination;
    }
    
    public int getSource(){
    	return this.source;
    }
    
    public int getDestination(){
    	return this.destination;
    }
    
    public void setEdge(Integer node1, Integer node2, int weight){
    	for(Edge e:edges){
    		if(e.nodes[0]==node1 && e.nodes[1]==node2){
    			e.weight=weight;
    		}
    	}
    }

    public ArrayList<Edge> listOfEdgesSorted(){
        ArrayList<Edge> edges = new ArrayList<Edge>(this.edges);
        Collections.sort(edges, new Comparator<Edge>() {
            public int compare(Edge  e1, Edge  e2) 
            {   
                return  e2.weight.compareTo(e1.weight);
            }   
        }); 
        return edges;
    }

    public ArrayList<Edge> getEdges(){
        return this.edges;
    }

    public int getNbNodes(){
        return this.nb_nodes;
    }

    public String toString(){
    	String out = Integer.toString(this.source)+ " " + Integer.toString(this.destination)+"\n";
        out += Integer.toString(this.nb_nodes);
        for (int i = 0; i < this.edges.size(); i++){
            Edge e = edges.get(i);
            out += "\n" + e.nodes[0] + " " + e.nodes[1] + " " + e.weight;
        }
        return out;
    }
}
