precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
 
 
 // by Nikos Papadopoulos, 4rknova / 2015
// WTFPL

#define THRESHOLD vec3(1.,.92,.1)

vec3 texsample(in vec2 uv)
{
    return texture2D(iChannel0, uv).xyz;
}

vec3 texfilter(in vec2 uv)
{
    vec3 val = texsample(uv);
    if (val.x < THRESHOLD.x) val.x = 1. - val.x;
    if (val.y < THRESHOLD.y) val.y = 1. - val.y;
    if (val.z < THRESHOLD.z) val.z = 1. - val.z;
	return val;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = fragCoord.xy / iResolution.xy;
    // uv.y = 1. - uv.y;	
    float m = 0.5 / iResolution.x;
    
    float l = smoothstep(0., 1. / iResolution.y, abs(m - uv.x));
    
    vec3 cf = texfilter(uv);
    vec3 cl = texsample(uv);
    vec3 cr = (uv.x < m ? cl : cf) * l;
    
    fragColor = vec4(cr, 1);

    vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}

void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}