precision highp float;

uniform vec3                iResolution;
uniform float               iTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;
uniform float               intensity;
/*by musk License Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.

	Draws 40 layers of antialiased circle patterns.
	Use mouse to set alpha, size, distribution.
	Test it with different images, videos, webcams.

	Inspired by the processing example

*/

//2D texture based 3 component 1D, 2D, 3D noise
vec3 noise(float p){return texture2D(iChannel0,vec2(p/iResolution.x,.0)).xyz;}
vec3 noise(vec2 p){return texture2D(iChannel0,p/iResolution.xy).xyz;}
vec3 noise(vec3 p){float m = mod(p.z,1.0);float s = p.z-m; float sprev = s-1.0;if (mod(s,2.0)==1.0) { s--; sprev++; m = 1.0-m; };return mix(texture2D(iChannel0,p.xy/iResolution.xy+noise(sprev).yz).xyz,texture2D(iChannel0,p.xy/iResolution.xy+noise(s).yz).xyz,m);}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;

	//uv.y*=-1.0;
	vec2 mouse = vec2(intensity,intensity)/ iResolution.xy;

	//vec3 color = texture2D(iChannel1,uv).xyz;
	vec3 color = vec3(1.0);

	float var_size = 1.0;
	float var_alpha = .4;
	float var_distr = 1.0;

	// if (iMouse.w>.5)
	// {
	// 	var_size = mouse.x - mod(mouse.x,.1)+.01;
	// 	var_size *= 3.0;
	// 	var_distr = mod(mouse.x,.1)*30.0;
	// 	var_distr -= mod(var_distr,1.0);
	// 	var_distr -= .75;
	// 	var_size*=2.0;
	// 	var_alpha = mouse.y;
	// }

	for (float q=.0; q<1.0; q+=.01)
	{
		float i = q;
		vec2 size = vec2(1.00-pow(i,var_distr)*.97) * var_size;
		size.x *= iResolution.y/iResolution.x;

		vec2 m = mod(uv+noise(q).yz*24.0,size);
		vec2 s = uv-m;

		vec2 offs = (.2+.6*noise(s*1466.1550+vec2(.1)).xy);
		vec2 p = m/size - offs;
		vec3 sample_color = texture2D(iChannel0,s + .5*size).xyz;

		float alpha = 1.0-(length(p)-.2)*iResolution.y*length(size)*.5;
		alpha = min(var_alpha,max(.0,alpha));
		color = mix(color,sample_color,alpha);

	}

	fragColor = vec4(color,1.0);

	vec2 uvori = fragCoord.xy / iResolution.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    fragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


void main() {
    mainImage(gl_FragColor, texCoord * iResolution.xy);
}