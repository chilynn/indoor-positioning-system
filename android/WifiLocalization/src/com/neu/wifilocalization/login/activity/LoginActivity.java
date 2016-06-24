package com.neu.wifilocalization.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.login.CommonLogin;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.register.activity.RegisterActivity;
import com.neu.wifilocalization.utils.AnimationUtils;

public class LoginActivity extends BaseActivity {

    private CommonLogin commonLogin;

    @ViewInject(R.id.username_edit)
    private EditText usernameEdit;
    @ViewInject(R.id.password_edit)
    private EditText passwordEdit;
    @ViewInject(R.id.login_button)
    private Button loginButton;
    @ViewInject(R.id.register_button)
    private Button reButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.login_index);
        ViewUtils.inject(this);
        initHeader();
        hideFooterBar();
        commonLogin = new CommonLogin(LoginActivity.this);
        commonLogin.initLastLogin(usernameEdit);
    }

    public void initHeader() {
        this.titleText.setText("登录");
    }

    @OnClick(R.id.login_button)
    public void login(View v) {
        if (isValidateSuccess()) {
            final User user = new User();
            user.setUsername(usernameEdit.getText().toString());
            user.setPassword(passwordEdit.getText().toString());
            commonLogin.login(user, true);
        }
    }

    @OnClick(R.id.register_button)
    public void register(View v) {
        Jumper.jump(LoginActivity.this, RegisterActivity.class);
        AnimationUtils.rightToLeft(LoginActivity.this);
    }

    public boolean isValidateSuccess() {
        if (usernameEdit.getText().toString().trim().equals("") || passwordEdit.getText().toString().trim().equals("")) {
            view.showMessage("请输入用户名密码");
            return false;
        } else {
            return true;
        }
    }

}
