package com.neu.wifilocalization.social.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseFragmentActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.social.fragment.SocialAddFriendFragment;
import com.neu.wifilocalization.social.fragment.SocialFriendListFragment;
import com.neu.wifilocalization.social.fragment.SocialRecentContactFragment;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 社交tab主页
 * 
 * @author alin
 * 
 */
public class SocialTabActivity extends BaseFragmentActivity {
    @ViewInject(R.id.tab_view_pager)
    private ViewPager viewPager;
    @ViewInject(R.id.tab_indicator)
    private TabPageIndicator indicator;

    private static final String[] CONTENT = new String[] { "最近联系人", "添加好友", "好友列表" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.common_tab);
        ViewUtils.inject(this);
        this.hideFooterBar();
        initTitleBar();
        initBodyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (redirect()) {

        } else {
            if (!hasNotReadMsg()) {
                System.out.println("没有未读消息");
                if (App.getInstance().footerHintList != null) {
                    App.getInstance().footerHintList.set(2, 0);
                }
                if (App.getInstance().footerGridAdapter != null) {
                    App.getInstance().footerGridAdapter.notifyDataSetChanged();
                }
            } else {
                System.out.println("有未读消息");
                if (App.getInstance().footerHintList != null) {
                    App.getInstance().footerHintList.set(2, 1);
                }
                if (App.getInstance().footerGridAdapter != null) {
                    App.getInstance().footerGridAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public boolean redirect() {
        if (model.get("socialToApplication") != null) {
            if (model.get("socialToApplication").toString().equals("1")) {
                Jumper.jump(SocialTabActivity.this, NewFriendActivity.class);
                AnimationUtils.rightToLeft(SocialTabActivity.this);
                model.put("socialToApplication", null);
                return true;
            } else {
                return false;
            }
        } else if (model.get("socialToUserInfo") != null) {
            if (model.get("socialToUserInfo").toString().equals("1")) {
                Jumper.jump(SocialTabActivity.this, UserInfoActivity.class);
                AnimationUtils.rightToLeft(SocialTabActivity.this);
                model.put("socialToUserInfo", null);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean hasNotReadMsg() {
        boolean hasNotReadMsg = false;
        try {
            List<ChatMsgEntity> notReadList = db
                    .findAll(Selector.from(ChatMsgEntity.class).where("isRead", "=", false));
            if (notReadList != null) {
                if (notReadList.size() == 0) {
                    hasNotReadMsg = false;
                } else {
                    hasNotReadMsg = true;
                }
            } else {
                hasNotReadMsg = false;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return hasNotReadMsg;
    }

    public void initTitleBar() {
        this.titleText.setText("社交");
        this.functionButton.setVisibility(View.GONE);
    }

    public void initBodyView() {
        FragmentStatePagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), CONTENT);
        viewPager.setAdapter(adapter);
        indicator.setUnselectTextSize(14.0f);
        indicator.setSelectTextSize(16.0f);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(new onPageChangeListener());
    }

    private class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private String[] CONTENT;

        public TabFragmentPagerAdapter(FragmentManager fm, String[] CONTENT) {
            super(fm);
            this.CONTENT = CONTENT;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return SocialRecentContactFragment.getInstance();
            } else if (position == 1) {
                return SocialAddFriendFragment.getInstance();
            } else {
                return SocialFriendListFragment.getInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

    class onPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            // System.out.println("onPageChangeListener位置---" + position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }
}
