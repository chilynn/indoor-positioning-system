package com.neu.wifilocalization.mycenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.model.ServerResponse;
import com.neu.wifilocalization.model.User;

public class EditInfoActivity extends BaseActivity {

    @ViewInject(R.id.mycenter_edit_type_text)
    private TextView editTypeText;
    @ViewInject(R.id.mycenter_info_edit)
    private EditText infoEdit;

    private String type = "1";
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.mycenter_edit_info);
        hideFooterBar();
        initTitleBar();
        ViewUtils.inject(this);
        initContent();
        initTypeText();
    }

    public void initTitleBar() {
        this.titleText.setText("设置个人信息");
        this.functionButton.setVisibility(View.VISIBLE);
        this.functionButton.setBackgroundResource(R.drawable.icon_confirm_white);
    }

    public void initContent() {
        if (model.get("editContent") != null) {
            content = controller.getHuizModel().get("editContent").toString();
            infoEdit.setText(content);
            infoEdit.setSelection(content.length());// 将光标移至文字末尾
        }
    }

    public void initTypeText() {
        type = controller.getHuizModel().get("editInfoType").toString();
        if (type.equals("1")) {
            editTypeText.setText("请输入您的昵称");
        }
        if (type.equals("2")) {
            editTypeText.setText("请输入您的个性签名");
        }
    }

    @OnClick(R.id.activity_base_function_button)
    public void modify(View v) {
        User user = new User();
        user.setId(loginedUser.getId());
        user.setUsername(loginedUser.getUsername());
        user.setPassword(loginedUser.getPassword());
        if (type.equals("1")) {
            user.setNickname(infoEdit.getText().toString());
        }
        if (type.equals("2")) {
            user.setSignature(infoEdit.getText().toString());
        }
        controller.editUser(true, true, user, new Callback<ServerResponse>() {
            @Override
            public void execute(ServerResponse serverResponse) {
                view.showMessage(serverResponse.getMsg());
                finish();
            }
        });
    }

}
