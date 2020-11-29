package perlin;

public class PerlinNoise {
    public void setGradient(GradientType type) {
        PerlinNoiseGrid.gtype = type;
        init();
    }

    public enum GradientType { CROSS, PLUS, RANDOM }
    private int
        width = 1000,
        height = 1000;
    private int
        wgrids = 32,
        hgrids = 32;
    private int
        wDim,// = width/wgrids,
        hDim;// = height/hgrids;

    private PerlinNoiseGrid[][] table;

    public PerlinNoise() {
        wDim = width/wgrids;
        hDim = height/hgrids;
        init();
    }

    public void generateNoise() {
        init();
    }
    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
        wDim = width/wgrids;
        hDim = height/hgrids;
        init();
    }
    public void setGridDimension(int wGrids, int hGrids) {
        wgrids = wGrids;
        hgrids = hGrids;
        wDim = width/wgrids;
        hDim = height/wgrids;
        init();
    }
    private void init() {
        initTable();
        createGrid();
        checkConnections();
    }

    private void initTable() {
        int wOff = 0, hOff = 0;
        if(height % hDim > 0)
            hOff = 1;
        if(width % wDim > 0)
            wOff = 1;
        table = new PerlinNoiseGrid[height/hDim + hOff][width/wDim + wOff];
        xgridlen = width/(table[0].length-wOff);
        ygridlen = height/(table.length-hOff);
    }
    private void createGrid() {
        //initialize tables
        for(int i = 0; i < table.length; i++) {
            for(int j = 0 ; j < table[0].length; j++) {
                table[i][j] = new PerlinNoiseGrid();
            }
        }
    }
    private void checkConnections() {
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

    private double xgridlen;// = cols/table[0].length;
    private double ygridlen;// = rows/table.length;

    public double noise(int i, int j) {
        boolean outX = (j / width) > 0;
        boolean outY = (i / height) > 0;
        if(outX) {
            if(j / width % 2 == 0)
                j = (j % width);
            else
                j = width-1 - (j % width);
        }
        if(outY) {
            if(i / height % 2 == 0)
                i = (i % height);
            else
                i = height-1 - (i % height);
        }
        int xgrid = getxgrid(j);
        int ygrid = getygrid(i);
        double x = getx(j);
        double y = gety(i);
        return (table[ygrid][xgrid].noise(y, x) + 1.0) / 2.0;
    }

    private double getx(int j) {
        if(xgridlen-1 <= 0)
            return 0;
        return ((j)%xgridlen)/(xgridlen-1);
    }
    private double gety(int i) {
        if(ygridlen-1 <= 0)
            return 0;
        return ((i)%ygridlen)/(ygridlen-1);
    }
    private int getxgrid(int j) {
        return (int)((j)/xgridlen);
    }
    private int getygrid(int i) {
        return (int)((i)/ygridlen);
    }
}
