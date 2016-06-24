package com.neu.wifilocalization.application;

import android.app.Activity;
import android.content.Intent;

import com.neu.wifilocalization.login.activity.LoginActivity;
import com.neu.wifilocalization.login.filter.LoginFilter;

/**
 * Activity之间的跳转
 * 
 * @author alin
 * 
 */
public class Jumper {
    // 从currentActivity跳转到destinationActivity
    // 通过登陆过滤判断是否需要跳转到登陆界面
    public static void jump(Activity currentActivity, Class destination) {
        if (LoginFilter.filter(currentActivity, destination)) {
            Intent intent = new Intent();
            intent.setClass(currentActivity, LoginActivity.class); // 过滤然后跳转到登陆界面
            currentActivity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(currentActivity, destination); // 不被过滤
            currentActivity.startActivity(intent);
        }
    }

}
