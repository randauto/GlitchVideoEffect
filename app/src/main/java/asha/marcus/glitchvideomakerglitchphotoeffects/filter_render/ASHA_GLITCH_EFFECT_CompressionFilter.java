package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_CompressionFilter extends FilterRender {
    private static float f5459E = 0.0f;
    public static float f5460F = 0.0f;
    private static float f5461I = 0.0f;
    public static float f5462J = 0.0f;
    public static boolean f5463v = false;
    private float f5464A;
    private long f5465B = System.currentTimeMillis();
    private String f5466C = "touchX";
    private int f5467D;
    private String f5468G = "touchY";
    private int f5469H;
    private long f5470K;
    private int f5471w;
    private int f5472x;
    private String f5473y = "iTime";
    private int f5474z;

    public ASHA_GLITCH_EFFECT_CompressionFilter() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nuniform float iTime;\nvec4 posterize(vec4 color, float numColors){\nreturn floor(color * numColors - 0.5) / numColors;\n}\nvec2 quantize(vec2 v, float steps){\nreturn floor(v * steps) / steps;\n}\nfloat dist(vec2 a, vec2 b){\nreturn sqrt(pow(b.x - a.x, 2.0) + pow(b.y - a.y, 2.0));\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*10.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat amount1 = pow(amountx, 2.0);\nvec2 pixel = amounty / gl_FragCoord.xy;\nvec4 color = texture2D(inputImageTexture, uv);\nfloat t = mod(mod(iTime, amount1 * 100.0 * (amount1 - 0.5)) * 109.0, 1.0);\nvec4 postColor = posterize(color, 16.0);\nvec4 a = posterize(texture2D(inputImageTexture, quantize(uv, 64.0 * t) + pixel * (postColor.rb - vec2(.5)) * 100.0), 5.0).rbga;\nvec4 b = posterize(texture2D(inputImageTexture, quantize(uv, 32.0 - t) + pixel * (postColor.rg - vec2(.5)) * 1000.0), 4.0).gbra;\nvec4 c = posterize(texture2D(inputImageTexture, quantize(uv, 16.0 + t) + pixel * (postColor.rg - vec2(.5)) * 20.0), 16.0).bgra;\ngl_FragColor = mix(\ntexture2D(inputImageTexture,\nuv + amount1 * (quantize((a * t - b + c - (t + t / 2.0) / 10.0).rg, 16.0) - vec2(.5)) * pixel * 100.0),\n(a + b + c) / 3.0,\n(0.5 - (dot(color, postColor) - 1.5)) * amountx);\n}");
        this.f5470K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5474z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5473y);
        this.f5467D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5466C);
        this.f5469H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5468G);
        this.f5471w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5472x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5474z, this.f5464A);
        GLES20.glUniform1f(this.f5467D, f5459E);
        GLES20.glUniform1f(this.f5469H, f5461I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5471w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5472x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5471w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5472x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5471w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5472x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5471w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5472x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5463v) {
            this.f5465B = System.currentTimeMillis() - this.f5470K;
            if (this.f5465B > DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                this.f5470K = System.currentTimeMillis();
            }
        }
        this.f5464A = (((float) this.f5465B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f5459E = f5460F;
        f5461I = f5462J;
    }
}
