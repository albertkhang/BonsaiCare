package com.albertkhang.bonsaicare.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;

import java.util.logging.Handler;

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

    public static void handleAddSearchIcon(ImageView searchIcon, boolean wantShowSearchIcon, ImageView addIcon, boolean wantShowAddIcon) {
        if (wantShowSearchIcon) {
            showIcon(searchIcon);
        } else {
            hideIcon(searchIcon);
        }

        if (wantShowAddIcon) {
            showIcon(addIcon);
        } else {
            hideIcon(addIcon);
        }
    }

    public static void handleSearchFrame(View view, boolean wantShowSearchFrame) {
        if (wantShowSearchFrame) {
            handleShowSearchFrame(view);
            ImageView btnBack = view.findViewById(R.id.btnBack);
            btnBack.setImageResource(R.drawable.ic_left_arrow);
        } else {
            handleHideSearchFrame(view);
            ImageView btnBack = view.findViewById(R.id.btnBack);
            btnBack.setImageResource(R.drawable.ic_left);
        }
    }

    private static void handleHideSearchFrame(View view) {
        hideSearchFrame(view);
        showIcon(view.findViewById(R.id.imgManageListSearchButton));
        scaleShowAddIconAndTitleTopBar(view);
    }

    private static void scaleShowAddIconAndTitleTopBar(View view) {
        ImageView addIcon = view.findViewById(R.id.imgManageListAddButton);
        TextView title = view.findViewById(R.id.txtDetailSettingTitle);

        showIcon(addIcon);
        showIcon(title);
    }

    private static void hideSearchFrame(View view) {
        final ImageView searchFrame = view.findViewById(R.id.searchFrame);
//        final ImageView searchFrameButton = view.findViewById(R.id.searchFrameButton);
        final EditText txt_search_frame = view.findViewById(R.id.txt_search_frame);

        ObjectAnimator scaleXAnimator1 = ObjectAnimator.ofFloat(searchFrame, "scaleX", 1.005f);
        ObjectAnimator scaleYAnimator1 = ObjectAnimator.ofFloat(searchFrame, "scaleY", 1.005f);
//        ObjectAnimator scaleXAnimator11 = ObjectAnimator.ofFloat(searchFrameButton, "scaleX", 1.005f);
//        ObjectAnimator scaleYAnimator11 = ObjectAnimator.ofFloat(searchFrameButton, "scaleY", 1.005f);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setDuration(50);
        animatorSet1.playTogether(scaleXAnimator1, scaleYAnimator1);

        animatorSet1.start();

        ObjectAnimator scaleXAnimator2 = ObjectAnimator.ofFloat(searchFrame, "scaleX", 0f);
        ObjectAnimator scaleYAnimator2 = ObjectAnimator.ofFloat(searchFrame, "scaleY", 0f);
//        ObjectAnimator scaleXAnimator22 = ObjectAnimator.ofFloat(searchFrameButton, "scaleX", 0f);
//        ObjectAnimator scaleYAnimator22 = ObjectAnimator.ofFloat(searchFrameButton, "scaleY", 0f);
        ObjectAnimator scaleXAnimator222 = ObjectAnimator.ofFloat(txt_search_frame, "scaleX", 0f);
        ObjectAnimator scaleYAnimator222 = ObjectAnimator.ofFloat(txt_search_frame, "scaleY", 0f);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.setStartDelay(50);
        animatorSet2.setDuration(100);
        animatorSet2.playTogether(scaleXAnimator2, scaleYAnimator2, scaleXAnimator222, scaleYAnimator222);

        animatorSet2.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                searchFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchFrame.setVisibility(View.GONE);
//                        searchFrameButton.setVisibility(View.GONE);
                        txt_search_frame.setVisibility(View.GONE);
                    }
                }, 150);
            }
        }).start();
    }

    private static void handleShowSearchFrame(View view) {
        scaleHideAddIconAndTitleTopBar(view);
        hideIcon(view.findViewById(R.id.imgManageListSearchButton));
        showSearchFrame(view);
    }

    private static void showSearchFrame(View view) {
        ImageView searchFrame = view.findViewById(R.id.searchFrame);
//        ImageView searchFrameButton = view.findViewById(R.id.searchFrameButton);
        EditText txt_search_frame = view.findViewById(R.id.txt_search_frame);
        searchFrame.setVisibility(View.VISIBLE);
//        searchFrameButton.setVisibility(View.VISIBLE);
        txt_search_frame.setVisibility(View.VISIBLE);

        ObjectAnimator scaleXAnimator1 = ObjectAnimator.ofFloat(searchFrame, "scaleX", 1.005f);
        ObjectAnimator scaleYAnimator1 = ObjectAnimator.ofFloat(searchFrame, "scaleY", 1.005f);
//        ObjectAnimator scaleXAnimator11 = ObjectAnimator.ofFloat(searchFrameButton, "scaleX", 1.005f);
//        ObjectAnimator scaleYAnimator11 = ObjectAnimator.ofFloat(searchFrameButton, "scaleY", 1.005f);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleXAnimator1, scaleYAnimator1);

        animatorSet1.start();

        ObjectAnimator scaleXAnimator2 = ObjectAnimator.ofFloat(searchFrame, "scaleX", 1f);
        ObjectAnimator scaleYAnimator2 = ObjectAnimator.ofFloat(searchFrame, "scaleY", 1f);
//        ObjectAnimator scaleXAnimator22 = ObjectAnimator.ofFloat(searchFrameButton, "scaleX", 1f);
//        ObjectAnimator scaleYAnimator22 = ObjectAnimator.ofFloat(searchFrameButton, "scaleY", 1f);
        ObjectAnimator scaleXAnimator222 = ObjectAnimator.ofFloat(txt_search_frame, "scaleX", 1f);
        ObjectAnimator scaleYAnimator222 = ObjectAnimator.ofFloat(txt_search_frame, "scaleY", 1f);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.setStartDelay(200);
        animatorSet2.setDuration(50);
        animatorSet2.playTogether(scaleXAnimator2, scaleYAnimator2, scaleXAnimator222, scaleYAnimator222);

        animatorSet2.start();
    }

    private static void scaleHideAddIconAndTitleTopBar(View view) {
        ImageView addIcon = view.findViewById(R.id.imgManageListAddButton);
        TextView title = view.findViewById(R.id.txtDetailSettingTitle);

        hideIcon(addIcon);
        hideIcon(title);
    }
}
