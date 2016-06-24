package com.neu.wifilocalization.view.popup;

import com.neu.wifilocalization.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyPopup extends MapPopupBase {

    public final static int ZERO = 0;
    public final static int PADDING_BOTTOM = 14;
    public final static int PADDING_TOP = 14;
    public final static int PADDING_LEFT = 10;
    public final static int PADDING_RIGHT = 10;
    public final static float DEF_TEXT_SIZE = 16;
    public final static int IMAGE_SIZE = 30;
    public final static int MAX_EMS = 14;

    private TextView leftButton;
    private TextView middleText;
    private TextView rightButton;

    public MyPopup(Context context, ViewGroup parentView) {
        super(context, parentView);

        middleText = new TextView(context);
        // middleText.setPadding((int) (PADDING_LEFT * dipScaleFactor), (int)
        // (PADDING_TOP * dipScaleFactor),
        // (int) (PADDING_RIGHT * dipScaleFactor), (int) (PADDING_BOTTOM *
        // dipScaleFactor));
        middleText.setTextSize(DEF_TEXT_SIZE);
        middleText.setGravity(Gravity.CENTER);
        middleText.setMaxEms(MAX_EMS);
        middleText.setBackgroundResource(R.drawable.bg_pop_middle);
        // middleText.setBackgroundColor(Color.BLUE);
        middleText.setTextColor(Color.WHITE);
        middleText.setFocusable(true);
        middleText.setClickable(true);

        leftButton = new TextView(context);
        // leftButton.setPadding((int) (PADDING_LEFT * dipScaleFactor), (int)
        // (PADDING_TOP * dipScaleFactor),
        // (int) (PADDING_RIGHT * dipScaleFactor), (int) (PADDING_BOTTOM *
        // dipScaleFactor));
        leftButton.setTextSize(DEF_TEXT_SIZE);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setMaxEms(MAX_EMS);
        leftButton.setBackgroundResource(R.drawable.bg_pop_left);
        // leftButton.setBackgroundColor(Color.GRAY);
        leftButton.setTextColor(Color.WHITE);
        leftButton.setText("删除");
        leftButton.setFocusable(true);
        leftButton.setClickable(true);

        rightButton = new TextView(context);
        // rightButton.setPadding((int) (PADDING_LEFT * dipScaleFactor), (int)
        // (PADDING_TOP * dipScaleFactor),
        // (int) (PADDING_RIGHT * dipScaleFactor), (int) (PADDING_BOTTOM *
        // dipScaleFactor));
        rightButton.setTextSize(DEF_TEXT_SIZE);
        rightButton.setGravity(Gravity.CENTER);
        rightButton.setMaxEms(MAX_EMS);
        rightButton.setTextColor(Color.WHITE);
        rightButton.setBackgroundResource(R.drawable.bg_pop_right);
        // rightButton.setBackgroundColor(Color.GREEN);
        rightButton.setText("采集");
        rightButton.setFocusable(true);
        rightButton.setClickable(true);

        container.addView(leftButton);
        container.addView(middleText);
        container.addView(rightButton);
    }

    public void moveBy(int dx, int dy) {
        if (lastX != -1 && lastY != -1) {
            int paddingBottom = 0;
            int paddingRight = 0;
            if (container.getPaddingTop() > (screenHeight - (middleText.getHeight() + 3))) {
                paddingBottom = (container.getPaddingBottom() - dy);
            }

            if (container.getPaddingLeft() > (screenWidth - (middleText.getWidth() + 3))) {
                paddingRight = container.getPaddingRight() - dx;
            }

            container.setPadding(container.getPaddingLeft() + dx, container.getPaddingTop() + dy, paddingRight,
                    paddingBottom);
        }
    }

    public void setText(String theText) {
        middleText.setPadding((int) (PADDING_LEFT * dipScaleFactor), (int) (PADDING_TOP * dipScaleFactor),
                (int) (PADDING_RIGHT * dipScaleFactor), (int) (PADDING_BOTTOM * dipScaleFactor));

        middleText.setText(" " + theText + " ");
    }

    public void setLeftButtonOnClickListener(View.OnTouchListener listener) {
        if (leftButton != null) {
            leftButton.setOnTouchListener(listener);
        }
    }

    public void setMiddleTextOnClickListener(View.OnTouchListener listener) {
        if (middleText != null) {
            middleText.setOnTouchListener(listener);
        }
    }

    public void setRightButtonOnClickListener(View.OnTouchListener listener) {
        if (rightButton != null) {
            rightButton.setOnTouchListener(listener);
        }
    }

}
