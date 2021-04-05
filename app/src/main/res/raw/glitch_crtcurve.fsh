
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
float dimmer = 0.75;
float stretch = 0.5;

void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = textureCoordinate;

    // Time varying pixel color
    vec3 col = vec3(1., 1., 1.);

    //loop effect
    dimmer = abs(sin(iTime));

    //need to offset uv to be centre
    vec2 c = uv;
    c.x -= 0.5;
    c.y -= 0.5;

    //vector disatance from centre
    vec2 dis = vec2(0.,0.) - c;

    //get float value of distance
    float mag = length(dis);

    //alter brightness
    mag *= dimmer;

    //decrease all color values by distance from centre
    vec3 colScale = col - mag;

    vec2 curve = uv;

    //get distortion scale
    vec2 distortion = curve - mag*stretch;

    //Change uv co-ordinates  by distotion scale
    curve *= distortion;

    //Get Channel Input
	col = texture(iChannel0, curve).rgb;

    //Change frag color by scale
  	col *= colScale;

    // Output to screen
    fragColor = vec4(col,1.0);

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


