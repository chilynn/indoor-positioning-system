package com.neu.wifilocalization.social.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.R.drawable;
import com.neu.wifilocalization.R.id;
import com.neu.wifilocalization.R.layout;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.model.UserServerResponse;
import com.neu.wifilocalization.utils.AnimationUtils;

/**
 * 搜号码页面
 * 
 * @author alin
 * 
 */
public class SearchUserActivity extends BaseActivity {

    @ViewInject(R.id.search_accoun_edit)
    private EditText searchAccountEdit;

    private List<User> dataList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.social_search_user);
        hideFooterBar();
        initTitleBar();
        ViewUtils.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList.clear();
        controller.getHuizModel().put("queryUsers", null);
    }

    public void initTitleBar() {
        this.titleText.setText("搜索");
        this.functionButton.setImageResource(R.drawable.icon_search_left_white);
    }

    @OnClick(R.id.activity_base_function_button)
    public void search(View v) {
        String searchString = searchAccountEdit.getText().toString();
        if (!searchString.equals("")) {
            controller.searchUser(true, true, loginedUser, searchString, "", new Callback<UserServerResponse>() {
                @Override
                public void execute(UserServerResponse userServerResponse) {
                    if(userServerResponse.getState().equals("1")){
                        model.put("userId", userServerResponse.getData().getId());
                        //model.put("userId", userServerResponse.getData().getId());
                        Jumper.jump(SearchUserActivity.this, UserInfoActivity.class);
                        AnimationUtils.rightToLeft(SearchUserActivity.this);
                    }else{
                        view.showMessage(userServerResponse.getMsg());
                    }
                }
            });
        } else {
            SearchUserActivity.this.view.showMessage("输入信息不能为空，请重新输入");
        }
    }
}
