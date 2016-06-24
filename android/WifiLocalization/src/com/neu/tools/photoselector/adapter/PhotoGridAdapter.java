package com.neu.tools.photoselector.adapter;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.neu.tools.photoselector.utils.BitmapStore;
import com.neu.tools.photoselector.utils.FileUtils;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.utils.BitmapUtil;

public class PhotoGridAdapter extends BaseAdapter {
    private int MaxSelect = 6;
    private Context context;
    private LayoutInflater inflater; // 视图容器
    private int selectedPosition = -1;// 选中的位置
    private boolean shape;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public PhotoGridAdapter(Context context, int MaxSelect) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.MaxSelect = MaxSelect;
    }

    public void update() {
        loading();
    }

    public int getCount() {
        return (BitmapStore.bmp.size() + 1);
    }

    public Object getItem(int arg0) {

        return null;
    }

    public long getItemId(int arg0) {

        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        final int coord = position;
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.tools_photo_selector_popwindow_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == BitmapStore.bmp.size()) {
            holder.image
                    .setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_add_image));
            if (position == MaxSelect) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(BitmapStore.bmp.get(position));
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                PhotoGridAdapter.this.notifyDataSetChanged();
                break;
            }
            super.handleMessage(msg);
        }
    };

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (BitmapStore.max == BitmapStore.drr.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        try {
                            String path = BitmapStore.drr.get(BitmapStore.max);
                            System.out.println(path);
                            Bitmap bm = BitmapStore.revitionImageSize(path);
                            BitmapStore.bmp.add(bm);
                            //BitmapStore.bmp.add(BitmapUtil.ImageCrop(bm));//按正方形裁切
                            String newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                            FileUtils.saveBitmap(BitmapUtil.ImageCrop(bm), "" + newStr);
                            BitmapStore.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}