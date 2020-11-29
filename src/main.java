import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GradientPoint;
import model.PerlinNoise;
import model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main extends Application {
    int width = 600,
        height = 450;
    int
        wDim = width/(width/100),
        hDim = height/(height/150);
    PerlinNoise[][] table;// = new PerlinNoise[rows/rDim][cols/cDim];
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Button b = new Button("run");
        canvas = new Canvas(width, height);
        initTable();
        b.setOnAction(e -> {
            Platform.runLater(() -> {
                createGrid();
                checkConnections();
                run();
                parseGradientVectors();
            });
        });
        root.getChildren().addAll(b, canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    public void initTable() {
        int wOff = 0, hOff = 0;
        if(height % hDim > 0)
            hOff = 1;
        if(width % wDim > 0)
            wOff = 1;
        table = new PerlinNoise[height/hDim + hOff][width/wDim + wOff];
        xgridlen = width/(table[0].length-wOff);
        ygridlen = height/(table.length-hOff);
    }

    public void createGrid() {
        //initialize tables
        for(int i = 0; i < table.length; i++) {
            for(int j = 0 ; j < table[0].length; j++) {
                table[i][j] = new PerlinNoise();
            }
        }
    }
    public void checkConnections() {
        //check connections
        for(int i = 0; i < table.length; i++) {
            for(int j = 0 ; j < table[0].length; j++) {
                if(j > 0) { //update to left neighbor's gridpoint
                    table[i][j].topleft = table[i][j-1].topright;
                    table[i][j].bottomleft = table[i][j-1].bottomright;
                }
                if(i > 0) {
                    table[i][j].topleft = table[i-1][j].bottomleft;
                    table[i][j].topright = table[i-1][j].bottomright;
                }
            }
        }
    }

    double xgridlen;// = cols/table[0].length;
    double ygridlen;// = rows/table.length;

    Canvas canvas;
    public void run() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setStroke(Color.BLACK);

        int xgrid, ygrid;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                xgrid = getxgrid(j);
                ygrid = getygrid(i);
                double x = getx(j);
                double y = gety(i);
                double scl = table[ygrid][xgrid].noise(y, x);
                int col = getColor(scl);
                g.setFill(Color.rgb(col, col, col));
                g.fillRect(j, i, 1, 1);
            }
        }
    }
    public void parseGradientVectors() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLUE);
        int r = 2;
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table[i].length; j++) {
                int x = (int)Math.round(xgridlen) * j;
                int y = (int)Math.round(ygridlen) * i;
                g.fillOval(x-r, y-r, r*2, r*2);
                displayGradientVectors(x, y, i, j);

            }
        }
    }
    public void displayGradientVectors(int x, int y, int i, int j) {
        GradientPoint topleft = table[i][j].topleft;
        GradientPoint topright = table[i][j].topleft;
        GradientPoint bottomleft = table[i][j].topleft;
        GradientPoint bottomright = table[i][j].topleft;

        int r = 10;
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setStroke(Color.RED);
        g.strokeLine(x, y, r*topleft.gradPoint.x+x, r*topleft.gradPoint.y+y);
        g.strokeLine(x, y, r*topright.gradPoint.x+x, r*topright.gradPoint.y+y);
        g.strokeLine(x, y, r*bottomleft.gradPoint.x+x, r*bottomleft.gradPoint.y+y);
        g.strokeLine(x, y, r*bottomright.gradPoint.x+x, r*bottomright.gradPoint.y+y);
    }

    public int getColor(double scale) {
        scale += 1.0;
        scale /= 2.0;
        return (int)Math.round(255.0*scale);
    }

    public int[] calcPoints() {
        int calcs = 360;
        initTable();
        List<Integer> list = new ArrayList();
        for(int i = 0; i < calcs; i++) {

        }

        int[] xPoints = new int[calcs];
        for(int i = 0; i < calcs; i++) {
            xPoints[i] = list.get(i);
        }
        return xPoints;
    }

    public double getx(int j) {
        if(xgridlen-1 <= 0)
            return 0;
        return ((j)%xgridlen)/(xgridlen-1);
    }
    public double gety(int i) {
        if(ygridlen-1 <= 0)
            return 0;
        return ((i)%ygridlen)/(ygridlen-1);
    }
    public int getxgrid(int j) {
        return (int)((j)/xgridlen);
    }
    public int getygrid(int i) {
        return (int)((i)/ygridlen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
