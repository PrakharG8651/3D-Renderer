import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;

public class Renderer extends JPanel {
    private BufferedImage img;
    private double[] zBuffer;

    public Renderer(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        zBuffer = new double[width * height];
        setPreferredSize(new Dimension(width, height));
    }

    public void render(List<Triangle> mesh, Matrix3 transform) {
        // Clear Z-Buffer
        for (int i = 0; i < zBuffer.length; i++) zBuffer[i] = Double.NEGATIVE_INFINITY;
        
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2.dispose();

        // Render each triangle
        for (Triangle t : mesh) {
            Vertex v1 = transform.transform(t.v1);
            Vertex v2 = transform.transform(t.v2);
            Vertex v3 = transform.transform(t.v3);
            
            // Offset to screen center
            int centerX = img.getWidth() / 2;
            int centerY = img.getHeight() / 2;
            
            v1.x += centerX; v1.y += centerY;
            v2.x += centerX; v2.y += centerY;
            v3.x += centerX; v3.y += centerY;

            rasterizeTriangle(v1, v2, v3, t.color);
        }
    }
    
    private void rasterizeTriangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        // 1. Calculate the bounding box of the triangle
        int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
        int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
        int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
        int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

        // 2. Precompute the area denominator for barycentric coordinates
        double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

        // 3. Iterate through every pixel in the bounding box
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                // Calculate barycentric coordinates (b1, b2, b3)
                double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                double b3 = 1 - b1 - b2;

                // 4. If point is inside triangle (all b are between 0 and 1)
                if (b1 >= 0 && b2 >= 0 && b3 >= 0) {
                    // Calculate depth for Z-buffering
                    double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                    int zIndex = y * img.getWidth() + x;

                    // 5. Depth test: only draw if closer than current depth
                    if (depth > zBuffer[zIndex]) {
                        img.setRGB(x, y, color.getRGB());
                        zBuffer[zIndex] = depth;
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Your render loop goes here
        g.drawImage(img, 0, 0, null);
    }
}