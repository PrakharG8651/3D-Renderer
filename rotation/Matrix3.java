package rotation;

import wireframe.Vertex;

public class Matrix3 {
    double[] values;

    // Constructor: Takes an array of 9 doubles for a 3x3 matrix
    public Matrix3(double[] values) {
        this.values = values;
    }

    // Multiply this matrix by another matrix
    public Matrix3 multiply(Matrix3 other) {
        double[] result = new double[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col] += this.values[row * 3 + i] * other.values[i * 3 + col];
                }
            }
        }
        return new Matrix3(result);
    }

    // Transform a single vertex by this matrix
    public Vertex transform(Vertex in) {
        return new Vertex(
            in.x * values[0] + in.y * values[3] + in.z * values[6],
            in.x * values[1] + in.y * values[4] + in.z * values[7],
            in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }

    // Factory method for X-axis rotation
    public static Matrix3 rotationX(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return new Matrix3(new double[] {
            1, 0, 0,
            0, c, -s,
            0, s, c
        });
    }

    // Factory method for Y-axis rotation
    public static Matrix3 rotationY(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return new Matrix3(new double[] {
            c, 0, s,
            0, 1, 0,
            -s, 0, c
        });
    }
}
