package com.neu.wifilocalization.social.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.MyBaseAdapter;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.SortModel;

public class SortAdapter extends MyBaseAdapter<SortModel> implements SectionIndexer {

    public SortAdapter(Context context, List<SortModel> dataList) {
        super(context, dataList);
    }

    /**
     * 当dataListView数据发生变化时,调用此方法来更新dataListView
     * 
     * @param dataList
     */
    public void updateListView(List<SortModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = dataList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.social_main_contact_list_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.userIconImage = (ImageView) view.findViewById(R.id.user_icon);
            viewHolder.signature = (TextView) view.findViewById(R.id.signature);
            viewHolder.userLayout = (LinearLayout) view.findViewById(R.id.user_layout);
            viewHolder.lineView = view.findViewById(R.id.line);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        Log.e("section", section + "");
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            Log.e("position", getPositionForSection(section) + "");
            viewHolder.lineView.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());

        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
            viewHolder.lineView.setVisibility(View.GONE);
        }

        // A-Z分类最后一个条目
        int cursor = 65; // 字母A的ascii是65----------字母Z的ascii是90
        while (cursor <= 90) {
            if (getPositionForSection(cursor) - 1 == position) {
                viewHolder.userLayout.setBackgroundResource(R.drawable.shape_last);
                // viewHolder.userLayout.setBackgroundColor(Color.rgb(255, 255,
                // 255));
                break; // 发现最后一条目则跳出
            } else {
                viewHolder.userLayout.setBackgroundColor(Color.rgb(255, 255, 255));
            }
            cursor++;
        }
        if (dataList.get(position).getImage() != null) {
            bitmapUtils.display(viewHolder.userIconImage, Const.BASE_IMAGE + "/" + dataList.get(position).getImage());
        }
        // bitmapUtils.display(viewHolder.userIconImage, IFishConst.BASE_IMAGE +
        // dataList.get(position).getImage());

        viewHolder.signature.setText(this.dataList.get(position).getSignature());
        viewHolder.tvTitle.setText(this.dataList.get(position).getName());

        // @符号的ascii值==64，标题设置为群
        if (section == 64) {

        }

        if (dataList.get(position).isGroup()) {
            viewHolder.tvLetter.setText("群");
            // json数据中，规定群名开头以@开头，此处去掉@符号
            viewHolder.tvTitle.setText(this.dataList.get(position).getName()
                    .substring(1, this.dataList.get(position).getName().length()));
            viewHolder.signature.setText(this.dataList.get(position).getSignature());
        }

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        TextView signature;
        ImageView userIconImage;
        LinearLayout userLayout;
        View lineView;
    }

    /**
     * 根据dataListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return dataList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = dataList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     * 
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}