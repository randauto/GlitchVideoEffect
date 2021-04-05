
#define texture(a,b) texture2D(a,fract(b))
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float intensity;
const int lookupSize = 64;
const float errorCarry = 0.3;

float getGrayscale(vec2 coords){
	vec2 uv = coords / iResolution.xy;
	uv.y = 1.0-uv.y;
	vec3 sourcePixel = texture(iChannel0, uv).rgb;
	return length(sourcePixel*vec3(0.2126,0.7152,0.0722));
}

void main() {
	

    vec2 fragCoord = vec2(textureCoordinate.x * iResolution.x, iResolution.y - textureCoordinate.y * iResolution.y);//将y坐标反转，

	int topGapY = int(iResolution.y - fragCoord.y);

	int cornerGapX = int((fragCoord.x < 10.0) ? fragCoord.x : iResolution.x - fragCoord.x);
	int cornerGapY = int((fragCoord.y < 10.0) ? fragCoord.y : iResolution.y - fragCoord.y);
	int cornerThreshhold = ((cornerGapX == 0) || (topGapY == 0)) ? 5 : 4;
	vec3 tempcolor = vec3(0,0,0);
	if (cornerGapX+cornerGapY < cornerThreshhold) {

		tempcolor = vec3(0,0,0);

	} else if (topGapY < 20) {

			if (topGapY == 19) {

				tempcolor = vec3(0,0,0);

			} else {

				tempcolor = vec3(1,1,1);

			}

	} else {

		float xError = 0.0;
		for(int xLook=0; xLook<lookupSize; xLook++){
			float grayscale = getGrayscale(fragCoord.xy + vec2(-lookupSize+xLook,0));
			grayscale += xError;
			float bit = grayscale >= 0.5 ? 1.0 : 0.0;
			xError = (grayscale - bit)*errorCarry;
		}

		float yError = 0.0;
		for(int yLook=0; yLook<lookupSize; yLook++){
			float grayscale = getGrayscale(fragCoord.xy + vec2(0,-lookupSize+yLook));
			grayscale += yError;
			float bit = grayscale >= 0.5 ? 1.0 : 0.0;
			yError = (grayscale - bit)*errorCarry;
		}

		float finalGrayscale = getGrayscale(fragCoord.xy);
		finalGrayscale += xError*0.5 + yError*0.5;
		float finalBit = finalGrayscale >= 0.5 ? 1.0 : 0.0;

		tempcolor = vec3(finalBit,finalBit,finalBit);

		
	}
	// gl_FragColor = vec4(tempcolor,1.);
	 vec2 uv = textureCoordinate;
      vec4 c0 = texture2D(iChannel0,uv);
	gl_FragColor = vec4(c0.rgb*(1.0-intensity) + tempcolor*intensity,1.);
}


