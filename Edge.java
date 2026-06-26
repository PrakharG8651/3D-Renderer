import java.awt.Color;

public class Edge {
    public Vertex v1, v2;
    public Color color;

    public Edge(Vertex v1, Vertex v2, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.color = color;
    }
}