package render3D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import render3D.objects.Mesh;
import render3D.objects.PrimitiveFactory;
import render3D.rendering.RenderLoop;
import render3D.rendering.RenderScene;

public class Runner {
    public static void main(String[] args) {
        List<Color> faceColors = new ArrayList<>();
        faceColors.add(Color.BLUE);
        faceColors.add(Color.RED);
        faceColors.add(Color.GREEN);
        faceColors.add(Color.YELLOW);
        faceColors.add(Color.MAGENTA);
        faceColors.add(Color.CYAN);

        final Mesh[] primitives = {
            PrimitiveFactory.createCube(100, faceColors),
            PrimitiveFactory.createCuboid(160, 90, 110, faceColors),
            PrimitiveFactory.createSphere(75, 18, 36, Color.ORANGE),
            PrimitiveFactory.createDisc(85, 48, Color.PINK),
            PrimitiveFactory.createCylinder(70, 130, 40, Color.CYAN, Color.BLUE, Color.GREEN)
        };
        String[] primitiveNames = { "Cube", "Cuboid", "Sphere", "Disc", "Cylinder" };

        RenderScene scene = new RenderScene(primitives, primitiveNames);
        scene.translateAll(0, 0, 50);
        RenderLoop.start("3D Engine", scene, 800, 600);
    }
}
