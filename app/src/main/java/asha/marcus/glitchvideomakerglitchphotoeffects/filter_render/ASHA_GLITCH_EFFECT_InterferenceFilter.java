package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_InterferenceFilter extends FilterRender {
    private static float f5569E = 0.0f;
    public static float f5570F = 0.0f;
    private static float f5571I = 0.0f;
    public static float f5572J = 0.0f;
    public static boolean f5573v = false;
    private float f5574A;
    private long f5575B = System.currentTimeMillis();
    private String f5576C = "touchX";
    private int f5577D;
    private String f5578G = "touchY";
    private int f5579H;
    private long f5580K;
    private int f5581w;
    private int f5582x;
    private String f5583y = "iTime";
    private int f5584z;

    public ASHA_GLITCH_EFFECT_InterferenceFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rng2(vec2 seed) {\nreturn fract(sin(dot(seed * floor(iTime * 100.), vec2(2.0,3.0))) * 1.0);\n}\nfloat rng(float seed) {\nreturn rng2(vec2(seed, 1.0));\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 3.0+mouse.y/iResolution.y*200.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 blockS = floor(uv * vec2(iResolution.x / 140., iResolution.y / 140.));\nvec2 blockL = floor(uv * vec2(iResolution.x / amounty, iResolution.y / amounty));\nfloat lineNoise = pow(rng2(blockS), 8.0) * pow(rng2(blockL), 3.0) * (0.5 * amountx);\ngl_FragColor = vec4(texture2D(inputImageTexture, uv - vec2(lineNoise * (0.0005 * amountx) * rng(31.0), 0)));\n}");
        this.f5580K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5584z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5583y);
        this.f5577D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5576C);
        this.f5579H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5578G);
        this.f5581w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5582x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5570F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5572J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5570F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5572J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5570F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5572J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5570F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5572J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5584z, this.f5574A);
        GLES20.glUniform1f(this.f5577D, f5569E);
        GLES20.glUniform1f(this.f5579H, f5571I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5581w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5582x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5581w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5582x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5581w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5582x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5581w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5582x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5573v) {
            this.f5575B = System.currentTimeMillis() - this.f5580K;
            if (this.f5575B > 1000) {
                this.f5580K = System.currentTimeMillis();
            }
        }
        this.f5574A = (((float) this.f5575B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f5569E = f5570F;
        f5571I = f5572J;
    }
}
