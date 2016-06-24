package com.neu.wifilocalization.adapter;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.model.Category;

/**
 * 省市适配器
 * 
 * @author alin
 * 
 */
public class CategoryListAdapter extends MyBaseAdapter<Category> {

    public CategoryListAdapter(Context context, List<Category> dataList, String type) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        view = LayoutInflater.from(context).inflate(R.layout.common_category_list_item, parent, false);
        viewHolder = new ViewHolder();
        viewHolder.nameText = (TextView) view.findViewById(R.id.category_name_text);
        viewHolder.nextImage = (ImageView) view.findViewById(R.id.category_next_image);
        viewHolder.nameText.setText(dataList.get(position).getName());
        return view;
    }

    private class ViewHolder {
        TextView nameText;
        ImageView nextImage;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

}
