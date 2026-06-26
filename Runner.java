import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Runner {
    public static void main(String[] args) {
        // 1. Setup the window
        JFrame frame = new JFrame("3D Engine");
        Renderer renderer = new Renderer(800, 600);
        frame.add(renderer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 2. Define your tetrahedron mesh
        // In your Runner.java
        List<Color> faceColors = new ArrayList<>();
        faceColors.add(Color.BLUE);
        faceColors.add(Color.RED);
        faceColors.add(Color.GREEN);
        faceColors.add(Color.YELLOW);
        faceColors.add(Color.MAGENTA);
        faceColors.add(Color.CYAN);
        Mesh cubeMesh = PrimitiveFactory.createCube(100, faceColors);
        cubeMesh.translate(0, 0, 50);
        double angle = 0;
        while (true) {
            angle += 0.05;
            
            // Create a rotation matrix for the current angle
            Matrix3 transform = Matrix3.rotationY(angle).multiply(Matrix3.rotationX(angle * 0.5));
            
            // Render the mesh with the rotation matrix
            renderer.render(cubeMesh.triangles, transform);
            
            // Trigger a redraw
            renderer.repaint();
            
            try { Thread.sleep(16); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}