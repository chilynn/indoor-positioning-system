package com.neu.tools.photoselector.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.neu.tools.photoselector.adapter.AblumSubAdapter;
import com.neu.tools.photoselector.adapter.AblumSubAdapter.TextCallback;
import com.neu.tools.photoselector.model.ImageItem;
import com.neu.tools.photoselector.utils.AlbumHelper;
import com.neu.tools.photoselector.utils.BitmapStore;
import com.neu.wifilocalization.R;

public class AlbumSubActivity extends Activity {

    private int MaxSelect = 6;

    public static final String EXTRA_IMAGE_LIST = "imagelist";
    private TextView cancelText;
    List<ImageItem> dataList;
    GridView gridView;
    AblumSubAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 0:
                Toast.makeText(AlbumSubActivity.this, "最多选择" + MaxSelect + "张图片", 400).show();
                break;

            default:
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tools_album_sub_grid);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        MaxSelect = extras.getInt("maxSelect");
        
        dataList = (List<ImageItem>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);

        initView();

        cancelText = (TextView) findViewById(R.id.cancle_text);
        cancelText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext();) {
                    list.add(it.next());
                }

                if (BitmapStore.act_bool) {
                    finish();
//                    Intent intent = new Intent(AlbumSubActivity.this, PublishDynamicActivity.class);
//                    startActivity(intent);
                    BitmapStore.act_bool = false;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (BitmapStore.drr.size() < MaxSelect) {
                        BitmapStore.drr.add(list.get(i));
                    }
                }
                finish();
            }

        });
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new AblumSubAdapter(AlbumSubActivity.this, dataList, mHandler, MaxSelect);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
            }

        });

    }
}
