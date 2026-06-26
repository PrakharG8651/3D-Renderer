import java.awt.Color;
import objects.PrimitiveFactory;

public class Cylinder {
    public static void main(String[] args) {
        DemoSupport.run("Cylinder", PrimitiveFactory.createCylinder(
            80,
            150,
            48,
            Color.CYAN,
            Color.BLUE,
            Color.GREEN
        ));
    }
}
