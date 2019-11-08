package com.albertkhang.bonsaicare.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

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
}
