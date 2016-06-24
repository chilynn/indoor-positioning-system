package com.neu.wifilocalization.view;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neu.wifilocalization.R;

/**
 * 自定义按钮（图标+文字）
 * 
 * @author alin
 * 
 */
public class MyImageButton extends LinearLayout {

    public MyImageButton(Context context, int imageResId, int textResId, int width) {
        super(context);
        mButtonImage = new ImageView(context);
        mButtonText = new TextView(context);
        setImageResource(imageResId);
        setText(textResId);
        initView(context, width);
    }

    public MyImageButton(Context context, int imageResId, String text, int width) {
        super(context);
        mButtonImage = new ImageView(context);
        mButtonText = new TextView(context);
        setImageResource(imageResId);
        setText(text);
        initView(context, width);
    }

    public void initView(Context context, int width) {
        mButtonImage.setPadding(0, 20, 0, 20);

        mButtonText.setTextColor(this.getResources().getColor(R.color.white));
        mButtonText.setPadding(10, 20, 0, 20);

        // 设置本布局的属性
        setClickable(true); // 可点击
        setFocusable(true); // 可聚焦
        setBackgroundResource(R.color.btn_normal);
        setOrientation(LinearLayout.HORIZONTAL); // 垂直布局

        LayoutParams params = new LayoutParams(width, LayoutParams.WRAP_CONTENT, 1);
        params.setMargins(1, 0, 1, 0);
        // 首先添加Image，然后才添加Text
        // 添加顺序将会影响布局效果

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(mButtonImage);
        layout.addView(mButtonText);
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);
        addView(layout);
        this.setLayoutParams(params);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            MyImageButton.this.setBackgroundResource(R.color.btn_pressed);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            MyImageButton.this.setBackgroundResource(R.color.btn_normal);
        }
        return super.onTouchEvent(event);
    }

    // ----------------public method-----------------------------
    /*
     * setImageResource方法
     */
    public void setImageResource(int resId) {
        mButtonImage.setImageResource(resId);
    }

    /*
     * setText方法
     */
    public void setText(int resId) {
        mButtonText.setText(resId);
    }

    public void setText(CharSequence buttonText) {
        mButtonText.setText(buttonText);
    }

    public void setBackgroundColor(int color) {
        setBackgroundResource(color);
    }

    /*
     * setTextColor方法
     */
    public void setTextColor(int color) {
        mButtonText.setTextColor(this.getResources().getColor(color));
    }

    // ----------------private attribute-----------------------------
    private ImageView mButtonImage = null;
    private TextView mButtonText = null;
}
