package com.neu.wifilocalization.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.R.id;
import com.neu.wifilocalization.R.layout;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.model.NodeListItem;
import com.neu.wifilocalization.search.adapter.NodeListAdapter;
import com.neu.wifilocalization.utils.CommonUtils;
import com.neu.wifilocalization.utils.SerializeUtil;

public class PushMessageActivity extends BaseActivity implements OnDismissCallback {

    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView mPullRefreshListView;

    private NodeListAdapter adapter;
    private ListView resultListView;
    private List<Node> dataList = new ArrayList<Node>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.push_message);
        ViewUtils.inject(this);
        initHeader();
        hideFooterBar();
        initBodyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().footerHintList != null) {
            App.getInstance().footerHintList.set(1, 0);
        }
        if (App.getInstance().footerGridAdapter != null) {
            App.getInstance().footerGridAdapter.notifyDataSetChanged();
        }
        initData();
    }

    public void initHeader() {
        this.titleText.setText("推送消息");
    }

    // 初始化数据
    private void initData() {
        dataList.clear();
        getSerializableNode();
        adapter.notifyDataSetChanged();
    }

    // public void getSerializableNode() {
    // List<NodeListItem> nodeList;
    // try {
    // nodeList = db.findAll(Selector.from(NodeListItem.class).orderBy("id",
    // true).limit(pageSize).offset(pageSize*pageIndex));
    // if (nodeList != null) {
    // for (int i = 0; i < nodeList.size(); i++) {
    // Node node = (Node)
    // SerializeUtil.readObject(nodeList.get(i).getContent());
    // dataList.add(node);
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // adapter.notifyDataSetChanged();
    // }

    public void getSerializableNode() {
        List<NodeListItem> nodeList;
        try {
            nodeList = db.findAll(Selector.from(NodeListItem.class).orderBy("id", true).limit(pageSize)
                    .offset(pageSize * pageIndex));
            if (nodeList != null) {
                for (int i = 0; i < nodeList.size(); i++) {
                    Node node = (Node) SerializeUtil.readObject(nodeList.get(i).getContent());
                    System.out.println("推送消息列表节点x坐标："+node.getPosition().getMap_x());
                    System.out.println("推送消息列表节点ID："+node.getId());
                    dataList.add(node);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("mPullRefreshListView.onRefreshComplete()");
        // mPullRefreshListView.onRefreshComplete();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
                if (mPullRefreshListView.getCurrentMode() == mPullRefreshListView.getMode().PULL_FROM_START) {
                    System.out.println("下拉刷新");
                    dataList.clear();
                    pageIndex = 0;
                    getSerializableNode();
                } else {
                    System.out.println("加载更多");
                    pageIndex++;
                    getSerializableNode();
                }

            } catch (InterruptedException e) {
                LogUtils.e("", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mPullRefreshListView.onRefreshComplete();
            adapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }
    }

    public void initBodyView() {
        mPullRefreshListView.setMode(Mode.BOTH);

        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        CommonUtils.getLastUpdatedLabel(PushMessageActivity.this));
                new GetDataTask().execute();
            }
        });
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
            }
        });

        resultListView = mPullRefreshListView.getRefreshableView();
        adapter = new NodeListAdapter(PushMessageActivity.this, dataList);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(adapter, this, false));
        swingBottomInAnimationAdapter.setListView(resultListView);
        resultListView.setAdapter(swingBottomInAnimationAdapter);
    }

    @Override
    public void onDismiss(ListView listView, int[] reverseSortedPositions) {

    }

}
