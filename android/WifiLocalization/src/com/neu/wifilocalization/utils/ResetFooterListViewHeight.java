package com.neu.wifilocalization.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * listview 嵌套 listview 的情况下，调用重新设置高度
 * @author alin
 *
 */
public class ResetFooterListViewHeight {

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount()-1; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = 200+totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount()-1));
		listView.setLayoutParams(params);
	}

}
