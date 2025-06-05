import java.sql.Array;
import java.util.*;
import java.io.File;

public class FordFulkerson {

	// return each node in dfs path order
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/

		// get sll edges
		int nodes = graph.getNbNodes();

		// avoid extra dfs
		boolean[] visited = new boolean[nodes];

		// initial point to branch from
		visited[source] = true;

		// list of edges
		ArrayList<Edge> edges = graph.getEdges();

		// build adj matrix from edges
		int[][] adjMatrix = new int[nodes][nodes];
		for(Edge e : graph.getEdges()){
			adjMatrix[e.nodes[0]][e.nodes[1]] = e.weight; // capacity of 
			// edge stored in adjacency matrix
		}

		// returns true when a opath to destination was found
		if(recurDFS(source, destination, adjMatrix, visited, path)){
			return path;
		}
		
		path.clear();
		// else empty path
		return path;
	}

	public static boolean recurDFS(Integer node, Integer destination, int[][] matrix, boolean[] arr, ArrayList<Integer> path){
		// mark as visited
		arr[node] = true;
		// add to path
		path.add(node);

		// found path
		if(node.equals(destination)){
			return true;
		}

		
		ArrayList<Integer> neighbors = new ArrayList<>();
		// loop over matrix
		for(int i = 0; i<matrix.length; i++){
			// found a valid edge from current node
			// (capacity>0)
			if(!arr[i] && matrix[node][i] > 0){
				neighbors.add(i);
				// recurse
				//if(recurDFS(i, destination, matrix, arr, path)){
				//	System.out.println("Destination reached: " + path);
				//	return true;
				}
			}
		

		for (Integer i : neighbors) {
			// reached base case
        if (recurDFS(i, destination, matrix, arr, path)) {
            return true;
        }
    }

		// didnt find a suitable edge from current node, not on path
		path.remove(node);
		// restore state
		arr[node] = false;
		return false;
	}

	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		
		/* YOUR CODE GOES HERE		*/
		
		Integer source = graph.getSource();

		Integer sink = graph.getDestination();

		int nodes = graph.getNbNodes();
		
		// need to store current flow in the graph as well as capacity, 
		//initially zero
		int[][] flow = new int[nodes][nodes];

		int[][] adjMatrix = new int[nodes][nodes];
		for(Edge e : graph.getEdges()){
			adjMatrix[e.nodes[0]][e.nodes[1]] = e.weight; // capacity of 
			// edge stored in adjacency matrix
		}

		// find path to sink node, used for while condition
		ArrayList<Integer> path = pathDFS(source, sink, graph);

		// while a path exists loop runs. 
		//this will end when no more augmenting paths are found
		// -> residual graph stopped returning a path
		while(!path.isEmpty()){
			Integer bottleneck = Integer.MAX_VALUE;

			// loop over path, compare, find bottle neck value
			for(int i = 0; i<path.size()-1; i++){
				int u = path.get(i);
				int v = path.get(i+1);

				// keep comparing against every capacity on the graph
				bottleneck = Math.min(bottleneck, adjMatrix[u][v]);
				

				System.out.println("Bottleneck:" + bottleneck);
			}

			// push forward edge with bottleneck
			for(int i = 0; i<path.size()-1; i++){
				int u = path.get(i);
				int v = path.get(i+1);

				// forward edge gets how much flow is pushed
				flow[u][v] += bottleneck;
				// backward edge is how much can be pushed back (i.e. how much is flowing)
				flow[v][u] -= bottleneck;
				// able to store both forward and backward edges by flipping matrix calls

				// for the whole path, you can always push more 
				// flow on its edges as mcuch as the bottleneck is
			}

			
			System.out.println("maxFlow" + maxFlow);

			// each bottleneck is more flow 
			// that will eventually be pushed towards the sink
			// so it is the 
			maxFlow+=bottleneck;
			path = AugmentingPath(source, sink, adjMatrix, flow);
			
		}

		// update each edge weight as the flow matrix (current flow)
		// is
		for (Edge e : graph.getEdges()) {
            int u = e.nodes[0];
            int v = e.nodes[1];
            e.weight = flow[u][v];
        }
		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}

	private static ArrayList<Integer> AugmentingPath(Integer source, Integer sink, int[][] matrix, int[][] flow){
		int nodes = matrix.length;

		// this is the graph that will be dfs'd now
		int[][] residual = new int[nodes][nodes];
		boolean[] visited = new boolean[nodes];
		// to pass to recurDFs

		
		for(int i =0; i<residual.length; i++){
			for(int j =0; j<residual[0].length;j++){
				// if more flow can be pushed its a forward edge
				if(flow[i][j]<matrix[i][j]){
					// forward edge represents 
					//how much more flow can be pushed
					//
					// so when there is no moer flow that can be pushed across that edge
					// the residual flow stops at 0
					// this halts dfs and doesnt go through that path to avoid breaking capacity
					// at that point there would be the cut
					residual[i][j] = matrix[i][j] - flow[i][j];
					
				}
					// backward edge 
					// represents how much flow can be pushed back
					// this is used in dfs to find more paths
					// as a back
					residual[j][i] = flow[i][j];
				
			}
		}

		ArrayList<Integer> path = new ArrayList<>();
		// valid path found
		if(recurDFS(source, sink, residual, visited, path)){
			return path;
		}
		return new ArrayList<>();
	}

}

