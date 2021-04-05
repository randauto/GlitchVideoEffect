precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
varying vec2                iMouse; 
 
void mainImage( out vec4 fragColor, in vec2 f ) {
	vec2 oriuv = (f.xy / iResolution.xy);
	vec4 oricol = texture2D(iChannel0, oriuv);
	vec2 uv = (f.xy / iResolution.xy) * 1. - 1.;
    vec4 s = texture2D(iChannel0, vec2(fract(iTime * .01), 1. + uv.y));
    vec4 n = texture2D(iChannel0, uv + 1.);
    vec2 d =  (iResolution.x / 8.) * (1. / iResolution.xy);
    vec4 p = texture2D(iChannel0, d * floor(uv / d));
	fragColor = ((vec4(1., .2, 0., 1.) * n + s * .5) + p * .3);
    
    fragColor = vec4(oricol.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}