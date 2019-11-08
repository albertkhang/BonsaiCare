package com.albertkhang.bonsaicare.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;

import com.albertkhang.bonsaicare.R;

import java.util.logging.Handler;

public class TickMarkAnimation {
    private static long shortDuration = 80;
    private static long longDuration = 150;

    private static float biggerVaule = 1.1f;
    private static float defaultVaule = 1f;
    private static float hideVaule = 0f;

    private static float startRotation = -90f;
    private static float endRotation = 0f;

    public static void showTickMark(View view) {
        if (view.getScaleX() == 0) {
            ObjectAnimator scaleXShortAnimator = ObjectAnimator.ofFloat(view, "scaleX", biggerVaule);
            ObjectAnimator scaleYShortAnimator = ObjectAnimator.ofFloat(view, "scaleY", biggerVaule);
            ObjectAnimator rotationShortAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);

            AnimatorSet shortAnimatorSet = new AnimatorSet();
            shortAnimatorSet.playTogether(scaleXShortAnimator, scaleYShortAnimator, rotationShortAnimator);

            shortAnimatorSet.setDuration(longDuration);
            shortAnimatorSet.start();

            ObjectAnimator scaleXLongAnimator = ObjectAnimator.ofFloat(view, "scaleX", defaultVaule);
            ObjectAnimator scaleYLongAnimator = ObjectAnimator.ofFloat(view, "scaleY", defaultVaule);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1);

            AnimatorSet longAnimatorSet = new AnimatorSet();
            longAnimatorSet.playTogether(scaleXLongAnimator, scaleYLongAnimator, alphaAnimator);

            longAnimatorSet.setDuration(shortDuration);
            longAnimatorSet.setStartDelay(longDuration);
            longAnimatorSet.start();
        }
    }

    public static void showTickMark(View view, long duration) {
        if (view.getScaleX() == 0) {
            ObjectAnimator scaleXShortAnimator = ObjectAnimator.ofFloat(view, "scaleX", biggerVaule);
            ObjectAnimator scaleYShortAnimator = ObjectAnimator.ofFloat(view, "scaleY", biggerVaule);
            ObjectAnimator rotationShortAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);

            AnimatorSet shortAnimatorSet = new AnimatorSet();
            shortAnimatorSet.playTogether(scaleXShortAnimator, scaleYShortAnimator, rotationShortAnimator);

            shortAnimatorSet.setDuration(duration);
            shortAnimatorSet.start();

            ObjectAnimator scaleXLongAnimator = ObjectAnimator.ofFloat(view, "scaleX", defaultVaule);
            ObjectAnimator scaleYLongAnimator = ObjectAnimator.ofFloat(view, "scaleY", defaultVaule);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1);

            AnimatorSet longAnimatorSet = new AnimatorSet();
            longAnimatorSet.playTogether(scaleXLongAnimator, scaleYLongAnimator, alphaAnimator);

            longAnimatorSet.setDuration(shortDuration);
            longAnimatorSet.setStartDelay(duration);
            longAnimatorSet.start();
        }
    }

    public static void hideTickMark(final ImageView view) {
        if (view.getScaleX() > 0) {
            ObjectAnimator scaleXShortAnimator = ObjectAnimator.ofFloat(view, "scaleX", biggerVaule);
            ObjectAnimator scaleYShortAnimator = ObjectAnimator.ofFloat(view, "scaleY", biggerVaule);

            AnimatorSet shortAnimatorSet = new AnimatorSet();
            shortAnimatorSet.playTogether(scaleXShortAnimator, scaleYShortAnimator);

            shortAnimatorSet.setDuration(shortDuration);
            shortAnimatorSet.start();

            ObjectAnimator scaleXLongAnimator = ObjectAnimator.ofFloat(view, "scaleX", hideVaule);
            ObjectAnimator scaleYLongAnimator = ObjectAnimator.ofFloat(view, "scaleY", hideVaule);
            ObjectAnimator rotationShortAnimator = ObjectAnimator.ofFloat(view, "rotation", endRotation, startRotation);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0);

            AnimatorSet longAnimatorSet = new AnimatorSet();
            longAnimatorSet.playTogether(scaleXLongAnimator, scaleYLongAnimator, rotationShortAnimator, alphaAnimator);

            longAnimatorSet.setDuration(longDuration);
            longAnimatorSet.setStartDelay(shortDuration);
            longAnimatorSet.start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageResource(R.drawable.ic_nottick);
                            view.setScaleX(0);
                            view.setScaleY(0);
                            showTickMark(view, shortDuration);
                        }
                    }, shortDuration * 2);
                }
            }).start();
        }
    }
}
