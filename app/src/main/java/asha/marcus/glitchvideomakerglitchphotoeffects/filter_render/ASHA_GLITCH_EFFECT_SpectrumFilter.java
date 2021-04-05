package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import p004cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_SpectrumFilter extends FilterRender {
    private static float f5329E = 0.0f;
    public static float f5330F = 0.0f;
    private static float f5331I = 0.0f;
    public static float f5332J = 0.0f;
    private static float f5333L = 0.3f;
    public static boolean f5334v = false;
    private float f5335A;
    private long f5336B = System.currentTimeMillis();
    private String f5337C = "touchX";
    private int f5338D;
    private String f5339G = "touchY";
    private int f5340H;
    private String f5341K = "amount";
    private long f5342M;
    private int f5343w;
    private int f5344x;
    private String f5345y = "iTime";
    private int f5346z;

    public ASHA_GLITCH_EFFECT_SpectrumFilter() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat sat( float t ) {\nreturn clamp( t, 0.0, 1.0 );\n}\nvec2 sat( vec2 t ) {\nreturn clamp( t, 0.0, 1.0 );\n}\nfloat remap  ( float t, float a, float b ) {\nreturn sat( (t - a) / (b - a) );\n}\nfloat linterp( float t ) {\nreturn sat( 1.0 - abs( 2.0*t - 1.0 ) );\n}\nvec3 spectrum_offset( float t ) {\nvec3 ret;\nfloat lo = step(t,0.5);\nfloat hi = 1.0-lo;\nfloat w = linterp( remap( t, 1.0/6.0, 5.0/6.0 ) );\nfloat neg_w = 1.0-w;\nret = vec3(lo,1.0,hi) * vec3(neg_w, w, neg_w);\nreturn pow( ret, vec3(1.0/2.2) );\n}\nfloat rand( vec2 n ) {\nreturn fract(sin(dot(n.xy, vec2(12.9898, 78.233)))* 43758.5453);\n}\nfloat srand( vec2 n ) {\nreturn rand(n) * 2.0 - 1.0;\n}\nfloat mytrunc( float x, float num_levels ) {\nreturn floor(x*num_levels) / num_levels;\n}\nvec2 mytrunc( vec2 x, float num_levels ) {\nreturn floor(x*num_levels) / num_levels;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nuv.y = uv.y;\nfloat time = mod(iTime*100.0, 32.0)/110.0;\nfloat gnm = sat( amountx );\nfloat rnd0 = rand( mytrunc( vec2(time, time), 6.0 ) );\nfloat r0 = sat((1.0-gnm)*0.7 + rnd0);\nfloat rnd1 = rand( vec2(mytrunc( uv.x, uv.y ), time) );\nfloat r1 = 0.5 - 0.5 * gnm + rnd1;\nr1 = 1.0 - max( 0.0, ((r1<1.0) ? r1 : 0.9999999) );\nfloat rnd2 = rand( vec2(mytrunc( uv.y, uv.x ), time) );\nfloat r2 = sat( rnd2 );\nfloat rnd3 = rand( vec2(mytrunc( uv.y, uv.x ), time) );\nfloat r3 = (1.0-sat(rnd3+0.8)) - 0.1;\nfloat pxrnd = rand( uv + time );\nfloat ofs = 0.05 * r2 * amountx * ( rnd0 > 0.5 ? 1.0 : -1.0 );\nofs += 0.5 * pxrnd * ofs;\nuv.y += 0.1 * r3 * amountx;\nconst int NUM_SAMPLES = 10;\nconst float RCP_NUM_SAMPLES_F = 1.0 / float(NUM_SAMPLES);\nvec4 sum = vec4(0.0);\nvec3 wsum = vec3(0.0);\nfor( int i=0; i<NUM_SAMPLES; ++i ) {\nfloat t = float(i) * RCP_NUM_SAMPLES_F;\nuv.x = sat( uv.x + ofs * t );\nvec4 samplecol = texture2D( inputImageTexture, uv, -10.0 );\nvec3 s = spectrum_offset( t );\nsamplecol.rgb = samplecol.rgb * s;\nsum += samplecol;\nwsum += s;\n}sum.rgb /= wsum;\nsum.a *= RCP_NUM_SAMPLES_F;\ngl_FragColor.a = sum.a;\ngl_FragColor.rgb = sum.rgb;\n}\n");
        this.f5342M = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5346z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5345y);
        this.f5338D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5337C);
        this.f5340H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5339G);
        this.f5343w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5344x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5330F = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5332J = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5330F = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5332J = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5330F = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5332J = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5330F = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5332J = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5346z, this.f5335A);
        GLES20.glUniform1f(this.f5338D, f5329E);
        GLES20.glUniform1f(this.f5340H, f5331I);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5343w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5344x, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5343w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5344x, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5343w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5344x, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5343w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5344x, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5334v) {
            this.f5336B = System.currentTimeMillis() - this.f5342M;
            if (this.f5336B > 1000) {
                this.f5342M = System.currentTimeMillis();
            }
        }
        this.f5335A = (((float) this.f5336B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f5329E = f5330F;
        f5331I = f5332J;
    }
}
