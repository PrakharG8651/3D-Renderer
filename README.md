# 3D Render

A small Java/Swing software renderer built from triangles. It currently renders a cube with z-buffering, colored faces, visible borders, camera controls, and flat camera-relative shading.

## Project Structure

```text
src/render3D/Runner.java        App entry point
src/render3D/wireframe/         Basic triangle/wireframe pieces: Vertex, Edge, Triangle
src/render3D/rotation/          Rotation/math helpers
src/render3D/objects/           Meshes and primitive shape builders
src/render3D/rendering/         Camera, controls, render loop, scene state, and software renderer
Demo/              Individual runnable primitive examples
build/classes/     Compiled output
```

## Build

```powershell
$sourceFiles = Get-ChildItem src/render3D -Recurse -Filter *.java
javac -d build/classes $sourceFiles
```

To include the primitive demos:

```powershell
$sourceFiles = Get-ChildItem src/render3D, Demo -Recurse -Filter *.java
javac -d build/classes $sourceFiles
```

## Run

```powershell
java -cp build/classes render3D.Runner
```

Run an individual primitive demo:

```powershell
java -cp build/classes Cube
java -cp build/classes Cuboid
java -cp build/classes Sphere
java -cp build/classes Disc
java -cp build/classes Cylinder
```

## Controls

```text
Space      toggle cube auto-rotation
Arrow keys orbit camera
+ / -      zoom in / out
V          cycle preset views
R          reset camera
S          toggle shading
P          cycle primitive
1          select cube
2          select cuboid
3          sphere
4          select disc
5          select cylinder
```
