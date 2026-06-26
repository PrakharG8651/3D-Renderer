import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Runner {
    public static void main(String[] args) {
        final boolean[] autoRotate = { true };
        final boolean[] shadeEnabled = { true };
        final int[] viewIndex = { 0 };
        Camera camera = new Camera();

        // 1. Setup the window
        JFrame frame = new JFrame("3D Engine");
        Renderer renderer = new Renderer(800, 600);
        frame.add(renderer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        autoRotate[0] = !autoRotate[0];
                        break;
                    case KeyEvent.VK_S:
                        shadeEnabled[0] = !shadeEnabled[0];
                        break;
                    case KeyEvent.VK_LEFT:
                        camera.orbit(-0.1, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        camera.orbit(0.1, 0);
                        break;
                    case KeyEvent.VK_UP:
                        camera.orbit(0, -0.1);
                        break;
                    case KeyEvent.VK_DOWN:
                        camera.orbit(0, 0.1);
                        break;
                    case KeyEvent.VK_EQUALS:
                    case KeyEvent.VK_ADD:
                        camera.zoom(-20);
                        break;
                    case KeyEvent.VK_MINUS:
                    case KeyEvent.VK_SUBTRACT:
                        camera.zoom(20);
                        break;
                    case KeyEvent.VK_R:
                        camera.reset();
                        break;
                    case KeyEvent.VK_V:
                        viewIndex[0] = (viewIndex[0] + 1) % 4;
                        setPresetView(camera, viewIndex[0]);
                        break;
                    default:
                        break;
                }
            }
        });
        frame.setVisible(true);

        // 2. Define your cube mesh
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
            if (autoRotate[0]) {
                angle += 0.03;
            }
            
            // Create a rotation matrix for the current angle
            Matrix3 transform = Matrix3.rotationY(angle).multiply(Matrix3.rotationX(angle * 0.5));
            
            // Render the mesh with the rotation matrix
            renderer.render(cubeMesh.triangles, transform, camera, shadeEnabled[0]);
            
            // Trigger a redraw
            renderer.repaint();
            
            try { Thread.sleep(16); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private static void setPresetView(Camera camera, int viewIndex) {
        switch (viewIndex) {
            case 0->camera.setView(0, 0);
            case 1->camera.setView(Math.toRadians(90), 0);
            case 2->camera.setView(0, Math.toRadians(-80));
            case 3->camera.setView(Math.toRadians(45), Math.toRadians(-30));
            default->camera.reset();
        }
    }
}
