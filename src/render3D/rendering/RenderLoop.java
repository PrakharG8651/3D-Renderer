package render3D.rendering;

import javax.swing.JFrame;

public class RenderLoop {
    public static void start(String titlePrefix, RenderScene scene, int width, int height) {
        JFrame frame = new JFrame(titlePrefix + " - " + scene.getCurrentName());
        Renderer renderer = new Renderer(width, height);
        frame.add(renderer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new Controls(frame, titlePrefix, scene));
        frame.setVisible(true);

        while (true) {
            scene.update();
            scene.render(renderer);
            renderer.repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
