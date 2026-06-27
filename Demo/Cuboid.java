import java.awt.Color;
import java.util.Arrays;
import render3D.objects.PrimitiveFactory;

public class Cuboid {
    public static void main(String[] args) {
        DemoSupport.run("Cuboid", PrimitiveFactory.createCuboid(180, 90, 120, Arrays.asList(
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.CYAN
        )));
    }
}
