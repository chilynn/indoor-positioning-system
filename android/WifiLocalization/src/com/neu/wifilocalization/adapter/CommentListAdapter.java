package com.neu.wifilocalization.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.CommentEntity;
import com.neu.wifilocalization.social.activity.UserInfoActivity;
import com.neu.wifilocalization.utils.CommonUtils;
import com.neu.wifilocalization.utils.emoji.EmojiConversionUtil;

/**
 * 评论列表适配器
 * 
 * @author alin
 * 
 */
public class CommentListAdapter extends MyBaseAdapter<CommentEntity> {

    public CommentListAdapter(Context context, List<CommentEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.common_more_comment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.rowLayout = (LinearLayout) view.findViewById(R.id.comment_list_item_layout);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.comment_avatar);
        viewHolder.nameText = (TextView) view.findViewById(R.id.commentor_name_text);
        viewHolder.contentText = (TextView) view.findViewById(R.id.comment_content_text);
        viewHolder.timeText = (TextView) view.findViewById(R.id.comment_time_text);
        viewHolder.ratingBar = (RatingBar) view.findViewById(R.id.comment_rating_bar);
        CommentEntity comment = dataList.get(position);
        if (comment.getUser().getAvatar() != null) {
            if (comment.getUser().getAvatar().equals("")) {
                viewHolder.imageView.setImageResource(R.drawable.default_avatar);
            } else {
                bitmapUtils.display(viewHolder.imageView, Const.BASE_IMAGE + "/" + comment.getUser().getAvatar());
            }
        } else {
            viewHolder.imageView.setImageResource(R.drawable.default_avatar);
        }
        viewHolder.nameText.setText(comment.getUser().getUsername());
        viewHolder.timeText.setText(CommonUtils.formatUnixTime(Long.parseLong(comment.getCreate_time())));
        SpannableString spannableString = EmojiConversionUtil.getInstace().getExpressionString(context,
                comment.getContent());
        viewHolder.contentText.setText(spannableString);
        viewHolder.rowLayout.setOnClickListener(new viewUser(comment.getUser().getId()));
        return view;
    }

    class viewUser implements OnClickListener {

        private String userId;

        public viewUser(String userId) {
            this.userId = userId;
        }

        @Override
        public void onClick(View v) {
            // AbstractIFishController controller =
            // IFishControllerFactory.getIFishControllerInstance();
            // controller.getHuizModel().put("userId", userId);
            // IFishTransfer.transfer((Activity)context,
            // UserInfoActivity.class);
            // AnimationUtil.animationRightToLeft(context);
        }
    }

    private class ViewHolder {
        LinearLayout rowLayout;
        ImageView imageView;
        TextView nameText;
        TextView contentText;
        TextView timeText;
        RatingBar ratingBar;
    }
}