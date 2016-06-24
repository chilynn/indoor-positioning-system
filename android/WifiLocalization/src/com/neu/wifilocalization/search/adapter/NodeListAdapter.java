package com.neu.wifilocalization.search.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.MyBaseAdapter;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.search.activity.NodeDetailActivity;
import com.neu.wifilocalization.search.activity.ResultMapActivity;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CommonUtils;

/**
 * 节点列表适配器
 * 
 * @author alin
 * 
 */
public class NodeListAdapter extends MyBaseAdapter<Node> {

    public NodeListAdapter(Context context, List<Node> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        view = LayoutInflater.from(context).inflate(R.layout.search_node_list_item, null);
        holder.mainLayout = (LinearLayout) view.findViewById(R.id.node_main_layout);
        holder.numberText = (TextView) view.findViewById(R.id.node_order_number_text);
        holder.nameText = (TextView) view.findViewById(R.id.node_name_text);
        holder.visitTimeText = (TextView) view.findViewById(R.id.visit_time_text);
        holder.imageView = (ImageView) view.findViewById(R.id.node_image);
        holder.descriptionText = (TextView) view.findViewById(R.id.node_description_text);
        holder.mapLayout = (RelativeLayout) view.findViewById(R.id.node_map_layout);

        Node node = dataList.get(position);

        holder.numberText.setText(position + 1 + ".");
        holder.nameText.setText(node.getName());
        holder.descriptionText.setText(node.getDescription());
        if(node.getImages()!=null){
            if (node.getImages().size() > 0) {
                bitmapUtils.display(holder.imageView, Const.BASE_IMAGE + "/" + node.getImages().get(0).getImage_url());
            }
        }
        holder.mainLayout.setOnClickListener(new viewNode(node));
        holder.mapLayout.setOnClickListener(new viewMap(node));

        if (node.getVisitTime() != null) {
            holder.visitTimeText.setVisibility(View.VISIBLE);
            holder.visitTimeText.setText(CommonUtils.getTimeDifference(Long.parseLong(node.getVisitTime())));
        }

        return view;
    }

    class viewNode implements OnClickListener {

        private Node node;

        public viewNode(Node node) {
            this.node = node;
        }

        @Override
        public void onClick(View v) {
            controller.getHuizModel().put("nodeId", node.getId());
            System.out.println("将要查看的节点ID："+node.getId());
            Jumper.jump((Activity) context, NodeDetailActivity.class);
            AnimationUtils.rightToLeft(context);
        }
    }

    class viewMap implements OnClickListener {

        private Node node;

        public viewMap(Node node) {
            this.node = node;
        }

        @Override
        public void onClick(View v) {
            EditText searchEditText = (EditText) controller.getHuizModel().get("searchEditText");
            if(searchEditText!=null){
                searchEditText.setText(node.getName());
                searchEditText.setSelection(node.getName().length());// 将光标移至文字末尾
            }
            System.out.println("节点的x坐标："+node.getPosition().getMap_x());
            controller.getHuizModel().put("searchContent", node.getName());
            controller.getHuizModel().put("searchResultNode", node);
            Jumper.jump((Activity) context, ResultMapActivity.class);
            AnimationUtils.rightToLeft(context);
        }
    }

    class ViewHolder {
        private LinearLayout mainLayout;
        private TextView numberText;
        private TextView nameText;
        private TextView visitTimeText;
        private ImageView imageView;
        private TextView descriptionText;
        private RelativeLayout mapLayout;
    }

}
