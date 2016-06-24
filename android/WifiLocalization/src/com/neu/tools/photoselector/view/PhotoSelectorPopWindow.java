package com.neu.tools.photoselector.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.neu.tools.photoselector.activity.AlbumIndexActivity;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;

public class PhotoSelectorPopWindow extends PopupWindow {

    private Activity activity;
    private static final int TAKE_PICTURE = 10;
    private int MaxSelect = 6;
    private String path = "";

    public PhotoSelectorPopWindow(final Activity activity, View parent, OnClickListener doCamera, final int MaxSelect) {

        this.activity = activity;
        this.MaxSelect = MaxSelect;
        View view = View.inflate(activity, R.layout.tools_photo_selector_popwindow, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));

        setWidth(LayoutParams.FILL_PARENT);
        setHeight(LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        // bt1.setOnClickListener(doCamera);
        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                photo();
                dismiss();
            }
        });

        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, AlbumIndexActivity.class);
                intent.putExtra("maxSelect", MaxSelect);
                activity.startActivity(intent);
                dismiss();
            }
        });
        bt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void photo() {
        String fileName = getStringToday() + ".jpg";
        Intent openCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName)));
        path = Environment.getExternalStorageDirectory() + "/" + fileName;
        App.getInstance().fileUrl = path;
        activity.startActivityForResult(openCameraIntent, Const.CAMERA_REQUEST_CODE);
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}