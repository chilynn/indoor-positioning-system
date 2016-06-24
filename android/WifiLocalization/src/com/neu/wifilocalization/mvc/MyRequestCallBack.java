package com.neu.wifilocalization.mvc;

import java.util.Map;

import com.huiztech.common.mvc.HuizController;
import com.huiztech.common.mvc.HuizView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public abstract class MyRequestCallBack<T> extends RequestCallBack<T> {

    private HuizController controller;
    private HuizView view;
    private Map<String, Object> model;
    private boolean showBusy;
    private boolean hideBusy;

    public MyRequestCallBack(HuizController controller) {
        this(controller, true, true);
    }

    public MyRequestCallBack(HuizController controller, boolean showBusy, boolean hideBusy) {
        this.controller = controller;
        this.view = controller.getHuizView();
        this.model = controller.getHuizModel();
        this.showBusy = showBusy;
        this.hideBusy = hideBusy;
    }
    
    @Override
    public void onSuccess(ResponseInfo<T> responseInfo){
        if (hideBusy) {
            view.hideBusy();
        }
    }

    @Override
    public void onFailure(HttpException error, String msg) {
        view.hideBusy();
        System.out.println("HTTP请求失败：" + msg);
    }

    @Override
    public void onStart() {
        if (showBusy) {
            view.showBusy();
        }
    }

    @Override
    public void onStopped() {
        view.hideBusy();
        System.out.println("请求停止");
    }

    // =========================================================================
    // ==== Setters and Getters
    // =========================================================================
    public HuizView getView() {
        return view;
    }

    public void setView(HuizView view) {
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public boolean isShowBusy() {
        return showBusy;
    }

    public void setShowBusy(boolean showBusy) {
        this.showBusy = showBusy;
    }

    public boolean isHideBusy() {
        return hideBusy;
    }

    public void setHideBusy(boolean hideBusy) {
        this.hideBusy = hideBusy;
    }

}
