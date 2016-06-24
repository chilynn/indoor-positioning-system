package com.neu.wifilocalization.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.IdServerResponse;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.utils.AnimationUtils;

public class UserInfoActivity extends BaseActivity {

    @ViewInject(R.id.avatar)
    private ImageView avatarImage;
    @ViewInject(R.id.name)
    private TextView nameText;
    @ViewInject(R.id.signature)
    private TextView signatureText;
    @ViewInject(R.id.friend_relation_layout)
    private LinearLayout friendRelationLayout;
    @ViewInject(R.id.chat_button)
    private Button chatButton;
    @ViewInject(R.id.location_button)
    private Button locationButton;
    @ViewInject(R.id.stranger_relation_layout)
    private LinearLayout strangerRelationLayout;
    @ViewInject(R.id.add_button)
    private Button addFriendButton;

    private String userId;
    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.social_user_info);
        ViewUtils.inject(this);
        initHeader();
        hideFooterBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBodyView();
    }

    public void initHeader() {
        this.titleText.setText("用户资料");
    }

    public void initBodyView() {
        getUserId();
        getUserInfo();
    }

    public void getUserId() {
        if (model.get("userId") != null) {
            userId = model.get("userId").toString();
        }
    }

    public void getUserInfo() {
        if (model.get("user") != null) {
            userInfo = (User) model.get("user");
            putUserInfoToView(userInfo);
        } else {
            controller.getUserInfo(true, true, loginedUser, userId, "", new Callback<User>() {
                @Override
                public void execute(User user) {
                    userInfo = user;
                    putUserInfoToView(userInfo);
                }
            });
        }
    }

    public void putUserInfoToView(User user) {
        if(user.getId().equals(loginedUser.getId())){
            friendRelationLayout.setVisibility(View.GONE);
            strangerRelationLayout.setVisibility(View.GONE);
        }else{
            initRelationship(user.getIs_friend());
        }
        if(user.getAvatar()!=null){
            if (!user.getAvatar().equals("")) {
                bitmapUtils.display(avatarImage, Const.BASE_IMAGE + "/" + user.getAvatar());
            } else {
                avatarImage.setImageResource(R.drawable.default_avatar);
            } 
        }else{
            avatarImage.setImageResource(R.drawable.default_avatar);
        }
        nameText.setText(user.getUsername());
        if (user.getSignature() != null) {
            signatureText.setText(user.getSignature());
        } else {
            signatureText.setText("暂无");
        }
    }

    public void initRelationship(String isFriend) {
        if (isFriend.equals("1")) {
            System.out.println("好友关系");
            friendRelationLayout.setVisibility(View.VISIBLE);
            strangerRelationLayout.setVisibility(View.GONE);
        } else {
            System.out.println("非好友关系");
            friendRelationLayout.setVisibility(View.GONE);
            strangerRelationLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.chat_button)
    public void chat(View v) {
        controller.getHuizModel().put("chatTarget", userInfo);
        Jumper.jump(UserInfoActivity.this, ChatActivity.class);
        AnimationUtils.rightToLeft(UserInfoActivity.this);
    }

    @OnClick(R.id.add_button)
    public void addFriend(View v) {
        controller.addFriend(true, true, "1", loginedUser, userInfo.getId(), "", new Callback<IdServerResponse>() {
            @Override
            public void execute(IdServerResponse idServerResponse) {

            }
        });
    }

    @OnClick(R.id.location_button)
    public void viewLocation(View v) {
        if (userInfo.getPosition() != null) {
            controller.getHuizModel().put("locationTarget", userInfo);
            Jumper.jump(UserInfoActivity.this, FriendLocationMapActivity.class);
            AnimationUtils.rightToLeft(UserInfoActivity.this);
        } else {
            view.showMessage("地图上没有留下" + userInfo.getUsername() + "的痕迹");
        }
    }

}
