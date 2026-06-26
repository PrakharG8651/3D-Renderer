public class Camera {
    private double yaw;
    private double pitch;
    private double distance;
    private double focalLength;

    public Camera() {
        this.yaw = 0;
        this.pitch = 0;
        this.distance = 0;
        this.focalLength = 400;
    }

    public Vertex apply(Vertex v) {
        Matrix3 viewRotation = Matrix3.rotationY(yaw).multiply(Matrix3.rotationX(pitch));
        Vertex out = viewRotation.transform(v);
        out.z += distance;
        return out;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public void orbit(double deltaYaw, double deltaPitch) {
        yaw += deltaYaw;
        pitch += deltaPitch;
    }

    public void zoom(double amount) {
        distance += amount;
        if (distance < -250) distance = -250;
        if (distance > 800) distance = 800;
    }

    public void setView(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void reset() {
        yaw = 0;
        pitch = 0;
        distance = 0;
    }
}
