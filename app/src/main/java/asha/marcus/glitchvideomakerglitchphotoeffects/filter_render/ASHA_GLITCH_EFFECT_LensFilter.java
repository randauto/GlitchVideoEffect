package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.opengl.GLES20;
import android.os.Build;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import cn.ezandroid.ezfilter.core.FilterRender;

public class ASHA_GLITCH_EFFECT_LensFilter extends FilterRender {
    public static float f5595A;
    private static float f5596D;
    public static float f5597E;
    private static float f5598z;
    private String f5599B = "touchY";
    private int f5600C;
    private int f5601v;
    private int f5602w;
    private String f5603x = "touchX";
    private int f5604y;

    public ASHA_GLITCH_EFFECT_LensFilter() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvec2 barrelDistortion( vec2 p, vec2 amt ) {\np = 2.0 * p - 1.0;\nfloat maxBarrelPower = sqrt(5.0);\nfloat radius = dot(p,p); //faster but doesn't match above accurately\np *= pow(vec2(radius), maxBarrelPower * amt);\nreturn p * 0.5 + 0.5;\n}\nvec2 brownConradyDistortion(vec2 uv, float scalar) {\nuv = (uv - 0.5 ) * 2.0;\nif( true ) {\nfloat barrelDistortion1 = -0.02 * scalar; // K1 in text books\nfloat barrelDistortion2 = 0.0 * scalar; // K2 in text books\nfloat r2 = dot(uv,uv);\nuv *= 1.0 + barrelDistortion1 * r2 + barrelDistortion2 * r2 * r2;\n}\nreturn (uv / 2.0) + 0.5;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*50.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat scalar = 1.0 * amountx;\nvec4 colourScalar = vec4(700.0, 560.0, 490.0, 1.0);\ncolourScalar /= max(max(colourScalar.x, colourScalar.y), colourScalar.z);\ncolourScalar *= 2.0;\ncolourScalar *= scalar;\nvec4 sourceCol = texture2D(inputImageTexture, uv);\nconst float numTaps = 8.0;\ngl_FragColor = vec4( 0.0 );\nfor( float tap = 0.0; tap < numTaps; tap += 1.0 ) {\ngl_FragColor.r += texture2D(inputImageTexture, brownConradyDistortion(uv, colourScalar.r)).r;\ngl_FragColor.g += texture2D(inputImageTexture, brownConradyDistortion(uv, colourScalar.g)).g;\ngl_FragColor.b += texture2D(inputImageTexture, brownConradyDistortion(uv, colourScalar.b)).b;\ncolourScalar *= 0.99;\n}\ngl_FragColor /= numTaps;\ngl_FragColor.a = 1.0;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5604y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5603x);
        this.f5600C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5599B);
        this.f5601v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5602w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5595A = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 2);
                f5597E = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5595A = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 2);
                f5597E = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5595A = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5597E = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5595A = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 2);
                f5597E = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5604y, f5598z);
        GLES20.glUniform1f(this.f5600C, f5596D);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5601v, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5602w, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5601v, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5602w, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5601v, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5602w, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5601v, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5602w, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f5598z = f5595A;
        f5596D = f5597E;
    }
}
