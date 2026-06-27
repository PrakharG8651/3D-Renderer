package render3D.rendering;

import render3D.objects.Mesh;
import render3D.rotation.Matrix3;

public class RenderScene {
    private final Mesh[] meshes;
    private final String[] names;
    private final Camera camera;
    private int meshIndex;
    private int viewIndex;
    private boolean autoRotate;
    private boolean shadeEnabled;
    private double angle;

    public RenderScene(Mesh mesh, String name) {
        this(new Mesh[] { mesh }, new String[] { name });
    }

    public RenderScene(Mesh[] meshes, String[] names) {
        this.meshes = meshes;
        this.names = names;
        this.camera = new Camera();
        this.meshIndex = 0;
        this.viewIndex = 0;
        this.autoRotate = true;
        this.shadeEnabled = true;
        this.angle = 0;
    }

    public void translateAll(double dx, double dy, double dz) {
        for (Mesh mesh : meshes) {
            mesh.translate(dx, dy, dz);
        }
    }

    public void update() {
        if (autoRotate) {
            angle += 0.03;
        }
    }

    public void render(Renderer renderer) {
        Matrix3 transform = Matrix3.rotationY(angle).multiply(Matrix3.rotationX(angle * 0.5));
        renderer.render(meshes[meshIndex].triangles, transform, camera, shadeEnabled);
    }

    public Camera getCamera() {
        return camera;
    }

    public String getCurrentName() {
        return names[meshIndex];
    }

    public void toggleAutoRotate() {
        autoRotate = !autoRotate;
    }

    public void toggleShading() {
        shadeEnabled = !shadeEnabled;
    }

    public void nextMesh() {
        meshIndex = (meshIndex + 1) % meshes.length;
    }

    public void selectMesh(int index) {
        if (index >= 0 && index < meshes.length) {
            meshIndex = index;
        }
    }

    public boolean hasMultipleMeshes() {
        return meshes.length > 1;
    }

    public int meshCount() {
        return meshes.length;
    }

    public void nextPresetView() {
        viewIndex = (viewIndex + 1) % 4;
        setPresetView(viewIndex);
    }

    private void setPresetView(int viewIndex) {
        switch (viewIndex) {
            case 0 -> camera.setView(0, 0);
            case 1 -> camera.setView(Math.toRadians(90), 0);
            case 2 -> camera.setView(0, Math.toRadians(-80));
            case 3 -> camera.setView(Math.toRadians(45), Math.toRadians(-30));
            default -> camera.reset();
        }
    }
}
