package com.neu.wifilocalization.login.filter;

import java.util.HashMap;
import java.util.Map;

import com.neu.wifilocalization.activity.PushMessageActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.mycenter.activity.MyCenterActivity;
import com.neu.wifilocalization.social.activity.SocialTabActivity;

import android.app.Activity;

/**
 * 登陆过滤器 有些界面需要用户登陆之后才允许进入
 * 
 * @author alin
 * 
 */
public class LoginFilter {

    private static Map<String, Class> filterList;

    // private static ArrayList<Class> previousPageList = new
    // ArrayList<Class>(); //保存未登录前停留的界面

    // 需要过滤的列表 ,手动添加到filterList中
    public static Map<String, Class> getFilterList() {
        if (filterList == null) {
            filterList = new HashMap<String, Class>();
            filterList.put(PushMessageActivity.class.getName(), PushMessageActivity.class);
            filterList.put(MyCenterActivity.class.getName(), MyCenterActivity.class);
            filterList.put(SocialTabActivity.class.getName(), SocialTabActivity.class);
        }
        return filterList;
    }

    // 判断是否需要被过滤
    public static boolean filter(Activity currentActivity, Class destinationClass) {
        if (getFilterList().get(destinationClass.getName()) != null) {
            if (isLogin()) {
                return false; // 登陆成功，则不需要被过滤
            } else {
                return true; // 没有登陆登陆，则需要被过滤
            }
        } else {
            return false; // 不在过滤列表，则不需要被过滤
        }
    }

    // 判断是否登陆
    public static boolean isLogin() {
        App application = App.getInstance();
        if (application.loginedUser != null) {// 判断controller中是否有user存在
            return true;
        } else {
            return false;
        }
    }

}
