package asha.marcus.glitchvideomakerglitchphotoeffects.filter_render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import asha.marcus.glitchvideomakerglitchphotoeffects.C0476R;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class ASHA_GLITCH_EFFECT_ArtifactsFilter extends MultiBitmapInputRender {
    public static boolean f5711C = false;
    private static float f5713M;
    public static float f5714N;
    private static float f5715Q;
    public static float f5716R;
    private int f5717E;
    private int f5718F;
    private String f5719G = "iTime";
    private int f5720H;
    private float f5721I;
    private long f5722J = System.currentTimeMillis();
    private String f5723K = "touchX";
    private int f5724L;
    private String f5725O = "touchY";
    private int f5726P;
    private long f5727S;

    public ASHA_GLITCH_EFFECT_ArtifactsFilter(Context context) {
        super(context, getBitmaps(context));
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*2.0;\nfloat amounty = 5.0+mouse.y/iResolution.y*25.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 block = floor(gl_FragCoord.xy / vec2(iResolution.x/amounty));\nvec2 uv_noise = block / vec2(iResolution.x/amounty);\nuv_noise += floor(vec2(iTime) * vec2(iResolution.x, iResolution.y)) / vec2(iResolution.x/amounty);\nfloat block_thresh = pow(fract(iTime * 1236.0453), 2.0) * (1.5 * amountx);\nfloat line_thresh = pow(fract(iTime * 2236.0453), 3.0) * (1.0 * amountx);\nvec2 uv_r = uv, uv_g = uv, uv_b = uv;\nif (texture2D(inputImageTexture2, uv_noise).r < block_thresh ||\ntexture2D(inputImageTexture2, vec2(uv_noise.y, 0.0)).g < line_thresh) {\nvec2 dist = (fract(uv_noise) - .5) * .3;\nuv_r += dist * 2.0;\nuv_g += dist * 0.2;\nuv_b += dist * 2.0;\n}\ngl_FragColor.r = texture2D(inputImageTexture, uv_r).r;\ngl_FragColor.g = texture2D(inputImageTexture, uv_g).g;\ngl_FragColor.b = texture2D(inputImageTexture, uv_b).b;\n}");
        this.f5727S = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f5720H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5719G);
        this.f5724L = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5723K);
        this.f5726P = GLES20.glGetUniformLocation(this.mProgramHandle, this.f5725O);
        this.f5717E = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f5718F = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
        try {
            f5714N = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getWidth() / 10);
            f5716R = (float) (ASHA_GLITCH_EFFECT_VideoFilterActivity.mRenderPipeline.getHeight() / 2);
        } catch (Exception unused) {
            f5714N = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth() / 10);
            f5716R = (float) (ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight() / 2);
        }
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f5720H, this.f5721I);
        GLES20.glUniform1f(this.f5724L, f5713M);
        GLES20.glUniform1f(this.f5726P, f5715Q);
        try {
            GLES20.glUniform1f(this.f5717E, (float) ASHA_GLITCH_EFFECT_VideoActivity.mRenderPipeline.getWidth());
            GLES20.glUniform1f(this.f5718F, (float) ASHA_GLITCH_EFFECT_VideoActivity.mRenderPipeline.getHeight());
        } catch (Exception unused) {
            GLES20.glUniform1f(this.f5717E, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getWidth());
            GLES20.glUniform1f(this.f5718F, (float) ASHA_GLITCH_EFFECT_PhotoActivity.mRenderPipeline.getHeight());
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f5711C) {
            this.f5722J = System.currentTimeMillis() - this.f5727S;
            if (this.f5722J > 200) {
                this.f5727S = System.currentTimeMillis();
            }
        }
        this.f5721I = (((float) this.f5722J) / 1000.0f) * 2.0f * 3.14159f * 0.01f;
        f5713M = f5714N;
        f5715Q = f5716R;
    }

    public static String[] getBitmaps(Context context) {
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), C0476R.C0477drawable.noise);
        File cacheDir = context.getCacheDir();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(cacheDir, "Artifacts.jpg"));
            decodeResource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        String str = cacheDir.getAbsolutePath().toString() + "/" + "Artifacts.jpg";
        if (!new File(str).exists()) {
            Log.e("file", "no image file at location :" + str);
        }
        return new String[]{str};
    }
}
