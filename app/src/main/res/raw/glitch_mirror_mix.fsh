
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
float average(vec3 col)
{
    return (col.r+col.g+col.b)/3.0;
}

void main()
{
	vec2 uv = textureCoordinate;

    vec2 uv2 = uv;
    uv2.x = 1.0-uv2.x;

	vec3 col1 = texture(iChannel0, uv).rgb;
    vec3 col2 = texture(iChannel0, uv2).rgb;

    if (average(col1)>average(col2))
    {
        fragColor = vec4(col1.r,col1.g,col1.b,1.0);
    }
    else
    {
        fragColor = vec4(col2.r,col2.g,col2.b,1.0);
    }

    // fragColor -= 0.3;
    fragColor *= (1.5-intensity);
    if(intensity == 0.0){
        vec2 uvori = textureCoordinate.xy;
        vec4 texori = texture2D(iChannel0, uvori);
        gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
    }
    
}


