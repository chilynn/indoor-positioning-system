package com.neu.wifilocalization.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lidroid.xutils.BitmapUtils;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.Image;

/**
 * 图片切换适配器
 * 
 * @author alin
 * 
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    protected BitmapUtils bitmapUtils;

    private List<Image> images;

    public ViewPagerAdapter(List<Image> images, Context context) {
        this.images = images;
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        // TODO Auto-generated method stub
        // ((ViewPager) arg0).removeView(images.get(arg1));
    }

    @Override
    public Object instantiateItem(View view, int position) {

        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ScaleType.FIT_XY);

        // TODO Auto-generated method stub
        ((ViewPager) view).addView(imageView);

        bitmapUtils.display(imageView, Const.BASE_IMAGE + "/" + images.get(position).getImage_url());
        System.out.println("幻灯片图片地址："+Const.BASE_IMAGE + "/" + images.get(position).getImage_url());
        
        return imageView;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }
}
