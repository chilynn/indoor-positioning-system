package com.neu.wifilocalization.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neu.wifilocalization.R;

/**
 * 群成员列表适配器
 * 
 * @author alin
 * 
 */
public class PopMenuContentAdapter extends MyBaseAdapter {

    private Context mContext;
    private List<String> dataList;

    public PopMenuContentAdapter(Context context, List<String> dataList) {
        super(context, dataList);
        mContext = context;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.common_popmenu_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contentText = (TextView) view.findViewById(R.id.popmenu_content_text);
            viewHolder.contentView = (LinearLayout) view.findViewById(R.id.popmenu_row_view);
            viewHolder.contentView.setOnClickListener(new clickListItem(dataList.get(position), position));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.contentText.setText(dataList.get(position).toString());

        return view;
    }

    private class ViewHolder {
        TextView contentText;
        LinearLayout contentView;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class clickListItem implements OnClickListener {
        private int position;
        private String content;

        public clickListItem(String content, int position) {
            this.content = content;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
        }
    }
}