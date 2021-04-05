package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_GlitchFilter extends FilterRender {
    private static float f5521E = 0.0f;
    public static float f5522F = 0.0f;
    private static float f5523I = 0.0f;
    public static float f5524J = 0.0f;
    public static boolean f5525v = false;
    private float f5526A;
    private long f5527B = System.currentTimeMillis();
    private String f5528C = "touchX";
    private int f5529D;
    private String f5530G = "touchY";
    private int f5531H;
    private long f5532K;
    private int f5533w;
    private int f5534x;
    private String f5535y = "iTime";
    private int f5536z;

    public ASHA_GLITCH_EFFECT_GlitchFilter() {
        setFragmentShader("precision lowp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) {\nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 0.7);\n}\nfloat randomRange (in vec2 seed, in float min, in float max) {\nreturn min + random2d(seed) * (max - min);\n}\nfloat insideRange(float v, float bottom, float top) {\nreturn step(bottom, v) - step(top, v);\n}\nfloat SPEED = 0.6;\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*0.5;\namounty = 0.5 - amounty;\nfloat time = floor(iTime * SPEED * 60.0);\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec3 outCol = texture2D(inputImageTexture, uv).rgb;\nfloat maxOffset = amountx/2.;\nfor (float i = 0.0; i < amounty; i += 0.1) {\nfloat sliceY = random2d(vec2(time , 2345.0 + float(i)));\nfloat sliceH = random2d(vec2(time , 9035.0 + float(i))) * 0.25;\nfloat hOffset = randomRange(vec2(time , 9625.0 + float(i)), -maxOffset, maxOffset);\nvec2 uvOff = uv;\nuvOff.x += hOffset;\nif (insideRange(uv.y, sliceY, fract(sliceY+sliceH)) == 1.0 ){\noutCol = texture2D(inputImageTexture, uvOff).rgb;\n}\n}\nfloat maxColOffset = amountx*0.05;\nfloat rnd = random2d(vec2(time , 9545.0));\nvec2 colOffset = vec2(randomRange(vec2(time , 9545.0),-maxColOffset,maxColOffset),\nrandomRange(vec2(time , 7205.0),-maxColOffset,maxColOffset));\nif (rnd < 0.33){\noutCol.r = texture2D(inputImageTexture, uv + colOffset).r;\n}else if (rnd < 0.66){\noutCol.g = texture2D(inputImageTexture, uv + colOffset).g;\n} else{\noutCol.b = texture2D(inputImageTexture, uv + colOffset).b;\n}\ngl_FragColor = vec4(outCol,1.0);\n}");
        this.f5532K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5536z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5535y);
        this.f5529D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5528C);
        this.f5531H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5530G);
        this.f5533w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5534x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5522F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5524J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5522F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5524J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5522F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5524J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5522F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5524J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5536z, this.f5526A);
        GLES20.glUniform1f(this.f5529D, f5521E);
        GLES20.glUniform1f(this.f5531H, f5523I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5533w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5534x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5533w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5534x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5533w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5534x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5533w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5534x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5525v) {
            this.f5527B = System.currentTimeMillis() - this.f5532K;
            if (this.f5527B > AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS) {
                this.f5532K = System.currentTimeMillis();
            }
        }
        this.f5526A = (((float) this.f5527B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f5521E = f5522F;
        f5523I = f5524J;
    }
}
