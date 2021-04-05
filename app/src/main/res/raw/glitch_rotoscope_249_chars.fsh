precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
 uniform float               intensity;
float a(vec2 v) { return texture2D(iChannel0, v/iResolution.xy).g; }

void main() {
    vec2 u = textureCoordinate.xy * iResolution.xy;
    gl_FragColor += 3.4*(.3-length(vec2((gl_FragColor.a = a(u)) - a(u + vec2(1,0)), gl_FragColor.a - a(u + vec2(0,1)))));

     vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
}