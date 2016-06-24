package com.neu.wifilocalization.activity;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapScrolledEvent;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.events.ObjectTouchEvent;
import com.ls.widgets.map.interfaces.Layer;
import com.ls.widgets.map.interfaces.MapEventsListener;
import com.ls.widgets.map.interfaces.OnMapTouchListener;
import com.ls.widgets.map.model.MapObject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.model.MapObjectEntity;
import com.neu.wifilocalization.model.MapObjectEntityContainer;
import com.neu.wifilocalization.model.Position;
import com.neu.wifilocalization.utils.MapWidgetUtils;
import com.neu.wifilocalization.view.popup.MyPopup;

public class BaseMapActivity extends BaseActivity implements MapEventsListener, OnMapTouchListener {

    protected SensorManager mSensorManager; // 传感器
    protected float rotateDegree;
    protected RotateDrawable rotateDrawable;
    protected int frontDegree = 250;

    protected MapWidget map;
    protected MyPopup popView;
    protected MapObject moveObject;
    protected MapObject otherMoveObject;

    protected MapObjectEntityContainer mapObjectEntityContainer = new MapObjectEntityContainer();

    protected int mapObejectEntityId = 0;
    protected int nextObjectId = 0;

    protected boolean isShowPop = true;
    protected int selectObjectId;

    protected int pinHeight;

    protected static final int mapXDivation = 0;
    protected static final int mapYDivation = -24;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.saveState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSensor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("BaseMapActivity - onResume()");
    }

    // 取消注册
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void initSensor() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // 获取管理服务
    }

    // 添加map对象
    protected void addScalableMapObject(int x, int y, Layer layer, Drawable drawable) {
        pinHeight = drawable.getIntrinsicHeight();
        MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, mapXDivation + x
                - drawable.getIntrinsicWidth() / 2, mapYDivation + y - drawable.getIntrinsicHeight() / 2, true, true);
        layer.addMapObject(object1);
        nextObjectId += 1;
    }

    protected void drawLine(Position p1, Position p2) {
        int startX = 200;
        int startY = 200;
        int endX = 300;
        int endY = 300;

        if (p1.getMap_x() < p2.getMap_x()) {
            startX = p1.getMap_x();
            startY = p1.getMap_y();
            endX = p2.getMap_x();
            endY = p2.getMap_y();
        } else {
            startX = p2.getMap_x();
            startY = p2.getMap_y();
            endX = p1.getMap_x();
            endY = p1.getMap_y();
        }

        double drawX = startX;
        double drawY = startY;

        int detaX = Math.abs(startX - endX);
        int detaY = Math.abs(startY - endY);
        double hypotenuse = Math.sqrt(Math.pow(detaX, 2) + Math.pow(detaY, 2));

        double sin = detaY / hypotenuse;
        double cos = detaX / hypotenuse;

        final int stroke = 20;// 2-solid effect; 20-dash effect

        while (drawX + stroke * cos < (endX)) {
            Drawable drawable = null;
            if ((drawX == startX)) {
                drawable = getResources().getDrawable(R.drawable.icon_red_marker);
            } else {
                drawable = getResources().getDrawable(R.drawable.icon_red_marker);
            }
            drawX = drawX + stroke * cos;
            if (endY > startY) {
                drawY = drawY + stroke * sin;
            } else {
                drawY = drawY - stroke * sin;
            }
            MapObject moveObject = new MapObject(Integer.valueOf(nextObjectId), drawable, (int) drawX-drawable.getIntrinsicWidth()/2, (int) drawY-drawable.getIntrinsicHeight()/2 ,
                    true, true);
            map.getLayerById(MapWidgetUtils.LAYER4_ID).addMapObject(moveObject);
            nextObjectId += 1;
        }
    }

    protected void removeObject(int objectId, Layer layer) {
        layer.removeMapObject(objectId);
    }

    // 处理地图滚动
    protected void handleOnMapScroll(MapWidget v, MapScrolledEvent event) {
        int dx = event.getDX();
        int dy = event.getDY();
        if (popView.isVisible()) {
            popView.moveBy(dx, dy);
        }
    }

    protected void showLocationsPopup(final int objectId, final int x, final int y, String text) {
        RelativeLayout mapLayout = (RelativeLayout) findViewById(R.id.indoor_map);
        if (popView != null) {
            popView.hide();
        }
        // ((MyPopup) popView).setIcon((BitmapDrawable)
        // getResources()
        // .getDrawable(R.drawable.map_popup_arrow));
        ((MyPopup) popView).setText(text);
        ((MyPopup) popView).show(mapLayout, x, y);
    }

    // * On map touch listener implemetnation *//
    @Override
    public void onTouch(MapWidget v, MapTouchedEvent event) {
        // Get touched object events from the MapTouchEvent
        ArrayList<ObjectTouchEvent> touchedObjs = event.getTouchedObjectIds();
        System.out.println("***onTouch***");
        if (touchedObjs.size() > 0) {

            int xInMapCoords = event.getMapX();
            int yInMapCoords = event.getMapY();
            int xInScreenCoords = event.getScreenX();
            int yInScreenCoords = event.getScreenY();

            ObjectTouchEvent objectTouchEvent = event.getTouchedObjectIds().get(0);
            long layerId = objectTouchEvent.getLayerId();
            Integer objectId = (Integer) objectTouchEvent.getObjectId();
            selectObjectId = objectId;
            String message = "You touched the object with id: " + objectId + " on layer: " + layerId + " mapX: "
                    + xInMapCoords + " mapY: " + yInMapCoords + " screenX: " + xInScreenCoords + " screenY: "
                    + yInScreenCoords;

            System.out.println(message);

            MapObjectEntity objectModel = mapObjectEntityContainer.getObjectById(objectId.intValue());
            if (objectModel != null) {
                // This is a case when we want to show popup info exactly above
                // the pin image
                float density = getResources().getDisplayMetrics().density;
                int imgHeight = (int) (pinHeight / density / 2);
                // Calculating position of popup on the screen
                int x = xToScreenCoords(objectModel.getX());
                int y = yToScreenCoords(objectModel.getY() - pinHeight);
                if (isShowPop) {
                    // Show it
                    showLocationsPopup(objectModel.getId(), x, y, objectModel.getCaption());
                }
            } else {
                // This is a case when we want to show popup where the user has
                // touched.
                //showLocationsPopup(objectModel.getId(), xInScreenCoords, yInScreenCoords, "Shows where user touched");
            }
            // Hint: If user touched more than one object you can show the
            // dialog in which ask the user to select concrete object
        } else {
            System.out.println("size = 0");
            if (popView != null) {
                popView.hide();
            }
        }
    }

    /***
     * Transforms coordinate in map coordinate system to screen coordinate
     * system
     * 
     * @param mapCoord
     *            - X in map coordinate in pixels.
     * @return X coordinate in screen coordinates. You can use this value to
     *         display any object on the screen.
     */
    private int xToScreenCoords(int mapCoord) {
        return (int) (mapCoord * map.getScale() - map.getScrollX());
    }

    private int yToScreenCoords(int mapCoord) {
        return (int) (mapCoord * map.getScale() - map.getScrollY());
    }

    @Override
    public void onPostZoomIn() {
    }

    @Override
    public void onPostZoomOut() {
    }

    @Override
    public void onPreZoomIn() {
        if (popView != null) {
            popView.hide();
        }
    }

    @Override
    public void onPreZoomOut() {
        if (popView != null) {
            popView.hide();
        }
    }

}
