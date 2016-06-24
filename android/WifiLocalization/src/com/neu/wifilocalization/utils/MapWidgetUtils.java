package com.neu.wifilocalization.utils;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.config.GPSConfig;
import com.ls.widgets.map.config.MapGraphicsConfig;
import com.ls.widgets.map.config.OfflineMapConfig;
import com.neu.wifilocalization.R;

public class MapWidgetUtils {

    public static final int MAP_ID = 2014;
    public static final Integer LAYER1_ID = 0;
    public static final Integer LAYER2_ID = 1;
    public static final Integer LAYER3_ID = 2;
    public static final Integer LAYER4_ID = 3;

    public static MapWidget initMap(MapWidget map, Context context, Bundle savedInstanceState) {
        MapWidgetUtils.clearLogo();

        // root name of the map under assets folder.
        // initial zoom level
        map = new MapWidget(savedInstanceState, context, "map", 11);
        map.setMaxZoomLevel(14);
        map.setMinZoomLevel(10);
        map.setId(MAP_ID);
        map.centerMap();

        map.createLayer(LAYER1_ID);// 长按出现的点的图层
        map.createLayer(LAYER2_ID);// 动点的图层
        map.createLayer(LAYER3_ID);// 查询结果图层
        map.createLayer(LAYER4_ID);// 路径图层

        OfflineMapConfig config = map.getConfig();
        config.setZoomBtnsVisible(false); // Sets embedded zoom buttons visible
        config.setPinchZoomEnabled(true); // Sets pinch gesture to zoom
        config.setFlingEnabled(true); // Sets inertial scrolling of the map
        config.setMapCenteringEnabled(true); // set bound

        // Configuration of GPS receiver
        GPSConfig gpsConfig = config.getGpsConfig();
        gpsConfig.setPassiveMode(false);
        gpsConfig.setGPSUpdateInterval(500, 5);

        // Configuration of position marker
        MapGraphicsConfig graphicsConfig = config.getGraphicsConfig();
        graphicsConfig.setAccuracyAreaColor(0x550000FF);
        graphicsConfig.setAccuracyAreaBorderColor(Color.GRAY);

        RelativeLayout layout = (RelativeLayout) ((Activity) context).findViewById(R.id.indoor_map);
        layout.addView(map, 0);
        layout.setBackgroundResource(R.color.gray);

        return map;
    }

    public static void clearLogo() {
        Class<?> c = null;
        try {
            // 反射找到Resources类
            c = Class.forName("com.ls.widgets.map.utils.Resources");
            Object obj = c.newInstance();
            // 找到Logo 属性，是一个数组
            Field field = c.getDeclaredField("LOGO");
            field.setAccessible(true);
            // 将LOGO字段设置为null
            field.set(obj, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
