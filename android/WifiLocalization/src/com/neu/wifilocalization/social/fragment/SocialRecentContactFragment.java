package com.neu.wifilocalization.social.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huiztech.common.http.HuizBaseJsonResponseHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.fragment.BaseFragment;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.model.UserServerResponse;
import com.neu.wifilocalization.social.adapter.RecentChatFriendListAdapter;
import com.neu.wifilocalization.utils.xmpp.XmppTool;

/**
 * 最近聊天好友页面
 * 
 * @author alin
 * 
 */
public class SocialRecentContactFragment extends BaseFragment {

    private static SocialRecentContactFragment instance;

    @ViewInject(R.id.social_recent_chat_view)
    private RelativeLayout recentChat;
    @ViewInject(R.id.recent_chat_list)
    private ListView recentChatList;

    private RecentChatFriendListAdapter adapter;
    private List<ChatMsgEntity> dataList = new ArrayList<ChatMsgEntity>();

    private boolean isEnableToRefresh = false;
    private int downloadCounter = 0;

    public static SocialRecentContactFragment getInstance() {
        if (instance == null) {
            instance = new SocialRecentContactFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_recent_contact, container, false);
        View listItemView = inflater.inflate(R.layout.social_recent_contact_list_item, container, false);
        ViewUtils.inject(this, view);
        ViewUtils.inject(this, listItemView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RecentChatFriendListAdapter(getActivity(), dataList);
        new Thread(initXmppConnectionRunnable).start();  
    }

    Runnable initXmppConnectionRunnable = new Runnable() {
        @Override
        public void run() {
            initXmppConnection();
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case 1:
                System.out.println("收到消息并更新最近联系人");
                if (isEnableToRefresh) {
                    // initMsgData();
                } else {
                    System.out.println("不允许");
                }
            default:
                break;
            }
        };
    };

    public void initXmppConnection() {
        ChatManager cm = XmppTool.getConnection().getChatManager();
        cm.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean able) {
                chat.addMessageListener(new MessageListener() {
                    @Override
                    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
                        String receiveMessageContent = message.getBody();
                        if (!receiveMessageContent.equals("")) {
                            android.os.Message msg = handler.obtainMessage();
                            msg.what = 1;
                            msg.sendToTarget();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isEnableToRefresh = true;
        initMsgData();
    }

    @Override
    public void onPause() {
        super.onPause();
        isEnableToRefresh = false;
    }

    public void initMsgData() {
        downloadCounter = 0;
        try {
            List<ChatMsgEntity> lastMsgList = db.findAll(Selector.from(ChatMsgEntity.class).where("isLast", "=", true)
                    .orderBy("date", true));
            if (lastMsgList != null) {
                dataList.clear();
                for (int i = 0; i < lastMsgList.size(); i++) {
                    if (lastMsgList.get(i).getChatTag().split("@")[0].equals(loginedUser.getUsername().toLowerCase())) {
                        dataList.add(lastMsgList.get(i));
                    }
                }
                if (lastMsgList.size() > 0) {
                    view.showBusy();
                    downloadUser(lastMsgList);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void downloadUser(final List<ChatMsgEntity> chatList) {
        final String chatWithWho = chatList.get(downloadCounter).getChatTag().split("@")[1];
        controller.getUserInfo(false, false, loginedUser, "", chatWithWho,
                new HuizBaseJsonResponseHandler.Callback<User>() {
                    @Override
                    public void execute(User user) {
                        downloadCounter = downloadCounter + 1;
                        try {
                            User userChatWith = null;
                            String sql = "select * from com_neu_wifilocalization_model_User where LOWER(username) = '"
                                    + chatWithWho + "'";
                            Cursor cursor = db.execQuery(sql);
                            cursor.moveToFirst();
                            for (int i = 0; i < cursor.getCount(); i++) {
                                String username = cursor.getString(cursor.getColumnIndex("username"));
                                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                                String id = cursor.getString(cursor.getColumnIndex("id"));

                                userChatWith = new User();
                                userChatWith.setUsername(username);
                                userChatWith.setAvatar(avatar);
                                userChatWith.setId(id);
                            }
                            if (userChatWith == null) {
                                User downloadUser = user;
                                System.out.println("用户" + downloadUser.getUsername() + "下载完成");
                                db.save(downloadUser);
                            } else {
                                User upadateUser = user;
                                System.out.println("用户" + upadateUser.getUsername() + "更新完成");
                                db.update(upadateUser, "avatar");
                            }
                            if (chatList.size() == downloadCounter) {
                                recentChatList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                view.hideBusy();
                            } else {
                                downloadUser(chatList);
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick(R.id.social_recent_chat_view)
    public void clickRecentChat(View v) {
        Toast.makeText(this.getActivity(), "start to chat", Toast.LENGTH_SHORT).show();
    }
}
