package com.neu.wifilocalization.social.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.model.NewRequest;
import com.neu.wifilocalization.social.adapter.NewFriendListAdapter;

/**
 * 新朋友
 * 
 * @author alin
 * 
 */
public class NewFriendActivity extends BaseActivity {

    @ViewInject(R.id.new_friend_list)
    private ListView newFriendList;

    private NewFriendListAdapter adapter;
    private List<NewRequest> dataList = new ArrayList<NewRequest>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.social_new_friend);
        ViewUtils.inject(this);
        hideFooterBar();
        initTitleBar();
        initBodyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initTitleBar() {
        this.titleText.setText("验证消息");
        this.functionButton.setVisibility(View.GONE);
    }

    public void initBodyView() {
        newFriendList.setOnItemClickListener(new clickListItem());
        adapter = new NewFriendListAdapter(this, dataList);
        newFriendList.setAdapter(adapter);
    }

    private void initData() {
        getNewUsers();
    }

    public void getNewUsers() {
        controller.getFriendApply(true, true, loginedUser, new Callback<List<NewRequest>>() {
            @Override
            public void execute(List<NewRequest> requestList) {
                dataList.clear();
                if (requestList.size() == 0) {
                    controller.getHuizModel().put("user_friend_create", null);
                } else {
                    dataList.addAll(requestList);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    class clickListItem implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Intent intent = new Intent();
            // intent.setClass(NewFriendActivity.this, UserInfoActivity.class);
            // startActivity(intent);
            // AnimationUtil.animationRightToLeft(NewFriendActivity.this);
        }
    }

}
