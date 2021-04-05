
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
mat2 RotateMat(float angle)
{
	float si = sin(angle);
	float co = cos(angle);
	return mat2(co, si, -si, co);
}


vec3 Colour(in float h)
{
	h = h * 4.0;
	return clamp( abs(mod(h+vec3(0.,4.,2.),6.)-3.)-1., 0., 1. );
}

void main()
{
	float time = iTime;

	// Rough panning...
	vec2 fragCoord = iResolution.xy * textureCoordinate;
	vec2 pixel = (fragCoord.xy - iResolution.xy*.5)/iResolution.xy
	+ vec2(0.0,.1-smoothstep(9.0, 12.0, time)*.35
	+ smoothstep(18.0, 20.0, time)*.15);

	vec3 col;
	float n;
	float inc = (smoothstep(17.35, 18.5, time)-smoothstep(18.5, 21.0, time)) * (time-16.0) * 0.1;

	mat2 rotMat = RotateMat(inc);
	for (int i = 1; i < 50; i++)
	{
		pixel = pixel * rotMat;

		float depth = 40.0+float(i) + smoothstep(18.0, 21.0, time)*65.;

		vec2 uv = pixel * depth/210.0;

		// Shifting the pan to the text near the end...

		// And shifts to the right again for the last line of text at 23:00!
		col = texture( iChannel0, fract(uv+vec2(.5 + smoothstep(20.0, 21.0, time)*.11
		 + smoothstep(23.0, 23.5, time)*.04
		  , .7-smoothstep(20.0, 21.0, time)*.2))).rgb;
		  col = mix(col, col * Colour((float(i)/50.0+iTime)), smoothstep(18.5, 21.5, time));

		  if ((1.0-(col.y*col.y)) < float(i+1) / 50.0)
		  {
		  	break;
		  }

	}

	// Some contrast...
	col = min(col*col*1.5, 1.0);
	// Fade to red evil face...
	float gr = smoothstep(17., 16., time) + smoothstep(18.5, 21.0, time);
	float bl = smoothstep(17., 15., time) + smoothstep(18.5, 21.0, time);
	col = col * vec3(1.0, gr, bl);
	// Cut off the messy end...
	col *= smoothstep(29.5, 28.2, time);
	fragColor = vec4(col, 1.0);

	  vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


