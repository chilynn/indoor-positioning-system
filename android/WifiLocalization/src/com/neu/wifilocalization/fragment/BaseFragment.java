package com.neu.wifilocalization.fragment;

import java.util.ArrayList;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
 * Fragment的基类
 * 
 * @author alin
 * 
 */
public class BaseFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_base, container, false);
        return baseView;
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMVC();
        initGson();
        initDbUtils();
        initBitmapUtils();
        initBaseView();
        initBaseListener();
        initASRD();
    }
    

    @Override
    public void onResume() {
        super.onResume();
        controller.setContext(this.getActivity());
        if (App.getInstance().loginedUser != null) {
            loginedUser = App.getInstance().loginedUser;
        }
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
        audioDialog = new BaiduASRDigitalDialog(getActivity(), params);
        audioDialog.setDialogRecognitionListener(mRecognitionListener);
    }

    public void initDbUtils() {
        db = App.getInstance().db;
    }

    public void initBitmapUtils() {
        bitmapUtils = new BitmapUtils(getActivity());
    }

    public void initMVC() {
        controller = ControllerBuilder.getInstance();
        controller.setContext(getActivity());
        view = controller.getHuizView();
        model = controller.getHuizModel();
    }

    public void initGson() {
        gson = new Gson();
    }
    
    public void baseFindElementById(View view) {
        returnButton = (ImageButton) view.findViewById(R.id.fragment_base_return_button);
        functionButton = (ImageButton) view.findViewById(R.id.fragment_base_function_button);
    }

    public View baseSetContentView(LayoutInflater inflater, ViewGroup container, View view) {
        View baseView = inflater.inflate(R.layout.fragment_base, container, false);
        baseFindElementById(baseView);
        RelativeLayout content = (RelativeLayout) baseView.findViewById(R.id.fragment_base_content);
        content.addView(view);
        return baseView;
    }

    public void initBaseView() {
        titleBar = (RelativeLayout) getActivity().findViewById(R.id.activity_base_header);
        returnButton = (ImageButton) getActivity().findViewById(R.id.activity_base_return_button);
        titleText = (TextView) getActivity().findViewById(R.id.activity_base_title_text);
        functionButton = (ImageButton) getActivity().findViewById(R.id.activity_base_function_button);
        footerBar = (RelativeLayout) getActivity().findViewById(R.id.activity_base_footer);
    }

    public void initBaseListener() {
        returnButton.setOnClickListener(new listener());
    }

    class listener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.activity_base_return_button: // 后退按钮
                getActivity().finish();
                AnimationUtils.leftToRight(getActivity());
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
}
