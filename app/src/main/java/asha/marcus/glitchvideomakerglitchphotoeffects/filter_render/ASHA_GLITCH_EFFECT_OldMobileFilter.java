package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_OldMobileFilter extends FilterRender {
    private static float f5637E = 0.0f;
    public static float f5638F = 0.0f;
    private static float f5639I = 0.0f;
    public static float f5640J = 0.0f;
    public static boolean f5641v = false;
    private float f5642A;
    private long f5643B = System.currentTimeMillis();
    private String f5644C = "touchX";
    private int f5645D;
    private String f5646G = "touchY";
    private int f5647H;
    private long f5648K;
    private int f5649w;
    private int f5650x;
    private String f5651y = "iTime";
    private int f5652z;

    public ASHA_GLITCH_EFFECT_OldMobileFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvec4 posterize(vec4 color, float numColors){\nreturn floor(color * numColors - 0.5) / numColors;\n}\nvec2 quantize(vec2 v, float steps){\nreturn floor(v * steps) / steps;\n}\nfloat dist(vec2 a, vec2 b){\nreturn sqrt(pow(b.x - a.x, 2.0) + pow(b.y - a.y, 2.0));\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat amount1 = pow(amountx, 2.0);\nvec2 pixel = 0. / gl_FragCoord.xy;\nvec4 color = texture2D(inputImageTexture, uv);\nfloat t = mod(mod(iTime, amount1 * 100.0 * (amount1 - 0.5)) * 109.0, 1.0);\nvec4 postColor = posterize(color, 16.0);\nvec4 a = posterize(texture2D(inputImageTexture, quantize(uv, 64.0 * t) + pixel * (postColor.rb - vec2(.5)) * 100.0), 5.0).rbga;\nvec4 b = posterize(texture2D(inputImageTexture, quantize(uv, 32.0 - t) + pixel * (postColor.rg - vec2(.5)) * 1000.0), 4.0).gbra;\nvec4 c = posterize(texture2D(inputImageTexture, quantize(uv, 16.0 + t) + pixel * (postColor.rg - vec2(.5)) * 20.0), 16.0).bgra;\ngl_FragColor = mix(\ntexture2D(inputImageTexture,\nuv + amount1 * (quantize((a * t - b + c - (t + t / 2.0) / 10.0).rg, 16.0) - vec2(.5)) * pixel * 100.0),\n(a + b + c) / 3.0,\n(5.0 - (dot(color, postColor) - 1.5)) * amountx);\n}");
        this.f5648K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5652z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5651y);
        this.f5645D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5644C);
        this.f5647H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5646G);
        this.f5649w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5650x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5638F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5640J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5638F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5640J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5638F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5640J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5638F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5640J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5652z, this.f5642A);
        GLES20.glUniform1f(this.f5645D, f5637E);
        GLES20.glUniform1f(this.f5647H, f5639I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5649w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5650x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5649w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5650x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5649w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5650x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5649w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5650x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5641v) {
            this.f5643B = System.currentTimeMillis() - this.f5648K;
            if (this.f5643B > DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                this.f5648K = System.currentTimeMillis();
            }
        }
        this.f5642A = (((float) this.f5643B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f5637E = f5638F;
        f5639I = f5640J;
    }
}
