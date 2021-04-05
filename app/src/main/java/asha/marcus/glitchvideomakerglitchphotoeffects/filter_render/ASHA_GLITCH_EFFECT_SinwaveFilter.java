package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.jnp_utils.ASHA_GLITCH_EFFECT_AppHelper;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_SinwaveFilter extends FilterRender {
    private static float f5297E = 0.0f;
    public static float f5298F = 0.0f;
    private static float f5299I = 0.0f;
    public static float f5300J = 0.0f;
    public static boolean f5301v = false;
    private float f5302A;
    private long f5303B = System.currentTimeMillis();
    private String f5304C = "touchX";
    private int f5305D;
    private String f5306G = "touchY";
    private int f5307H;
    private long f5308K;
    private int f5309w;
    private int f5310x;
    private String f5311y = "iTime";
    private int f5312z;

    public ASHA_GLITCH_EFFECT_SinwaveFilter() {
        ASHA_GLITCH_EFFECT_AppHelper.log("TOUCH_FILTER", "CONSTRUCTOR");
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*0.5;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat POWER = amountx;\nfloat VERTICAL_SPREAD = 4.0;\nfloat ANIM_SPEED = 0.4;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 uv1 = gl_FragCoord.xy / iResolution.xy;\nfloat y = (uv.y + iTime * ANIM_SPEED) * VERTICAL_SPREAD;\nuv.x += ( \nsin(y) \n + sin(y * 0.0) * 0.2 \n + sin(y * 0.0) * 0.03\n)\n * POWER\n * sin(uv.y * 3.14)\n * sin(iTime);\nfloat r = texture2D(inputImageTexture, uv).r;\nfloat g = texture2D(inputImageTexture, uv1).g;\nfloat b = texture2D(inputImageTexture, uv).b;\ngl_FragColor = vec4(r,g,b,1.0);\n}");
        this.f5308K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        ASHA_GLITCH_EFFECT_AppHelper.log("TOUCH_FILTER", "INIT");
        this.f5312z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5311y);
        this.f5305D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5304C);
        this.f5307H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5306G);
        this.f5309w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5310x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5298F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 10);
                f5300J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5298F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 10);
                f5300J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5298F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 10);
                f5300J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5298F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 10);
                f5300J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        ASHA_GLITCH_EFFECT_AppHelper.log("TOUCH_FILTER", "BIND");
        GLES20.glUniform1f(this.f5312z, this.f5302A);
        GLES20.glUniform1f(this.f5305D, f5297E);
        GLES20.glUniform1f(this.f5307H, f5299I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5309w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5310x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5309w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5310x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5309w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5310x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5309w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5310x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        ASHA_GLITCH_EFFECT_AppHelper.log("TOUCH_FILTER", "DRAW");
        if (!f5301v) {
            this.f5303B = System.currentTimeMillis() - this.f5308K;
            if (this.f5303B > 20000) {
                this.f5308K = System.currentTimeMillis();
            }
        }
        this.f5302A = (((float) this.f5303B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f5297E = f5298F;
        f5299I = f5300J;
    }

    public static void setTouch(float f, float f2) {
        f5298F = f;
        f5300J = f2;
    }
}
