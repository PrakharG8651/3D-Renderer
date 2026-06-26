package objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import wireframe.Triangle;
import wireframe.Vertex;

public class PrimitiveFactory {
    public static Mesh createCube(double size, List<Color> faceColor) {
        return createCuboid(size, size, size, faceColor);
    }

    public static Mesh createCuboid(double width, double height, double depth, List<Color> faceColor) {
        double x = width / 2.0;
        double y = height / 2.0;
        double z = depth / 2.0;
        List<Triangle> tris = new ArrayList<>();

        Vertex[] v = {
            new Vertex(-x, -y, -z), new Vertex(x, -y, -z),
            new Vertex(x, y, -z), new Vertex(-x, y, -z),
            new Vertex(-x, -y, z), new Vertex(x, -y, z),
            new Vertex(x, y, z), new Vertex(-x, y, z)
        };

        addQuad(tris, v[0], v[1], v[2], v[3], faceColor.get(0)); // Back
        addQuad(tris, v[4], v[5], v[6], v[7], faceColor.get(1)); // Front
        addQuad(tris, v[0], v[1], v[5], v[4], faceColor.get(2)); // Bottom
        addQuad(tris, v[2], v[3], v[7], v[6], faceColor.get(3)); // Top
        addQuad(tris, v[0], v[3], v[7], v[4], faceColor.get(4)); // Left
        addQuad(tris, v[1], v[2], v[6], v[5], faceColor.get(5)); // Right

        return new Mesh(tris);
    }

    public static Mesh createDisc(double radius, int segments, Color color) {
        segments = Math.max(3, segments);
        List<Triangle> tris = new ArrayList<>();
        Vertex center = new Vertex(0, 0, 0);
        Vertex[] rim = createCircleXY(radius, segments, 0);

        for (int i = 0; i < segments; i++) {
            Vertex a = rim[i];
            Vertex b = rim[(i + 1) % segments];
            tris.add(new Triangle(center, a, b, color, false, true, false));
        }

        return new Mesh(tris);
    }

    public static Mesh createCylinder(double radius, double height, int segments, Color sideColor, Color topColor, Color bottomColor) {
        segments = Math.max(3, segments);
        List<Triangle> tris = new ArrayList<>();
        double y = height / 2.0;
        Vertex topCenter = new Vertex(0, y, 0);
        Vertex bottomCenter = new Vertex(0, -y, 0);
        Vertex[] top = createCircleXZ(radius, segments, y);
        Vertex[] bottom = createCircleXZ(radius, segments, -y);

        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;
            addSmoothQuad(tris, bottom[i], bottom[next], top[next], top[i], sideColor);
            tris.add(new Triangle(topCenter, top[i], top[next], topColor, false, true, false));
            tris.add(new Triangle(bottomCenter, bottom[next], bottom[i], bottomColor, false, true, false));
        }

        return new Mesh(tris);
    }

    public static Mesh createSphere(double radius, int latitudeSegments, int longitudeSegments, Color color) {
        latitudeSegments = Math.max(2, latitudeSegments);
        longitudeSegments = Math.max(3, longitudeSegments);
        List<Triangle> tris = new ArrayList<>();
        Vertex[][] vertices = new Vertex[latitudeSegments + 1][longitudeSegments];

        for (int lat = 0; lat <= latitudeSegments; lat++) {
            double theta = Math.PI * lat / latitudeSegments;
            double y = radius * Math.cos(theta);
            double ringRadius = radius * Math.sin(theta);

            for (int lon = 0; lon < longitudeSegments; lon++) {
                double phi = 2.0 * Math.PI * lon / longitudeSegments;
                double x = ringRadius * Math.cos(phi);
                double z = ringRadius * Math.sin(phi);
                vertices[lat][lon] = new Vertex(x, y, z);
            }
        }

        for (int lat = 0; lat < latitudeSegments; lat++) {
            for (int lon = 0; lon < longitudeSegments; lon++) {
                int next = (lon + 1) % longitudeSegments;
                Vertex a = vertices[lat][lon];
                Vertex b = vertices[lat][next];
                Vertex c = vertices[lat + 1][next];
                Vertex d = vertices[lat + 1][lon];

                if (lat == 0) {
                    tris.add(new Triangle(a, c, d, color));
                } else if (lat == latitudeSegments - 1) {
                    tris.add(new Triangle(a, b, d, color));
                } else {
                    addSmoothQuad(tris, a, b, c, d, color);
                }
            }
        }

        return new Mesh(tris);
    }

    private static Vertex[] createCircleXY(double radius, int segments, double z) {
        Vertex[] vertices = new Vertex[segments];
        for (int i = 0; i < segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            vertices[i] = new Vertex(radius * Math.cos(angle), radius * Math.sin(angle), z);
        }
        return vertices;
    }

    private static Vertex[] createCircleXZ(double radius, int segments, double y) {
        Vertex[] vertices = new Vertex[segments];
        for (int i = 0; i < segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            vertices[i] = new Vertex(radius * Math.cos(angle), y, radius * Math.sin(angle));
        }
        return vertices;
    }

    private static void addSmoothQuad(List<Triangle> tris, Vertex a, Vertex b, Vertex c, Vertex d, Color color) {
        tris.add(new Triangle(a, b, c, color));
        tris.add(new Triangle(a, c, d, color));
    }

    private static void addQuad(List<Triangle> tris, Vertex a, Vertex b, Vertex c, Vertex d, Color color) {
        tris.add(new Triangle(a, b, c, color, true, true, false));
        tris.add(new Triangle(a, c, d, color, false, true, true));
    }
}
