package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;
import asha.marcus.glitchvideomakerglitchphotoeffects.C0476R;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_PhotoFilterActivity;
import asha.marcus.glitchvideomakerglitchphotoeffects.activity.ASHA_GLITCH_EFFECT_VideoFilterActivity;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import p004cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class ASHA_GLITCH_EFFECT_CorruptionFilter extends MultiBitmapInputRender {
    public static boolean f5735C = false;
    private static float f5737M;
    public static float f5738N;
    private static float f5739Q;
    public static float f5740R;
    private int f5741E;
    private int f5742F;
    private String f5743G = "iTime";
    private int f5744H;
    private float f5745I;
    private long f5746J = System.currentTimeMillis();
    private String f5747K = "touchX";
    private int f5748L;
    private String f5749O = "touchY";
    private int f5750P;
    private long f5751S;

    public ASHA_GLITCH_EFFECT_CorruptionFilter(Context context) {
        super(context, getBitmaps(context));
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*2.0;\nfloat amounty = 5.0+mouse.y/iResolution.y*15.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 block = floor(gl_FragCoord.xy / vec2(iResolution.x/amounty));\nvec2 uv_noise = block / vec2(iResolution.x/amounty);\nuv_noise += floor(vec2(iTime) * vec2(iResolution.x, iResolution.y)) / vec2(iResolution.x/amounty);\nfloat block_thresh = pow(fract(iTime * 1236.0453), 2.0) * (0.5 * amountx);\nfloat line_thresh = pow(fract(iTime * 2236.0453), 3.0) * (1.0 * amountx);\nvec2 uv_r = uv, uv_g = uv, uv_b = uv;\nif (texture2D(inputImageTexture2, uv_noise).r < block_thresh ||\ntexture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).g < line_thresh) {\nvec2 dist = (fract(uv_noise) - .5) * .3;\nuv_r += dist * 0.1;\nuv_g += dist * 0.2;\nuv_b += dist * .125;\n}\ngl_FragColor.r = texture2D(inputImageTexture, uv_r).r;\ngl_FragColor.g = texture2D(inputImageTexture, uv_g).g;\ngl_FragColor.b = texture2D(inputImageTexture, uv_b).b;\nif (texture2D(inputImageTexture2, uv_noise).g < block_thresh)\ngl_FragColor.rgb = gl_FragColor.ggg;\nif (texture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).b * 3.5 < line_thresh)\ngl_FragColor.rgb = vec3(0.0, dot(gl_FragColor.rgb, vec3(1.0)), 0.0);\nif (texture2D(inputImageTexture2, uv_noise).g * 1.0 < block_thresh ||\ntexture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).g * 2.5 < line_thresh) {\nfloat line = fract(gl_FragCoord.y / 3.0);\nvec3 mask = vec3(3.0, 0.0, 0.0);\nif (line > 0.333)\nmask = vec3(0.0, 3.0, 0.0);\nif (line > 0.666)\nmask = vec3(0.0, 0.0, 3.0);\ngl_FragColor.xyz *= mask;\n}\n}");
        this.f5751S = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5744H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5743G);
        this.f5748L = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5747K);
        this.f5750P = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5749O);
        this.f5741E = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5742F = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                f5738N = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth() / 10);
                f5740R = (float) (asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused) {
                f5738N = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 10);
                f5740R = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
            }
        } else {
            try {
                f5738N = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 10);
                f5740R = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
            } catch (Exception unused2) {
                f5738N = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth() / 10);
                f5740R = (float) (ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight() / 2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5744H, this.f5745I);
        GLES20.glUniform1f(this.f5748L, f5737M);
        GLES20.glUniform1f(this.f5750P, f5739Q);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                GLES20.glUniform1f(this.f5741E, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5742F, (float) asha.marcus.glitchvideomakerglitchphotoeffects.activity.VideoActivity.mRenderPipeline.getHeight());
            } catch (Exception unused) {
                GLES20.glUniform1f(this.f5741E, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5742F, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
            }
        } else {
            try {
                GLES20.glUniform1f(this.f5741E, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5742F, (float) ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight());
            } catch (Exception unused2) {
                GLES20.glUniform1f(this.f5741E, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getWidth());
                GLES20.glUniform1f(this.f5742F, (float) ASHA_GLITCH_EFFECT_PhotoFilterActivity.mRenderPipeline.getHeight());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5735C) {
            this.f5746J = System.currentTimeMillis() - this.f5751S;
            if (this.f5746J > 200) {
                this.f5751S = System.currentTimeMillis();
            }
        }
        this.f5745I = (((float) this.f5746J) / 1000.0f) * 2.0f * 3.14159f * 0.01f;
        f5737M = f5738N;
        f5739Q = f5740R;
    }

    public static String[] getBitmaps(Context context) {
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), C0476R.C0477drawable.noise);
        File cacheDir = context.getCacheDir();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(cacheDir, "Corruption.jpg"));
            decodeResource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        String str = cacheDir.getAbsolutePath().toString() + "/" + "Corruption.jpg";
        if (!new File(str).exists()) {
            Log.e("file", "no image file at location :" + str);
        }
        return new String[]{str};
    }
}
