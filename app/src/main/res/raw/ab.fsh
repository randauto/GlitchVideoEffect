precision mediump float;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = fragCoord.xy;

	float amount = 0.0;
	
	amount = (1.0 + sin(iGlobalTime*6.0)) * 0.5;
	amount *= 1.0 + sin(iGlobalTime*16.0) * 0.5;
	amount *= 1.0 + sin(iGlobalTime*19.0) * 0.5;
	amount *= 1.0 + sin(iGlobalTime*27.0) * 0.5;
	amount = pow(amount, 3.0);

	amount *= 0.05;
	
    vec3 col;
    col.r = texture2D( sTexture, vec2(uv.x+amount,uv.y) ).r;
    col.g = texture2D( sTexture, uv ).g;
    col.b = texture2D( sTexture, vec2(uv.x-amount,uv.y) ).b;

	col *= (1.0 - amount * 0.25);
	
    fragColor = vec4(col,1.0);
}


void main() {
	mainImage(gl_FragColor, textureCoordinate);
}