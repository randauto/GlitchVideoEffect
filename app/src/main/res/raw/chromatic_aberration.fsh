precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = fragCoord.xy;
	vec4 texori = texture2D(iChannel0, uv);
	float amount = 0.0;
	float curTime = iTime + 10.; 
	amount = (1.0 + sin(curTime*6.0)) * 0.5;
	amount *= 1.0 + sin(curTime*16.0) * 0.5;
	amount *= 1.0 + sin(curTime*19.0) * 0.5;
	amount *= 1.0 + sin(curTime*27.0) * 0.5;
	amount = pow(amount, 3.0);

	amount *= 0.05;
	
    vec3 col;
    col.r = texture2D( iChannel0, vec2(uv.x+amount,uv.y) ).r;
    col.g = texture2D( iChannel0, uv ).g;
    col.b = texture2D( iChannel0, vec2(uv.x-amount,uv.y) ).b;

	col *= (1.0 - amount * 0.5);
	
    fragColor = vec4(col,1.0);

    
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


void main() {
	mainImage(gl_FragColor, texCoord);
}