precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
varying vec2                iMouse; 
 
 //Pixelated edges effect
//I'm pretty sure that everyone wants to get rid of pixalated edges and I'm a hundred percent 
//sure that this shader won't help, but it was a pretty interesting exercise.
//Basically, I'm using the Sobel filter to find the edges of the image, and where i've been able to find 
//an edge i apply a "pixelated image effect".
//You can find more informations here: http://en.wikipedia.org/wiki/Sobel_operator

/*
mat3 Gx = mat3 (
    	1.0, 0.0, -1.0,
    	2.0, 0.0, -2.0,
    	1.0, 0.0, -1.0
    );

mat3 Gy = mat3 (
    	-1.0, 2.0, 1.0,
    	0.0, 0.0, 0.0,
    	-1.0, -2.0, -1.0
    );

Another solution might be:
mat3 Gx = mat3 (
    	3.0, 0.0, -3.0,
    	10.0, 0.0, -10.0,
    	3.0, 0.0, -3.0
    );

mat3 Gy = mat3 (
    	3.0, 10.0, 3.0,
    	0.0, 0.0, 0.0,
    	-3.0, -10.0, -3.0
    );

*/

//Get texture's color and return luma
float LookUp (vec2 p, vec2 offset)
{
    float offsetScale = 1.0;   
    vec2 uv = p + offset * offsetScale;
    vec4 col = texture2D(iChannel0, uv/iResolution.xy);
	
	// return luma
    return 0.212 * col.r + 0.715 * col.g + 0.072 * col.b;
}


//Calculate the sobel convolsion
//Return GY, GX, and the length of the gradient/displacement
//If you need to calculate the direction: float alpha = atan (gy/gx);
vec3 SobelFilter (vec2 uv)
{
    //Get the info for all surrounding pixels
    float tl = LookUp (uv, vec2 (-1.0, 1.0));
    float tc = LookUp (uv, vec2 (0.0, 1.0));
    float tr = LookUp (uv, vec2 (1.0, 1.0));
    
    float l = LookUp (uv, vec2 (-1.0, 0.0));
    float c = LookUp (uv, vec2 (0.0, 0.0));
    float r = LookUp (uv, vec2 (1.0, 0.0));
    
    float bl = LookUp (uv, vec2 (-1.0, -1.0));
    float bc = LookUp (uv, vec2 (0.0, -1.0));
    float br = LookUp (uv, vec2 (1.0, -1.0));
    
    //Apply sobel filter kernels for X and Y using the convolusion matrices (GX and GY)
    float gx = tl - tr + 2.0 * l - 2.0 * r + bl - br;
    float gy = -tl - 2.0*tc - tr + bl + 2.0 * bc + br;
    
    //return gx, gy and the length of the gradient
    return vec3 (gx, gy, sqrt (gx * gx + gy * gy));
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{    
    //Uv -> [0,1]
    vec2 uv = fragCoord/iResolution.xy;	    
    float aspectRatio = iResolution.x/iResolution.y;
    
   	//Sobel filter
    vec3 sobelFilter = SobelFilter (fragCoord);
    float gradient = sobelFilter.x * sobelFilter.x + sobelFilter.y * sobelFilter.y;
   
    vec2 offset = vec2 (0.0);
    float pixelVal = 512.0; //normal texture dimension
   	//If our gradient's length is bigger than our min factor than pixelize that zone.
    float minPixelizeFactor = 0.3; //you can play with these 2 variables
    float minPixelVal = 50.0;
    if (sobelFilter.z > minPixelizeFactor)
    {
        pixelVal = minPixelVal;       
    }
    
    vec2 pixelUV = floor (uv * pixelVal + offset)/pixelVal;   
    
    //Get the normal color    
    vec4 tex = texture2D (iChannel0, pixelUV);     
   	vec3 colortest = tex.rgb + vec3 (pow (gradient, 0.4), gradient, 0.0);
    	
	fragColor = vec4(colortest,1.0);

    vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}