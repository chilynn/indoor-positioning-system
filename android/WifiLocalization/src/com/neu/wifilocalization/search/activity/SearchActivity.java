package com.neu.wifilocalization.search.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.search.adapter.NodeListAdapter;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CommonUtils;

public class SearchActivity extends BaseActivity implements OnDismissCallback {
    @ViewInject(R.id.search_edit)
    private EditText searchEdit;
    @ViewInject(R.id.search_clear)
    private ImageView clearImage;
    @ViewInject(R.id.function_image)
    private ImageView functionImage;

    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView mPullRefreshListView;

    private DialogRecognitionListener mRecognitionListener;
    
    private NodeListAdapter adapter;
    private ListView resultListView;
    private List<Node> dataList = new ArrayList<Node>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_index);
        ViewUtils.inject(this);
        initEditListener();
        initBodyView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (model.get("isShowDialog").toString().equals("1")) {
            audioDialog.show();
            model.put("isShowDialog", "0");
        }
    }

    @OnClick(R.id.search_return_button)
    public void back(View v) {
        finish();
        AnimationUtils.leftToRight(SearchActivity.this);
    }

    @OnClick(R.id.search_clear)
    public void clear(View v) {
        searchEdit.setText("");
    }

    @OnClick(R.id.function_image)
    public void function(View v) {
        if (searchEdit.getText().toString().equals("")) {
            view.showMessage("语音搜索");
            audioDialog.show();
        } else {
            view.showMessage("文字搜索");
            if (dataList.size() > 0) {
                model.put("searchResultNodeList", dataList);
                model.put("searchContent", searchEdit.getText().toString());
                Jumper.jump(SearchActivity.this, ResultMapActivity.class);
                AnimationUtils.rightToLeft(SearchActivity.this);
            }
        }
    }

    // 文字搜索
    public void textSearch(boolean showLoading, String content, int offset) {
        controller.searchNode(showLoading, true, content, offset, new Callback<List<Node>>() {
            @Override
            public void execute(List<Node> nodeList) {
                dataList.clear();
                if (nodeList.size() > 0) {
                    dataList.addAll(nodeList);
                } else {
                    view.showMessage("查询不到!");
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void initEditListener() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged");
                if (s.length() == 0) {
                    clearImage.setVisibility(View.GONE);
                    functionImage.setImageResource(R.drawable.icon_record);
                    dataList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    clearImage.setVisibility(View.VISIBLE);
                    functionImage.setImageResource(R.drawable.icon_search3);
                    String content = searchEdit.getText().toString();
                    textSearch(true, content, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("beforeTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged");
            }
        });
    }

    public void initRecognitionListener() {
        mRecognitionListener = new DialogRecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> rs = results != null ? results.getStringArrayList(RESULTS_RECOGNITION) : null;
                if (rs != null && rs.size() > 0) {
                    resultText = rs.get(0).replaceFirst("。", "");
                    searchEdit.setText(resultText);
                    searchEdit.setSelection(resultText.length());
                    clearImage.setVisibility(View.VISIBLE);
                    functionImage.setImageResource(R.drawable.icon_search3);
                    //textSearch(true, resultText, 0);
                }
            }
        };
        audioDialog.setDialogRecognitionListener(mRecognitionListener);
    }
    
    public void initBodyView() {
        initRecognitionListener();
        
        mPullRefreshListView.setMode(Mode.DISABLED);

        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        CommonUtils.getLastUpdatedLabel(SearchActivity.this));
            }
        });
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
            }
        });

        resultListView = mPullRefreshListView.getRefreshableView();
        adapter = new NodeListAdapter(SearchActivity.this, dataList);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(adapter, this, false));
        swingBottomInAnimationAdapter.setListView(resultListView);
        resultListView.setAdapter(swingBottomInAnimationAdapter);
        model.put("searchEditText", searchEdit);
    }

    @Override
    public void onDismiss(ListView listView, int[] reverseSortedPositions) {

    }

}
