import java.awt.Color;
import java.util.Arrays;
import objects.PrimitiveFactory;

public class Cube {
    public static void main(String[] args) {
        DemoSupport.run("Cube", PrimitiveFactory.createCube(120, Arrays.asList(
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.CYAN
        )));
    }
}
