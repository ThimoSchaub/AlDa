package graph;

public class main {

	public static void main(String[] args) {
		int n = 7;
		// Anzahl Knoten
		AdjacencyListDirectedGraph<Integer> adjacencyList = new AdjacencyListDirectedGraph<Integer>();
		System.out.println(adjacencyList.addVertex(5));
		System.out.println(adjacencyList.addVertex(6));
		System.out.println(adjacencyList.addVertex(4));
		System.out.println(adjacencyList.succ);
		System.out.println(adjacencyList.addEdge(4, 5 , 200.0));
		//System.out.println(adjacencyList.addEdge(4, 5));
		System.out.println(adjacencyList.addEdge(4, 6));
		System.out.println(adjacencyList.addEdge(6, 5));
		System.out.println(adjacencyList.getPredecessorVertexList(6));
				
		/*for (int i = 0; i < n; i++)
			adjacencyList.put((i, new AdjacencyListDirectedGraph();
		adjacencyList.get(4).put(3, 43);
		// Kante von 4 nach 3 mit Gewicht 43 eintragen
		adjacencyList.get(4).put(6, 46);
		// Kante von 4 nach 6 mit Gewicht 46 eintragen
		adjacencyList.get(4).put(2, 42);
		// Kante von 4 nach 2 mit Gewicht 42 eintragen
		*/
	}

}
