package com.neu.wifilocalization.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.google.gson.Gson;
import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.model.MapObject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.model.AccessPoint;
import com.neu.wifilocalization.model.IdServerResponse;
import com.neu.wifilocalization.model.LocationServerResponse;
import com.neu.wifilocalization.utils.MapWidgetUtils;

/**
 * 定位服务
 * 
 * @author alin
 * 
 */
public class LocationService extends BaseService implements SensorEventListener {

    private WifiManager wifiManager;
    private SensorManager mSensorManager; // 传感器
    private Thread thread;
    private Gson gson;

    private static final int threadSleepTime = 500;
    private boolean isThreadLoop = true;
    private int isInitLocation = 0;

    private Map<String, List<AccessPoint>> scanResultsMap = new HashMap<String, List<AccessPoint>>();
    private int type = -1; // 0=数据采集 1=定位
    private int scanTimes = 0;
    private int scanCounter = 0;

    private RotateDrawable rotateDrawable;
    private float rotateDegree;
    private int aheadDegree;

    private int x;
    private int y;
    private int objectModelId;

    private MapWidget map;
    private MapObject moveObject;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("定位服务-onStart()");
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                type = bundle.getInt("type");
                scanTimes = bundle.getInt("scanTimes");
                scanCounter = 0;
                x = bundle.getInt("x");
                y = bundle.getInt("y");
                objectModelId = bundle.getInt("objectModelId");
                System.out.println("定位服务type" + type);
                System.out.println("定位服务scanTimes" + scanTimes);
            }
        }
        if (controller.getHuizModel().get("map") != null) {
            map = (MapWidget) controller.getHuizModel().get("map");
        }
        if (controller.getHuizModel().get("moveObject") != null) {
            moveObject = (MapObject) controller.getHuizModel().get("moveObject");
        }
        if (controller.getHuizModel().get("rotateDrawable") != null) {
            rotateDrawable = (RotateDrawable) controller.getHuizModel().get("rotateDrawable");
        }
        view.showBusy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("LocationService - onDestroy");
        isThreadLoop = false;
        unregisterReceiver(rssiReceiver);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(rssiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); //
        // 获取管理服务
        // // 注册监听器
        // mSensorManager.registerListener(this,
        // mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        // SensorManager.SENSOR_DELAY_GAME);
        gson = new Gson();
        thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (isThreadLoop) {
                wifiManager.startScan();
                try {
                    thread.sleep(threadSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void requestLocalization(String jsonStr) {
        controller.requestLocalization(false, false, loginedUser, jsonStr, new Callback<LocationServerResponse>() {
            @Override
            public void execute(LocationServerResponse locationServerResponse) {
                if (locationServerResponse.getState().equals("1")) {
                    model.put("currentPositionId", locationServerResponse.getData().getId() + "");
                    float mapX = locationServerResponse.getData().getMap_x();
                    float mapY = locationServerResponse.getData().getMap_y();
                    aheadDegree = locationServerResponse.getData().getAhead_degree();
                    System.out.println("x坐标：" + mapX + "y坐标" + mapY);
                    Drawable drawable;
                    if (scanCounter % 2 == 0) {
                        drawable = getResources().getDrawable(R.drawable.icon_marker_blue2);
                        moveObject.setDrawable(drawable);
                    } else {
                        drawable = getResources().getDrawable(R.drawable.icon_marker_blue);
                        moveObject.setDrawable(drawable);
                    }
                    moveObject.moveTo((int) mapX - drawable.getIntrinsicWidth() / 2,
                            (int) mapY - drawable.getIntrinsicHeight() / 2);
                    map.invalidate();
                    isInitLocation = isInitLocation + 1;
                    if (isInitLocation == 2) {
                        map.getLayerById(MapWidgetUtils.LAYER2_ID).setVisible(true);
                        map.invalidate();
                        view.hideBusy();
                    } else {
                    }
                    if (scanCounter == 2) {
                        System.out.println("第" + scanCounter + "次");
                        map.scrollMapTo((int) mapX, (int) mapY);
                    }
                } else {
                    view.hideBusy();
                    isThreadLoop = false;
                    LocationService.this.stopSelf();
                    System.out.println("数据库没有position记录");
                    view.showMessage("数据库无位置记录");
                }
            }
        });
    }

    public void uploadWifiData(String jsonStr) {
        controller.uploadWifiData(false, false, jsonStr, x + "", y + "", "", new Callback<IdServerResponse>() {
            @Override
            public void execute(IdServerResponse serverResponse) {
                view.hideBusy();
                controller.getHuizModel().put(objectModelId + "", serverResponse.getId());
                controller.getHuizView().showMessage(serverResponse.getMsg());
            }
        });
    }

    public String getWifiScanResults() {
        scanCounter++;
        if (type == 0) {
            System.out.println(scanCounter + "===========" + scanTimes);
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        List<AccessPoint> apList = new ArrayList<AccessPoint>();
        String text = "";
        for (ScanResult scanResult : scanResults) {
            AccessPoint ap = new AccessPoint();
            ap.setBssid(scanResult.BSSID);
            ap.setSsid(scanResult.SSID);
            ap.setRssi(scanResult.level);
            apList.add(ap);
            List<AccessPoint> eachApList = null;
            if (scanResultsMap.get(scanResult.BSSID) == null) {
                eachApList = new ArrayList<AccessPoint>();
            } else {
                eachApList = scanResultsMap.get(scanResult.BSSID);
            }
            eachApList.add(ap);
            scanResultsMap.put(scanResult.BSSID, eachApList);
        }
        String jsonStr = gson.toJson(apList);
        if (type == 0) {
            System.out.println("信号采集第" + scanCounter + "次");
        } else if (type == 1) {
            requestLocalization(jsonStr);
        }
        return text;
    }

    public int smoothData(List<AccessPoint> eachApList) {
        int soomthAverageRssi = -1;
        boolean isNumQualified = true;
        final double standardDeviationThreshold = 3.0;
        double standardDeviation = 0;
        int averageRssi = 0;
        int totalRssi = 0;
        for (int i = 0; i < eachApList.size(); i++) {
            int rssi = eachApList.get(i).getRssi();
            System.out.println(rssi);
            totalRssi = totalRssi + rssi;
        }
        if (eachApList.size() > 2) {
            averageRssi = totalRssi / (eachApList.size());
        } else {
            isNumQualified = false;
        }
        if (isNumQualified) {
            int leftCounter = 0;
            int rightCounter = 0;
            int totalRssiLeft = 0;
            int totalRssiRight = 0;
            int averageRssiLeft = 0;
            int averageRssiRight = 0;
            double totalPower = 0;
            for (int i = 0; i < eachApList.size(); i++) {
                int rssi = eachApList.get(i).getRssi();
                if (rssi > averageRssi) {
                    totalRssiLeft = totalRssiLeft + rssi;
                    leftCounter = leftCounter + 1;
                } else {
                    totalRssiRight = totalRssiRight + rssi;
                    rightCounter = rightCounter + 1;
                }
                totalPower = totalPower + Math.pow((rssi - averageRssi), 2);
            }
            standardDeviation = Math.sqrt((totalPower / eachApList.size()));
            System.out.println(eachApList.get(0).getSsid() + "的均值" + averageRssi);
            System.out.println(eachApList.get(0).getSsid() + "的标准差" + standardDeviation);
            if (leftCounter > 0) {
                averageRssiLeft = totalRssiLeft / leftCounter;
            }
            if (rightCounter > 0) {
                averageRssiRight = totalRssiRight / rightCounter;
            }
            double alpha = 0;
            // 信号波动大，信号会衰减
            if (standardDeviation > standardDeviationThreshold) {
                alpha = 0.5 * (1 - ((standardDeviation - standardDeviationThreshold) / standardDeviation));
                soomthAverageRssi = (int) ((1 - alpha) * averageRssiLeft + alpha * averageRssiRight);
                if (soomthAverageRssi < averageRssi) {
                    System.out.println("出现均值平滑后比原来还要大");
                    soomthAverageRssi = averageRssi;
                }
            } else {
                soomthAverageRssi = averageRssi;
            }
            System.out.println(eachApList.get(0).getSsid() + "平滑后的均值" + soomthAverageRssi);
            return soomthAverageRssi;
        } else {
            return soomthAverageRssi;
        }
    }

    public BroadcastReceiver rssiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (type == 0) {
                if (scanCounter == scanTimes) {
                    Iterator<String> iterator = scanResultsMap.keySet().iterator();
                    List<AccessPoint> apList = new ArrayList<AccessPoint>();
                    while (iterator.hasNext()) {
                        boolean isQualified = true;
                        String key = iterator.next();
                        String ssid = "";
                        List<AccessPoint> eachApList = scanResultsMap.get(key);
                        int smoothAverageRssi = smoothData(eachApList);
                        if (smoothAverageRssi != -1) {
                            AccessPoint ap = new AccessPoint();
                            ap.setBssid(key);
                            ap.setSsid(ssid);
                            ap.setRssi(smoothAverageRssi);
                            apList.add(ap);
                        }
                    }
                    String jsonStr = gson.toJson(apList);
                    uploadWifiData(jsonStr);
                    System.out.println("定位服务关闭");
                    scanCounter = 0;
                    isThreadLoop = false;
                    LocationService.this.stopSelf();
                }
            } else if (type == 1) {
                System.out.println("定位请求第" + scanCounter + "次");
            }
            getWifiScanResults();
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
        case Sensor.TYPE_ORIENTATION:
            rotateDegree = event.values[0];
            int degree = (int) rotateDegree;
            if (degree > aheadDegree) {
                degree = degree - aheadDegree;
            } else {
                degree = 360 - (aheadDegree - degree);
            }
            degree = degree * (10000 / 360);
            // if (degree > 315 || degree < 45) {
            // rotateDrawable.setLevel(0);
            // } else if (45 < degree && degree < 135) {
            // rotateDrawable.setLevel((10000 / 4) * 1);
            // } else if (135 < degree && degree < 225) {
            // rotateDrawable.setLevel((10000 / 4) * 2);
            // }else{
            // rotateDrawable.setLevel((10000 / 4) * 3);
            // }
            rotateDrawable.setLevel(degree);
            Message message = new Message();
            message.what = 1;
            myHandler.sendMessage(message);
            break;
        }
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

}
