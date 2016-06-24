package com.neu.wifilocalization.mvc;

import java.util.HashMap;

import com.huiztech.common.mvc.HuizView;

public class ControllerBuilder {

    private static AbstractController instance;

    /**
     * <pre>
     *  使用方法:
     *      AbstractController controller = ControllerBuilder.getInstance();
     * </pre>
     * 
     * @return AbstractController 单例,整个系统中只有此类的一个实例
     */
    public static AbstractController getInstance() {
        if (instance == null) {
            instance = new Controller();
            instance.setHuizModel(new HashMap<String, Object>());
            instance.setHuizView(new HuizView());
        }
        return instance;
    }

}
