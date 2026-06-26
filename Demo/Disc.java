import java.awt.Color;
import objects.PrimitiveFactory;

public class Disc {
    public static void main(String[] args) {
        DemoSupport.run("Disc", PrimitiveFactory.createDisc(100, 64, Color.PINK));
    }
}
