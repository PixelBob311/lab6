import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        var graph1 = new FCTGenerator(16);
        var graph1Edges = graph1.getEdges();
        System.out.println("Рёбра:");
        for (int i = 0; i < graph1Edges.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + graph1Edges.get(i).sourceVertex.value + " -> " + graph1Edges.get(i).targetVertex.value + "; weight = " + graph1Edges.get(i).weight);
        }
        Graph graph2 = new Graph();
        graph2.vertices = graph1.getVertices();
        graph2.edges = graph1Edges;
        PrintSeparator();
        System.out.println("Последовательный алгоритм");
        var boruvkaSeq = new BoruvkaSequential(graph2);
        var MSTSeq = boruvkaSeq.MST;
        for (int i = 0; i < MSTSeq.edges.size(); i++) {
            System.out.println("\t" + MSTSeq.edges.get(i).sourceVertex.value + " -> " + MSTSeq.edges.get(i).targetVertex.value + "; weight = " + MSTSeq.edges.get(i).weight);
        }
        System.out.println("Суммарный вес рёбер при последовательном поиске = " + boruvkaSeq.MSTTotalWeight);
        PrintSeparator();
        System.out.println("Параллельный алгоритм");
        var boruvkaPar = new BoruvkaParallel(graph2, 8);
        var MSTPar = boruvkaPar.MST;
        for (int i = 0; i < MSTPar.edges.size(); i++) {
            System.out.println("\t" + MSTPar.edges.get(i).sourceVertex.value + " -> " + MSTPar.edges.get(i).targetVertex.value + "; weight = " + MSTPar.edges.get(i).weight);
        }
        System.out.println("Суммарный вес рёбер при параллельном поиске = " + boruvkaPar.MSTTotalWeight);
        PrintSeparator();
        System.out.println("Проверка равенства результатов \n\t" + boruvkaSeq.MSTTotalWeight + " == " + boruvkaPar.MSTTotalWeight + "? " + (boruvkaSeq.MSTTotalWeight == boruvkaPar.MSTTotalWeight));
    }

    public static void PrintSeparator(){
        System.out.println("===========================================");
    }
}