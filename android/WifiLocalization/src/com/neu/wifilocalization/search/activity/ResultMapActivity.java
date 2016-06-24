package com.neu.wifilocalization.search.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapScrolledEvent;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.events.ObjectTouchEvent;
import com.ls.widgets.map.interfaces.OnLocationChangedListener;
import com.ls.widgets.map.interfaces.OnMapScrollListener;
import com.ls.widgets.map.model.MapObject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseMapActivity;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.MapObjectEntity;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.model.Position;
import com.neu.wifilocalization.model.RouteServerResponse;
import com.neu.wifilocalization.service.LocationService;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.MapWidgetUtils;
import com.neu.wifilocalization.view.MyImageButton;
import com.neu.wifilocalization.view.popup.MyPopup;

/**
 * 查询结果地图
 * 
 * @author alin
 * 
 */
public class ResultMapActivity extends BaseMapActivity {

    private Intent locationIntent;

    @ViewInject(R.id.search_edit)
    private EditText searchEdit;
    @ViewInject(R.id.select_name_text)
    private TextView selectedNameText;
    @ViewInject(R.id.result_detail_layout)
    private LinearLayout detailLayout;

    private Node singleNode;
    private List<Node> nodeList;
    private String selectedNodeId = "";
    private String selectedPositionId = "";
    private Map<Integer, MapObject> mapObjectHashMap = new HashMap<Integer, MapObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.search_result_map);
        ViewUtils.inject(this);
        this.hideFooterBar();
        map = MapWidgetUtils.initMap(map, ResultMapActivity.this, savedInstanceState);
        initPopView();
        initBodyView();
        initMapListeners();
        initMovableObject();
    }

    @OnClick(R.id.request_positioning)
    public void requestPositioning(View v) {
        view.showMessage("定位当前模式");
        if (popView != null) {
            popView.hide();
        }
        map.getLayerById(MapWidgetUtils.LAYER1_ID).clearAll();
        map.invalidate();
        initLocalizationService();
    }

    private void initMovableObject() {
        Drawable drawable = getResources().getDrawable(R.drawable.icon_red_marker);
        // rotateDrawable = (RotateDrawable)
        // getResources().getDrawable(R.drawable.rotate_drawable);
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
        locationIntent = new Intent(ResultMapActivity.this, LocationService.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putInt("scanTimes", 1);
        locationIntent.putExtras(bundle);
        startService(locationIntent);
    }

    public void stopLocationService() {
        if (locationIntent != null) {
            stopService(locationIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (model.get("searchResultNode") != null) {
            singleNode = (Node) model.get("searchResultNode");
            showSingleResult(singleNode);
            model.put("searchResultNode", null);
        }
        if (model.get("searchResultNodeList") != null) {
            nodeList = (List<Node>) model.get("searchResultNodeList");
            showResultList(nodeList);
            model.put("searchResultNodeList", null);
        }
    }

    public void showSingleResult(final Node node) {
        int mapX = node.getPosition().getMap_x();
        int mapY = node.getPosition().getMap_y();
        Drawable drawable = getResources().getDrawable(R.drawable.icon_location_marker_blue);
        addScalableMapObject(mapX, mapY, map.getLayerById(MapWidgetUtils.LAYER3_ID), drawable);
        MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, mapX, mapY, "详情");
        mapObjectEntity.setNode(node);
        mapObjectEntityContainer.addObject(mapObjectEntity);
        mapObejectEntityId += 1;
        selectedNameText.setText(node.getName());
        selectedNodeId = node.getId() + "";
        selectedPositionId = node.getPosition().getId() + "";
        controller.getHuizModel().put("nodeDetail", node);
        map.scrollMapTo(node.getPosition().getMap_x(), node.getPosition().getMap_y());
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

    public void showResultList(final List<Node> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            int mapX = resultList.get(i).getPosition().getMap_x();
            int mapY = resultList.get(i).getPosition().getMap_y();
            Drawable drawable = null;
            if (i == 0) {
                drawable = getResources().getDrawable(R.drawable.icon_location_marker_blue);
                selectedNameText.setText(resultList.get(0).getName());
                selectedNodeId = resultList.get(0).getId() + "";
                selectedPositionId = resultList.get(0).getPosition().getId() + "";
                controller.getHuizModel().put("nodeDetail", resultList.get(0));
            } else {
                drawable = getResources().getDrawable(R.drawable.icon_location_marker_red);
            }

            pinHeight = drawable.getIntrinsicHeight();
            MapObject object = new MapObject(Integer.valueOf(nextObjectId), drawable, mapXDivation + mapX
                    - drawable.getIntrinsicWidth() / 2, mapYDivation + mapY - drawable.getIntrinsicHeight() / 2, true,
                    true);
            map.getLayerById(MapWidgetUtils.LAYER3_ID).addMapObject(object);
            nextObjectId += 1;

            MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, mapX, mapY, "详情");
            mapObjectEntity.setNode(resultList.get(i));
            mapObjectEntityContainer.addObject(mapObjectEntity);
            mapObjectHashMap.put(mapObejectEntityId, object);
            mapObejectEntityId += 1;
        }
        if (resultList.size() > 0) {
            System.out.println("移动到某个点");
            map.scrollMapTo(resultList.get(0).getPosition().getMap_x(), resultList.get(0).getPosition().getMap_y());
        }
    }

    private void initPopView() {
        popView = new MyPopup(this, (RelativeLayout) findViewById(R.id.indoor_map));
        isShowPop = false;
    }

    public void initBodyView() {
        if (model.get("searchContent") != null) {
            searchEdit.setText(model.get("searchContent").toString());
        }
    }

    @OnClick(R.id.result_navigation_layout)
    public void route(View v) {
        String startId = "";
        String endId = selectedPositionId;
        if (model.get("currentPositionId") != null) {
            startId = model.get("currentPositionId").toString();
        }
        if ((!startId.equals("")) && (!endId.equals(""))) {
            view.showMessage("服务器开始检索路径");
            if (startId.equals(endId)) {
                view.showMessage("你已经在目的地了");
            } else {
                controller.getShortestRoute(true, true, loginedUser, startId, endId,
                        new Callback<RouteServerResponse>() {
                            @Override
                            public void execute(RouteServerResponse routeServerResponse) {
                                if (routeServerResponse.getState().equals("1")) {
                                    map.getLayerById(MapWidgetUtils.LAYER3_ID).setVisible(false);
                                    map.invalidate();
                                    List<Position> positionList = routeServerResponse.getData();
                                    if (positionList.size() >= 2) {
                                        for (int i = 0; i < positionList.size(); i++) {
                                            if (i + 1 < positionList.size()) {
                                                Position p1 = positionList.get(i);
                                                Position p2 = positionList.get(i + 1);
                                                drawLine(p1, p2);
                                            }
                                        }
                                        drawStartAndEnd(positionList.get(0), positionList.get(positionList.size() - 1));
                                    }
                                }
                            }
                        });
            }
        } else {
            view.showMessage("请先定位当前");
        }
    }

    public void drawStartAndEnd(Position startPosition, Position endPosition) {
        Drawable drawableStart = getResources().getDrawable(R.drawable.icon_start);
        MapObject startObject = new MapObject(Integer.valueOf(nextObjectId), drawableStart,
                (int) startPosition.getMap_x()-drawableStart.getIntrinsicWidth()/2, (int) startPosition.getMap_y() - drawableStart.getIntrinsicHeight(),
                true, true);
        map.getLayerById(MapWidgetUtils.LAYER4_ID).addMapObject(startObject);
        nextObjectId += 1;
        
//        MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, (int) startPosition.getMap_x(), (int) startPosition.getMap_y() - drawableStart.getIntrinsicHeight(), "编辑");
//        mapObjectEntityContainer.addObject(mapObjectEntity);
//        mapObejectEntityId += 1;
        
        Drawable drawableEnd = getResources().getDrawable(R.drawable.icon_end);
        MapObject endObject = new MapObject(Integer.valueOf(nextObjectId), drawableEnd,
                (int) endPosition.getMap_x()-drawableEnd.getIntrinsicWidth()/2, (int) endPosition.getMap_y() - drawableEnd.getIntrinsicHeight(),
                true, true);
        map.getLayerById(MapWidgetUtils.LAYER4_ID).addMapObject(endObject);
        nextObjectId += 1;
        
//        MapObjectEntity mapObjectEntity2 = new MapObjectEntity(mapObejectEntityId, (int) endPosition.getMap_x(), (int) endPosition.getMap_y() - drawableStart.getIntrinsicHeight(), "编辑");
//        mapObjectEntityContainer.addObject(mapObjectEntity2);
//        mapObejectEntityId += 1;
    }

    @OnClick(R.id.result_detail_layout)
    public void viewDetail(View v) {
        model.put("nodeId", selectedNodeId);
        Jumper.jump(ResultMapActivity.this, NodeDetailActivity.class);
        AnimationUtils.rightToLeft(ResultMapActivity.this);
    }

    @Override
    public void onTouch(MapWidget v, MapTouchedEvent event) {
        super.onTouch(v, event);
        ArrayList<ObjectTouchEvent> touchedObjs = event.getTouchedObjectIds();
        if (touchedObjs.size() > 0) {
            ObjectTouchEvent objectTouchEvent = event.getTouchedObjectIds().get(0);
            long layerId = objectTouchEvent.getLayerId();
            Integer objectId = (Integer) objectTouchEvent.getObjectId();
            final MapObjectEntity objectModel = mapObjectEntityContainer.getObjectById(objectId.intValue());
            if (objectModel != null) {
                Iterator iter = mapObjectHashMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    MapObject val = (MapObject) entry.getValue();
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_location_marker_red);
                    val.setDrawable(drawable);
                }
                if (mapObjectHashMap.get(objectId) != null) {
                    MapObject mapObject = mapObjectHashMap.get(objectId);
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_location_marker_blue);
                    mapObject.setDrawable(drawable);
                    map.invalidate();
                }
                selectedNameText.setText(objectModel.getNode().getName());
                selectedNodeId = objectModel.getNode().getId() + "";
                selectedPositionId = objectModel.getNode().getPosition().getId() + "";
                controller.getHuizModel().put("nodeDetail", objectModel.getNode());
                map.scrollMapTo(objectModel.getX(), objectModel.getY());
            }
        } else {

        }
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
            }
        });
    }

    @OnClick(R.id.search_return_button)
    public void back(View v) {
        finish();
        AnimationUtils.leftToRight(ResultMapActivity.this);
    }
}
