package com.neu.wifilocalization.social.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.fragment.BaseFragment;
import com.neu.wifilocalization.social.activity.NewFriendActivity;
import com.neu.wifilocalization.social.activity.SearchUserActivity;
import com.neu.wifilocalization.utils.AnimationUtils;

/**
 * 添加好友
 * 
 * @author alin
 * 
 */
public class SocialAddFriendFragment extends BaseFragment {

    private static SocialAddFriendFragment instance;

    @ViewInject(R.id.near_by_person)
    private RelativeLayout nearByPerson;
    @ViewInject(R.id.search_friend)
    private RelativeLayout searchFriend;

    public static SocialAddFriendFragment getInstance() {
        if (instance == null) {
            instance = new SocialAddFriendFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_addfriends, container, false);// 加载fragment布局
        ViewUtils.inject(this, view); // 注入view和事件
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.near_by_person)
    public void nearPerson(View v) {
    }

    @OnClick(R.id.new_friend)
    public void newFriend(View v) {
        Jumper.jump(getActivity(), NewFriendActivity.class);
        AnimationUtils.rightToLeft(getActivity());
    }

    @OnClick(R.id.search_friend)
    public void searchFriend(View v) {
        Jumper.jump(getActivity(), SearchUserActivity.class);
        AnimationUtils.rightToLeft(getActivity());
    }

}
