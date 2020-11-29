# PerlinNoise2D
<h1>Perlin Noise generator</h1>
<img src="images/perlin.png">
<h3>Jar location: out/artifacts/basic_perlin/basic_perlin.jar</h3>
<h3>How to use</h3>
<p>Create new Perlin Noise object: PerlinNoise perlin = new PerlinNoise();<br>
Set canvas dimensions: perlin.setDimension(width, height);<br>
Set subgrid dimensions (i.e. 32 x 32 grid in your canvas: perlin.setGridDimension(gridWidth, gridHeight);<br>
Call noise funciton to get a value from 0.0 to 1.0: double scale = perlin.noise(i, j);<br>;
