
#define texture(a,b) texture2D(a,fract(b))
#define fragColor gl_FragColor
precision highp float;

 varying vec2 textureCoordinate;

 uniform sampler2D iChannel0;

 uniform float iTime;

 uniform vec3 iResolution;
uniform float               intensity;

#define BLUE vec3(0, 0, 0.84)
#define YELLOW vec3(0.84, 0.84, 0)
#define CYAN vec3(0.0, 0.84, 0.84)
#define MAGENTA vec3(0.84, 0.0, 0.0)
#define WHITE vec3(0.84, 0.84, 0.84)
#define BLACK vec3(0)

#define BORDER 0.10

#define TIME iTime

#define NOTHING 0
#define WAIT_FLASH 1
#define PROGRAM 2
#define DATA 3
#define SCREEN_LINES 4
#define COLOR_FILL 5
#define DONE 6


float rand()
{
    return fract(sin(dot(vec2(TIME), vec2(12.9898, 78.233))) * 43758.5453);
}

float inMiddle(in vec2 xy)
{
    vec2 bottomLeft = vec2(BORDER);
    vec2 topRight = vec2(1.0) - bottomLeft;
    vec2 s = step(bottomLeft, xy) - step(topRight, xy);
    return s.x * s.y;
}

bool inMiddleBool(in vec2 xy)
{
    return inMiddle(xy) == 1.0;
}

vec3 screen(in vec2 uv)
{
    return texture(iChannel0, vec2(uv.x, 1.0 - uv.y)).xyz;
}

vec2 pixellate(in vec2 uv)
{
	return floor(uv * vec2(192.0, 256.0)) / vec2(192.0, 256.0);
}

vec3 blueYellowLoadPattern(in vec2 uv)
{
    float r = rand();
    float lines = mix(20.0, 30.0, r);
    float offset = dot(pixellate(uv), vec2(0.1, lines)) + r;
    float f = step(fract(offset), 0.5);
    return mix(BLUE, YELLOW, step(f, 0.0));
}

vec3 cyanMagentaLoadPattern(in vec2 uv, float t)
{
    float lines = 15.0;
    float offset = dot(pixellate(uv), vec2(0.1, lines)) + t;
    float f = step(fract(offset), 0.5);
    return mix(MAGENTA, CYAN, step(f, 0.0));
}

vec3 toBWPixel(in vec3 pixel)
{
    return mix(WHITE, BLACK, step(dot(pixel, vec3(0.2126, 0.7152, 0.0722)), 0.5));
}

void main()
{
	vec2 uv = textureCoordinate;
    uv.y = 1.0 - uv.y;

    vec3 rgb;

    // Select phase.
    float t;
    int PHASE;

    if (TIME > 32.9)
        PHASE = DONE, t = TIME - 32.9;
    else if (TIME > 28.9)
        PHASE = COLOR_FILL, t = TIME - 28.9;
    else if (TIME > 9.7)
        PHASE = SCREEN_LINES, t = TIME - 9.7;
    else if (TIME > 8.7)
        PHASE = PROGRAM, t = TIME - 8.7;
    else if (TIME > 6.7)
        PHASE = WAIT_FLASH, t = TIME - 6.7;
    else if (TIME > 5.7)
        PHASE = NOTHING, t = TIME - 5.7;
    else if (TIME > 5.5)
        PHASE = DATA, t = TIME - 5.5; // 'BLIP'
    else if (TIME > 4.0)
        PHASE = PROGRAM, t = TIME - 4.0;
    else
        PHASE = WAIT_FLASH, t = TIME;

    if (PHASE == NOTHING)
    {
        rgb = WHITE;
    }
    else if (PHASE == WAIT_FLASH)
    {
        rgb = mix(mix(MAGENTA, CYAN, step(mod(t, 2.0), 1.0)),
                  WHITE,
                  inMiddle(uv));
    }
    else if (PHASE == PROGRAM)
    {
        if (inMiddleBool(uv))
        {
            rgb = WHITE;
        }
        else
        {
            float lines = 15.0;
            float offset = dot(pixellate(uv), vec2(0.1, lines)) + t;
            float f = step(fract(offset), 0.5);

            rgb = mix(MAGENTA, CYAN, step(f, 0.0));
        }
    }
    else if (PHASE == DATA)
    {
        rgb = mix(blueYellowLoadPattern(uv), WHITE, inMiddle(uv));
    }
    else if (PHASE == SCREEN_LINES)
    {
        if (inMiddleBool(uv))
        {
            float lines_revealed = t * 10.0;

            float y = (uv.y - BORDER) / (1.0 - BORDER * 2.0);
            float line = floor(y * 192.0);

            float block = mod(floor(line / 8.0), 8.0);
            float block_offset = fract(line / 8.0) * 8.0;

            float section_start_t = floor(line / 64.0) * 64.0;

            float third_blocks = 8.0;
            float draw_from_t = section_start_t + block + block_offset * third_blocks;

            vec3 pixel = screen(pixellate(uv));
            rgb = mix(WHITE, toBWPixel(pixel), step(draw_from_t, lines_revealed));
        }
        else
        {
            rgb = blueYellowLoadPattern(uv);
        }
    }
    else if (PHASE == COLOR_FILL)
    {
        if (inMiddleBool(uv))
        {
            float y = (uv.y - BORDER) / (1.0 - BORDER * 2.0);
            float x = (uv.x - BORDER) / (1.0 - BORDER * 2.0);

            float block = floor(x * 32.0) + floor(y * 24.0) * 32.0;
            float blocks_to_show = mix(0.0, 768.0, t / 4.0);

            rgb = mix(toBWPixel(screen(pixellate(uv))), screen(uv), step(block, blocks_to_show));
        }
        else
        {
            rgb = blueYellowLoadPattern(uv);
        }
    }
    else if (PHASE == DONE)
    {
        rgb = mix(WHITE, screen(uv), inMiddle(uv));
    }

    fragColor = vec4(rgb, 1.0);

           vec2 uvori = textureCoordinate.xy;
    vec4 texori = texture2D(iChannel0, uvori);
    gl_FragColor = vec4(texori.rgb*(1.-intensity) + gl_FragColor.rgb*intensity,1.);
}


