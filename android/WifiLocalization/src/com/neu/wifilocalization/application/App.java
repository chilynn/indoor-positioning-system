package com.neu.wifilocalization.application;

import java.util.ArrayList;

import org.jivesoftware.smack.ChatManager;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import cn.jpush.android.api.JPushInterface;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.neu.wifilocalization.adapter.GridMenuAdapter;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.social.adapter.ChatMsgViewAdapter;
import com.neu.wifilocalization.utils.emoji.EmojiConversionUtil;

public class App extends Application {

    private static App instance = null;

    public String fileUrl = "";

    public DbUtils db;
    public User loginedUser;

    // ---------------------程序相关----------------------------
    public boolean isRunning = false;
    public GridMenuAdapter footerGridAdapter;
    public ArrayList<Integer> footerHintList;
    // ---------------------程序相关----------------------------
    
    // ---------------------聊天相关------------------------------
    public ChatMsgViewAdapter chatAdapter;
    public ChatManager chatManager;
    public String chattingWith = "";

    // ---------------------聊天相关------------------------------

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Application - onCreate()");
        instance = this;
        initDbUtils();
        initJPush();
        initEmoji();
    }

    public void initDbUtils() {
        System.out.println("初始化数据库");
        DbUpgradeListener dbUpgradeListener = new DbUpgradeListener() {
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                System.out.println("数据库升级-DbUpgradeListener-onUpgrade");
            }
        };
        db = DbUtils.create(this, Environment.getExternalStorageDirectory() + "/", "WifiLocalizationTest19", 1,
                dbUpgradeListener);
    }

    public void initJPush() {
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
    }
    
    public void initEmoji() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EmojiConversionUtil.getInstace().getFileText(App.this);
            }
        }).start();
    }
}
