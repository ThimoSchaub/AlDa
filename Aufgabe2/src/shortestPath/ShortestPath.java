// O. Bittel;
// 18.10.2011

package shortestPath;

import graph.*;
import sim.SYSimulation;

import java.awt.*;
import java.util.*;
import java.util.List;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ShortestPath<V> {

    SYSimulation sim = null;

    Map<V, Double> dist; // Distanz für jeden Knoten
    Map<V, V> pred; // Vorgänger für jeden Knoten
    DirectedGraph<V> graph; // Graph
    Heuristic<V> heuristic;
    V start;
    V ziel;
    // ...

    /**
     * Berechnet im Graph g kürzeste Wege nach dem A*-Verfahren.
     * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
     * Wird h = null gewählt, dann ist das Verfahren identisch mit dem Dijkstra-Verfahren.
     *
     * @param g Gerichteter Graph
     * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
     *          dem Dijkstra-Verfahren gesucht.
     */
    public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
        graph = g;
        heuristic = h;
        dist = new HashMap<>();
        pred = new HashMap<>();
    }

    /**
     * Diese Methode sollte nur verwendet werden,
     * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
     * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
     * <p>
     * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
     * <p><blockquote><pre>
     *    if (sim != null)
     *       sim.visitStation((Integer) v, Color.blue);
     * </pre></blockquote>
     *
     * @param sim SYSimulation-Objekt.
     */
    public void setSimulator(SYSimulation sim) {
        this.sim = sim;
    }

    /**
     * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
     * <p>
     * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
     * der als nächstes aus der Kandidatenliste besucht wird, animiert.
     *
     * @param s Startknoten
     * @param g Zielknoten
     */
    public void searchShortestPath(V s, V g) {
        this.start = s;
        this.ziel = g;
        Queue<V> kl = new PriorityQueue<>();
        for (V v : graph.getVertexList()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            pred.put(v, null);
        }
        dist.put(s, 0.0);
        kl.add(s);

        while (!kl.isEmpty()) {
            double min = Double.POSITIVE_INFINITY;
            V minNode = kl.element();
            if (heuristic != null) {
                for (V v : kl) {
                    if (dist.get(v)+ heurstik <= min) {
                        //System.out.println("Kleinster knoten"+v);
                        min = dist.get(v);
                        minNode = v;
                    }
                }
                kl.remove(minNode);
                V v = minNode;
            } else {
                for (V v : kl) {
                    if (dist.get(v) <= min) {
                        //System.out.println("Kleinster knoten"+v);
                        min = dist.get(v);
                        minNode = v;
                    }
                }
                kl.remove(minNode);
                V v = minNode;

            }

            if (sim != null) {
                sim.visitStation(Integer.valueOf(v.toString()));
            }
            for (V w : graph.getSuccessorVertexList(v)) {
                if (dist.get(w) == Double.POSITIVE_INFINITY) {
                    kl.add(w);
                }
                if (dist.get(v) + graph.getWeight(v, w) < dist.get(w)) {
                    pred.put(w, v);
                    dist.put(w, dist.get(v) + graph.getWeight(v, w));
                }
            }
        }
        if (sim != null) {
            List<V> list = getShortestPath();
            for (V v : list) {
                if (v != start) {
                    sim.drive(Integer.valueOf(pred.get(v).toString()), Integer.valueOf(v.toString()), Color.GREEN);

                }
            }
        }

    }

    /**
     * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return kürzester Weg als Liste von Knoten.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public List<V> getShortestPath() {
        List<V> list = new ArrayList<>();
        V node = ziel;
        while (pred.get(node) != start) {
            list.add(node);
            node = pred.get(node);
        }
        list.add(node);
        list.add(start);
        Collections.reverse(list);
        return list;
    }

    /**
     * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return Länge eines kürzesten Weges.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public double getDistance() {
        return dist.get(ziel);
    }

}
