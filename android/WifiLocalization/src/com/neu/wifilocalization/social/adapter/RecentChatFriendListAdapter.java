package com.neu.wifilocalization.social.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.MyBaseAdapter;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.social.activity.ChatActivity;
import com.neu.wifilocalization.utils.AnimationUtils;

/**
 * 最近联系人列表适配器
 * 
 * @author alin
 * 
 */
public class RecentChatFriendListAdapter extends MyBaseAdapter<ChatMsgEntity> {

    private String chatWithWho = "";

    public RecentChatFriendListAdapter(Context context, List<ChatMsgEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(R.layout.social_recent_contact_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.recentChatView = (RelativeLayout) view.findViewById(R.id.social_recent_chat_view);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.head_icon);
        viewHolder.messageHintView = (ImageView) view.findViewById(R.id.message_hint);
        viewHolder.nameText = (TextView) view.findViewById(R.id.name_text);
        viewHolder.timeText = (TextView) view.findViewById(R.id.time_text);
        viewHolder.lastChatText = (TextView) view.findViewById(R.id.last_chat_text);

        ChatMsgEntity chatMsg = dataList.get(position);

        if (!chatMsg.isRead()) {
            viewHolder.messageHintView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.messageHintView.setVisibility(View.GONE);
        }

        try {
            chatWithWho = chatMsg.getChatTag().split("@")[1];
            User userChatWith = null;
            String sql = "select * from com_neu_wifilocalization_model_User where LOWER(username) = '" + chatWithWho
                    + "'";
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

            viewHolder.recentChatView.setOnClickListener(new clickRecentChat(userChatWith));

            if (userChatWith != null) {
                if (userChatWith.getAvatar() != null) {
                    if (chatMsg.getAvatar() == null) {
                        chatMsg.setAvatar(userChatWith.getAvatar());
                    }
                    bitmapUtils.display(viewHolder.imageView, Const.BASE_IMAGE + "/" + userChatWith.getAvatar());
                }
            }

            User queryResultUser = db.findFirst(Selector.from(User.class).where("username", "=",
                    userChatWith.getUsername()));
            if (queryResultUser.getScreen_name() != null) {
                if (!queryResultUser.getScreen_name().equals("")) {
                    viewHolder.nameText.setText(queryResultUser.getScreen_name());
                } else {
                    viewHolder.nameText.setText(userChatWith.getUsername());
                }
            } else {
                viewHolder.nameText.setText(userChatWith.getUsername());
            }

            viewHolder.timeText.setText(chatMsg.getDate());

            if (chatMsg.getText().startsWith(Const.chatMessageSoundTag)) {
                viewHolder.lastChatText.setText("[语音]");
            } else if (chatMsg.getText().startsWith(Const.chatMessagePictureTag)
                    || chatMsg.getText().startsWith(Const.chatMessageLocalPictureTag)) {
                viewHolder.lastChatText.setText("[图片]");
            } else {
                viewHolder.lastChatText.setText(chatMsg.getText());
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return view;
    }

    private class ViewHolder {
        RelativeLayout recentChatView;
        ImageView imageView;
        ImageView messageHintView;
        TextView nameText;
        TextView timeText;
        TextView lastChatText;
    }

    class clickRecentChat implements OnClickListener {
        private User chatWith;
        private String name;

        public clickRecentChat(User chatWith) {
            this.chatWith = chatWith;
        }

        @Override
        public void onClick(View v) {

            if (chatWith.getUsername().charAt(0) == '@') {
            } else {
                controller.getHuizModel().put("chatTarget", chatWith);
                Jumper.jump((Activity) context, ChatActivity.class);
                AnimationUtils.rightToLeft(context);
            }
        }
    }

}