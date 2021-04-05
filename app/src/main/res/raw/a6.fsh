#extension GL_OES_standard_derivatives : enable
precision mediump float;
varying lowp vec2 textureCoordinate;
uniform sampler2D sTexture;
uniform vec3 iResolution;
uniform float iGlobalTime;

// ray marching
#define MAX_STEPS 255
#define MAX_DIST 100.
#define iMouse vec2(1., 1.)
#define EPSILON .001// to move along gradient

bool animateHeight = false;
bool animateDepth = false;
bool rotateMouse = false;

// lighting
bool light = true;
vec3 cam = vec3(0.0, 0.0, -5.0);
struct Light { vec3 pos; float ld, ls; };
struct Material { vec3 ka, kd, ks; float a; };
#define LIGHTS 1

vec3 rotate(inout vec3 p, float a)
{
    float s = sin(a);
    float c = cos(a);
    mat2 m = mat2(c, -s, s, c);
    p.yz = m*p.yz;
    return p;
}

vec2 worldToTex(vec3 p)
{
    vec2 uv = p.xz*.5+.5;
    uv.y = 1. - uv.y;
    return uv;
}

float grayScale(vec4 c) { return c.x*.29 + c.y*.58 + c.z*.13; }


float heightField(vec3 p)
{
    vec2 uv = worldToTex(p);
    vec2 s = 1./iResolution.xy;
    float c = grayScale(texture2D(sTexture, uv));
    if (animateHeight) {
        c = mix(c, 1.-c, sin(iGlobalTime*2.)*.8);
        c*=.3;
    }
    //c+=abs(sin(iTime*.5))*.5;
    return c;
}

bool traceHeightField(vec3 ro, vec3 rd, out vec3 hitPos)
{
    const float max_steps = 60.;

    float s = .01;
    float lh = 0.;
    vec3  ly = vec3(0.);
    for (float t = 0.; t < max_steps; t += s)
    {
        vec3 p = ro + rd*t;
        float h = heightField(p);
        if (p.y < h) {
            t = t - s + s*(lh-ly.y)/(p.y-ly.y-h+lh);
            //t = (t-.5*s);
            hitPos = ro + rd  * t;
            return true;
        }
        lh = h;
        ly = p;
    }
    return false;
}

vec3 estimateNormal(vec3 p)
{
    vec3 xDir = vec3(EPSILON, 0, 0);
    vec3 yDir = vec3(0, EPSILON, 0);
    vec3 zDir = vec3(0, 0, EPSILON);

    return normalize(vec3(heightField(p+xDir) - heightField(p-xDir),
    heightField(p+yDir) - heightField(p-yDir),
    heightField(p+zDir) - heightField(p-zDir)));
}

//Light[LIGHTS] getLights()
//{
//    vec3 l1 = vec3(0.5, 0.5, -5.0);
//    l1 = vec3((iMouse.xy/iResolution.xy)*2.-1., -5.0);
//    l1 = rotate(l1, 1.25 * 2.-1.);
//    //l1.z = -5.0;
//
//    return Light[LIGHTS](Light(l1, 0.5, 1.0));
//}

vec3 contribution(Material m, vec3 p, Light l)
{
    vec3 N = estimateNormal(p);
    vec3 L = normalize(l.pos - p);
    vec3 V = normalize(cam - p);
    vec3 H = normalize(V + L);
    vec3 R = normalize(reflect(-L, N));

    float ndotL = dot(N, L);
    float ndotH = dot(N, H);
    float rdotV = dot(V, R);

    if (ndotL < 0.0) return vec3(0);

    // diffuse component
    vec3 diffuse = max(ndotL, 0.0) * m.kd*l.ld;

    // specular
    vec3 specular = pow(max(ndotH, 0.0), m.a) * m.ks*l.ls;

    return (diffuse+specular);
}

vec3 phong(vec3 p)
{
    //Material
    vec3 ka = vec3(0.0, 0.0, 0.3);
    vec3 kd = vec3(0.0, 0.0, 0.6);
    vec3 ks = vec3(1);
    float alpha = 1.0;
    Material m = Material(ka, kd, ks, alpha);

    //Lights
    vec3 la = vec3(0.5);
    vec3 color = la*m.ka;

    vec3 l1 = vec3(0.5, 0.5, -5.0);
    l1 = vec3((iMouse.xy/iResolution.xy)*2.-1., -5.0);
    l1 = rotate(l1, 1.25 * 2.-1.);
    color+=contribution(m, p, Light(l1, 0.5, 1.0));
    return color;
}



void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec2 uv = fragCoord/iResolution.xy*2.-1.;

    // compute ray origin and direction
    vec3 rd = normalize(vec3(uv, -2.));
    vec3 ro = vec3(0., 0., 2.);

    // rotate view
    ro = rotate(ro, 1.25 * 2.-1.);
    rd = rotate(rd, 1.25 * 2.-1.);
    cam = rotate(cam, 1.25 * 2.-1.);

    // rotate view w/mouse
    if (rotateMouse) {
        vec2 mouse = iMouse.xy / iResolution.xy * 2.-1.;
        if (iMouse.x > 0.0) {
            float ax = (1. - mouse.y);
            ro = rotate(ro, ax);
            rd = rotate(rd, ax);
        }
    }

    vec3 c = vec3(0.);
    vec3 hitPos;
    if (traceHeightField(ro, rd, hitPos)) {
        vec2 uv = worldToTex(hitPos);
        if (animateHeight)
        c = texture2D(sTexture, uv).xyz;
        float depth = (animateHeight) ?
        1.0 - clamp(sin(mod(iGlobalTime, 5.)*.2), 0., 1.) : 0.0;
        if (hitPos.y > depth)
        c = texture2D(sTexture, uv).xyz;
        if (light) c += phong(hitPos);
    }

    fragColor = vec4(c, 1.0);
}


void main() {
    mainImage(gl_FragColor, textureCoordinate.xy*iResolution.xy);
}



