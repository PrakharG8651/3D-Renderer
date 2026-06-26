import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;

public class Renderer extends JPanel {
    private int width, height;
    private BufferedImage img;
    private double[] zBuffer;

    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        zBuffer = new double[width * height];
        setPreferredSize(new Dimension(width, height));
    }

    private void project(Vertex v, double cameraDist) {
        // Scale X and Y based on how far away (Z) the vertex is
        double factor = cameraDist / (v.z + cameraDist);
        v.x *= factor;
        v.y *= factor;
    }

    public void render(List<Triangle> mesh, Matrix3 transform) {
        // Clear Z-Buffer
        for (int i = 0; i < zBuffer.length; i++) zBuffer[i] = Double.NEGATIVE_INFINITY;
        
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2.dispose();

        // 2. Render Loop
        double cameraDist = 400; // Focal length
        int centerX = img.getWidth() / 2;
        int centerY = img.getHeight() / 2;
        // Render each triangle
        for (Triangle t : mesh) {
            // Transform the vertices
            Vertex v1 = transform.transform(t.v1);
            Vertex v2 = transform.transform(t.v2);
            Vertex v3 = transform.transform(t.v3);

            // Apply Perspective Projection
            project(v1, cameraDist);
            project(v2, cameraDist);
            project(v3, cameraDist);

            // Offset to screen center
            v1.x += centerX; v1.y += centerY;
            v2.x += centerX; v2.y += centerY;
            v3.x += centerX; v3.y += centerY;

            Triangle projected = new Triangle(v1, v2, v3, t.color);
            projected.edges = new Edge[] {
                new Edge(v1, v2, t.edges[0].color),
                new Edge(v2, v3, t.edges[1].color),
                new Edge(v3, v1, t.edges[2].color)
            };

            rasterizeTriangle(projected,g2);
        }
        g2.dispose();
    }

    private double calculateT(int x, int y, int x1, int y1, int x2, int y2) {
        // Calculate the parameter t for linear interpolation
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            return (dx == 0) ? 0 : (x - x1) / dx;
        } else {
            return (dy == 0) ? 0 : (y - y1) / dy;
        }
    }

    private void drawLine(Edge e, double[] zBuffer, int width, int height) {
        // 1. Get pixel coordinates
        int x1 = (int)e.v1.x, y1 = (int)e.v1.y;
        int x2 = (int)e.v2.x, y2 = (int)e.v2.y;
        int startX = x1, startY = y1;

        // 2. Simple Line Interpolation
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1, sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            // Z-Buffer check for the current pixel
            if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height) {
                int zIndex = y1 * width + x1;
                
                // Interpolate depth between v1.z and v2.z
                double t = calculateT(x1, y1, startX, startY, x2, y2);
                double depth = (1 - t) * e.v1.z + t * e.v2.z;

                if (depth >= zBuffer[zIndex]) {
                    img.setRGB(x1, y1, e.color.getRGB());
                    zBuffer[zIndex] = depth;
                }
            }

            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x1 += sx; }
            if (e2 < dx) { err += dx; y1 += sy; }
        }
    }
    
    private void rasterizeTriangle(Triangle t, Graphics2D g2) {
        // 1. Calculate the bounding box of the triangle
        int minX = (int) Math.max(0, Math.ceil(Math.min(t.v1.x, Math.min(t.v2.x, t.v3.x))));
        int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(t.v1.x, Math.max(t.v2.x, t.v3.x))));
        int minY = (int) Math.max(0, Math.ceil(Math.min(t.v1.y, Math.min(t.v2.y, t.v3.y))));
        int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(t.v1.y, Math.max(t.v2.y, t.v3.y))));

        // 2. Precompute the area denominator for barycentric coordinates
        double triangleArea = (t.v1.y - t.v3.y) * (t.v2.x - t.v3.x) + (t.v2.y - t.v3.y) * (t.v3.x - t.v1.x);

        // 3. Iterate through every pixel in the bounding box
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                // Calculate barycentric coordinates (b1, b2, b3)
                double b1 = ((y - t.v3.y) * (t.v2.x - t.v3.x) + (t.v2.y - t.v3.y) * (t.v3.x - x)) / triangleArea;
                double b2 = ((y - t.v1.y) * (t.v3.x - t.v1.x) + (t.v3.y - t.v1.y) * (t.v1.x - x)) / triangleArea;
                double b3 = 1 - b1 - b2;

                if (b1 >= 0 && b2 >= 0 && b3 >= 0) {
                    // Calculate depth for Z-buffering
                    double depth = b1 * t.v1.z + b2 * t.v2.z + b3 * t.v3.z;
                    int zIndex = y * img.getWidth() + x;

                    // 5. Depth test: only draw if closer than current depth
                    if (depth > zBuffer[zIndex]) {
                        img.setRGB(x, y, t.color.getRGB());
                        zBuffer[zIndex] = depth;
                    }
                }
            }
        }

        for(Edge edge : t.edges) {
            drawLine(edge, zBuffer, width, height);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Your render loop goes here
        g.drawImage(img, 0, 0, null);
    }
}
