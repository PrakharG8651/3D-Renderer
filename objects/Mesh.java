package objects;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import wireframe.Triangle;
import wireframe.Vertex;

public class Mesh {
    public List<Triangle> triangles;

    public Mesh(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    // This allows you to move any shape to a new location in 3D space easily
    public void translate(double dx, double dy, double dz) {
        Set<Vertex> moved = new HashSet<>();
        for (Triangle t : triangles) {
            translateVertex(t.v1, dx, dy, dz, moved);
            translateVertex(t.v2, dx, dy, dz, moved);
            translateVertex(t.v3, dx, dy, dz, moved);
        }
    }

    private void translateVertex(Vertex v, double dx, double dy, double dz, Set<Vertex> moved) {
        if (moved.add(v)) {
            v.x += dx;
            v.y += dy;
            v.z += dz;
        }
    }
}
