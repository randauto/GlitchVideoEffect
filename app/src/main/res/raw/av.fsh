precision highp float;
uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           sTexture;
varying vec2                textureCoordinate;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = fragCoord/iResolution.xy;

    vec2 pos=vec2(0.5+0.5*sin(iGlobalTime),uv.y);
    vec3 col=vec3(texture2D(sTexture,uv));
    vec3 col2=vec3(texture2D(sTexture,pos))*0.2;
    col+=col2;


    // Output to screen
    fragColor = vec4(col,1.0);
}

void main()
{
    mainImage(gl_FragColor, textureCoordinate*iResolution.xy);
}
