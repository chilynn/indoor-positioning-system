package com.neu.wifilocalization.register.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.activity.IndoorMapActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.login.CommonLogin;
import com.neu.wifilocalization.model.ServerResponse;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CommonUtils;

public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.username_edit)
    private EditText usernameEdit;
    @ViewInject(R.id.password_edit)
    private EditText passwordEdit;
    @ViewInject(R.id.password_confirm_edit)
    private EditText passwordConfirmEdit;
    @ViewInject(R.id.register_button)
    private Button registerButton;

    private CommonLogin commonLogin;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.register_index);
        ViewUtils.inject(this);
        initHeader();
        hideFooterBar();
        commonLogin = new CommonLogin(RegisterActivity.this);
    }

    public void initHeader() {
        this.titleText.setText("注册");
    }
    

    @OnClick(R.id.register_button)
    public void register(View v) {
        if (isValidateSuccess()) {
            final User user = new User();
            user.setUsername(usernameEdit.getText().toString());
            user.setPassword(passwordEdit.getText().toString());
            controller.getHuizView().showBusy();
            controller.regiseter(true,false,user, new Callback<ServerResponse>() {
                @Override
                public void execute(ServerResponse serverResponse) {
                    controller.getHuizView().hideBusy();
                    view.showMessage(serverResponse.getMsg());
                    commonLogin.login(user, false);
//                    if(serverResponse.getState().equals("1")){
//                        App.getInstance().loginedUser= user;
//                        finish();
//                        Jumper.jump(RegisterActivity.this, IndoorMapActivity.class);
//                        AnimationUtils.fadeInOut(RegisterActivity.this);
//                    }
                }
            });
        }
    }

    public boolean isValidateSuccess() {
        if (usernameEdit.getText().toString().trim().equals("") || passwordEdit.getText().toString().trim().equals("")
                || passwordConfirmEdit.getText().toString().equals("")) {
            view.showMessage("请输入注册信息");
            return false;
        } else {
            if (!passwordEdit.getText().toString().equals(passwordConfirmEdit.getText().toString())) {
                view.showMessage("密码不一致");
                return false;
            } else {
                if (usernameEdit.getText().toString().length() < 16 && passwordEdit.getText().toString().length() < 16) {
                    if (CommonUtils.isValidateUserName(usernameEdit.getText().toString())
                            && CommonUtils.isValidateUserName(passwordEdit.getText().toString())) {
                        return true;
                    } else {
                        view.showMessage("用户名和密码只允许英文字母、下划线和数字");
                        return false;
                    }
                } else {
                    view.showMessage("用户名和密码不得超于16位");
                    return false;
                }
            }
        }
    }

}
