package com.albertkhang.bonsaicare.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.TextView;

public class TopBarAnimation {
    private static long transparentShowDuration = 150;
    private static long transparentHideDuration = 100;

    private static float translationValue = 50f;
    private static float translationTitleValue = 50f;

    private static float alphaValue = 1f;

    private static long showTitleDuration = 150;
    private static long hideTitleDuration = 100;

    public static void showIcon(View view) {
        if (view.getTranslationY() != 0) {
            view.setVisibility(View.VISIBLE);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", alphaValue);
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(view, "translationY", 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(transparentShowDuration);
            animatorSet.setStartDelay(hideTitleDuration);
            animatorSet.playTogether(alphaAnimator, translationYAnimator);

            animatorSet.start();
        }
    }

    public static void hideIcon(final View view) {
        if (view.getTranslationY() != translationValue) {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0);
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(view, "translationY", translationValue);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(transparentHideDuration);
            animatorSet.playTogether(alphaAnimator, translationYAnimator);

            animatorSet.start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.GONE);
                        }
                    }, transparentHideDuration);
                }
            }).start();
        }
    }

    public static void showTitle(TextView title, int text) {
        if (title.getTranslationY() == 0) {
            hideTitle(title);

            title.setText(text);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(title, "alpha", alphaValue);
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(title, "translationY", 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(showTitleDuration);
            animatorSet.setStartDelay(hideTitleDuration);
            animatorSet.playTogether(alphaAnimator, translationYAnimator);

            animatorSet.start();
        }
    }

    public static void hideTitle(View view) {
        if (view.getTranslationY() != translationTitleValue) {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0);
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(view, "translationY", translationTitleValue);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(hideTitleDuration);
            animatorSet.playTogether(alphaAnimator, translationYAnimator);

            animatorSet.start();
        }
    }
}
