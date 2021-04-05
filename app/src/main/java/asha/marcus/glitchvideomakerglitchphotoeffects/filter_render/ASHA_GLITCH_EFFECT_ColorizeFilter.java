package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;

import cn.ezandroid.ezfilter.core.FilterRender;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_ColorizeFilter extends FilterRender {
    private static float f5443E = 0.0f;
    public static float f5444F = 0.0f;
    private static float f5445I = 0.0f;
    public static float f5446J = 0.0f;
    public static boolean f5447v = false;
    private float f5448A;
    private long f5449B = System.currentTimeMillis();
    private String f5450C = "touchX";
    private int f5451D;
    private String f5452G = "touchY";
    private int f5453H;
    private long f5454K;
    private int f5455w;
    private int f5456x;
    private String f5457y = "iTime";
    private int f5458z;

    public ASHA_GLITCH_EFFECT_ColorizeFilter() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 10.0);\n}\nvoid main(){\nvec2 uv = gl_FragCoord.xy/iResolution.xy;\nif (touchY > 0.0){\nfloat time = floor(iTime*20.);\nfloat rnd = random2d(vec2(time, 10.));\nvec4 col = (texture2D(inputImageTexture, uv+vec2(0.0,rnd/10.)));\nfor (float i = 0.0; i < 5.0; i += 1.0) {\nfloat rnd = random2d(vec2(time, i*10.));\nfloat rnd2 = random2d(vec2(time, 10.+i));\nfloat rnd3 = random2d(vec2(time, 20.+i));\nif (rnd<.30){\nif( uv.y < rnd2 && uv.y > rnd3 )\ncol.rg = col.rg * rnd*10.;\n}else if (rnd<0.60){\nif( uv.y < rnd2 && uv.y > rnd3 )\ncol.gb = col.gb * rnd*10.;\n}else if (rnd<.90){\nif( uv.y < rnd2 && uv.y > rnd3 )\ncol.rb = col.rb * rnd*10.;\n}\n}\ngl_FragColor = col;\n}else{\ngl_FragColor = texture2D(inputImageTexture, uv);\n}\n}");
        this.f5454K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5458z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5457y);
        this.f5451D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5450C);
        this.f5453H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5452G);
        this.f5455w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5456x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5458z, this.f5448A);
        GLES20.glUniform1f(this.f5451D, f5443E);
        GLES20.glUniform1f(this.f5453H, f5445I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5455w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5456x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5455w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5456x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5455w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5456x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5455w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5456x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5447v) {
            this.f5449B = System.currentTimeMillis() - this.f5454K;
            if (this.f5449B > DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                this.f5454K = System.currentTimeMillis();
            }
        }
        this.f5448A = (((float) this.f5449B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f5443E = f5444F;
        f5445I = f5446J;
    }
}
