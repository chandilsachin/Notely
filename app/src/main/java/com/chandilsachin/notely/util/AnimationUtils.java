package com.chandilsachin.notely.util;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;


public class AnimationUtils {

    public static void slideUpAnimation(View view) {
        view.post(() -> {
            view.setY(view.getTop() + view.getHeight());
            view.animate().y(view.getTop()).setStartDelay(50)
                    .setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator());
        });
    }

    public static void slideUpAnimation(View view, float fromY, float toY) {
        view.post(() -> {
            view.setY(fromY);
            view.animate().y(toY).setStartDelay(50)
                    .setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator());
        });
    }

    public static void slideDownAnimation(View view) {
        view.post(() -> {
            view.setY(view.getTop());
            view.animate().y(view.getTop() + view.getHeight()).setStartDelay(50)
                    .setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator());
        });
    }

    public static void slideDownAnimation(View view, float fromY, float toY) {
        view.post(() -> {
            view.setY(fromY);
            view.animate().y(toY).setStartDelay(50)
                    .setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator());
        });
    }

    public static void showContentFadeIn(View contentView) {
        contentView.setAlpha(0f);
        contentView.setScaleX(0.7f);
        contentView.animate().alpha(1f).scaleX(1f).setStartDelay(50)
                .setDuration(500).setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        contentView.animate().setListener(null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    /*public static void rotateView(Context context, View v) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context,
                R.anim.rotate_anim);
        animation.setInterpolator(new LinearInterpolator());
        v.startAnimation(animation);
    }*/
}
