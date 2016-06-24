package com.neu.wifilocalization.activity;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.google.gson.Gson;
import com.huiztech.common.mvc.HuizView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.mvc.AbstractController;
import com.neu.wifilocalization.mvc.ControllerBuilder;
import com.neu.wifilocalization.utils.AnimationUtils;

/**
 * FragmentActivity的基类
 * 
 * @author alin
 * 
 */
public class BaseFragmentActivity extends FragmentActivity {

    protected RelativeLayout titleBar;
    protected ImageButton returnButton;
    protected TextView titleText;
    protected ImageButton functionButton;

    protected RelativeLayout footerBar;

    protected AbstractController controller;
    protected Map<String, Object> model;
    protected HuizView view;

    protected DbUtils db;
    protected BitmapUtils bitmapUtils;

    protected Gson gson;

    protected int offset = 0;

    protected User loginedUser;

    protected BaiduASRDigitalDialog audioDialog;
    protected DialogRecognitionListener mRecognitionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_base);
        initMVC();
        initGson();
        initDbUtils();
        initBitmapUtils();
        initBaseView();
        initBaseListener();
        initASRD();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.setContext(this);
        loginedUser = App.getInstance().loginedUser;
    }
    
    @Override
    protected void onDestroy() {
        if (audioDialog != null) {
            audioDialog.dismiss();
        }
        super.onDestroy();
    }

    public void initASRD() {
        mRecognitionListener = new DialogRecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> rs = results != null ? results.getStringArrayList(RESULTS_RECOGNITION) : null;
                if (rs != null && rs.size() > 0) {
                    view.showMessage(rs.get(0));
                }
            }
        };

        Bundle params = new Bundle();
        params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Const.API_KEY);
        params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, Const.SECRET_KEY);
        params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, VoiceRecognitionConfig.PROP_INPUT);
        audioDialog = new BaiduASRDigitalDialog(this, params);
        audioDialog.setDialogRecognitionListener(mRecognitionListener);
    }

    public void initDbUtils() {
        db = App.getInstance().db;
    }

    public void initBitmapUtils() {
        bitmapUtils = new BitmapUtils(BaseFragmentActivity.this);
    }

    public void initMVC() {
        controller = ControllerBuilder.getInstance();
        controller.setContext(this);
        view = controller.getHuizView();
        model = controller.getHuizModel();
    }

    public void initGson() {
        gson = new Gson();
    }

    public void setBaseContentView(int layoutResId) {
        RelativeLayout content = (RelativeLayout) findViewById(R.id.activity_base_content);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutResId, null);
        content.addView(v);
    }

    public void initBaseView() {
        titleBar = (RelativeLayout) this.findViewById(R.id.activity_base_header);
        returnButton = (ImageButton) this.findViewById(R.id.activity_base_return_button);
        titleText = (TextView) this.findViewById(R.id.activity_base_title_text);
        functionButton = (ImageButton) this.findViewById(R.id.activity_base_function_button);
        footerBar = (RelativeLayout) this.findViewById(R.id.activity_base_footer);
    }

    public void initBaseListener() {
        returnButton.setOnClickListener(new listener());
    }

    class listener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.activity_base_return_button: // 后退按钮
                finish();
                AnimationUtils.leftToRight(BaseFragmentActivity.this);
                break;
            }
        }
    }

    public void hideTitleBar() {
        titleBar.setVisibility(View.GONE);
    }

    public void showTitleBar() {
        titleBar.setVisibility(View.VISIBLE);
    }

    public void hideFooterBar() {
        footerBar.setVisibility(View.GONE);
    }

    public void showFooterBar() {
        footerBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            AnimationUtils.leftToRight(BaseFragmentActivity.this);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
