import java.util.ArrayList;
import java.util.List;

public class BoruvkaSequential {
    //самые минимальные вершины, которые есть в мст
    List<Edge> minEdges = new ArrayList<>();
    List<Vertex> parents = new ArrayList<>();
    Vertex vertex1;
    Vertex vertex2;
    int MSTTotalWeight = 0;
    Graph MST = new Graph();
    int componentsCount;

    BoruvkaSequential(Graph graph) {
        MST.vertices = graph.vertices;
        componentsCount = MST.vertices.size();
        parents.addAll(MST.vertices);
        ///////////////////////////
        while (componentsCount > 1) {
            for (int i = 0; i < MST.vertices.size(); i++) {
                //создаем такое же кол-во вершин, сколько и в исходном графе
                minEdges.add(null);
            }
            for (int i = 0; i < graph.edges.size(); i++) {
                vertex1 = root(graph.edges.get(i).sourceVertex);
                vertex2 = root(graph.edges.get(i).targetVertex);
                if (vertex1 == vertex2) {
                    continue;
                }
                /*
                 если в графе одна из нод меньше,
                 чем добавленная или ее вообще нет - меняем значение добавленной
                 на значение ноды из дерева или добавляем ей значение, если ее не было.
                * */
                if (minEdges.get(vertex1.value) == null || minEdges.get(vertex1.value).weight > graph.edges.get(i).weight) {
                    minEdges.set(vertex1.value, graph.edges.get(i));
                }
                if (minEdges.get(vertex2.value) == null || minEdges.get(vertex2.value).weight > graph.edges.get(i).weight) {
                    minEdges.set(vertex2.value, graph.edges.get(i));
                }

            }
            for (int i = 0; i < minEdges.size(); i++) {
                if (minEdges.get(i) != null) {
                    if (merge(minEdges.get(i).sourceVertex, minEdges.get(i).targetVertex)) {
                        MSTTotalWeight += minEdges.get(i).weight;
                        MST.edges.add(minEdges.get(i));
                        componentsCount--;
                    }
                }
            }
            minEdges = new ArrayList<>();
        }
        int g = 1;
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
