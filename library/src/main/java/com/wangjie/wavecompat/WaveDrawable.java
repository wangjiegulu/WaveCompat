package com.wangjie.wavecompat;

import android.graphics.*;
import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/29/15.
 */
public class WaveDrawable extends WaveDrawableBase {
    private OnWaveDrawableListener onWaveDrawableListener;

    @Override
    public void setOnWaveDrawableListener(OnWaveDrawableListener onWaveDrawableListener) {
        this.onWaveDrawableListener = onWaveDrawableListener;
    }

    private int width;
    private int height;
    private Point touchPoint;
    private Paint paint;

    private static final long DEFAULT_ANIMATION_DURATION = 200l;
    private long animationDuration = DEFAULT_ANIMATION_DURATION;

    public WaveDrawable setColor(int color) {
        paint.setColor(color);
        return this;
    }

    private int radius;
    private int currentRadius;

    private Bitmap frameBitmap;

    public WaveDrawable() {
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
    }

    private PorterDuffXfermode mProPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if ((width = bounds.right - bounds.left) > 0 && (height = bounds.bottom - bounds.top) > 0) {
            invalidateSelf();

            if (null == touchPoint) {
                touchPoint = new Point(width, height);
            }

            setTouchPoint(touchPoint);

            startWaveAnimator();
        }
    }

    public void startWaveAnimator() {
        if (null != onWaveDrawableListener) {
            onWaveDrawableListener.onWaveDrawableAnimatorStart();
        }
        if (waveAnimator.isRunning()) {
            waveAnimator.end();
        }
        waveAnimator.removeAllUpdateListeners();

        waveAnimator.setIntValues(0, radius);
        waveAnimator.setDuration(animationDuration);
        waveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentRadius = (int) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        waveAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (null != onWaveDrawableListener) {
                    onWaveDrawableListener.onWaveDrawableAnimatorEnd();
                }
            }
        });
        waveAnimator.setInterpolator(new DecelerateInterpolator());
        waveAnimator.start();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        paint.setXfermode(null);
        if (null != frameBitmap) {
            canvas.drawBitmap(frameBitmap, 0, 0, paint);
        }
        paint.setXfermode(mProPorterDuffXfermode);
        if (null != touchPoint) {
            canvas.drawCircle(touchPoint.x, touchPoint.y, currentRadius, paint);
        }
    }

    public WaveDrawable setTouchPoint(int x, int y) {
        return this.setTouchPoint(new Point(x, y));
    }

    public WaveDrawable setTouchPoint(@NonNull Point point) {
        this.touchPoint = point;
        int xx = Math.max(point.x, width - point.x);
        int yy = Math.max(point.y, height - point.y);
        radius = (int) Math.sqrt(xx * xx + yy * yy);
        return this;
    }

    private ValueAnimator waveAnimator = new ValueAnimator();

    public WaveDrawable setFrameBitmap(Bitmap frameBitmap) {
        this.frameBitmap = frameBitmap;
        return this;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }
}
