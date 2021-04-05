package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_Glitch2Filter extends FilterRender {
    private static float f5537E = 0.0f;
    public static float f5538F = 0.0f;
    private static float f5539I = 0.0f;
    public static float f5540J = 0.0f;
    public static boolean f5541v = false;
    private float f5542A;
    private long f5543B = System.currentTimeMillis();
    private String f5544C = "touchX";
    private int f5545D;
    private String f5546G = "touchY";
    private int f5547H;
    private long f5548K;
    private int f5549w;
    private int f5550x;
    private String f5551y = "iTime";
    private int f5552z;

    public ASHA_GLITCH_EFFECT_Glitch2Filter() {
        setFragmentShader("precision mediump float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 0.7);\n}\nfloat randomRange (in vec2 seed, in float min, in float max) {\nreturn min + random2d(seed) * (max - min);\n}\nfloat insideRange(float v, float bottom, float top) {\nreturn step(bottom, v) - step(top, v);\n}\nfloat SPEED = 0.6;\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat time = floor(iTime * SPEED * 60.0);    \nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec3 outCol = texture2D(inputImageTexture, uv).rgb;\nfloat rnd = random2d(vec2(time , 9545.0));\nvec2 colOffset = vec2(randomRange(vec2(time , 9545.0), 0.0, 0.1), \nrandomRange(vec2(time , 7205.0), 0.0, 0.1));\nif (rnd < 0.33){\noutCol.g = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).g;\n}else if (rnd < 0.66){\noutCol.r = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).r;\n}else{\noutCol.b = texture2D(inputImageTexture, vec2((uv.x+amountx/3.)+0.14, (uv.y+amounty/3.)-0.20) + colOffset).b;\n}\ngl_FragColor = vec4(outCol,1.0);\n}");
        this.f5548K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5552z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5551y);
        this.f5545D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5544C);
        this.f5547H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5546G);
        this.f5549w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5550x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5538F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5540J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5538F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5540J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5538F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5540J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5538F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5540J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5552z, this.f5542A);
        GLES20.glUniform1f(this.f5545D, f5537E);
        GLES20.glUniform1f(this.f5547H, f5539I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5549w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5549w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5549w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5549w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5550x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5541v) {
            this.f5543B = System.currentTimeMillis() - this.f5548K;
            if (this.f5543B > AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS) {
                this.f5548K = System.currentTimeMillis();
            }
        }
        this.f5542A = (((float) this.f5543B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f5537E = f5538F;
        f5539I = f5540J;
    }
}
