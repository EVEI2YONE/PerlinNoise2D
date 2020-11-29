# PerlinNoise2D
<h1>Perlin Noise generator</h1>
<img src="images/perlin.png">
<h3>Jar location: out/artifacts/basic_perlin/basic_perlin.jar</h3>
<h3>How to use</h3>
<p>
1. Create new Perlin Noise object:</p>
       <p>PerlinNoise perlin = new PerlinNoise();<br></p>
<p>
2. Set canvas dimensions:</p>
       <p>perlin.setDimension(width, height);<br></p>
<p>
3. Set subgrid dimensions (i.e. 32 x 32 grid in your canvas:</p>
       <p>perlin.setGridDimension(gridWidth, gridHeight);<br></p>
<p>
4. Call noise funciton to get a value from 0.0 to 1.0:</p>
       <p>double scale = perlin.noise(i, j);<br></p>
