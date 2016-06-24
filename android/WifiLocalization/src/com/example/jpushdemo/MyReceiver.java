package com.example.jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.google.gson.Gson;
import com.lidroid.xutils.DbUtils;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.model.MapObject;
import com.neu.wifilocalization.activity.IndoorMapActivity;
import com.neu.wifilocalization.activity.WelcomeActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.model.JpushMessage;
import com.neu.wifilocalization.model.NodeListItem;
import com.neu.wifilocalization.mvc.AbstractController;
import com.neu.wifilocalization.mvc.ControllerBuilder;
import com.neu.wifilocalization.utils.SerializeUtil;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    private AbstractController controller = ControllerBuilder.getInstance();
    private MapWidget map;
    private MapObject otherMoveObject;
    private DbUtils db;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        db = App.getInstance().db;

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
            String receiveExtras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Gson gson = new Gson();
            JpushMessage jpushMessage = gson.fromJson((String) receiveExtras, JpushMessage.class);
            if (jpushMessage.getType().equals("user_friend_apply")) {
                AbstractController controller = ControllerBuilder.getInstance();
                if (App.getInstance() != null) {
                    controller.getHuizModel().put("indexToSocial", "1");
                    controller.getHuizModel().put("socialToApplication", "1");
                    if (App.getInstance().isRunning) {
                        System.out.println("程序已经启动");
                        Intent i = new Intent(context, IndoorMapActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else {
                        System.out.println("程序没有启动");
                        Intent i = new Intent(context, WelcomeActivity.class);
                        i.putExtras(bundle);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                } else {

                }
            } else if (jpushMessage.getType().equals("user_friend_accept")) {
                AbstractController controller = ControllerBuilder.getInstance();
                if (App.getInstance() != null) {
                    controller.getHuizModel().put("indexToSocial", "1");
                    controller.getHuizModel().put("socialToUserInfo", "1");
                    controller.getHuizModel().put("userId", jpushMessage.getFriend_id());
                    if (App.getInstance().isRunning) {
                        System.out.println("程序已经启动");
                        Intent i = new Intent(context, IndoorMapActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else {
                        System.out.println("程序没有启动");
                        Intent i = new Intent(context, WelcomeActivity.class);
                        i.putExtras(bundle);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                } else {

                }
            } else {
                System.out.println("不是这个类型");
            }
            // // 打开自定义的Activity
            // Intent i = new Intent(context, TestActivity.class);
            // i.putExtras(bundle);
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // context.startActivity(i);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    // send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String receiveExtras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Gson gson = new Gson();
        JpushMessage jpushMessage = gson.fromJson((String) receiveExtras, JpushMessage.class);
        if (jpushMessage.getType().equals("position")) {
            if (controller.getHuizModel().get("otherMoveObject") != null
                    && controller.getHuizModel().get("map") != null) {
                map = (MapWidget) controller.getHuizModel().get("map");
                otherMoveObject = (MapObject) controller.getHuizModel().get("otherMoveObject");
                otherMoveObject.moveTo((int) jpushMessage.getPosition().getMap_x(), (int) jpushMessage.getPosition()
                        .getMap_y());
                map.invalidate();
            }
        } else if (jpushMessage.getType().equals("node")) {
            System.out.println("接收到节点的推送信息" + jpushMessage.getNode().getId() + "#" + jpushMessage.getNode().getName());
            NodeListItem nli = new NodeListItem();
            try {
                nli.setContent(SerializeUtil.writeObject(jpushMessage.getNode()));
                db.saveOrUpdate(nli);
            } catch (Exception e) {
                e.printStackTrace();
            }
            App.getInstance().footerHintList.set(1, 1);
            if (App.getInstance().footerGridAdapter != null) {
                App.getInstance().footerGridAdapter.notifyDataSetChanged();
            }
        }
    }
}
