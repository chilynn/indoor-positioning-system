package com.neu.wifilocalization.social.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.tools.photoselector.utils.BitmapStore;
import com.neu.tools.photoselector.utils.FileUtils;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.social.adapter.ChatMsgViewAdapter;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CommonUtils;
import com.neu.wifilocalization.utils.MyAudioRecorder;
import com.neu.wifilocalization.utils.MyAudioRecorder.Callback;
import com.neu.wifilocalization.utils.xmpp.XmppTool;
import com.neu.wifilocalization.view.PopMenu;

/**
 * 聊天界面
 * 
 * @author alin
 * 
 */
public class ChatActivity extends BaseActivity {

    @ViewInject(R.id.btn_send)
    private ImageButton sendButton;
    @ViewInject(R.id.et_sendmessage)
    private EditText mEditTextContent;
    @ViewInject(R.id.chat_listview)
    private ListView chatListView;
    @ViewInject(R.id.ll_facechoose)
    private RelativeLayout faceSelectLayout;
    @ViewInject(R.id.additional_select_layout)
    private RelativeLayout additonalSelectLayout;
    
    private Chat chat;
    private User chatWith;

    private ChatMsgViewAdapter adapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

    private ImageButton record;
    private MyAudioRecorder audioRecorder;

    private int MaxSelect = 1;

    private PopMenu popMenu;
    private boolean isFriend;
    private boolean isBlack;

    public void initTitleBar() {
        controller.getHuizModel().put("screenName", null);
        User queryResultUser;
        try {
            queryResultUser = db.findFirst(Selector.from(User.class).where("username", "=", chatWith.getUsername()));
            if (queryResultUser.getScreen_name() != null) {
                if (!queryResultUser.getScreen_name().equals("")) {
                    this.titleText.setText(queryResultUser.getScreen_name());
                    controller.getHuizModel().put("screenName", queryResultUser.getScreen_name());
                } else {
                    this.titleText.setText(queryResultUser.getUsername());
                }
            } else {
                this.titleText.setText(queryResultUser.getUsername());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        this.functionButton.setImageResource(R.drawable.icon_more);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.social_chat);
        ViewUtils.inject(this);
        initUserInfo();
        hideFooterBar();
        audioRecorder = new MyAudioRecorder(ChatActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initPullList();
        initData();
        initXmppChat();
        record = (ImageButton) this.findViewById(R.id.btn_sound);

        // 录音
        record.setOnTouchListener(new record());
    }

    public void getImagePath() {
        try {
            String path = BitmapStore.drr.get(BitmapStore.max);
            Bitmap bm = BitmapStore.revitionImageSize(path);
            BitmapStore.bmp.add(bm);
            String newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
            FileUtils.saveBitmap(bm, "" + newStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTitleBar();
        App.getInstance().chattingWith = chatWith.getUsername();
        if (BitmapStore.drr.size() == 1) {
            getImagePath();
            String Str = BitmapStore.drr.get(0).substring(BitmapStore.drr.get(0).lastIndexOf("/") + 1,
                    BitmapStore.drr.get(0).lastIndexOf("."));
            sendPicture(FileUtils.SDPATH + Str + ".jpg");
        }
    }

    public void sendPicture(String url) {
        String base64Code = "";
        try {
            base64Code = CommonUtils.encodeBase64File(url);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        updataLastMsg();
        ChatMsgEntity entity = new ChatMsgEntity();
        entity.setDate(CommonUtils.getStringOfCurrentTime());
        entity.setName(loginedUser.getUsername());
        entity.setAvatar(loginedUser.getAvatar());
        entity.setMsgType(false);
        entity.setLast(true);
        entity.setRead(true);
        entity.setChatTag(loginedUser.getUsername() + "@" + chatWith.getUsername());
        entity.setText(Const.chatMessageLocalPictureTag + url);
        try {
            chat.sendMessage(Const.chatMessagePictureTag + base64Code);
        } catch (XMPPException e) {
            e.printStackTrace();
        }

        BitmapStore.clearAll();

        dbSaveChatMsg(entity);
        mDataArrays.add(entity);
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListView.getCount() - 1);
    }

    public void initUserInfo() {
        if (controller.getHuizModel().get("chatTarget") != null) {
            chatWith = (User) controller.getHuizModel().get("chatTarget");

            // controller.getUserInfo(loginedUser, "", "1", chatWith.getId(),
            // new HuizBaseJsonResponseHandler.Callback<User>() {
            // @Override
            // public void execute(User user) {
            // controller.getHuizModel().put("chatTarget", user);
            // chatWith = user;
            // if (chatWith.getIs_friend().equals("1")) {
            // isFriend = true;
            // } else {
            // isFriend = false;
            // }
            //
            // if (chatWith.getIs_blacklist().equals("1")) {
            // isBlack = true;
            // } else {
            // isBlack = false;
            // }
            //
            // initPopWindowData();
            // }
            // });
        } else {
            chatWith = new User();
            chatWith.setUsername("test1");
        }
    }
    
    private class record implements OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                audioRecorder.showRecording();
                break;
            case MotionEvent.ACTION_UP:
                audioRecorder.finishRecord(new Callback() {
                    @Override
                    public void execute() {
                        String base64Code = "";
                        try {
                            base64Code = CommonUtils.encodeBase64File(CommonUtils.audioFile);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        updataLastMsg();
                        ChatMsgEntity entity = new ChatMsgEntity();
                        entity.setDate(CommonUtils.getStringOfCurrentTime());
                        entity.setName(loginedUser.getUsername());
                        entity.setAvatar(loginedUser.getAvatar());
                        entity.setMsgType(false);
                        entity.setLast(true);
                        entity.setRead(true);
                        entity.setChatTag(loginedUser.getUsername() + "@" + chatWith.getUsername());
                        entity.setText(Const.chatMessageSoundTag + base64Code);
                        try {
                            chat.sendMessage(Const.chatMessageSoundTag + base64Code);
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        }
                        dbSaveChatMsg(entity);
                        mDataArrays.add(entity);
                        adapter.notifyDataSetChanged();
                        mEditTextContent.setText("");
                        chatListView.setSelection(chatListView.getCount() - 1);
                        // sendFile("mnt/sdcard/1899.amr");
                    }
                });
                break;
            }
            return false;
            }
    }

    public void initXmppChat() {
        ChatManager cm = XmppTool.getConnection().getChatManager();
        chat = cm.createChat(chatWith.getUsername() + "@" + Const.OPEN_FIRE_SERVER_NAME, null);
        cm.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean able) {
                chat.addMessageListener(new MessageListener() {
                    @Override
                    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
                        System.out.println("Logged in as " + XmppTool.getConnection().getUser());
                        System.out.println("--tags-form--" + message.getFrom());
                        System.out.println("--tags-message--" + message.getBody());
                        String receiveMessageContent = message.getBody();
                        System.out.println(chatWith.getUsername().toLowerCase());
                        if (message.getFrom().toLowerCase().contains(chatWith.getUsername().toLowerCase())) {
                            String[] args = new String[] { chatWith.getUsername(), receiveMessageContent,
                                    CommonUtils.getCurrentTime(), "IN" };
                            android.os.Message msg = handler.obtainMessage();
                            msg.what = 2;
                            msg.obj = receiveMessageContent;
                            msg.sendToTarget();
                        } else {
                            System.out.println("不相等");
                        }
                    }
                });
            }
        });
    }

    private void dbSaveChatMsg(ChatMsgEntity entity) {
        try {
            db.save(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case 2:
                if (msg.obj == null) {
                    view.showMessage("receive an empty message");
                } else {
                    ChatMsgEntity entity = new ChatMsgEntity();
                    String receiveMessageContent = msg.obj.toString();
                    System.out.println("聊天界面收到的消息:" + receiveMessageContent);
                    if (receiveMessageContent.startsWith(Const.chatMessageSoundTag)) {
                        // view.showMessage("聊天界面收到一条语音");
                        entity.setText(receiveMessageContent);
                    } else if (receiveMessageContent.startsWith(Const.chatMessagePictureTag)) {
                        // view.showMessage("聊天界面收到一张图片");
                        String base64Code = receiveMessageContent.replaceFirst("\\[1\\]", "");
                        String picUrl = CommonUtils.getPicPath();
                        System.out.println("聊天界面收到一张图片" + picUrl);
                        try {
                            CommonUtils.decoderBase64File(base64Code, picUrl);
                            entity.setText(Const.chatMessageLocalPictureTag + picUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // view.showMessage("聊天界面收到一条消息");
                        entity.setText(receiveMessageContent);
                    }
                    entity.setDate(CommonUtils.getCurrentTime());
                    entity.setAvatar(chatWith.getAvatar());
                    entity.setName(chatWith.getUsername());
                    entity.setChatTag(loginedUser.getUsername() + "@" + chatWith.getUsername());
                    entity.setMsgType(true);
                    entity.setDate(CommonUtils.getCurrentTime());
                    // dbSaveChatMsg(entity);

                    mDataArrays.add(entity);
                    adapter.notifyDataSetChanged();
                    // mEditTextContent.setText("");
                    chatListView.setSelection(chatListView.getCount() - 1);
                }
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
            }
        };
    };

    // 初始化列表和适配器
    public void initPullList() {
        chatListView.setCacheColorHint(Color.TRANSPARENT);// 防止滚动时不变黑
        chatListView.setDividerHeight(0);
    }

    public void initData() {
        try {
            List<ChatMsgEntity> chatList = db.findAll(Selector.from(ChatMsgEntity.class).where("chatTag", "=",
                    loginedUser.getUsername().toLowerCase() + "@" + chatWith.getUsername().toLowerCase()));
            if (chatList != null) {
                for (int i = 0; i < chatList.size(); i++) {
                    if (chatList.get(i).getMsgType()) {
                        chatList.get(i).setAvatar(chatWith.getAvatar());
                    } else {
                        chatList.get(i).setAvatar(loginedUser.getAvatar());
                    }
                    chatList.get(i).setRead(true);
                    db.update(chatList.get(i), "isRead", "avatar");
                    mDataArrays.add(chatList.get(i));
                }
            } else {
                System.out.println("null" + "@@");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        adapter = new ChatMsgViewAdapter(this, mDataArrays);
        App.getInstance().chatAdapter = adapter;
        chatListView.setAdapter(adapter);

    }

    public void updataLastMsg() {
        ChatMsgEntity lastMsg = null;

        try {
            lastMsg = db.findFirst(Selector
                    .from(ChatMsgEntity.class)
                    .where("chatTag", "=",
                            loginedUser.getUsername().toLowerCase() + "@" + chatWith.getUsername().toLowerCase())
                    .and(WhereBuilder.b("isLast", "=", true)));

            if (lastMsg != null) {
                lastMsg.setLast(false);
                db.update(lastMsg, "isLast");
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_send)
    public void sendMsg(View v) {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() == 0) {
            if (faceSelectLayout.getVisibility() == View.VISIBLE) {
                faceSelectLayout.setVisibility(View.GONE);
                additonalSelectLayout.setVisibility(View.GONE);
            }
            if (additonalSelectLayout.getVisibility() == View.GONE) {
                additonalSelectLayout.setVisibility(View.VISIBLE);
            } else {
                additonalSelectLayout.setVisibility(View.GONE);
            }
        }

        if (contString.length() > 0) {
            updataLastMsg();
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(CommonUtils.getStringOfCurrentTime());
            entity.setAvatar(loginedUser.getAvatar());
            entity.setChatTag(loginedUser.getUsername() + "@" + chatWith.getUsername());
            entity.setName(loginedUser.getUsername());
            entity.setMsgType(false);
            entity.setLast(true);
            entity.setRead(true);
            entity.setText(contString);

            dbSaveChatMsg(entity);

            mDataArrays.add(entity);
            adapter.notifyDataSetChanged();
            mEditTextContent.setText("");
            chatListView.setSelection(chatListView.getCount() - 1);
            try {
                System.out.println("准备发送："+entity.getText());
                chat.sendMessage(entity.getText());
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.activity_base_function_button)
    public void viewInfo(View v) {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        popMenu.showAsDropDown(v);
    }

    public void initPopWindowData() {
        ArrayList<String> contentList = new ArrayList<String>();
        if (isFriend) {
            contentList.add("修改备注");
            contentList.add("删除");
        } else {
            // contentList.add("修改备注");
            contentList.add("加好友");
        }

        if (isBlack) {
            System.out.println("已经屏蔽了");
            contentList.add("取消屏蔽");
        } else {
            System.out.println("没有屏蔽");
            contentList.add("屏蔽");
        }

        popMenu = new PopMenu(this, contentList);
    }

    // 点击头像跳转个人空间
    public void clickLeftHead(View v) {
        model.put("userId", chatWith.getId());
        Jumper.jump(ChatActivity.this, UserInfoActivity.class);
        AnimationUtils.fadeInOut(ChatActivity.this);
    }

    public void clickRightHead(View v) {
        model.put("userId", loginedUser.getId());
        Jumper.jump(ChatActivity.this, UserInfoActivity.class);
        AnimationUtils.fadeInOut(ChatActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case Const.CAMERA_REQUEST_CODE:
            if (data == null) {
                System.out.println("data为null");
            } else {
                System.out.println("data不为空");
            }

            if (BitmapStore.drr.size() < MaxSelect) {
                if (!App.getInstance().fileUrl.equals("")) {
                    File tempFile = new File(App.getInstance().fileUrl);
                    if (tempFile.isFile()) {
                        BitmapStore.drr.add(App.getInstance().fileUrl);
                        App.getInstance().fileUrl = "";
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        XmppTool.closeConnection();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            App.getInstance().chattingWith = "";
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}