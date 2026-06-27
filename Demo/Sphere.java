import java.awt.Color;
import render3D.objects.PrimitiveFactory;

public class Sphere {
    public static void main(String[] args) {
        DemoSupport.run("Sphere", PrimitiveFactory.createSphere(90, 24, 48, Color.ORANGE));
    }
}
