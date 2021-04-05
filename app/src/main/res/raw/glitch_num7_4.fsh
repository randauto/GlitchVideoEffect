
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying  vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform  float iTime;

 uniform  vec3 iResolution;
uniform float               intensity;
vec3 chequer(vec2 uv) {
    uv = fract(uv);
    return uv.x > 0.5 != uv.y > 0.5 ? vec3(0.6, 0.6, 1.0) : vec3(1.0, 1.0, 0.6);
}

#define PI 3.14159265359
#define TriplePI (3.0 * PI)
#define DoublePI (2.0 * PI)
#define HalfPI (PI / 2.0)

/*
   These functions take a "camera" variable specifying:
     X/Y: Where on the screen we are rendering.
     Z: How far forwards we are.
   And return:
     X/Y: UVs you can use for texture mapping.
     Z: How far the pixel is.
   These are designed so that planeX/planeY line up nicely with tunnel so you can lerp between them.
*/

/* A wall directly in front of you. */
vec3 planeZ(float z, vec3 camera) {
    z -= camera.z;
    return vec3(camera.xy * z, z);
}

/* Parallel walls to the left/right of you. */
vec3 planeX(float x, vec3 camera) {
    float divisor = x / camera.x;
    return vec3(camera.y * divisor + (camera.x > 0.0 ? 0.0 : (camera.y > 0.0 ? 2.0 : -2.0)), abs(divisor) + camera.z, abs(divisor));
}

/* Parallel floor and ceiling. */
vec3 planeY(float y, vec3 camera) {
    float divisor = y / -camera.y;
    return vec3(camera.x * divisor + (camera.y > 0.0 ? 1.0 : -1.0), abs(divisor) + camera.z, abs(divisor));
}

/* A round tunnel. */
vec3 tunnel(float radius, vec3 camera) {
    float dist = radius / length(camera.xy);
    return vec3(atan(camera.y, camera.x) * radius / HalfPI, dist + camera.z, dist);
}

/* Similar to planeZ, but matches up with tunnel. */
vec3 tunnelEnd(float z, float radius, vec3 camera) {
    float angle = atan(camera.x, camera.y);
    float dist = length(camera.xy);
    return vec3(angle * radius / HalfPI, dist * (z - camera.z), z - camera.z);
}

/* -------------------------------------------------------- */

void main()
{
    vec2 fragCoord = iResolution.xy * textureCoordinate;
    vec2 aspectNdc = (fragCoord.xy - (iResolution.xy / 2.0)) / (min(iResolution.x, iResolution.y) / 2.0);

    vec3 camera = vec3(aspectNdc, iTime);

	vec3 _tunnel = tunnel(1.0, camera);
    vec3 _planeX = planeX(1.0, camera);
    vec3 _planeY = planeY(1.0, camera);

    float ofThisLoop = mod(iTime * 0.5, 3.0);

    ofThisLoop = sin(HalfPI * fract(ofThisLoop)) + floor(ofThisLoop);

    vec3 compute = mix(_planeX, _tunnel, clamp(ofThisLoop, 0.0, 1.0));
    compute = mix(compute, _planeY, clamp(ofThisLoop - 1.0, 0.0, 1.0));
    compute = mix(compute, _planeX, clamp(ofThisLoop - 2.0, 0.0, 1.0));

	fragColor = vec4(mix(vec3(0.8, 0.9, 1.0), mix(vec3(0.6, 0.7, 1.0), texture(iChannel0, compute.xy).rgb, 1.0 / (1.0 + compute.z * 0.5)), 1.0 / (1.0 + compute.z * 0.1)), 1.0);

    vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + fragColor.rgb*intensity,1.);
}


