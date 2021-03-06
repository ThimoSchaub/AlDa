// O. Bittel;
// 22.02.2017

package graph;
import java.util.List;

/**
 * Graph mit gerichteten Kanten.
 * Es ist zu beachten, dass g.addEdge(v,w) 
 * eine gerichtete Kante von v nach w einf�gt.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public interface DirectedGraph<V> extends Graph<V> {

    /**
     * Liefert Eingangsgrad des Knotens v zur?ck.
     * Das ist die Anzahl der Kanten mit Zielknoten v.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knoteneingangsgrad
     */
    int getInDegree(V v);

    /**
     * Liefert Ausgangsgrad des Knotens v zur?ck.
     * Das ist die Anzahl der Kanten mit Quellknoten v.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotenausgangsgrad
     */
    int getOutDegree(V v);

    /**
     * Liefert die Liste aller Vorg�ngerknoten zu v zur�ck.
     * Das sind alle die Knoten, von denen eine Kante zu v f�hrt.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotenliste
     */
    List<V> getPredecessorVertexList(V v);

    /**
     * Liefert die Liste aller Nachfolgerknoten zu v zur�ck. 
     * Das sind alle die Knoten, zu denen eine Kante von v f�hrt.
     * @param v Knoten
     * @throws IllegalArgumentException falls Knoten v
     * nicht im Graph vorhanden ist.
     * @return Knotenliste
     */
    List<V> getSuccessorVertexList(V v);
}
