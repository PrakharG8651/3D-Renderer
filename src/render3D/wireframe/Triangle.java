package render3D.wireframe;

import java.awt.Color;

public class Triangle {
    public Vertex v1, v2, v3;
    public Color color;
    public Edge[] edges;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;

        this.edges = new Edge[] {
            new Edge(v1, v2, color),
            new Edge(v2, v3, color),
            new Edge(v3, v1, color)
        };
    }

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Color color, boolean isBorder1, boolean isBorder2, boolean isBorder3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
        Color edgeColor = Color.BLACK; // Color for edges if they are borders

        this.edges = new Edge[] {
            new Edge(v1, v2, (isBorder1?edgeColor:color)),
            new Edge(v2, v3, (isBorder2?edgeColor:color)),
            new Edge(v3, v1, (isBorder3?edgeColor:color))
        };
    }
}
