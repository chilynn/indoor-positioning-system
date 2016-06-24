package com.neu.wifilocalization.login;

import java.util.Set;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.EditText;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.neu.wifilocalization.activity.IndoorMapActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.model.UserServerResponse;
import com.neu.wifilocalization.mvc.AbstractController;
import com.neu.wifilocalization.mvc.ControllerBuilder;
import com.neu.wifilocalization.mvc.RequestCallback;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CommonUtils;
import com.neu.wifilocalization.utils.xmpp.XmppTool;

/**
 * 登陆的公共操作
 * 
 * @author alin
 * 
 */
public class CommonLogin {

    private Context context;
    private AbstractController controller;
    private DbUtils db;

    public CommonLogin(Context context) {
        this.context = context;
        db = App.getInstance().db;
        controller = ControllerBuilder.getInstance();
    }

    // 登陆操作
    public void login(final User loginUser, boolean showLoading) {
        controller.login(showLoading, loginUser, new Callback<UserServerResponse>() {
            @Override
            public void execute(UserServerResponse serverResponse) {
                controller.getHuizView().showMessage(serverResponse.getMsg());
                if (serverResponse.getState().equals("1")) {
                    User loginedUser = serverResponse.getData();
                    System.out.println("登陆用户id:" + loginedUser.getId());
                    loginedUser.setPassword(loginUser.getPassword());
                    loginedUser.setLastLogin(true);
                    loginedUser.setAutoLogin(true);
                    App.getInstance().loginedUser = loginedUser;
                    //xmppConnect(loginedUser);
                    initJpush(loginedUser);
                    handleLastLogin(loginedUser);
                    ((Activity) context).finish();
                    Jumper.jump((Activity) context, IndoorMapActivity.class);
                    AnimationUtils.fadeInOut(context);
                } else {
                    ((Activity) context).finish();
                    Jumper.jump((Activity) context, IndoorMapActivity.class);
                    AnimationUtils.fadeInOut(context);
                }
            }
        });
    }

    // 倒计时后跳转到主界面
    public void timeCountAndJump() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // Intent intent = new Intent();
                // intent.addCategory(Intent.CATEGORY_HOME);// 后台运行
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 后台运行
                // intent.setClass(context, IndoorMapActivity.class);
                // ((Activity) context).startActivity(intent);
                Jumper.jump((Activity) context, IndoorMapActivity.class);
                AnimationUtils.fadeInOut(context);
                ((Activity) context).finish();
            }
        }.start();
    }

    // 初始化最后登录的用户，并在usernameEdit设置
    public void initLastLogin(EditText usernameEdit) {
        try {
            User queryUser = db.findFirst(Selector.from(User.class).where("isLastLogin", "=", true));
            if (queryUser != null) {
                usernameEdit.setText(queryUser.getUsername());
            } else {

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void initJpush(User user) {
        JPushInterface.setAlias(context, Const.JPUSH_LOGIN_ALIAS + user.getId(), new TagAliasCallback() {
            @Override
            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                System.out.println("JPush 设置alias返回的：" + arg0);
            }
        });
    }

    // 处理最后一次登陆的用户
    public void handleLastLogin(User loginUser) {
        try {
            User lastLoginUser = db.findFirst(Selector.from(User.class).where("isLastLogin", "=", true));
            if (lastLoginUser != null) {
                if (lastLoginUser.getId() == loginUser.getId()) {

                } else {
                    lastLoginUser.setLastLogin(false);
                    db.update(lastLoginUser, null);
                }
            }

            User queryUser = db.findFirst(Selector.from(User.class).where("id", "=", loginUser.getId()));
            if (queryUser == null) {
                System.out.println("db中不存在用户，save " + loginUser.getId());
                db.save(loginUser);
            } else {
                db.update(loginUser, null);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    // 连接openfire服务器
    public void xmppConnect(final User loginUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (XmppTool.getConnection() != null) {
                        XmppTool.closeConnection();
                    }
                    XmppTool.getConnection().login(loginUser.getUsername(), loginUser.getPassword());
                    initXmppChat(loginUser);
                    Presence presence = new Presence(Presence.Type.available);
                    XmppTool.getConnection().sendPacket(presence);
                } catch (XMPPException e) {
                    XmppTool.closeConnection();
                }
            }
        }).start();
    }

    // 初始化聊天室
    public void initXmppChat(final User loginUser) {
        ChatManager cm = XmppTool.getConnection().getChatManager();
        Chat chat = cm.createChat(loginUser.getUsername() + "@" + Const.OPEN_FIRE_SERVER_NAME, null);
        App.getInstance().chatManager = cm;
        cm.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean able) {
                chat.addMessageListener(new MessageListener() {
                    @Override
                    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
                        System.out.println("--来自于--" + message.getFrom());
                        //System.out.println("--消息是--" + message.getBody());
                        String receiveMessageContent = message.getBody();
                        if (!receiveMessageContent.equals("")) {
                            CommonUtils.Vibrate((Activity) context, 1000);
                            String chatWith = message.getFrom().split("@")[0];
                            String[] args = new String[] { loginUser.getUsername(), chatWith, receiveMessageContent };
                            android.os.Message msg = handler.obtainMessage();
                            msg.what = 1;
                            msg.obj = args;
                            msg.sendToTarget();
                        }
                    }
                });
            }
        });
    }

    // 消息处理handler
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case 1:
                String loginUserName = ((String[]) msg.obj)[0];
                String chatWith = ((String[]) msg.obj)[1];
                String message = ((String[]) msg.obj)[2];
                ChatMsgEntity entity = new ChatMsgEntity();
                if (message.startsWith(Const.chatMessageSoundTag)) {
                    entity.setText(message);
                } else if (message.startsWith(Const.chatMessagePictureTag)) {
                    String base64Code = message.replaceFirst("\\[1\\]", "");
                    String picUrl = CommonUtils.getPicPath();
                    try {
                        CommonUtils.decoderBase64File(base64Code, picUrl);
                        entity.setText(Const.chatMessageLocalPictureTag + picUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    entity.setText(message);
                }
                ChatMsgEntity lastMsg = null;
                try {
                    lastMsg = db.findFirst(Selector.from(ChatMsgEntity.class)
                            .where("chatTag", "=", loginUserName.toLowerCase() + "@" + chatWith.toLowerCase())
                            .and(WhereBuilder.b("isLast", "=", true)));
                    if (lastMsg != null) {
                        lastMsg.setLast(false);
                        db.update(lastMsg, "isLast");
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

                entity.setAvatar("");
                entity.setName(chatWith);
                entity.setChatTag(loginUserName + "@" + chatWith);
                entity.setMsgType(true);

                if (!App.getInstance().chattingWith.equals("")) {
                    if (chatWith.equals(App.getInstance().chattingWith)) {
                        entity.setRead(true);
                    } else {
                        entity.setRead(false);
                    }
                } else {
                    entity.setRead(false);
                }
                entity.setLast(true);
                entity.setDate(CommonUtils.getStringOfCurrentTime());
                dbSaveChatMsg(entity);

                App.getInstance().footerHintList.set(2, 1);
                if (App.getInstance().footerGridAdapter != null) {
                    App.getInstance().footerGridAdapter.notifyDataSetChanged();
                }
            default:
                break;
            }
        };
    };

    // 离线消息存入本地数据库
    public void dbSaveChatMsg(ChatMsgEntity entity) {
        try {
            db.save(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
