package com.neu.wifilocalization.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.neu.tools.photoselector.activity.AlbumIndexActivity;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.EmojiAdapter;
import com.neu.wifilocalization.adapter.EmojiViewPagerAdapter;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.EmojiEntity;
import com.neu.wifilocalization.social.activity.ChatActivity;
import com.neu.wifilocalization.utils.emoji.EmojiConversionUtil;

/**
 * 
 ****************************************** 
 * @author 廖乃波
 * @文件名称 : FaceRelativeLayout.java
 * @创建时间 : 2013-1-27 下午02:34:17
 * @文件描述 : 带表情的自定义输入框
 ****************************************** 
 */
public class EmojiRelativeLayout extends RelativeLayout implements OnItemClickListener, OnClickListener {

    private Context context;

    /** 表情页的监听事件 */
    private OnCorpusSelectedListener mListener;

    /** 显示表情页的viewpager */
    private ViewPager vp_face;

    /** 表情页界面集合 */
    private ArrayList<View> pageViews;

    /** 游标显示布局 */
    private LinearLayout layout_point;

    /** 游标点集合 */
    private ArrayList<ImageView> pointViews;

    /** 表情集合 */
    private List<List<EmojiEntity>> emojis;

    /** 表情区域 */
    private View view;

    /** 文件区域 */
    private RelativeLayout additonalSelectLayout;

    private ImageView takePhoteImageView;
    private String path = "";

    private ImageView localAlbumImageView;

    /** 输入框 */
    private EditText et_sendmessage;

    private ImageButton sendButton;

    /** 表情数据填充器 */
    private List<EmojiAdapter> faceAdapters;

    /** 当前表情页 */
    private int current = 0;

    public EmojiRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public EmojiRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public EmojiRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setOnCorpusSelectedListener(OnCorpusSelectedListener listener) {
        mListener = listener;
    }

    /**
     * 表情选择监听
     * 
     * @author naibo-liao
     * @时间： 2013-1-15下午04:32:54
     */
    public interface OnCorpusSelectedListener {

        void onCorpusSelected(EmojiEntity emoji);

        void onCorpusDeleted();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        emojis = EmojiConversionUtil.getInstace().emojiLists;
        onCreate();
    }

    private void onCreate() {
        Init_View();
        initListener();
        Init_viewPager();
        Init_Point();
        Init_Data();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_face:
            if (additonalSelectLayout.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
                additonalSelectLayout.setVisibility(View.GONE);
            }
            // 隐藏表情选择框
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            break;
        case R.id.et_sendmessage:
            // 隐藏表情选择框
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }
            break;
        case R.id.take_phote_image:
            System.out.println("拍照");
            photo();
            break;
        case R.id.local_album_image:
            System.out.println("相册");
            Intent intent = new Intent(context, AlbumIndexActivity.class);
            intent.putExtra("maxSelect", 1);
            context.startActivity(intent);
            break;
        }
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public void photo() {
        String fileName = getStringToday() + ".jpg";
        Intent openCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName)));
        openCameraIntent.putExtra("return-data", true);
        path = Environment.getExternalStorageDirectory() + "/" + fileName;
        System.out.println("文件路径" + path);
        App.getInstance().fileUrl = path;
        ((Activity) context).startActivityForResult(openCameraIntent, Const.CAMERA_REQUEST_CODE);
    }

    /**
     * 隐藏表情选择框
     */
    public boolean hideFaceView() {
        // 隐藏表情选择框
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * 初始化控件
     */
    private void Init_View() {
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
        sendButton = (ImageButton) findViewById(R.id.btn_send);
        sendButton.setOnClickListener(this);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);
        et_sendmessage.setOnClickListener(this);
        takePhoteImageView = (ImageView) findViewById(R.id.take_phote_image);
        localAlbumImageView = (ImageView) findViewById(R.id.local_album_image);
        takePhoteImageView.setOnClickListener(this);
        localAlbumImageView.setOnClickListener(this);
        findViewById(R.id.btn_face).setOnClickListener(this);
        view = findViewById(R.id.ll_facechoose);
        additonalSelectLayout = (RelativeLayout) findViewById(R.id.additional_select_layout);
    }

    private void initListener() {
        if (context.getClass().equals(ChatActivity.class)) {
            et_sendmessage.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 0) {
                        sendButton.setImageResource(R.drawable.icon_chat_function);
                    } else {
                        additonalSelectLayout.setVisibility(View.GONE);
                        sendButton.setImageResource(R.drawable.icon_send);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    /**
     * 初始化显示表情的viewpager
     */
    private void Init_viewPager() {
        pageViews = new ArrayList<View>();
        // 左侧添加空页
        View nullView1 = new View(context);
        // 设置透明背景
        nullView1.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);

        // 中间添加表情页

        faceAdapters = new ArrayList<EmojiAdapter>();
        for (int i = 0; i < emojis.size(); i++) {
            GridView view = new GridView(context);
            EmojiAdapter adapter = new EmojiAdapter(context, emojis.get(i));
            view.setAdapter(adapter);
            faceAdapters.add(adapter);
            view.setOnItemClickListener(this);
            view.setNumColumns(7);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing(1);
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(5, 0, 5, 0);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);
            pageViews.add(view);
        }

        // 右侧添加空页面
        View nullView2 = new View(context);
        // 设置透明背景
        nullView2.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView2);
    }

    /**
     * 初始化游标
     */
    private void Init_Point() {

        pointViews = new ArrayList<ImageView>();
        ImageView imageView;
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 8;
            layoutParams.height = 8;
            layout_point.addView(imageView, layoutParams);
            if (i == 0 || i == pageViews.size() - 1) {
                imageView.setVisibility(View.GONE);
            }
            if (i == 1) {
                imageView.setBackgroundResource(R.drawable.d2);
            }
            pointViews.add(imageView);

        }
    }

    /**
     * 填充数据
     */
    private void Init_Data() {
        vp_face.setAdapter(new EmojiViewPagerAdapter(pageViews));

        vp_face.setCurrentItem(1);
        current = 0;
        vp_face.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                current = arg0 - 1;
                // 描绘分页点
                draw_Point(arg0);
                // 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
                if (arg0 == pointViews.size() - 1 || arg0 == 0) {
                    if (arg0 == 0) {
                        vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
                        pointViews.get(1).setBackgroundResource(R.drawable.d2);
                    } else {
                        vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
                        pointViews.get(arg0 - 1).setBackgroundResource(R.drawable.d2);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     * 绘制游标背景
     */
    public void draw_Point(int index) {
        for (int i = 1; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.drawable.d2);
            } else {
                pointViews.get(i).setBackgroundResource(R.drawable.d1);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        EmojiEntity emoji = (EmojiEntity) faceAdapters.get(current).getItem(arg2);
        if (emoji.getId() == R.drawable.face_del_icon) {
            int selection = et_sendmessage.getSelectionStart();
            String text = et_sendmessage.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[");
                    int end = selection;
                    et_sendmessage.getText().delete(start, end);
                    return;
                }
                et_sendmessage.getText().delete(selection - 1, selection);
            }
        }
        if (!TextUtils.isEmpty(emoji.getCharacter())) {
            if (mListener != null)
                mListener.onCorpusSelected(emoji);
            SpannableString spannableString = EmojiConversionUtil.getInstace().addFace(getContext(), emoji.getId(),
                    emoji.getCharacter());
            et_sendmessage.append(spannableString);
        }

    }
}
