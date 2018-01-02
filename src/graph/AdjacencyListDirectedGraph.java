// O. Bittel;
// 04.05.2017

package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementierung von DirectedGraph mit einer doppelten HashMap 
 * für die Nachfolgerknoten und einer einer doppelten HashMap 
 * für die Vorgängerknoten.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung 
 * mit schnellem Zugriff auf die Knoten.
 * @author Anton Hummel & Thimo Schaub
 * @since 02.01.2018
 * @param <V> Knotentyp.
 */
public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
    // doppelte HashMap f�r die Nachfolgerknoten:
    public final HashMap<V, HashMap<V, Double>> succ = new HashMap<>(); 
    
    // doppelte HashMap f�r die Vorgängerknoten:
    public final HashMap<V, HashMap<V, Double>> pred = new HashMap<>(); 
    
    
    private int numberEdge = 0;

   

    @Override
    public boolean addVertex(V v) {
    	if (!succ.containsKey(v)){
    		succ.put(v, new HashMap<>());
    		pred.put(v, new HashMap<>());
    		return true;
    	} else {
        	return false;
    	}
    	
    }

    @Override
    public boolean addEdge(V v, V w) {
    	if (!containsVertex(v)) {
    		addVertex(v);
    	}
    	if (!containsVertex(v)) {
    		addVertex(w);
    	}
        if (containsEdge(v, w)) {
        	succ.get(v).put(w, 1.0);
        	pred.get(w).put(v, 1.0);
        	return false;
        }else {
        	succ.get(v).put(w, 1.0);
        	pred.get(w).put(v, 1.0);
        	numberEdge++;
        	return true;
    	}
    }
    @Override
    public boolean addEdge(V v, V w, double weight) {
    	if (!containsVertex(v)) {
    		addVertex(v);
    	}
    	if (!containsVertex(w)) {
    		addVertex(w);
    	}
        if (containsEdge(v, w)) {
        	succ.get(v).put(w, weight);
        	pred.get(w).put(v, weight);
        	return false;
        }else {
        	succ.get(v).put(w, weight);
        	pred.get(w).put(v, weight);
        	numberEdge++;
        	return true;
    	}
    }

    @Override
    public boolean containsVertex(V v) {
        return succ.containsKey(v);
    }

    @Override
    public boolean containsEdge(V v, V w) {
    	if (!containsVertex(v)||!containsVertex(w)) {
    		throw new IllegalArgumentException(v +" or "+ w + " isn`t a Vertex");
    	}
        return succ.get(v).containsKey(w);
    }

    @Override
    public double getWeight(V v, V w) {
    	 return succ.get(v).get(w);
    }

    @Override
    public int getNumberOfVertexes() {
    	return succ.size();
    }

    @Override
    public int getNumberOfEdges() {
        return numberEdge;
    }

    @Override
    public List<V> getVertexList() {
        LinkedList<V> list = new LinkedList<>();
        for (V v : succ.keySet()) {
        	list.add(v);       	
        }
        return list;
    }

    @Override
    public List<Edge<V>> getEdgeList() {
    	 List<Edge<V>> liste= new LinkedList<>();
         for (V m : getVertexList()) {
        	 for (V n : succ.get(m).keySet()) {
        		liste.add(new Edge<V>(m,n,getWeight(m,n)));
        	 	 
    		 }
         }
         return liste;
    }
    
    @Override
    public int getInDegree(V v) {
    	if (!containsVertex(v)) {
    		throw new IllegalArgumentException("The Argument"+ v +" isn`t a Vertex");
    	}
    	int in = 0;
    	List<Edge<V>> n = getEdgeList();
    	for (Edge<V> e : n) {
			if (e.getTarget()== v) {
				in++;
			}
		}
    	return in;
    }

    @Override
    public int getOutDegree(V v) {
    	if (!containsVertex(v)) {
    		throw new IllegalArgumentException("The Argument "+ v +" isn`t a Vertex");
    	}
    	int out = 0;
    	List<Edge<V>> n = getEdgeList();
    	for (Edge<V> e : n) {
			if (e.getSource()== v) {
				out++;
			}
		}
    	return out;
    }

    @Override
    public List<V> getPredecessorVertexList(V v) {
    	if (!containsVertex(v)) {
    		throw new IllegalArgumentException("The Argument "+ v +" isn`t a Vertex");
    	}
    	List<V> list = new LinkedList<>();
    	for (V n : pred.get(v).keySet()) {
    		list.add(n);
    	 	 
		 }
    	return list;
    }

    @Override
    public List<V> getSuccessorVertexList(V v) {
    	if (!containsVertex(v)) {
    		throw new IllegalArgumentException("The Argument "+ v +" isn`t a Vertex");
    	}
    	List<V> list = new LinkedList<>();
    	for (V n : succ.get(v).keySet()) {
    		list.add(n);
    	 	 
		 }
    	return list;
    }
}
