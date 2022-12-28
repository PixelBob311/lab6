import java.util.ArrayList;
import java.util.List;

public class BoruvkaParallel {
    List<Edge> minEdges = new ArrayList<>();
    List<Vertex> parents = new ArrayList<>();
    int MSTTotalWeight = 0;
    Graph MST = new Graph();
    int MSTEdgesCount = 0;

    BoruvkaParallel(Graph graph, int threadsCount) throws InterruptedException {
        MST.vertices = graph.vertices;
        for (int i = 0; i < MST.vertices.size(); i++) {
            parents.add(MST.vertices.get(i));
        }
        var threads = new Thread[threadsCount];

        while (MSTEdgesCount < graph.vertices.size() - 1) {
            for (int t = 0; t < MST.vertices.size(); t++) {
                minEdges.add(null);
            }
            for (int th = 0; th < threadsCount; th++) {
                int startIndex = th * graph.edges.size() / threadsCount;
                int endIndex = (th + 1) * graph.edges.size() / threadsCount;
                if (th == threadsCount - 1) {
                    endIndex = graph.edges.size();
                }
                int lastEndIndex = endIndex;
                threads[th] = new Thread(() -> {
                    for (int i = startIndex; i < lastEndIndex; i++) {
                        Edge currentEdge = graph.edges.get(i);
                        var vertex1 = root(currentEdge.sourceVertex).value;
                        var vertex2 = root(currentEdge.targetVertex).value;
                        if (vertex1 != vertex2) {
                            if (minEdges.get(vertex1) == null || minEdges.get(vertex1).weight > currentEdge.weight) {
                                minEdges.set(vertex1, currentEdge);
                            }
                            if (minEdges.get(vertex2) == null || minEdges.get(vertex2).weight > currentEdge.weight) {
                                minEdges.set(vertex2, currentEdge);
                            }
                        }
                    }
                }
                );
                threads[th].start();
            }
            for (int i = 0; i < threadsCount; i++) {
                threads[i].join();
            }

            for (int i = 0; i < graph.vertices.size(); i++) {
                Edge currentEdge = minEdges.get(i);
//                если i-ое ребро заполнено (то есть оно входит в остов) и если это не петля - пытаемся замержить
                if (currentEdge != null) {
                    if (root(currentEdge.sourceVertex) != root(currentEdge.targetVertex)) {
                        merge(minEdges.get(i).sourceVertex, minEdges.get(i).targetVertex);
                        MSTTotalWeight += minEdges.get(i).weight;
                        MST.edges.add(minEdges.get(i));
                        MSTEdgesCount += 1;
                    }
                }
            }
            minEdges = new ArrayList<>();
        }
    }

    public Vertex root(Vertex vertex) {
        while (vertex != parents.get(vertex.value)) {
            vertex = parents.get(vertex.value);
        }
        return vertex;
    }

    public boolean merge(Vertex firstVertex, Vertex secondVertex) {
        firstVertex = root(firstVertex);
        secondVertex = root(secondVertex);
        if (firstVertex == secondVertex) {
            return false;
        }
        parents.set(secondVertex.value, firstVertex);
        return true;
    }
}
