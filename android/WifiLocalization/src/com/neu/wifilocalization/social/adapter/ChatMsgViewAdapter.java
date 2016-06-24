package com.neu.wifilocalization.social.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.MyBaseAdapter;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.utils.CommonUtils;
import com.neu.wifilocalization.utils.emoji.EmojiConversionUtil;

public class ChatMsgViewAdapter extends MyBaseAdapter<ChatMsgEntity> {

    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> dataList) {
        super(context, dataList);
    }

    public int getItemViewType(int position) {
        ChatMsgEntity entity = dataList.get(position);

        if (entity.getMsgType()) {
            return IMsgViewType.IMVT_COM_MSG;
        } else {
            return IMsgViewType.IMVT_TO_MSG;
        }

    }

    public int getViewTypeCount() {
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMsgEntity chatMsg = dataList.get(position);
        boolean isComMsg = chatMsg.getMsgType();

        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (isComMsg) {
                convertView = LayoutInflater.from(context).inflate(R.layout.social_chatting_item_msg_text_left, null);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.social_chatting_item_msg_text_right, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.iv_userhead);
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (chatMsg.getAvatar() == null) {
            try {
                User chatWith = db.findFirst(Selector.from(User.class).where("username", "=", chatMsg.getName()));
                chatMsg.setAvatar(chatWith.getAvatar());
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            
        }
        if(chatMsg.getAvatar()!=null){
            if(chatMsg.getAvatar().equals("")){
                viewHolder.avatar.setImageResource(R.drawable.default_avatar);
            }else{
                bitmapUtils.display(viewHolder.avatar, Const.BASE_IMAGE + "/" + chatMsg.getAvatar());
            }
        }else{
            viewHolder.avatar.setImageResource(R.drawable.default_avatar);
        }
       
        viewHolder.tvSendTime.setText(chatMsg.getDate());
        if (chatMsg.getText().startsWith(Const.chatMessageSoundTag)){
            String base64Code = chatMsg.getText().replaceFirst("\\[2\\]", "");
            viewHolder.tvContent.setOnClickListener(new clickAudioRecord(base64Code));
            viewHolder.tvContent.setText("");
            if (isComMsg) {
                viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources()
                        .getDrawable(R.drawable.icon_audio_left), null);
            } else {
                viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources()
                        .getDrawable(R.drawable.icon_audio), null);
            }
        } else if (chatMsg.getText().startsWith(Const.chatMessagePictureTag)
                || chatMsg.getText().startsWith(Const.chatMessageLocalPictureTag)) {
            String imagePath = chatMsg.getText().replaceFirst("\\[3\\]", "");
            boolean isFileNotFound = false;
            Drawable drawable = null;
            Bitmap btp = null;
            try {
                FileInputStream fs = new FileInputStream(imagePath);
                BufferedInputStream bs = new BufferedInputStream(fs);
                btp = BitmapFactory.decodeStream(bs);
                drawable = new BitmapDrawable(btp);
                bs.close();
                fs.close();
            } catch (FileNotFoundException e) {
                isFileNotFound = true;
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            viewHolder.tvContent.setOnClickListener(new clickAudioRecord(""));
            if (isFileNotFound) {
                viewHolder.tvContent.setText("[此图片已经删除]");
            } else {
                viewHolder.tvContent.setText("");
            }
        } else {
            viewHolder.tvContent.setOnClickListener(new clickAudioRecord(""));
            SpannableString spannableString = EmojiConversionUtil.getInstace().getExpressionString(context,
                    chatMsg.getText());
            viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            viewHolder.tvContent.setText(spannableString);
        }
        return convertView;
    }

    static class ViewHolder {
        public ImageView avatar;
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }

    class clickAudioRecord implements OnClickListener {

        private String base64Code;

        public clickAudioRecord(String base64Code) {
            this.base64Code = base64Code;
        }

        @Override
        public void onClick(View v) {
            if (base64Code.equals("")) {
                System.out.println("@text msg@");
            } else {
                try {
                    CommonUtils.decoderBase64File(base64Code, CommonUtils.audioFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    // 模拟器里播放传url，真机播放传getAmrPath()
                    mediaPlayer.setDataSource(CommonUtils.audioFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
