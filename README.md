# 3D Render

A lightweight Java/Swing software renderer built from triangles. It currently renders 3D primitives with z-buffering, colored faces, visible borders, camera controls, and flat camera-relative shading.

## Project Structure

```text
src/render3D/Runner.java        App entry point
src/render3D/wireframe/         Basic triangle/wireframe classes: Vertex, Edge, Triangle
src/render3D/rotation/          Rotation and matrix utilities
src/render3D/objects/           Meshes and primitive shape builders
src/render3D/rendering/         Camera, controls, render loop, scene state, and renderer
Demo/                           Individual runnable primitive examples
build/classes/                  Compiled output
render3D.jar                    Pre-built library for reuse
```

## Build

Compile only the rendering library:

```powershell
New-Item -ItemType Directory -Force -Path build/classes | Out-Null
$sourceFiles = Get-ChildItem src/render3D -Recurse -Filter *.java
javac -d build/classes $sourceFiles
```

Compile the library along with the primitive demos:

```powershell
New-Item -ItemType Directory -Force -Path build/classes | Out-Null
$sourceFiles = Get-ChildItem src/render3D, Demo -Recurse -Filter *.java
javac -d build/classes $sourceFiles
```

## Run

Launch the default renderer:

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

## Create the JAR

```powershell
jar cf render3D.jar -C build/classes .
```

## Using the JAR in Another Project

Copy `render3D.jar` into your project's `lib` directory.

Example project:

```text
MyProject/
├── Main.java
└── lib/
    └── render3D.jar
```

Import the required classes:

```java
import render3D.objects.Mesh;
import render3D.objects.PrimitiveFactory;
import render3D.rendering.RenderLoop;
import render3D.rendering.RenderScene;
```

Compile your project:

```powershell
javac -cp lib/render3D.jar Main.java
```

Run your application:

**Windows**

```powershell
java -cp ".;lib/render3D.jar" Main
```

**Linux/macOS**

```bash
java -cp ".:lib/render3D.jar" Main
```

## Controls

```text
Space      Toggle auto-rotation
Arrow Keys Orbit camera
+ / -      Zoom in / out
V          Cycle preset views
R          Reset camera
S          Toggle shading
P          Cycle primitive

1          Cube
2          Cuboid
3          Sphere
4          Disc
5          Cylinder
```
