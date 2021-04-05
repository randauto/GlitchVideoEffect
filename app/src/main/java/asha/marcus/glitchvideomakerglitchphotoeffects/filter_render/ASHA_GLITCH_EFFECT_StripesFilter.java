package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_StripesFilter extends FilterRender {
    private static float mAmount = 0.0f;
    private static float mMix = 0.3f;
    private String UNIFORM_AMOUNT = "amount";
    private String UNIFORM_ITIME = "iTime";
    private int mAmountHandler;
    private int mHeight;
    private float mITime;
    private int mInitHandler;
    private long mStartTime;
    private int mWidth;

    public void setTouch(float f, float f2) {
    }

    public ASHA_GLITCH_EFFECT_StripesFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float amount;\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\n    return fract(sin(dot(co.xy ,vec2(12.,78.))) * 1.) * 2.0 - 1.0;\n}\nfloat offset(float blocks, vec2 uv) {\n\treturn rand(vec2(iTime, floor(uv.y * blocks)));\n}\nvoid main() {\n\tvec2 uv = gl_FragCoord.xy / iResolution.xy;\n    float blocks = (iResolution.y * (amount/70.));\n\tgl_FragColor.r = texture2D(inputImageTexture, uv + vec2(offset(blocks, uv) * (amount) * (0.2 * amount), (amount / 24.0))).r;\n\tgl_FragColor.g = texture2D(inputImageTexture, uv + vec2(offset(blocks, uv) * (amount) * (0.4 * amount), (amount / -24.0))).g;\n\tgl_FragColor.b = texture2D(inputImageTexture, uv + vec2(offset(blocks, uv) * (amount) * (0.6 * amount), (amount / 30.0))).b;\n\n}");
        this.mStartTime = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.mInitHandler = GLES20.glGetUniformLocation(this.mProgramHandle, this.UNIFORM_ITIME);
        this.mAmountHandler = GLES20.glGetUniformLocation(this.mProgramHandle, this.UNIFORM_AMOUNT);
        this.mWidth = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.mHeight = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mInitHandler, this.mITime);
        GLES20.glUniform1f(this.mAmountHandler, mAmount);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.mWidth, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.mHeight, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.mWidth, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.mHeight, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.mWidth, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.mHeight, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.mWidth, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.mHeight, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
        if (currentTimeMillis > 1000) {
            this.mStartTime = System.currentTimeMillis();
        }
        this.mITime = (((float) currentTimeMillis) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        mAmount = mMix;
    }

    public static void adjust(float f) {
        mMix = f;
    }
}
