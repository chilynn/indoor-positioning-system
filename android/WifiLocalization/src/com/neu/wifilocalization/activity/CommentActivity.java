package com.neu.wifilocalization.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.model.CommentEntity;
import com.neu.wifilocalization.model.ServerResponse;

/**
 * 添加评论
 * 
 * @author alin
 * 
 */
public class CommentActivity extends BaseActivity {

    @ViewInject(R.id.common_comment_frame)
    protected RelativeLayout commentRelativeLayout;
    @ViewInject(R.id.comment_edit)
    protected EditText commentEdit;

    private CommentEntity postComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.common_comment);
        this.hideFooterBar();
        ViewUtils.inject(this);
        initTitleBar();
        initComment();
    }

    public void initComment() {
        if (controller.getHuizModel().get("comment") != null) {
            postComment = (CommentEntity) controller.getHuizModel().get("comment");
        }
    }

    public void initTitleBar() {
        this.returnButton.setImageResource(R.drawable.icon_cancel_white);
        this.functionButton.setImageResource(R.drawable.icon_confirm_white);
    }

    @OnClick(R.id.activity_base_function_button)
    public void confirm(View v) {
        String commentText = commentEdit.getText().toString();
        if (commentText.equals("")) {
            Toast.makeText(CommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
        } else {
            postComment.setContent(commentText);
            controller.addComment(true, true, loginedUser, postComment, new Callback<ServerResponse>() {
                @Override
                public void execute(ServerResponse serverResponse) {
                    view.showMessage(serverResponse.getMsg());
                    CommentActivity.this.finish();
                }
            });
        }
    }

    @OnClick(R.id.activity_base_return_button)
    public void cancel(View v) {
        finish();
    }

    public void commentBaseSetContentView(int layoutResId) {
        RelativeLayout content = (RelativeLayout) findViewById(R.id.common_comment_addition);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutResId, null);
        content.addView(v);
    }

}
