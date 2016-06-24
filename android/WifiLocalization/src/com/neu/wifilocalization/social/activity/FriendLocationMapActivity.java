package com.neu.wifilocalization.social.activity;

import java.util.Set;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.RelativeLayout;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.lidroid.xutils.ViewUtils;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapScrolledEvent;
import com.ls.widgets.map.interfaces.OnLocationChangedListener;
import com.ls.widgets.map.interfaces.OnMapScrollListener;
import com.ls.widgets.map.model.MapObject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseMapActivity;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.MapObjectEntity;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.service.LocationService;
import com.neu.wifilocalization.utils.MapWidgetUtils;
import com.neu.wifilocalization.view.popup.MyPopup;

/**
 * 好友位置
 * 
 * @author alin
 * 
 */
public class FriendLocationMapActivity extends BaseMapActivity {

    private User userInfo;

    private Intent locationIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.social_friend_location_map);
        ViewUtils.inject(this);
        this.hideFooterBar();
        getUserInfo();
        initTitle();
        map = MapWidgetUtils.initMap(map, FriendLocationMapActivity.this, savedInstanceState);
        initPopView();
        initJpush();
        initBodyView();
        initMapListeners();
        initMovableObject();
        initOtherMovableObject();
        initLocalizationService();
    }

    public void getUserInfo() {
        if (model.get("locationTarget") != null) {
            userInfo = (User) model.get("locationTarget");
        }
    }

    public void initTitle() {
        this.titleText.setText("Ta的位置");
    }

    private void initPopView() {
        popView = new MyPopup(this, (RelativeLayout) findViewById(R.id.indoor_map));
        isShowPop = false;
    }

    public void initBodyView() {

    }

    public void initJpush() {
        JPushInterface.setAlias(FriendLocationMapActivity.this, Const.JPUSH_ALIAS + userInfo.getId(),
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
                        System.out.println("JPush 设置alias返回的：" + arg0);
                    }
                });
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

    private void initMovableObject() {
        Drawable drawable = getResources().getDrawable(R.drawable.icon_red_marker);
        moveObject = new MapObject(Integer.valueOf(nextObjectId), drawable, 0, 0, true, true);
        map.getLayerById(MapWidgetUtils.LAYER2_ID).addMapObject(moveObject);
        nextObjectId += 1;
        MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, 0, 0, "操作");
        mapObjectEntityContainer.addObject(mapObjectEntity);
        mapObejectEntityId += 1;
        map.getLayerById(MapWidgetUtils.LAYER2_ID).setVisible(false);
    }

    private void initOtherMovableObject() {
        Drawable drawable = getResources().getDrawable(R.drawable.icon_user);
        otherMoveObject = new MapObject(Integer.valueOf(nextObjectId), drawable, 0, 0, true, true);
        map.getLayerById(MapWidgetUtils.LAYER2_ID).addMapObject(otherMoveObject);
        nextObjectId += 1;
        MapObjectEntity mapObjectEntity = new MapObjectEntity(mapObejectEntityId, 0, 0, "操作");
        mapObjectEntityContainer.addObject(mapObjectEntity);
        mapObejectEntityId += 1;
        System.out.println("用户的x坐标：" + userInfo.getPosition().getMap_x());
        otherMoveObject.moveTo((int) userInfo.getPosition().getMap_x(), (int) userInfo.getPosition().getMap_y());
        map.invalidate();
    }

    public void initLocalizationService() {
        controller.getHuizModel().put("map", map);
        controller.getHuizModel().put("moveObject", moveObject);
        controller.getHuizModel().put("otherMoveObject", otherMoveObject);
        locationIntent = new Intent(FriendLocationMapActivity.this, LocationService.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putInt("scanTimes", 1);
        locationIntent.putExtras(bundle);
        startService(locationIntent);
    }

    public void clearJpush() {
        System.out.println("取消jpush");
        JPushInterface.setAlias(FriendLocationMapActivity.this, "", new TagAliasCallback() {
            @Override
            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                System.out.println("JPush 设置alias返回的：" + arg0);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        clearJpush();
        if (locationIntent != null) {
            stopService(locationIntent);
        }
    }

}
