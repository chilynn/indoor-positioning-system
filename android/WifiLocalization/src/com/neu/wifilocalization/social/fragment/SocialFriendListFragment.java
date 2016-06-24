package com.neu.wifilocalization.social.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.fragment.BaseFragment;
import com.neu.wifilocalization.model.ChatMsgEntity;
import com.neu.wifilocalization.model.SortModel;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.social.activity.UserInfoActivity;
import com.neu.wifilocalization.social.adapter.SortAdapter;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CharacterParser;
import com.neu.wifilocalization.utils.PinyinComparator;
import com.neu.wifilocalization.view.ClearEditText;

public class SocialFriendListFragment extends BaseFragment {

    private static SocialFriendListFragment instance;
    
    @ViewInject(R.id.default_hint_text)
    private TextView defaultHintText;
    @ViewInject(R.id.social_friend_list)
    private ListView sortListView;
    @ViewInject(R.id.filter_edit)
    private ClearEditText mClearEditText;
    private SortAdapter adapter;
    private List<User> dataList = new ArrayList<User>();

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList = new ArrayList<SortModel>();
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    public static SocialFriendListFragment getInstance() {
        if (instance == null) {
            instance = new SocialFriendListFragment();
        }
        return instance;
    }

    public SocialFriendListFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_friend_list, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBodyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
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

    public void initBodyView() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.getHuizModel().put("userId", ((SortModel) adapter.getItem(position)).getId());
                Jumper.jump(getActivity(), UserInfoActivity.class);
                AnimationUtils.rightToLeft(getActivity());
            }
        });
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        adapter = new SortAdapter(getActivity(), SourceDateList);
    }

    private void initData() {
        controller.getUserFriendList(true, true, loginedUser, new Callback<List<User>>() {
            @Override
            public void execute(List<User> list) {
                dataList.clear();
                SourceDateList.clear();
                for (int i = 0; i < list.size(); i++) {
                    User friendInfo = list.get(i);
                    friendInfo.setIs_friend("1");
                    dataList.add(friendInfo);
                    try {
                        User friend = db.findFirst(Selector.from(User.class).where("id", "=", friendInfo.getId()));
                        if (friend == null) {
                            db.saveOrUpdate(friendInfo);
                        } else {
                            db.update(friendInfo, null);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                if (dataList.size() == 0) {
                    defaultHintText.setVisibility(View.VISIBLE);
                } else {
                    defaultHintText.setVisibility(View.GONE);
                }

                SourceDateList.addAll(filledDataFromJson(dataList));

                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                sortListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 为ListView填充数据来自json的数据
     * 
     * @param data
     * @return
     */
    private List<SortModel> filledDataFromJson(List<User> data) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < data.size(); i++) {
            User user = data.get(i);
            SortModel sortModel = new SortModel();
            sortModel.setId(user.getId() + "");
            sortModel.setName(user.getUsername());
            // sortModel.setSignature(data.get(i).getSignature());
            sortModel.setImage(data.get(i).getAvatar());
            if (user.getScreen_name() != null) {
                if (!user.getScreen_name().equals("")) {
                    sortModel.setName(user.getScreen_name());
                    System.out.println(user.getScreen_name());
                } else {
                    sortModel.setName(user.getUsername());
                    System.out.println(user.getUsername());
                }
            } else {
                sortModel.setName(user.getUsername());
                System.out.println(user.getUsername());
            }

            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(sortModel.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else if (sortString.matches("[0-9]")) {
                sortModel.setSortLetters("#");
            } else {
                sortModel.setSortLetters("@");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

}
