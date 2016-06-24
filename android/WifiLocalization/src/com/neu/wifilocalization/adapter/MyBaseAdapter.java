package com.neu.wifilocalization.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.mvc.AbstractController;
import com.neu.wifilocalization.mvc.ControllerBuilder;

/**
 * BaseAdapter
 * 
 * @author alin
 * 
 */
public class MyBaseAdapter<DATA_TYPE> extends BaseAdapter {

    protected Context context;
    protected BitmapUtils bitmapUtils;
    protected List<DATA_TYPE> dataList;
    protected DbUtils db;
    protected AbstractController controller;

    public MyBaseAdapter(Context context, List<DATA_TYPE> dataList) {
        this.context = context;
        this.dataList = dataList;
        bitmapUtils = new BitmapUtils(context);
        db = App.getInstance().db;
        controller = ControllerBuilder.getInstance();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
