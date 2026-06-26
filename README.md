# 3D Render

A small Java/Swing software renderer built from triangles. It currently renders a cube with z-buffering, colored faces, visible borders, camera controls, and flat camera-relative shading.

## Project Structure

```text
Runner.java        App entry point
wireframe/         Basic triangle/wireframe pieces: Vertex, Edge, Triangle
rotation/          Rotation/math helpers
objects/           Meshes and primitive shape builders
rendering/         Camera and software renderer
build/classes/     Compiled output
```

## Build

```powershell
javac -d build/classes Runner.java wireframe/*.java rotation/*.java objects/*.java rendering/*.java
```

## Run

```powershell
java -cp build/classes Runner
```

## Controls

```text
Space      toggle cube auto-rotation
Arrow keys orbit camera
+ / -      zoom in / out
V          cycle preset views
R          reset camera
S          toggle shading
```
