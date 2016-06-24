package com.neu.wifilocalization.utils;

import android.app.Activity;
import android.content.Context;

import com.neu.wifilocalization.R;

/**
 * 动画效果帮助类
 * 
 * @author alin
 * 
 */
public class AnimationUtils {

    public static void rightToLeft(Context context) {
        int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (VERSION >= 5) {
            // 从右往左推
            ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    public static void leftToRight(Context context) {
        int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (VERSION >= 5) {
            // 从右往左推
            ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public static void fadeInOut(Context context) {
        int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (VERSION >= 5) {
            // 淡入淡出
            ((Activity) context).overridePendingTransition(R.anim.fade, R.anim.hold);
        }
    }

    public static void bottomStaticTopCover(Context context) {
        int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (VERSION >= 5) {
            // xxxxx
            ((Activity) context).overridePendingTransition(R.anim.bottom_static, R.anim.up_cover);
        }
    }

    public static void bottomToTop(Context context) {
        int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (VERSION >= 5) {
            // 从下往上推
            ((Activity) context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
    }
}
