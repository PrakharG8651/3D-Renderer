package rendering;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class Controls extends KeyAdapter {
    private final JFrame frame;
    private final String titlePrefix;
    private final RenderScene scene;

    public Controls(JFrame frame, String titlePrefix, RenderScene scene) {
        this.frame = frame;
        this.titlePrefix = titlePrefix;
        this.scene = scene;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE -> scene.toggleAutoRotate();
            case KeyEvent.VK_S -> scene.toggleShading();
            case KeyEvent.VK_LEFT -> scene.getCamera().orbit(-0.1, 0);
            case KeyEvent.VK_RIGHT -> scene.getCamera().orbit(0.1, 0);
            case KeyEvent.VK_UP -> scene.getCamera().orbit(0, -0.1);
            case KeyEvent.VK_DOWN -> scene.getCamera().orbit(0, 0.1);
            case KeyEvent.VK_EQUALS, KeyEvent.VK_ADD -> scene.getCamera().zoom(-20);
            case KeyEvent.VK_MINUS, KeyEvent.VK_SUBTRACT -> scene.getCamera().zoom(20);
            case KeyEvent.VK_R -> scene.getCamera().reset();
            case KeyEvent.VK_V -> scene.nextPresetView();
            case KeyEvent.VK_P -> {
                if (scene.hasMultipleMeshes()) {
                    scene.nextMesh();
                    updateTitle();
                }
            }
            case KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5,
                 KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9 -> {
                int index = e.getKeyCode() - KeyEvent.VK_1;
                if (index < scene.meshCount()) {
                    scene.selectMesh(index);
                    updateTitle();
                }
            }
            default -> {
            }
        }
    }

    private void updateTitle() {
        frame.setTitle(titlePrefix + " - " + scene.getCurrentName());
    }
}
