package com.chiligram.android.app.modelo.messagesLib;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    private Utils() {

    }

    /**
     * Gets timestamp in millis and converts it to HH:mm (e.g. 16:44).
     */
    public static String formatTime(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }

    /**
     * Crops image into a circle that fits within the ImageView.
     */
    public static void displayRoundImageFromUrl(final Context context, final String url, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .dontAnimate()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });



    }
    public static void displayRoundImageFromFile(final Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(file)
                .centerCrop()
                .dontAnimate()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });



    }

    public static void displayImageFromFile(final Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(file)
                .centerCrop()
                .dontAnimate()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(false);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    public static void displayRoundImageFromBitMap(final Context context, final Bitmap bmp, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(bmp)
                .centerCrop()
                .dontAnimate()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
    public static void displayImageFromBitMap(final Context context, final Bitmap bmp, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(bmp)
                .centerCrop()
                .dontAnimate()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(false);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    public static void isIntoFile(InputStream is, File file){
        try {
            OutputStream output = new FileOutputStream(file);
            try {
                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;

                while ((read = is.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }

                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                output.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}