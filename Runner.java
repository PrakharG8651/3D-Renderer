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
        List<Triangle> mesh = new ArrayList<>();
        mesh.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100), new Vertex(-100, 100, -100), Color.WHITE));
        mesh.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100), new Vertex(100, -100, -100), Color.RED));
        mesh.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100), new Vertex(100, 100, 100), Color.GREEN));
        mesh.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100), new Vertex(-100, -100, 100), Color.BLUE));

        // 3. The Animation Loop
        double angle = 0;
        while (true) {
            angle += 0.05;
            
            // Create a rotation matrix for the current angle
            Matrix3 transform = Matrix3.rotationY(angle).multiply(Matrix3.rotationX(angle * 0.5));
            
            // Render the mesh with the rotation matrix
            renderer.render(mesh, transform);
            
            // Trigger a redraw
            renderer.repaint();
            
            try { Thread.sleep(16); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}