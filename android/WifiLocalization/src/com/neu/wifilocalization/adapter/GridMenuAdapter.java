package com.neu.wifilocalization.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.CategoryActivity;
import com.neu.wifilocalization.activity.PushMessageActivity;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.mycenter.activity.MyCenterActivity;
import com.neu.wifilocalization.social.activity.SocialTabActivity;
import com.neu.wifilocalization.utils.AnimationUtils;

public class GridMenuAdapter extends MyBaseAdapter<String> {

    private List<Integer> hintList;

    public GridMenuAdapter(Context context, List<String> dataList, List<Integer> hintList) {
        super(context, dataList);
        this.hintList = hintList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = dataList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.map_footer_grid_item, null);
            holder.gridItemView = (RelativeLayout) convertView.findViewById(R.id.footer_grid_item);
            holder.gridImage = (ImageView) convertView.findViewById(R.id.grid_icon);
            holder.gridText = (TextView) convertView.findViewById(R.id.grid_text);
            holder.messageHintImage = (ImageView) convertView.findViewById(R.id.message_hint);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gridText.setText(name);
        if (position == 0) {
            holder.gridImage.setBackgroundResource(R.drawable.icon_near);
            holder.gridItemView.setOnClickListener(new clickNear());
        } else if (position == 1) {
            holder.gridImage.setBackgroundResource(R.drawable.icon_message);
            holder.gridItemView.setOnClickListener(new clickMessage());
        } else if (position == 2) {
            holder.gridImage.setBackgroundResource(R.drawable.icon_chat2);
            holder.gridItemView.setOnClickListener(new clickSocial());
        } else if (position == 3) {
            holder.gridImage.setBackgroundResource(R.drawable.icon_myself);
            holder.gridItemView.setOnClickListener(new clickMyCenter());
        }
        if (hintList.get(position) == 1) {
            holder.messageHintImage.setVisibility(View.VISIBLE);
        } else {
            holder.messageHintImage.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        RelativeLayout gridItemView;
        ImageView gridImage;
        TextView gridText;
        ImageView messageHintImage;
    }

    class clickNear implements OnClickListener {
        @Override
        public void onClick(View v) {
            controller.getHuizModel().put("nearCategory", "1");
            Jumper.jump((Activity) context, CategoryActivity.class);
            AnimationUtils.fadeInOut(context);
        }
    }

    class clickMessage implements OnClickListener {
        @Override
        public void onClick(View v) {
            Jumper.jump((Activity) context, PushMessageActivity.class);
            AnimationUtils.fadeInOut(context);
        }
    }

    class clickSocial implements OnClickListener {
        @Override
        public void onClick(View v) {
            Jumper.jump((Activity) context, SocialTabActivity.class);
            AnimationUtils.fadeInOut(context);
        }
    }

    class clickMyCenter implements OnClickListener {
        @Override
        public void onClick(View v) {
            Jumper.jump((Activity) context, MyCenterActivity.class);
            AnimationUtils.fadeInOut(context);
        }
    }

}
