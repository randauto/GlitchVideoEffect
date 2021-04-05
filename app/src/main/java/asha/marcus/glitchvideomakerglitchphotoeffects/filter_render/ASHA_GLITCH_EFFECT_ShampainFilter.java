package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_ShampainFilter extends FilterRender {
    private static float f5281E = 0.0f;
    public static float f5282F = 0.0f;
    private static float f5283I = 0.0f;
    public static float f5284J = 0.0f;
    public static boolean f5285v = false;
    private float f5286A;
    private long f5287B = System.currentTimeMillis();
    private String f5288C = "touchX";
    private int f5289D;
    private String f5290G = "touchY";
    private int f5291H;
    private long f5292K;
    private int f5293w;
    private int f5294x;
    private String f5295y = "iTime";
    private int f5296z;

    public ASHA_GLITCH_EFFECT_ShampainFilter() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.;\namounty = 1.0-amounty;\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nfloat time = floor(iTime*20.);\nfloat rnd = random2d(vec2(time, 10.));\nvec4 col = texture2D(inputImageTexture, uv);\nfloat rnd2 = random2d(vec2(time, 10.));\nfloat rnd3 = random2d(vec2(time, 20.));\nif( uv.y > amounty-0.1+(rnd2/5.) && uv.y < amounty+0.01+(rnd3/5.) )\ncol.r = texture2D(inputImageTexture, uv+vec2(0.0,rnd/10.0)).r * 3.0;\ngl_FragColor = col;\n}");
        this.f5292K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5296z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5295y);
        this.f5289D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5288C);
        this.f5291H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5290G);
        this.f5293w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5294x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5282F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5284J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5282F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5284J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5282F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5284J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5282F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5284J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5296z, this.f5286A);
        GLES20.glUniform1f(this.f5289D, f5281E);
        GLES20.glUniform1f(this.f5291H, f5283I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5293w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5294x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5293w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5294x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5293w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5294x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5293w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5294x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5285v) {
            this.f5287B = System.currentTimeMillis() - this.f5292K;
            if (this.f5287B > DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                this.f5292K = System.currentTimeMillis();
            }
        }
        this.f5286A = (((float) this.f5287B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f5281E = f5282F;
        f5283I = f5284J;
    }
}
