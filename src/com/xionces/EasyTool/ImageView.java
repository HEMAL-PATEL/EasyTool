package com.xionces.EasyTool;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.util.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by TayfunCESUR on 7.01.2016.
 */
public class ImageView extends android.widget.ImageView {

    private static final float BITMAP_SCALE = 0.4f;
    private boolean isBusy = false;
    private float radius = -1;

    public ImageView(Context context) {
        super(context);
    }

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public ImageView L(String url, Context context) {
        final Bitmap[] bitmap = new Bitmap[1];

        ImageView img = this;
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected void onPreExecute() {
                isBusy = true;
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap[0];
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                isBusy = false;
                img.setImageBitmap(result);
            }
        }.execute();

        return this;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public ImageView LAC(String url) {
        final Bitmap[] bitmap = new Bitmap[1];
        ImageView img = this;
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap[0];
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                img.setImageBitmap(
                        getCircleBitmap(
                                Bitmap.createScaledBitmap(result, img.getWidth(), img.getHeight(), true)
                        )
                );


            }
        }.execute();
        return this;
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    public ImageView LAB(String url, Context context, float Radius) throws Exception {

        final Bitmap[] bitmap = new Bitmap[1];

        ImageView img = this;
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected void onPreExecute() {
                isBusy = true;
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap[0];
            }

            @Override
            protected void onPostExecute(Bitmap result) {

                Bitmap blurredBitmap = blur(context, result, Radius);
                img.setImageBitmap(blurredBitmap);
            }
        }.execute();

        return this;
    }


    @TargetApi(Build.VERSION_CODES.DONUT)
    public ImageView LABAC(String url, Context context, float Radius) throws Exception {

        final Bitmap[] bitmap = new Bitmap[1];

        ImageView img = this;
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected void onPreExecute() {
                isBusy = true;
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap[0];
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                Bitmap blurredBitmap = blur(context, result, Radius);
                img.setImageBitmap(getCircleBitmap(
                        Bitmap.createScaledBitmap(blurredBitmap, img.getWidth(), img.getHeight(), true)
                ));
            }
        }.execute();

        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Context context, Bitmap imag, float radius) {
        Bitmap outputBitmap = null;
        try {
            int width = Math.round(imag.getWidth() * BITMAP_SCALE);
            int height = Math.round(imag.getHeight() * BITMAP_SCALE);

            Bitmap inputBitmap = Bitmap.createScaledBitmap(imag, width, height, false);
            outputBitmap = Bitmap.createBitmap(inputBitmap);

            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            theIntrinsic.setRadius(radius);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputBitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }


}

