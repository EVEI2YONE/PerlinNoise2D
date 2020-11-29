# PerlinNoise2D
<h1>Perlin Noise generator</h1>
<img src="images/perlin.png">
<h3>Jar location: out/artifacts/basic_perlin/basic_perlin.jar</h3>
<h3>How to use</h3>
<p>
1. Create new Perlin Noise object: <br></p>
       <p style="margin-left:10%; margin-right:10%;">PerlinNoise perlin = new PerlinNoise();<br></p>
2. Set canvas dimensions: <br>
       perlin.setDimension(width, height);<br>
3. Set subgrid dimensions (i.e. 32 x 32 grid in your canvas: <br>
       perlin.setGridDimension(gridWidth, gridHeight);<br>
4. Call noise funciton to get a value from 0.0 to 1.0: <br>
       double scale = perlin.noise(i, j);<br>
