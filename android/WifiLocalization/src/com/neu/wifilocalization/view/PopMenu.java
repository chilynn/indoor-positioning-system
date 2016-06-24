package com.neu.wifilocalization.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.PopMenuContentAdapter;

public class PopMenu {
    private ArrayList<String> itemList;
    private Context context;
    private PopupWindow popupWindow;
    private ListView contentList;
    private PopMenuContentAdapter adapter;

    public PopMenu(Context context,ArrayList<String> itemList) {

        this.context = context;
        this.itemList = itemList;
        
        View view = LayoutInflater.from(context).inflate(R.layout.common_popmenu_list, null);
        contentList = (ListView) view.findViewById(R.id.common_popmenu_list);
        adapter = new PopMenuContentAdapter(context,itemList);
        contentList.setAdapter(adapter);
        popupWindow = new PopupWindow(view, context.getResources().getDimensionPixelSize(R.dimen.popmenu_width),
                LayoutParams.WRAP_CONTENT);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        setOnClickListener();
    }

    public void setOnClickListener() {
//        editInfoButton.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Intent intent = new Intent(context, GroupInfoActivity.class);
//                // context.startActivity(intent);
//                // AnimationUtil.animationLeftToRight(context);
//            }
//        });
//        deleteButton.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        ignoreButton.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    // 下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent) {
        popupWindow.showAsDropDown(parent, 10,
        // 保证尺寸是根据屏幕像素密度来的
                context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 刷新状态
        popupWindow.update();
    }

    // 隐藏菜单
    public void dismiss() {
        popupWindow.dismiss();
    }

}
