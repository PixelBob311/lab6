import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FCTGenerator {
    static List<Vertex> vertices;
    static List<Edge> edges = new ArrayList<>();
    static List<Integer> weights = new ArrayList<>();

    FCTGenerator(int verticesCount) {
        vertices = new ArrayList<>(verticesCount);
        Random randomizer = new Random();
        int value;
        for (int i = 0; i < verticesCount; i++) {
            vertices.add(new Vertex(i));
        }
        //генерим ребра графа
        for (int i = 0; i < verticesCount - 1; i++) {
            for (int j = i + 1; j < verticesCount; j++) {
                do {
                    value = randomizer.nextInt(1000);
                } while (weights.contains(value));
                weights.add(value);
                edges.add(new Edge(vertices.get(i), vertices.get(j), value));
            }
        }
    }

    public static List<Vertex> getVertices() {
        return vertices;
    }

    public static List<Edge> getEdges() {
        return edges;
    }
}
