
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
void main()
{
	vec2 uv = textureCoordinate;

    vec4 color = texture(iChannel0, uv);

    float strength = 16.0;

    float x = (uv.x + 4.0 ) * (uv.y + 4.0 ) * (iTime * 10.0);
	vec4 grain = vec4(mod((mod(x, 13.0) + 1.0) * (mod(x, 123.0) + 1.0), 0.01)-0.005) * strength;

    //if(abs(uv.x - 0.5) < 0.002)
        //color = vec4(0.0);

    //if(uv.x > 0.5)
    //{
    //	grain = 1.0 - grain;
	//	fragColor = color * grain;
    //}
    //else
    //{
	//	fragColor = color + grain;
    //}

    fragColor = color + grain;

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


