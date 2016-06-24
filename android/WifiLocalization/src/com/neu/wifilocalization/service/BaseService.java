package com.neu.wifilocalization.service;

import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.huiztech.common.mvc.HuizView;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.mvc.AbstractController;
import com.neu.wifilocalization.mvc.ControllerBuilder;

public class BaseService extends Service {

    protected AbstractController controller;
    protected HuizView view;
    protected Map<String, Object> model;
    
    protected User loginedUser;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        controller = ControllerBuilder.getInstance();
        view = controller.getHuizView();
        model = controller.getHuizModel();
        loginedUser =  App.getInstance().loginedUser;
    }

}
