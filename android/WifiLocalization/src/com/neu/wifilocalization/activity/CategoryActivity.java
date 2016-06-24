package com.neu.wifilocalization.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.CategoryListAdapter;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.Category;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.search.activity.ResultMapActivity;
import com.neu.wifilocalization.utils.AnimationUtils;

/**
 * 类别activity
 * 
 * @author alin
 * 
 */
public class CategoryActivity extends BaseActivity {

    @ViewInject(R.id.common_category_list)
    private ListView categoryList;

    private CategoryListAdapter adapter;
    private List<Category> dataList = new ArrayList<Category>();

    private boolean isNearCategory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.common_category);
        ViewUtils.inject(this);
        hideFooterBar();
        initTitleBar();
        initBodyView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isSelected();
        isNearCategory();
    }

    public void isSelected() {
        if (model.get("subSelected") != null) {
            model.put("subSelected", null);
            finish();
        }
    }

    public void isNearCategory() {
        if (model.get("nearCategory") != null) {
            isNearCategory = true;
        }
    }

    public void initTitleBar() {
        this.titleText.setText("类别");
        this.functionButton.setVisibility(View.GONE);
    }

    public void initBodyView() {
        categoryList.setOnItemClickListener(new clickListItem());
        adapter = new CategoryListAdapter(CategoryActivity.this, dataList, "1");
        categoryList.setAdapter(adapter);
    }

    public void initData() {
        try {
            List<Category> mainCategoryList = db.findAll(Selector.from(Category.class).where("level", "=", "1"));
            if (mainCategoryList != null) {
                for (int i = 0; i < mainCategoryList.size(); i++) {
                    dataList.add(mainCategoryList.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class clickListItem implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            final Category category = dataList.get(position);
            boolean hasChildren = false;
            try {
                List<Category> children = db.findAll(Selector.from(Category.class).where("pid", "=", category.getId()));
                if (children != null) {
                    if (children.size() >= 1) {
                        hasChildren = true;
                    }
                } else {
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (!hasChildren) {
                if(isNearCategory){
                    controller.searchNode(true, true, category.getName(), 0, new Callback<List<Node>>() {
                        @Override
                        public void execute(List<Node> list) {
                            if (list.size() > 0) {
                                controller.getHuizModel().put("searchResultNodeList", list);
                                model.put("searchContent", category.getName());
                                Jumper.jump(CategoryActivity.this, ResultMapActivity.class);
                                AnimationUtils.rightToLeft(CategoryActivity.this);
                            }else{
                                view.showMessage("暂无结果");
                            }
                        }
                    });
                }else{
                    model.put("category", category);
                    finish();
                }
            } else {
                model.put("pid", category.getId());
                Jumper.jump(CategoryActivity.this, SubCategoryActivity.class);
                AnimationUtils.rightToLeft(CategoryActivity.this);
            }
        }
    }

}
