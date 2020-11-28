package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class PerlinNoise2D {
    int rowLen = 1000;
    int colLen = 1000;
    Random rand = new Random();

    int x0 = 0, y0 = 0, x1 = colLen, y1 = rowLen;
    int[] opt = {1, -1};
    double[]
        g0={choose(opt), choose(opt)},
        g1={choose(opt), choose(opt)},
        g2={choose(opt), choose(opt)},
        g3={choose(opt), choose(opt)};
    int[]
        d0={x0, y0},
        d1={x1, y0},
        d2={x0, y1},
        d3={x1, y1};
    public double noise2d(int y, int x) {
        updateGrid(x, y);
        double
            d0x = x-d0[0], d0y = y-d0[1],
            d1x = x-d1[0], d1y = y-d1[1],
            d2x = x-d2[0], d2y = y-d2[1],
            d3x = x-d3[0], d3y = y-d3[1];
        d0x /= rowLen; d0y /= colLen;
        d1x /= rowLen; d1y /= colLen;
        d2x /= rowLen; d2y /= colLen;
        d3x /= rowLen; d3y /= colLen;
        double
            s=dot(g0, new double[]{d0x, d0y}),//x-d0[0], y-d0[1]}),
            t=dot(g1, new double[]{d1x, d1y}),//x-d1[0], y-d1[1]}),
            u=dot(g2, new double[]{d2x, d2y}),//x-d2[0], y-d2[1]}),
            v=dot(g3, new double[]{d3x, d3y});//x-d3[0], y-d3[1]});
        //s/=rowLen; t/=colLen; u/=rowLen; v/=colLen;
        double Sx = 3*Math.pow(d0x, 2) - 2*Math.pow(d0x, 3);
        double a = s + Sx*(t-s);
        double b = u + Sx*(v-u);

        double Sy = 3*Math.pow(d0y, 2) - 2*Math.pow(d0y, 3);
        double z = a + Sy*(b-a);
        return Math.abs(z);

//        double a = s + weighted(s, t);
//        double b = u + weighted(u, v);
//        double avg = a + weighted(a, b);
//        return avg;
    }
    public void updateGrid(int x, int y) {}

    public double weighted(double s, double t) {
        return (3*Math.pow(t-s, 2)-2*Math.pow(t-s, 3));
    }

    public double average(String type, double a, double b) {
        switch(type) {
            case "row": return (a+b)/2.0;
            case "col": return (a+b)/2.0;
            default:
                System.out.println("incorrect type");
                return 0.0;
        }
    }

    public double choose(int...values) {
        return values[rand.nextInt(values.length)];
    }

    public double dot(double[]...vectors) {
        double sum = 0;
        for(int j = 0; j < vectors[0].length; j++) {
            double product = 1;
            for(int i = 0; i < vectors.length; i++) {
                product *= vectors[i][j];
            }
            sum += product;
        }
        return sum;
    }
}



















