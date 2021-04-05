
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
#define SCALE 2.
#define SWIRL 1

void main()
{
    vec4 O = vec4(0.);
    vec2 U = iResolution.xy * textureCoordinate;
    float s=0., s2=0., t=iTime;
	U = U/iResolution.xy -.5;
    U.x += .03*sin(3.14*t);
    float sc = pow(SCALE,-mod(t,2.)-.8);
    U *= sc;

    for (int i=0; i<10; i++) {
        vec2 V = abs(U+U);
        if (max(V.x,V.y)>1.) break;
        V = smoothstep(1.,.5,V);
        float m = V.x*V.y;
	    O = mix(O,texture2D(iChannel0,U+.5),m);
        s = mix(s,1.,m); s2 = s2*(1.-m)*(1.-m) + m*m;
        U*=SCALE;
#if SWIRL
        U.x=-U.x;
#endif
    }

    vec4 mean = texture2D(iChannel0,U,10.);
    O = mean + (O-s*mean)/sqrt(s2);

    fragColor = O;

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


