package com.neu.wifilocalization.activity;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import cn.jpush.android.api.JPushInterface;

import com.example.jpushdemo.ExampleUtil;
import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.login.CommonLogin;
import com.neu.wifilocalization.model.Category;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.utils.CommonUtils;

/**
 * 欢迎页
 * 
 * @author alin
 * 
 */
public class WelcomeActivity extends BaseActivity {

    private CommonLogin commonLogin;

    public static boolean isForeground = false;
    // for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.app_index_welcome);
        hideTitleBar();
        hideFooterBar();
        JPushInterface.init(getApplicationContext());
        setPushTime();
        registerMessageReceiver(); // used for receive msg
        initCategory();
        commonLogin = new CommonLogin(WelcomeActivity.this);
        initDefaultLogin();
        initDefaultPath();
        App.getInstance().isRunning = true;
    }

    public void initCategory() {
        controller.getCategoryList(false, false, new Callback<List<Category>>() {
            @Override
            public void execute(List<Category> list) {
                // try {
                // db.deleteAll(Category.class);
                // } catch (DbException e1) {
                // // TODO Auto-generated catch block
                // e1.printStackTrace();
                // }
                for (int i = 0; i < list.size(); i++) {
                    Category category = list.get(i);
                    try {
                        Category queryResult = db.findFirst(Selector.from(Category.class).where("id", "=",
                                category.getId()));
                        if (queryResult == null) {
                            System.out.println(category.getName() + "不存在");
                            db.save(category);
                        } else {
                            System.out.println(category.getName() + "更新");
                            queryResult = category;
                            db.update(queryResult);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void initDefaultLogin() {
        try {
            final User lastLoginUser = db.findFirst(Selector.from(User.class).where("isLastLogin", "=", true));
            if (lastLoginUser != null) {
                if (lastLoginUser.isAutoLogin()) {
                    commonLogin.login(lastLoginUser, false);
                } else {
                    view.showMessage("未登录");
                    commonLogin.timeCountAndJump();
                }
            } else {
                view.showMessage("未登录");
                commonLogin.timeCountAndJump();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void initDefaultPath() {
        // CommonUtils.initAudioPath();
        File dir = new File(Environment.getExternalStorageDirectory() + CommonUtils.imagePath+"/");
        if (!dir.exists()) {
            System.out.println("图片路径不存在");
            try {
                dir.mkdirs();
            } catch (Exception e) {
            }
        }else{
            System.out.println("图片路径存在");
        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                // setCostomMsg(showMsg.toString());
            }
        }
    }

    /**
     * 设置允许接收通知时间
     */
    private void setPushTime() {
        StringBuffer daysSB = new StringBuffer();
        Set<Integer> days = new HashSet<Integer>();
        days.add(0);
        daysSB.append("0,");
        days.add(1);
        daysSB.append("1,");
        days.add(2);
        daysSB.append("2,");
        days.add(3);
        daysSB.append("3,");
        days.add(4);
        daysSB.append("4,");
        days.add(5);
        daysSB.append("5,");
        days.add(6);
        daysSB.append("6,");
        // 调用JPush api设置Push时间
        JPushInterface.setPushTime(getApplicationContext(), days, 0, 23);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        JPushInterface.onResume(WelcomeActivity.this);
    }

    @Override
    public void onPause() {
        isForeground = false;
        super.onPause();
        JPushInterface.onPause(WelcomeActivity.this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}
