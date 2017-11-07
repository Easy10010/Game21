package com.example.game21.other;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.game21.R;
import com.example.game21.fragments.game_result_sence;
import com.example.game21.fragments.gaming_sence;

public class senceManager {
    Context mContext;
    Activity mActivity;

    private static long Duration = 1000;
    public final fragmentsManager fm;

    public senceManager(Context context) {
        mContext = context;
        mActivity = (Activity) context;

        fm = new fragmentsManager(mActivity, R.id.bottom_layout);
        fm.addFragment("gaming_sence", new gaming_sence())
                .addFragment("game_result_sence", new game_result_sence())
                .setShowAnim(AnimationUtils.loadAnimation(mContext, R.anim.faded_in_translate_y))
                .setHideAnim(AnimationUtils.loadAnimation(mContext, R.anim.faded_out_translate_y))
                .hide("gaming_sence", false)
                .hide("game_result_sence", false);

    }

    public senceManager toGamingSence() {
        if (mActivity.isDestroyed()) return this;
        fm.show("gaming_sence").hide("game_result_sence");
        return this;
    }

    public senceManager toResultSence() {
        if (mActivity.isDestroyed()) return this;
        fm.show("game_result_sence").hide("gaming_sence");
        return this;
    }

    public senceManager progressBarEXP(int currEXP, int addEXP) {
        if (mActivity.isDestroyed()) return this;
        View v = fm.get("game_result_sence").getView();
        ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar_currEXP);
        TextView tv = (TextView) v.findViewById(R.id.textView3);
        EXP expClass = new EXP(mContext, currEXP);

        if (addEXP < 0 ){
            pb.getProgressDrawable().setColorFilter(
                    mContext.getResources().getColor(R.color.progressBarGradientColor2)
                    , PorterDuff.Mode.SRC_IN);
            TextView tv2 = (TextView) v.findViewById(R.id.textView2);
            tv2.setTextColor(mContext.getResources().getColor(R.color.progressBarGradientColor2));
            tv2.setText("- "+(-addEXP));
            Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.faded_in_out);
            tv2.startAnimation(anim);
        }else{
            pb.getProgressDrawable().setColorFilter(
                    mContext.getResources().getColor(R.color.progressBarGradientColor1)
                    , PorterDuff.Mode.SRC_IN);
            TextView tv2 = (TextView) v.findViewById(R.id.textView2);
            tv2.setTextColor(mContext.getResources().getColor(R.color.progressBarGradientColor1));
            tv2.setText("+ "+addEXP);
            Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.faded_in_out_y);
            tv2.startAnimation(anim);
        }
        pb.setMax(expClass.getCurrLevelEXP());
        pb.setProgress(currEXP);
        _progressBar(pb, tv, currEXP, currEXP + addEXP, expClass.getNextLevelEXP(),400);
        return this;
    }

    private void _progressBar(final ProgressBar pb, final TextView tv, final int from, final int to, final int NextLevelEXP, final int StartDelay) {
        final ValueAnimator anim = ValueAnimator.ofInt(from, to);
        final int max = pb.getMax();
        ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                pb.setProgress(value);
                tv.setText(String.format("当前经验(%d/%d)", value, max));
                if (value > max) {
                    anim.removeAllUpdateListeners();
                    anim.end();
                    anim.cancel();
                    pb.setMax(NextLevelEXP);
                    _progressBar(pb, tv, 0, to, new EXP(mContext, value).getNextLevelEXP(),0);
                }
            }
        };
        anim.addUpdateListener(listener);
        anim.setDuration(600);
        if (StartDelay!=0){
            anim.setStartDelay(StartDelay);
        }
        anim.start();
    }
}
