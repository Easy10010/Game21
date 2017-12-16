package com.example.game21.other;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;


/**
 * Created by Yuann72 on 2017/10/28.
 */

public class timer {
    static android.widget.ProgressBar ProgressBar;
    boolean running = false;

    AnimatorSet mAnimatorSet;
    ValueAnimator mAnimator_millis,mAnimator_color;
    int Millis;
    int[] progressBarGradientColor = new int[]{0,0};
    timer.OnEvent listener;
    public interface OnEvent {
        void end();
    }

    timer(android.widget.ProgressBar pb, int Millis, OnEvent l) {
        ProgressBar = pb;

        listener = l;
        setMillis(Millis);
    }

    private timer resetAnima(){
        mAnimatorSet = new AnimatorSet();

        mAnimator_millis = ValueAnimator.ofInt(Millis, 0);
        mAnimator_color = null;
        if (progressBarGradientColor[0] != 0 && progressBarGradientColor[1] != 0) {
            mAnimator_color = ValueAnimator.ofInt(0,100);
            final gradientColor g = new gradientColor(progressBarGradientColor[0], progressBarGradientColor[1]);
            mAnimator_color.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();

                    ProgressBar.getProgressDrawable().setColorFilter(
                            g.getColor(value), PorterDuff.Mode.SRC_IN);
                }
            });
            mAnimatorSet.playTogether(mAnimator_millis,mAnimator_color);
        }else{
            mAnimatorSet.playTogether(mAnimator_millis);
        }
        mAnimatorSet.setDuration(Millis);

        mAnimator_millis.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ProgressBar.setProgress(value);
            }
        });

        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (running) {
                    if (listener != null) {
                        listener.end();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return this;
    }

    public timer setMillis(int m) {
        ProgressBar.setMax(m);
        Millis = m;
        return this;
    }
    public timer setGradientColor(int formColor,int toColor) {
        progressBarGradientColor[0] = formColor;
        progressBarGradientColor[1] = toColor;
        return this;
    }
    public timer start() {
        running = true;
        if (mAnimatorSet != null){
            mAnimatorSet.removeAllListeners();
            mAnimatorSet.end();
        }
        resetAnima();
        mAnimatorSet.start();
        return this;
    }
    public timer end() {
        running = false;
        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
            mAnimatorSet.end();
        }
        return this;
    }
}