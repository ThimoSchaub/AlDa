// O. Bittel;
// 04.05.2017

package graph;

import java.util.*;


/**
 * Implementierung von DirectedGraph mit einer doppelten HashMap
 * fÃ¼r die Nachfolgerknoten und einer einer doppelten HashMap
 * fÃ¼r die VorgÃ¤ngerknoten.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung
 * mit schnellem Zugriff auf die Knoten.
 *
 * @param <V> Knotentyp.
 * @author Anton Hummel & Thimo Schaub
 * @since 02.01.2018
 */
public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
    // doppelte HashMap für die Nachfolgerknoten:
    public final HashMap<V, HashMap<V, Double>> succ = new HashMap<>();

    // doppelte HashMap für die VorgÃ¤ngerknoten:
    public final HashMap<V, HashMap<V, Double>> pred = new HashMap<>();

    private static int post = 1;
    private int numberEdge = 0;

    /**
     * Gibt alle Knoten nach der Tiefensuche als Liste zurück.
     * @param g Graph
     * @param <V> Typ der Knoten
     * @return gibt die Liste aller Knoten und die damit verbundenen Knoten zurück
     */
    public static <V> List<List<V>> depthFirstSearchAllNodes(DirectedGraph<V> g) {
        List<List<V>> list = new LinkedList<>();
        HashMap<V, Integer> besucht = new HashMap<V, Integer>();
        for (V v : g.getVertexList()) {
            if (!besucht.containsKey(v)) {
                List<V> liste = new LinkedList<>();
                list.add(depthFirstSearchAllNodes(v, g, besucht, liste));
            }
        }
        return list;
    }

    /**
     * Rekursive Funktion der Tiefensuche
     * @param v Vertex
     * @param g Graph
     * @param besucht Hashmap für den besucht-Zustand
     * @param list Liste der Knoten
     * @param <V> Typ der Knoten
     * @return gibt die Liste aller Knoten zurück die mit dem Startknoten v verbunden sind
     */
    private static <V> List<V> depthFirstSearchAllNodes(V v, DirectedGraph<V> g, HashMap besucht, List<V> list) {

        if (!besucht.containsKey(v)) {
            besucht.put(v, 0);
            list.add(v);
            for (V m : g.getSuccessorVertexList(v)) {
                depthFirstSearchAllNodes(m, g, besucht, list);
            }

            besucht.put(v, post++);
            }
        return list;
    }

    /**
     * Topologische Sortierung des Graphens
     * @param g Graph
     * @param <V> Typ der Knoten
     * @return Liste der sortierten Knoten
     */
    public static <V> List<V> topologicalSort(DirectedGraph<V> g) {
        List<V> ts = new LinkedList<>();
        HashMap<V,Integer> inDegree =new HashMap<>() ;
        Queue<V> q =new ArrayDeque<>();

        for(V v : g.getVertexList()) {
            inDegree.put(v,g.getInDegree(v));
            if(g.getInDegree(v)==0){
                q.add(v);
            }
        }
        while(!q.isEmpty()) {
            V v = q.remove();
            ts.add(v);
            for (V w : g.getSuccessorVertexList(v)) {
                int i = inDegree.get(w)-1;
                inDegree.put(w,i);
                if (i==0){
                    q.add(w);
                }
            }
        }
        if (ts.size()!= g.getNumberOfVertexes())
            return null;
        else
            return ts;


    }

    /**
     * Invertiert die KAnten um festzustellen welche Knoten Stong Komponents sind
     * @param g graphh der durchsucht wird
     * @param <V> typ der knoten
     * @return Liste der Strong Komponents  und den damit verbundenen Knoten
     */
    public static <V>List<List<V>>findStrongComponents(DirectedGraph<V>g){
        DirectedGraph<V>g2 = new AdjacencyListDirectedGraph<V>();
        for (V v: g.getVertexList()){
            g2.addVertex(v);
        }
        for(Edge e: g.getEdgeList()){
            g2.addEdge((V)e.target,(V)e.source,e.weight);
        }
        return depthFirstSearchAllNodes(g2);
    }

    @Override
    public boolean addVertex(V v) {
        if (!succ.containsKey(v)) {
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
        } else {
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
        } else {
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
        if (!containsVertex(v) || !containsVertex(w)) {
            throw new IllegalArgumentException(v + " or " + w + " isn`t a Vertex");
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
        List<Edge<V>> liste = new LinkedList<>();
        for (V m : getVertexList()) {
            for (V n : succ.get(m).keySet()) {
                liste.add(new Edge<V>(m, n, getWeight(m, n)));

            }
        }
        return liste;
    }

    @Override
    public int getInDegree(V v) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("The Argument" + v + " isn`t a Vertex");
        }
        int in = 0;
        List<Edge<V>> n = getEdgeList();
        for (Edge<V> e : n) {
            if (e.getTarget() == v) {
                in++;
            }
        }
        return in;
    }

    @Override
    public int getOutDegree(V v) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("The Argument " + v + " isn`t a Vertex");
        }
        int out = 0;
        List<Edge<V>> n = getEdgeList();
        for (Edge<V> e : n) {
            if (e.getSource() == v) {
                out++;
            }
        }
        return out;
    }

    @Override
    public List<V> getPredecessorVertexList(V v) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("The Argument " + v + " isn`t a Vertex");
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
            throw new IllegalArgumentException("The Argument " + v + " isn`t a Vertex");
        }
        List<V> list = new LinkedList<>();
        for (V n : succ.get(v).keySet()) {
            list.add(n);

        }
        return list;
    }
}
