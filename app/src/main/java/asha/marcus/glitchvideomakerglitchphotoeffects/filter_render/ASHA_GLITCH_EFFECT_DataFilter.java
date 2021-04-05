package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_DataFilter extends FilterRender {
    private static float f5475E = 0.0f;
    public static float f5476F = 0.0f;
    private static float f5477I = 0.0f;
    public static float f5478J = 0.0f;
    public static boolean f5479v = false;
    private float f5480A;
    private long f5481B = System.currentTimeMillis();
    private String f5482C = "touchX";
    private int f5483D;
    private String f5484G = "touchY";
    private int f5485H;
    private long f5486K;
    private int f5487w;
    private int f5488x;
    private String f5489y = "iTime";
    private int f5490z;

    public ASHA_GLITCH_EFFECT_DataFilter() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 1.0);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\n    float amountx = 1.0+mouse.x/iResolution.x*100.;\namountx = 100.-amountx;\n    float amounty = 1.0+mouse.y/iResolution.y*100.;\n    \n    //Speed 0-1\n    float speed = 1.0;\n    \n    //Global time\n    float time = floor(iTime*(speed*60.0));\n    \n    //Coordinates\n    vec2 uv = gl_FragCoord.xy/iResolution.xy;\n    \n    //Input texture\n   \tvec4 col = texture2D(inputImageTexture, uv);\n    \n    //Every block\n    for (float i = 0.0; i < 5.-(amounty/20.); i += 1.0) {\n        \n        //Get random every block\n        float rnd = random2d(vec2(time , float(i*100.)));\n\n        //Random distance each block\n        float dist = (i/0.1+(rnd*10.))+amounty-10.;\n\n        //Progressive Y \n        float offset = (amountx/50.*i/10.)+(rnd/10.);\n\n        //Color goes dark\n        float c = (0.7-(i/6.))+(rnd/2.);\n\n        //New texture\n\t\tvec4 bg = texture2D(inputImageTexture, uv + vec2(offset, 0.0))*c;\n\n        //Black space\n        bg = mix(vec4(0.0), bg, clamp(4.-floor(offset*510.)+(1.-uv.x)*500., 0., 1.));\n        \n        float f = clamp(4.-floor(dist)*4.+(1.-uv.y)*300. , 0., 1.);\n\n        float f2 = (rnd>uv.x)?1.:0.;\n\n        vec4 rowColor = mix(bg, col, f2);\n\n        col = mix(col, rowColor, f);\n\n        f = clamp(4.-floor(dist+2.)*4.+(1.-uv.y)*300., 0., 1.);\n\n        col = mix(col, bg, f);\n        \n    }\n    \n    //Display\n    gl_FragColor = col;\n}");
        this.f5486K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5490z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5489y);
        this.f5483D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5482C);
        this.f5485H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5484G);
        this.f5487w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5488x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5476F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5478J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5476F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5478J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5476F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5478J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5476F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5478J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5490z, this.f5480A);
        GLES20.glUniform1f(this.f5483D, f5475E);
        GLES20.glUniform1f(this.f5485H, f5477I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5487w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5488x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5487w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5488x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5487w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5488x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5487w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5488x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5479v) {
            this.f5481B = System.currentTimeMillis() - this.f5486K;
            if (this.f5481B > AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS) {
                this.f5486K = System.currentTimeMillis();
            }
        }
        this.f5480A = (((float) this.f5481B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f5475E = f5476F;
        f5477I = f5478J;
    }
}
