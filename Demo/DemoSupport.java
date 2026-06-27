import render3D.objects.Mesh;
import render3D.rendering.RenderLoop;
import render3D.rendering.RenderScene;

public class DemoSupport {
    public static void run(String title, Mesh mesh) {
        RenderScene scene = new RenderScene(mesh, title);
        scene.translateAll(0, 0, 50);
        RenderLoop.start("3D Engine Demo", scene, 1000, 700);
    }
}
