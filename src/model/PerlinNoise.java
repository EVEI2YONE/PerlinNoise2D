package model;

import java.util.Random;

public class PerlinNoise {
    static Random rand = new Random(1);

    public GradientPoint topleft, topright, bottomleft, bottomright;

    public PerlinNoise() {
        init();
    }
    public void init() {
        topleft = new GradientPoint();
        topright = new GradientPoint();
        bottomleft = new GradientPoint();
        bottomright = new GradientPoint();

        setupGradientPoints();
        setupGradientVectors();
    }
    //Gradient points are from 0 to 1 for x and y
    public void setupGradientPoints() {
        topleft.loc = new Point(0, 0);
        topright.loc = new Point(1, 0);
        bottomleft.loc = new Point(0, 1);
        bottomright.loc = new Point(1, 1);
    }
    public void setupGradientVectors() {
        topleft.gradPoint = newPoint();
        topright.gradPoint = newPoint();
        bottomleft.gradPoint = newPoint();
        bottomright.gradPoint = newPoint();
    }

    public double noise(double y, double x) {
         double
             d0x = x,
             d0y = y,
             d1x = x-1,
             d1y = y,
             d2x = x,
             d2y = y-1,
             d3x = x-1,
             d3y = y-1;
         double
             a = dot(topleft.gradPoint.x, topleft.gradPoint.y, d0x, d0y),
             b = dot(topright.gradPoint.x, topright.gradPoint.y, d1x, d1y),
             c = dot(bottomleft.gradPoint.x, bottomleft.gradPoint.y, d2x, d2y),
             d = dot(bottomright.gradPoint.x, bottomright.gradPoint.y, d3x, d3y);
         double
             ab = interpolate(a, b, x),
             cd = interpolate(c, d, x),
             z = interpolate(ab, cd, y);
         return z;
    }

    public double interpolate(double start, double end, double ratio) {
        return start + ratio*(end-start);
    }
    public double dot(double x1, double y1, double x2, double y2) {
        return x1*x2 + y1*y2;
    }

    public Point newPoint() {
        double angle = rand.nextDouble()*2.0*Math.PI;
        double x = Math.cos(angle);
        double y = Math.sin(angle);
        return new Point(x, y);
        //return new Point(choose(), choose());
    }

    double[] opt = {1, -1};
    public double choose() {
        return opt[rand.nextInt(2)];
    }
}
