public class Edge {
    int weight;
    Vertex sourceVertex, targetVertex;

    Edge(Vertex sourceVertex, Vertex targetVertex, int weight) {
        this.weight = weight;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
    }
}
