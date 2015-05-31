package com.wangjie.wavecompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/29/15.
 */
public class WaveCompat {

    private static final String TAG = WaveCompat.class.getSimpleName();

    public static void startWaveFilter(final Activity activity, WaveDrawable waveDrawable, final Intent intent) {
        View contentView = activity.findViewById(android.R.id.content);
        Bitmap bitmap = convertViewToBitmap(contentView);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        View view = new View(activity);
        view.setLayoutParams(new ViewGroup.LayoutParams(width, height));

        waveDrawable.setFrameBitmap(bitmap);
        int statusBarHeight = getStatusBarHeight(activity);

        new WaveContainer(view, waveDrawable, width, height) {
            @Override
            protected void onWaveDrawableAnimatorEndExtraAction() {
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.startActivity(intent);
            }
        }
//                .showAtLocation(contentView, Gravity.NO_GRAVITY, 0, 0);
                .showAtLocation(contentView, Gravity.NO_GRAVITY, 0, statusBarHeight);
    }

    public static void startWaveFilterForResult(final Activity activity, WaveDrawable waveDrawable, final Intent intent, final int requestCode) {
        View contentView = activity.findViewById(android.R.id.content);
        Bitmap bitmap = convertViewToBitmap(contentView);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        View view = new View(activity);
        view.setLayoutParams(new ViewGroup.LayoutParams(width, height));

        waveDrawable.setFrameBitmap(bitmap);
        int statusBarHeight = getStatusBarHeight(activity);

        new WaveContainer(view, waveDrawable, width, height) {
            @Override
            protected void onWaveDrawableAnimatorEndExtraAction() {
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.startActivityForResult(intent, requestCode);
            }
        }
//                .showAtLocation(contentView, Gravity.NO_GRAVITY, 0, 0);
                .showAtLocation(contentView, Gravity.NO_GRAVITY, 0, statusBarHeight);
    }

    private static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    private static Bitmap convertViewToBitmap(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
        return view.getDrawingCache();
    }

}
