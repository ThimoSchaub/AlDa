package graph;

import java.util.LinkedList;
import java.util.List;

public class main {

    public static AdjacencyListDirectedGraph<Integer> graph = new AdjacencyListDirectedGraph<Integer>();
    public static AdjacencyListDirectedGraph<Integer> graph2 = new AdjacencyListDirectedGraph<Integer>();
    public static AdjacencyListDirectedGraph<String> anziehen = new AdjacencyListDirectedGraph<String>();
	public static void main(String[] args) {
		int n = 7;
		// Anzahl Knoten

		for (int i = 1; i <= 7; i++)
		    graph.addVertex(i);
        graph.addEdge(1,2);
        graph.addEdge(2,5);
        graph.addEdge(5,1);
        graph.addEdge(2,6);
        graph.addEdge(4,6);
        graph.addEdge(4,3);
        graph.addEdge(3,7);
        graph.addEdge(7,4);
        System.out.println(AdjacencyListDirectedGraph.findStrongComponents(graph));

        for (int i = 1; i <= 10; i++)
            graph2.addVertex(i);
        graph2.addEdge(1,2);
        graph2.addEdge(1,4);
        graph2.addEdge(2,3);
        graph2.addEdge(2,6);
        graph2.addEdge(3,1);
        graph2.addEdge(3,4);
        graph2.addEdge(3,5);
        graph2.addEdge(4,5);
        graph2.addEdge(6,3);
        graph2.addEdge(7,6);
        graph2.addEdge(7,8);
        graph2.addEdge(8,6);
        graph2.addEdge(8,10);
        graph2.addEdge(10,9);
        graph2.addEdge(9,8);
        //System.out.println(AdjacencyListDirectedGraph.depthFirstSearchAllNodes(graph2));

        anziehen.addVertex("Socken");
        anziehen.addVertex("Schuhe");
        anziehen.addVertex("Unterhose");
        anziehen.addVertex("Hose");
        anziehen.addVertex("Gürtel");
        anziehen.addVertex("Unterhemd");
        anziehen.addVertex("Hemd");
        anziehen.addVertex("Pulli");
        anziehen.addVertex("Mantel");
        anziehen.addVertex("Schal");
        anziehen.addVertex("Handschuhe");
        anziehen.addVertex("Mütze");
        anziehen.addEdge("Schuhe", "Handschuhe");
        anziehen.addEdge("Schal", "Handschuhe");
        anziehen.addEdge("Mütze", "Handschuhe");
        anziehen.addEdge("Socken", "Schuhe");
        anziehen.addEdge("Hose", "Schuhe");
        anziehen.addEdge("Hose", "Gürtel");
        anziehen.addEdge("Unterhose", "Hose");
        anziehen.addEdge("Unterhemd", "Hemd");
        anziehen.addEdge("Hemd", "Pulli");
        anziehen.addEdge("Pulli", "Mantel");
        anziehen.addEdge("Mantel", "Schal");
        anziehen.addEdge("Gürtel", "Mantel");
        anziehen.addEdge("Schal", "Hose");
        //System.out.println(AdjacencyListDirectedGraph.topologicalSort(anziehen));


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
