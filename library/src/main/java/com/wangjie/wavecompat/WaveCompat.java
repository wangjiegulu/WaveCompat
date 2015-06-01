package com.wangjie.wavecompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/29/15.
 */
public class WaveCompat {

    private static final String TAG = WaveCompat.class.getSimpleName();

    public static class IntentKey {
        public static final String IS_WAVE_COMPAT = "WAVE_COMPAT_INTENT_KEY_IS_WAVE_COMPAT";
        public static final String BACKGROUND_COLOR = "WAVE_COMPAT_INTENT_KEY_BACKGROUND_COLOR";
    }

    /**
     * Start an activity with wave effect.
     * @param activity The FROM activity
     * @param waveDrawable
     * @param intent
     * @param backgroundColor Background of the TO activity
     */
    public static void startWaveFilter(final Activity activity, WaveDrawable waveDrawable, final Intent intent, int backgroundColor) {
        intent.putExtra(IntentKey.BACKGROUND_COLOR, backgroundColor);
        startWaveFilter(activity, waveDrawable, intent);
    }

    /**
     * Start an activity with wave effect, but without background color of the TO activity
     * @param activity
     * @param waveDrawable
     * @param intent
     */
    public static void startWaveFilter(final Activity activity, WaveDrawable waveDrawable, final Intent intent) {
        prepareWaveIntent(intent);

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

    /**
     * Start an activity for result with wave effect.
     * @param activity
     * @param waveDrawable
     * @param intent
     * @param requestCode
     * @param backgroundColor
     */
    public static void startWaveFilterForResult(final Activity activity, WaveDrawable waveDrawable, final Intent intent, final int requestCode, int backgroundColor) {
        intent.putExtra(IntentKey.BACKGROUND_COLOR, backgroundColor);
        startWaveFilterForResult(activity, waveDrawable, intent, requestCode);
    }

    /**
     * Start an activity for result with wave effect, but without background color of the TO activity
     * @param activity
     * @param waveDrawable
     * @param intent
     * @param requestCode
     */
    public static void startWaveFilterForResult(final Activity activity, WaveDrawable waveDrawable, final Intent intent, final int requestCode) {
        prepareWaveIntent(intent);

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

    /**
     * Do some prepare things.
     * @param intent
     */
    private static void prepareWaveIntent(Intent intent) {
        intent.putExtra(IntentKey.IS_WAVE_COMPAT, true);
    }

    /**
     * The default initial transition animation.
     * @param activity
     * @param offsetY
     * @param backgroundFromColor
     * @param backgroundToColor
     */
    public static void transitionDefaultInitial(Activity activity, int offsetY, int backgroundFromColor, int backgroundToColor) {
        final View contentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
        alphaAnimator.setDuration(200);
        ValueAnimator marginTopAnimator = ValueAnimator.ofInt(offsetY, 0);
        marginTopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) contentView.getLayoutParams();
                lp.topMargin = value;
                contentView.setLayoutParams(lp);
            }
        });
        marginTopAnimator.setDuration(200);

        ValueAnimator bgColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), backgroundFromColor, backgroundToColor);
        bgColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                contentView.setBackgroundColor(value);
            }
        });
        bgColorAnimator.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(marginTopAnimator).with(alphaAnimator).before(bgColorAnimator);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
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
