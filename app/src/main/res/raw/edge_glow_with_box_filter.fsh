precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;//0 - 100
 
 
 const float kernelWidth = 3.;
const float kernelHeight = 3.;
const float kernelSize = kernelWidth * kernelHeight;

vec2 getUV(vec2 fragCoord) {
	return vec2(fragCoord.x / iResolution.x, fragCoord.y / iResolution.y);
}

bool isEdgeFragment(vec2 fragCoord) {
	float kernel[(int(kernelWidth * kernelHeight))];
	kernel[0] = -1.;
	kernel[1] = -1.;
	kernel[2] = -1.;
	kernel[3] = -1.;
	kernel[4] =  8.;
	kernel[5] = -1.;
	kernel[6] = -1.;
	kernel[7] = -1.;
	kernel[8] = -1.;
	
	vec4 result = vec4(0.);
	vec2 uv = getUV(fragCoord);
	
	for(float y = 0.; y < kernelHeight; ++y) {
		for(float x = 0.; x < kernelWidth; ++x) {
			result += texture2D(iChannel0, vec2(uv.x + (float(int(x - kernelWidth / 2.)) / iResolution.x), 
												uv.y + (float(int(y - kernelHeight / 2.)) / iResolution.y)))
										   * kernel[int(x + (y * kernelWidth))];
		}
	}
	
	return ((length(result) > 0.2) ? true : false);
}

void mainImage( out vec4 fragColor, in vec2 fragCoord ) {
	vec4 actualColor = texture2D(iChannel0, getUV(fragCoord));
	float curtime = iTime + 12.;
	if(!isEdgeFragment(fragCoord)) {
		fragColor = actualColor;
	} else {
		fragColor = vec4(0., 1., 0., 1.) * sin(curtime * 5.) + actualColor * cos(curtime * 5.);
    }

	vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}