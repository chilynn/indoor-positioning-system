package com.neu.wifilocalization.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapScrolledEvent;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.interfaces.OnLocationChangedListener;
import com.ls.widgets.map.interfaces.OnMapLongClickListener;
import com.ls.widgets.map.interfaces.OnMapScrollListener;
import com.ls.widgets.map.model.MapObject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.GridMenuAdapter;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.MapObjectEntity;
import com.neu.wifilocalization.search.activity.SearchActivity;
import com.neu.wifilocalization.service.LocationService;
import com.neu.wifilocalization.social.activity.SocialTabActivity;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.MapWidgetUtils;
import com.neu.wifilocalization.view.MyGridView;
import com.neu.wifilocalization.view.popup.MyPopup;

public class IndoorMapActivity extends BaseMapActivity{

    private Intent locationIntent;

    @ViewInject(R.id.search_edit)
    private EditText searchEdit;
    @ViewInject(R.id.voice_recorder)
    private ImageView voiceRecorderImage;
    @ViewInject(R.id.collect_info)
    private ImageView collectInfo;
    @ViewInject(R.id.request_positioning_image)
    private ImageView requestPositioningImage;
    @ViewInject(R.id.footer_grid)
    private MyGridView footerGridView;

    private GridMenuAdapter footerGridAdapter;

    private int type = 0; // 0=数据采集 1=定位

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.saveState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("IndoorMapActivity - onCreate()");
        setContentView(R.layout.map_index);
        ViewUtils.inject(this);
        this.hideFooterBar();
        map = MapWidgetUtils.initMap(map, IndoorMapActivity.this, savedInstanceState);
        initPopView();
        initMapListeners();
        initFooterGrid();
        initMovableObject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("IndoorMapActivity - onResume()");
        // // 注册监听器
        // mSensorManager.registerListener(this,
        // mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        // SensorManager.SENSOR_DELAY_GAME);
        if (!redirect()) {
            //initLocalizationService();
        }
    }

    // 取消注册
    @Override
    protected void onPause() {
        // mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        // mSensorManager.unregisterListener(this);
        stopLocationService();
    }

    public void stopLocationService() {
        if (locationIntent != null) {
            stopService(locationIntent);
        }
    }

    public boolean redirect() {
        if (model.get("indexToSocial") != null) {
            if (model.get("indexToSocial").toString().equals("1")) {
                Jumper.jump(IndoorMapActivity.this, SocialTabActivity.class);
                AnimationUtils.rightToLeft(IndoorMapActivity.this);
                model.put("indexToSocial", null);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void initMovableObject() {
        Drawable drawable = getResources().getDrawable(R.drawable.icon_marker_blue2);
        rotateDrawable = (RotateDrawable) getResources().getDrawable(R.drawable.rotate_drawable);
        model.put("rotateDrawable", rotateDrawable);
        moveObject = new MapObject(Integer.valueOf(nextObjectId), drawable, 0, 0, true, true);
        map.getLayerById(MapWidgetUtils.LAYER2_ID).addMapObject(moveObject);
        nextObjectId += 1;
        MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, 0, 0, "操作");
        mapObjectEntityContainer.addObject(mapObjectEntity);
        mapObejectEntityId += 1;
        map.getLayerById(MapWidgetUtils.LAYER2_ID).setVisible(false);
    }

    public void initLocalizationService() {
        System.out.println("开启定位服务");
        stopLocationService();
        controller.getHuizModel().put("map", map);
        controller.getHuizModel().put("moveObject", moveObject);
        locationIntent = new Intent(IndoorMapActivity.this, LocationService.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putInt("scanTimes", 1);
        locationIntent.putExtras(bundle);
        startService(locationIntent);
    }

    private void initFooterGrid() {
        ArrayList<String> gridItemNames = new ArrayList<String>();
        gridItemNames.add("附近");
        gridItemNames.add("消息");
        gridItemNames.add("社交");
        gridItemNames.add("我");
        ArrayList<Integer> hintList = new ArrayList<Integer>();
        hintList.add(0);
        hintList.add(0);
        hintList.add(0);
        hintList.add(0);
        footerGridAdapter = new GridMenuAdapter(IndoorMapActivity.this, gridItemNames, hintList);
        footerGridView.setAdapter(footerGridAdapter);

        App.getInstance().footerGridAdapter = footerGridAdapter;
        App.getInstance().footerHintList = hintList;
    }

    private void initPopView() {
        popView = new MyPopup(this, (RelativeLayout) findViewById(R.id.indoor_map));
        initPopMenuListener();
    }

    private void initMapListeners() {
        map.setOnMapTouchListener(this);
        map.addMapEventsListener(this);
        map.setOnMapScrolledListener(new OnMapScrollListener() {
            public void onScrolledEvent(MapWidget v, MapScrolledEvent event) {
                handleOnMapScroll(v, event);
            }
        });
        map.setOnLocationChangedListener(new OnLocationChangedListener() {
            @Override
            public void onLocationChanged(MapWidget v, Location location) {
                // You can handle location change here.
                // For example you can scroll to new location by using
                // v.scrollMapTo(location)
            }
        });

        if (type == 0) {
            // 长按地图事件
            map.setOnMapLongClickListener(new OnMapLongClickListener() {
                @Override
                public boolean onLongClick(MapWidget map, MapTouchedEvent event) {
                    int mapX = event.getMapX();
                    int mapY = event.getMapY();
                    System.out.println("x坐标" + mapX + "\ny坐标" + mapY);
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_location_marker_red);
                    addScalableMapObject(mapX, mapY, map.getLayerById(MapWidgetUtils.LAYER1_ID), drawable);
                    MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, mapX, mapY, "编辑");
                    mapObjectEntityContainer.addObject(mapObjectEntity);
                    mapObejectEntityId += 1;
                    return false;
                }
            });
        }
    }

    @OnClick(R.id.search_edit)
    public void search(View v) {
        model.put("isShowDialog", "0");
        Jumper.jump(IndoorMapActivity.this, SearchActivity.class);
        AnimationUtils.rightToLeft(IndoorMapActivity.this);
    }

    @OnClick(R.id.collect_info)
    public void collectInfo(View v) {
        view.showMessage("采集信号模式");
        map.getLayerById(MapWidgetUtils.LAYER2_ID).setVisible(false);
        map.invalidate();
        if (locationIntent != null) {
            stopService(locationIntent);
        }
    }

    @OnClick(R.id.request_positioning_image)
    public void requestPositioning(View v) {
        view.showMessage("定位当前模式");
        if (popView != null) {
            popView.hide();
        }
        map.getLayerById(MapWidgetUtils.LAYER1_ID).clearAll();
        map.invalidate();
        initLocalizationService();
    }

    @OnClick(R.id.voice_recorder)
    public void voiceSearch(View v) {
        model.put("isShowDialog", "1");
        Jumper.jump(IndoorMapActivity.this, SearchActivity.class);
        AnimationUtils.rightToLeft(IndoorMapActivity.this);
    }

    private void initPopMenuListener() {
        popView.setLeftButtonOnClickListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (popView != null) {
                        popView.hide();
                        removeObject(selectObjectId, map.getLayerById(MapWidgetUtils.LAYER1_ID));
                    }
                }
                return false;
            }
        });

        popView.setMiddleTextOnClickListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (popView != null) {
                        // popView.hide();
                        MapObjectEntity objectModel = mapObjectEntityContainer.getObjectById(selectObjectId);
                        if (controller.getHuizModel().get(objectModel.getId() + "") != null) {
                            controller.getHuizModel().put("positionId",
                                    controller.getHuizModel().get(objectModel.getId() + ""));
                            Jumper.jump(IndoorMapActivity.this, EditNodeActivity.class);
                            AnimationUtils.rightToLeft(IndoorMapActivity.this);
                        } else {
                            view.showMessage("请先采集信息");
                        }
                    }
                }
                return false;
            }
        });

        popView.setRightButtonOnClickListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (popView != null) {
                        MapObjectEntity objectModel = mapObjectEntityContainer.getObjectById(selectObjectId);
                        controller.getHuizView().showMessage("采集信号中...");
                        Intent locationIntent = new Intent(IndoorMapActivity.this, LocationService.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 0);
                        bundle.putInt("scanTimes", 12);
                        bundle.putInt("x", objectModel.getX());
                        bundle.putInt("y", objectModel.getY());
                        bundle.putInt("objectModelId", objectModel.getId());
                        locationIntent.putExtras(bundle);
                        startService(locationIntent);
                        popView.hide();
                    }
                }
                return false;
            }
        });
    }

    // 实例化一个handler
    Handler myHandler = new Handler() {
        // 接收到消息后处理
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                map.invalidate();
                break;
            }
            super.handleMessage(msg);
        }
    };

//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        int sensorType = event.sensor.getType();
//        switch (sensorType) {
//        case Sensor.TYPE_ORIENTATION:
//            rotateDegree = event.values[0];
//            int degree = (int) rotateDegree;
//            if (degree > frontDegree) {
//                degree = degree - frontDegree;
//            } else {
//                degree = 360 - (frontDegree - degree);
//            }
//            degree = degree * (10000 / 360);
//            // if (degree > 315 || degree < 45) {
//            // rotateDrawable.setLevel(0);
//            // } else if (45 < degree && degree < 135) {
//            // rotateDrawable.setLevel((10000 / 4) * 1);
//            // } else if (135 < degree && degree < 225) {
//            // rotateDrawable.setLevel((10000 / 4) * 2);
//            // }else{
//            // rotateDrawable.setLevel((10000 / 4) * 3);
//            // }
//            rotateDrawable.setLevel(degree);
//            Message message = new Message();
//            message.what = 1;
//            myHandler.sendMessage(message);
//            break;
//        }
//    }

    // =============================================================================================
    // Back Event handle
    // =============================================================================================
    class Exit {
        private boolean isExit = false;
        private Runnable task = new Runnable() {
            @Override
            public void run() {
                isExit = false;
            }
        };

        public void doExitInOneSecond() {
            stopLocationService();
            isExit = true;
            HandlerThread thread = new HandlerThread("doTask");
            thread.start();
            new Handler(thread.getLooper()).postDelayed(task, 1000);
        }

        public boolean isExit() {
            return isExit;
        }

        public void setExit(boolean isExit) {
            this.isExit = isExit;
        }
    }

    Exit exit = new Exit();

    /**
     * press again to exit the program。
     */
    private void pressAgainExit() {
        if (exit.isExit()) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ExitAppToast), 1000).show();
            exit.doExitInOneSecond();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            pressAgainExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
