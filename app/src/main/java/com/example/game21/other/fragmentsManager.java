package com.example.game21.other;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.animation.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuann72 on 2017/11/3.
 */

public class fragmentsManager {
    Activity mActivity;
    android.app.FragmentManager fm;
    Map<String, Fragment> fragMap = new HashMap<String, Fragment>();
    int containerID;
    Animation Anim_Show = null, Anim_Hide = null;

    fragmentsManager(Activity activity, int layoutID) {
        containerID = layoutID;
        mActivity = activity;
        fm = mActivity.getFragmentManager();
    }

    public fragmentsManager addFragment(String key, Fragment frag) {
        FragmentTransaction fT = fm.beginTransaction();
        fT.add(containerID, frag, key);
        fT.commit();
        fragMap.put(key, frag);
        return this;
    }

    public fragmentsManager setShowAnim(Animation Anim) {
        Anim_Show = Anim;
        return this;
    }

    public fragmentsManager setHideAnim(Animation Anim) {
        Anim_Hide = Anim;
        return this;
    }

    public fragmentsManager show(String key) {
        show(key, true);
        return this;
    }

    public fragmentsManager hide(String key) {
        hide(key, true);
        return this;
    }

    public fragmentsManager show(String key, Boolean withAnim) {
        Fragment frag = get(key);
        if (frag != null){
            fm.beginTransaction().show(frag).commit();
            if (Anim_Show != null && withAnim == true){
                frag.getView().startAnimation(Anim_Show);
            }
        }
        return this;
    }

    public fragmentsManager hide(String key, Boolean withAnim) {
        final Fragment frag = get(key);
        if (frag != null){
            if (Anim_Hide != null && withAnim == true){
                Anim_Hide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fm.beginTransaction().hide(frag).commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                frag.getView().startAnimation(Anim_Hide);
            }else{
                fm.beginTransaction().hide(frag).commit();
            }
        }
        return this;
    }

    public Fragment get(String key) {
        Fragment frag = null;
        if (!fragMap.containsKey(key)) {
            Log.d("ERROR", key + " KEY NO FOUND");
        } else {
            frag = fragMap.get(key);
        }
        return frag;
    }


}