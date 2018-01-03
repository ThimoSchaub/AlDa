package shortestPath;

import graph.*;

import java.io.FileNotFoundException;

import sim.SYSimulation;

import java.awt.Color;
import java.io.IOException;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Kürzeste Wege im Scotland-Yard Spielplan mit A* und Dijkstra.
 *
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ScotlandYard {

    /**
     * Fabrikmethode zur Erzeugung eines gerichteten Graphens für den Scotland-Yard-Spielplan.
     * <p>
     * Liest die Verbindungsdaten von der Datei ScotlandYard_Kanten.txt.
     * Für die Verbindungen werden folgende Gewichte angenommen:
     * U-Bahn = 5, Taxi = 2 und Bus = 3.
     * Falls Knotenverbindungen unterschiedliche Beförderungsmittel gestatten,
     * wird das billigste Beförderungsmittel gewählt.
     * Bei einer Vebindung von u nach v wird in den gerichteten Graph sowohl
     * eine Kante von u nach v als auch von v nach u eingetragen.
     *
     * @return Gerichteter und Gewichteter Graph für Scotland-Yard.
     * @throws FileNotFoundException
     */
    public static DirectedGraph<Integer> getGraph() throws FileNotFoundException {

        DirectedGraph<Integer> sy_graph = new AdjacencyListDirectedGraph<>();
        Scanner in = new Scanner(new File("Z:/Alda/Aufgabe_2/Aufgabe_2/Aufgabe2/src/shortestPath/ScotlandYard_Kanten.txt"));
        for (int i = 1; i < 200; i++) {
            sy_graph.addVertex(i);
        }
        while (in.hasNext()) {

            String line = in.nextLine();
            String[] linesplit = line.split(" ");
            int start = Integer.valueOf(linesplit[0]);
            int ziel = Integer.valueOf(linesplit[1]);
            String typ = linesplit[2];
            double weigth = Double.POSITIVE_INFINITY;
            if (typ.equals("Taxi")) {
                weigth = 2.0;
            } else if (typ.equals("Bus")) {
                weigth = 3.0;
            } else if (typ.equals("UBahn")) {
                weigth = 5.0;
            }
            if (sy_graph.containsEdge(ziel, start)) {
                if (sy_graph.getWeight(ziel, start) > weigth) {
                    sy_graph.addEdge(ziel, start, weigth);
                }
            } else {
                sy_graph.addEdge(ziel, start, weigth);
            }
            if (sy_graph.containsEdge(start, ziel)) {
                if (sy_graph.getWeight(start, ziel) > weigth) {
                    sy_graph.addEdge(start, ziel, weigth);
                }
            } else {
                sy_graph.addEdge(start, ziel, weigth);
            }
        }

        return sy_graph;
    }


    /**
     * Fabrikmethode zur Erzeugung einer Heuristik für die Schätzung
     * der Distanz zweier Knoten im Scotland-Yard-Spielplan.
     * Die Heuristik wird für A* benötigt.
     * <p>
     * Liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten von der Datei
     * ScotlandYard_Knoten.txt in eine Map ein.
     * Die zurückgelieferte Heuristik-Funktion estimatedCost
     * berechnet einen skalierten Euklidischen Abstand.
     *
     * @return Heuristik für Scotland-Yard.
     * @throws FileNotFoundException
     */
    public static Heuristic<Integer> getHeuristic() throws FileNotFoundException {
        return new ScotlandYardHeuristic();
    }

    /**
     * Scotland-Yard Anwendung.
     *
     * @param args wird nicht verewendet.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        DirectedGraph<Integer> syGraph = getGraph();
        Heuristic<Integer> syHeuristic = getHeuristic();
        //System.out.println(syGraph.getWeight(78, 79));
        //System.out.println(syGraph.getWeight(65, 82));

        ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph, syHeuristic);

        sySp.searchShortestPath(65, 157);
        System.out.println("shortest Path" + sySp.getShortestPath());
        System.out.println("Distance = " + sySp.getDistance()); // 9.0

        sySp.searchShortestPath(1, 175);
        System.out.println("Search shortest Path between 1 and 175");
        System.out.println("Distance = " + sySp.getDistance()); // 25.0

        sySp.searchShortestPath(1, 173);
        System.out.println("Search shortest Path between 1 and 173");
        System.out.println("Distance = " + sySp.getDistance()); // 22.0


        SYSimulation sim;
        try {
            sim = new SYSimulation();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        sySp.setSimulator(sim);
        sim.startSequence("Shortest path from 1 to 173");


        //sySp.searchShortestPath(65,157); // 9.0
        //sySp.searchShortestPath(1,175); //25.0
        System.out.println("Search shortest Path between 1 and 173");
        sySp.searchShortestPath(1, 173); //22.0
        // bei Heuristik-Faktor von 1/10 wird nicht der optimale Pfad produziert.
        // bei 1/30 funktioniert es.

        System.out.println("Distance = " + sySp.getDistance());
        List<Integer> sp = sySp.getShortestPath();

        int a = -1;
        for (int b : sp) {
            if (a != -1)
                sim.drive(a, b, Color.RED.darker());
            sim.visitStation(b);
            a = b;
        }

        sim.stopSequence();


    }

}

class ScotlandYardHeuristic implements Heuristic<Integer> {
    private Map<Integer, Point> coord; // Ordnet jedem Knoten seine Koordinaten zu

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public ScotlandYardHeuristic() throws FileNotFoundException {
        // ...
    }

    public double estimatedCost(Integer u, Integer v) {
        // ...
        return 1.0;
    }
}

