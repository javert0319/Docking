package com.lib.frame.view;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * ActivityLifecycle管理类
 *
 * @author EthanCo
 * @since 2018/10/18
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private Activity mCurrActivity;
    private boolean currActivityStarted = false;
    private ArrayList<Activity> list = new ArrayList<>();


    public ArrayList<Activity> getActivityList() {
        return list;
    }

    public boolean isContainsActivity(String activityName) {
        for (Activity activity : list) {
            if (activity.getClass().getSimpleName().equals(activityName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 按照传入的acitivtyName匹配activity，并将其余在此activity栈顶的都finish
     * A打开B，B打开C，C调用此方法传入A名字，将依次杀掉C，B
     * @param activityName
     */
    public void clearTopActivity(String activityName) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().getSimpleName().equals(activityName)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int j = list.size() - 1; j >= 0; j--) {
                if (j > index) {
                    list.get(j).finish();
                } else if (j == index) {
                    break;
                }
            }
        }
    }

    public boolean isCurrActivityStarted() {
        return currActivityStarted;
    }

    private ActivityLifecycle() {
    }

    private static class SingleTonHolder {
        private static ActivityLifecycle sInstance = new ActivityLifecycle();
    }

    public static ActivityLifecycle getInstance() {
        return SingleTonHolder.sInstance;
    }

    public Activity getCrrActivity() {
        return mCurrActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        list.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mCurrActivity = activity;
        currActivityStarted = true;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (mCurrActivity == activity) {
            currActivityStarted = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (mCurrActivity == activity) {
            currActivityStarted = false;
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        list.remove(activity);
    }
}
