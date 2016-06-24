package com.neu.wifilocalization.search.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.adapter.CommentListAdapter;
import com.neu.wifilocalization.adapter.ViewPagerAdapter;
import com.neu.wifilocalization.model.CommentEntity;
import com.neu.wifilocalization.model.Image;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.view.MyImageButton;

/**
 * 话题
 * 
 * @author alin
 * 
 */
public class NodeDetailActivity extends BaseActivity {

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.radioGroup)
    private ViewGroup group;
    @ViewInject(R.id.ScrollView)
    private ScrollView scrollView;

    @ViewInject(R.id.node_title_text)
    private TextView nodeTitleText;
    @ViewInject(R.id.node_content_text)
    private TextView nodeContentText;
    @ViewInject(R.id.node_comment_list)
    private ListView commentListView;

    @ViewInject(R.id.footer_progress)
    private ProgressBar moreProgressBar;
    @ViewInject(R.id.list_footview)
    private RelativeLayout footerView;

    @ViewInject(R.id.view_comment_layout)
    private LinearLayout viewCommentLayout;
    private MyImageButton viewCommentButton;

    private ViewPagerAdapter viewPageradapter;
    private ImageView[] radioGroupImages;
    private List<Image> images;

    private String nodeId = "";

    private CommentListAdapter commentAdapter;
    private List<CommentEntity> commentDataList = new ArrayList<CommentEntity>();

    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.node_detail);
        ViewUtils.inject(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View listFooterView = inflater.inflate(R.layout.common_pulllist_footer, null);
        ViewUtils.inject(this, listFooterView); // 注入view和事件
        initId();
        initCommentParams();
        initTitleBar();
        initBodyView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isClick) {
            viewMore();
        }
    }

    public void initId() {
        if (model.get("nodeId") != null) {
            nodeId = model.get("nodeId").toString();
        }
    }

    public void initTitleBar() {
        this.titleText.setText("节点详情");
        this.functionButton.setVisibility(View.GONE);
    }

    public void initCommentParams() {
        CommentEntity comment = new CommentEntity();
        comment.setContent_id(nodeId);
        controller.getHuizModel().put("comment", comment);
    }

    @SuppressLint("ResourceAsColor")
    public void initBodyView() {
        scrollView.smoothScrollTo(0, 0);
        commentListView.setCacheColorHint(Color.TRANSPARENT);// 防止滚动时不变黑
        commentListView.setDividerHeight(0);
        commentListView.addFooterView(footerView);
        commentAdapter = new CommentListAdapter(this, commentDataList);
        commentListView.setAdapter(commentAdapter);

        viewCommentButton = new MyImageButton(this, R.drawable.icon_comment_blue, "更多评论", 1);
        viewCommentButton.setBackgroundResource(R.color.white);
        viewCommentButton.setTextColor(R.color.blue_text);
        viewCommentLayout.addView(viewCommentButton);
        viewCommentButton.setOnClickListener(new viewComment());
    }

    private void initData() {
        controller.getNodeDetail(true, true, loginedUser, nodeId, new Callback<Node>() {
            @Override
            public void execute(Node node) {
                nodeTitleText.setText(node.getName());
                nodeContentText.setText(node.getDescription());

                images = node.getImages();
                viewPageradapter = new ViewPagerAdapter(images, NodeDetailActivity.this);
                viewPager.setAdapter(viewPageradapter);
                viewPager.setOnPageChangeListener(new ViewPageChangeListener());

                radioGroupImages = new ImageView[images.size()];
                for (int i = 0; i < images.size(); i++) {
                    ImageView imageView = new ImageView(NodeDetailActivity.this);
                    imageView.setLayoutParams(new LayoutParams(20, 20));
                    imageView.setPadding(20, 0, 20, 0);
                    radioGroupImages[i] = imageView;
                    if (i == 0) {
                        // 默认选中第一张图片
                        radioGroupImages[i].setBackgroundResource(R.drawable.page_indicator_focused);
                    } else {
                        radioGroupImages[i].setBackgroundResource(R.drawable.page_indicator);
                    }
                    group.addView(radioGroupImages[i]);
                }
            }
        });
    }

    class viewComment implements OnClickListener {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            isClick = true;
            commentDataList.clear();
            commentAdapter.notifyDataSetChanged();
            viewCommentButton.setImageResource(R.drawable.icon_comment_white);
            viewCommentButton.setTextColor(R.color.white);
            moreProgressBar.setVisibility(View.VISIBLE);
            controller.getDetailReviewList(commentListView, moreProgressBar, commentDataList, commentAdapter, "",
                    nodeId, 0);
        }
    }

    public void viewMore() {
        moreProgressBar.setVisibility(View.VISIBLE);
        controller.setRequestOffset(commentDataList.size());
        System.out.println("commentDataList.size()@" + commentDataList.size());
        System.out.println("offset@" + controller.getRequestOffset());
        controller.getDetailReviewList(commentListView, moreProgressBar, commentDataList, commentAdapter, "4", nodeId,
                controller.getRequestOffset());
    }

    @OnClick(R.id.list_footview)
    public void clickListFooter(View v) {
        viewMore();
    }

    class ViewPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < radioGroupImages.length; i++) {
                radioGroupImages[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
                if (arg0 != i) {
                    radioGroupImages[i].setBackgroundResource(R.drawable.page_indicator);
                }
            }

        }
    }

}
