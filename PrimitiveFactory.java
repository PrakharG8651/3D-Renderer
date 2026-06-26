import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveFactory {
    public static Mesh createCube(double size, List<Color> faceColor) {
        double s = size / 2.0;
        List<Triangle> tris = new ArrayList<>();
        // Vertices of a cube
        Vertex[] v = {
            new Vertex(-s, -s, -s), new Vertex(s, -s, -s),
            new Vertex(s, s, -s), new Vertex(-s, s, -s),
            new Vertex(-s, -s, s), new Vertex(s, -s, s),
            new Vertex(s, s, s), new Vertex(-s, s, s)
        };

        // Helper to add a quad face (2 triangles)
        addQuad(tris, v[0], v[1], v[2], v[3], faceColor.get(0)); // Back
        addQuad(tris, v[4], v[5], v[6], v[7], faceColor.get(1)); // Front
        addQuad(tris, v[0], v[1], v[5], v[4], faceColor.get(2)); // Bottom
        addQuad(tris, v[2], v[3], v[7], v[6], faceColor.get(3)); // Top
        addQuad(tris, v[0], v[3], v[7], v[4], faceColor.get(4)); // Left
        addQuad(tris, v[1], v[2], v[6], v[5], faceColor.get(5)); // Right

        return new Mesh(tris);
    }

    private static void addQuad(List<Triangle> tris, Vertex a, Vertex b, Vertex c, Vertex d, Color color) {
        tris.add(new Triangle(a, b, c, color, true, true, false));
        tris.add(new Triangle(a, c, d, color, false, true, true));
    }
}