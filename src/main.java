import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.PerlinNoise;
import model.PerlinNoise2D;
import model.TestPerlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main extends Application {
    int rows = 600,
        cols = 450;
    int rDim = 600/2;
    int cDim = 450/3;
    PerlinNoise[][] table;// = new PerlinNoise[rows/rDim][cols/cDim];
    Random rand = new Random();
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Button b = new Button("run");
        canvas = new Canvas(rows, cols);
        b.setOnAction(e -> {
            Platform.runLater(() -> {
                initTable();
                run();
            });
        });
        root.getChildren().addAll(b, canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    public void initTable() {
        table = new PerlinNoise[rows/rDim][cols/cDim];
        xgridlen = cols/table[0].length;
        ygridlen = rows/table.length;
        //initialize tables
        for(int i = 0; i < table.length; i++) {
            for(int j = 0 ; j < table[0].length; j++) {
                table[i][j] = new PerlinNoise();
            }
        }
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

//        int[] xPoints = calcPoints();
//        int[] yPoints = calcPoints();

        int xgrid, ygrid;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                double scl = table[getygrid(i)][getxgrid(j)].noise(gety(i), getx(j));
                int col = getColor(scl);
                g.setFill(Color.rgb(col, col, col));
                g.fillRect(i, j, 1, 1);
            }
        }
    }

    public int getColor(double scale) {
        scale += 1;
        return (int)Math.round(255.0/2.0*scale);
        //return rand.nextInt(255);
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
        return ((j-(j/xgridlen))%xgridlen)/xgridlen;
    }
    public double gety(int i) {
        return ((i-(i/ygridlen))%ygridlen)/ygridlen;
    }
    public int getxgrid(int j) {
        return (int)((j-(j/xgridlen))/xgridlen);
    }
    public int getygrid(int i) {
        return (int)((i-(i/ygridlen))/ygridlen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
