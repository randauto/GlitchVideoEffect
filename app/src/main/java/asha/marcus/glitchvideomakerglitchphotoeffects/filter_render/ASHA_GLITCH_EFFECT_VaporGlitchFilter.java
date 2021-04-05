package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_VaporGlitchFilter extends FilterRender {
    private static float f5369E = 0.0f;
    public static float f5370F = 0.0f;
    private static float f5371I = 0.0f;
    public static float f5372J = 0.0f;
    public static boolean f5373v = false;
    private float f5374A;
    private long f5375B = System.currentTimeMillis();
    private String f5376C = "touchX";
    private int f5377D;
    private String f5378G = "touchY";
    private int f5379H;
    private long f5380K;
    private int f5381w;
    private int f5382x;
    private String f5383y = "iTime";
    private int f5384z;

    public ASHA_GLITCH_EFFECT_VaporGlitchFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\nreturn fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 10.0);\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 2.0+mouse.y/iResolution.y*1.0;\nvec2 uv = vec2(gl_FragCoord.x + tan(gl_FragCoord.y * iTime * 10.0 * rand(vec2(iTime))) * (amountx*10.), gl_FragCoord.y) / iResolution.xy;\nvec4 t = texture2D(inputImageTexture, uv);\nvec4 tt = t;\ntt.r *= texture2D(inputImageTexture, uv - (amountx*0.1) * tan(gl_FragCoord.y * rand(vec2(iTime)))).r * t.r * amounty;\ntt.b *= texture2D(inputImageTexture, uv + (amountx*0.1) * tan(gl_FragCoord.y * rand(vec2(iTime)))).b * t.b * amounty;\ntt.g /= amounty*0.5;\ngl_FragColor = tt * 1.0;\n}");
        this.f5380K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5384z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5383y);
        this.f5377D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5376C);
        this.f5379H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5378G);
        this.f5381w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5382x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5370F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5372J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5370F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5372J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5370F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5372J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5370F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5372J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5384z, this.f5374A);
        GLES20.glUniform1f(this.f5377D, f5369E);
        GLES20.glUniform1f(this.f5379H, f5371I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5381w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5382x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5381w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5382x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5381w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5382x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5381w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5382x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5373v) {
            this.f5375B = System.currentTimeMillis() - this.f5380K;
            if (this.f5375B > AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS) {
                this.f5380K = System.currentTimeMillis();
            }
        }
        this.f5374A = (((float) this.f5375B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f5369E = f5370F;
        f5371I = f5372J;
    }
}
