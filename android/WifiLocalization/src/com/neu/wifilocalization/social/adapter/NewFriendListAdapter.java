package com.neu.wifilocalization.social.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.adapter.MyBaseAdapter;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.IdServerResponse;
import com.neu.wifilocalization.model.NewRequest;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.mvc.AbstractController;
import com.neu.wifilocalization.mvc.ControllerBuilder;

/**
 * 新朋友列表适配器
 * 
 * @author alin
 * 
 */
public class NewFriendListAdapter extends MyBaseAdapter<NewRequest> {

    public NewFriendListAdapter(Context context, List<NewRequest> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.social_new_friend_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView) view.findViewById(R.id.new_friend_avatar);
        viewHolder.nameText = (TextView) view.findViewById(R.id.new_friend_name_text);
        viewHolder.signature = (TextView) view.findViewById(R.id.new_friend_signature_text);
        viewHolder.acceptButton = (Button) view.findViewById(R.id.new_friend_accept_btn);
        viewHolder.rejectButton = (Button) view.findViewById(R.id.new_friend_reject_btn);

        NewRequest newFriendRequest = dataList.get(position);

        if (newFriendRequest.getSender().getAvatar() != null) {
            if (!newFriendRequest.getSender().getAvatar().equals("")) {
                bitmapUtils.display(viewHolder.imageView, Const.BASE_IMAGE + "/"
                        + newFriendRequest.getSender().getAvatar());
            } else {
                viewHolder.imageView.setImageResource(R.drawable.default_avatar);
            }
        } else {
            viewHolder.imageView.setImageResource(R.drawable.default_avatar);
        }
        System.out.println("申请人用户名："+newFriendRequest.getSender().getUsername());
        viewHolder.nameText.setText(newFriendRequest.getSender().getUsername());
        viewHolder.signature.setText(newFriendRequest.getSender().getSignature());
        viewHolder.acceptButton.setText("通过验证");
        viewHolder.acceptButton.setOnClickListener(new accept(newFriendRequest.getId(), position));
        viewHolder.rejectButton.setOnClickListener(new reject(newFriendRequest.getUser_friend_id(), position));
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView nameText;
        TextView signature;
        Button acceptButton;
        Button rejectButton;
    }

    class accept implements OnClickListener {

        private String requestId;
        private int position;

        public accept(String requestId, int position) {
            this.requestId = requestId;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            final AbstractController controller = ControllerBuilder.getInstance();
            User user = App.getInstance().loginedUser;
            controller.addFriend(true, true, "2", user, "", requestId, new Callback<IdServerResponse>() {
                @Override
                public void execute(IdServerResponse serverResponse) {
                    controller.getHuizView().showMessage(serverResponse.getMsg());
                    if (serverResponse.getState().equals("1")) {
                        dataList.remove(position);
                        NewFriendListAdapter.this.notifyDataSetChanged();
                    } else {

                    }
                }
            });
        }
    }

    class reject implements OnClickListener {

        private String requestId;
        private int position;

        public reject(String requestId, int position) {
            this.requestId = requestId;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            final AbstractController controller = ControllerBuilder.getInstance();
            User user = (User) controller.getHuizModel().get("loginUser");
            // controller.addUser(user, "1", "0", "", requestId, new
            // Callback<ServerResponse>() {
            // @Override
            // public void execute(ServerResponse serverResponse) {
            // controller.getHuizView().showMessage(serverResponse.getMsg());
            // if (serverResponse.getState().equals("1")) {
            // dataList.remove(position);
            // NewFriendListAdapter.this.notifyDataSetChanged();
            // } else {
            //
            // }
            // }
            // });
        }
    }

    class rejectJoin implements OnClickListener {

        private String requestId;
        private int position;

        public rejectJoin(String requestId, int position) {
            this.requestId = requestId;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            final AbstractController controller = ControllerBuilder.getInstance();
            User user = (User) controller.getHuizModel().get("loginUser");
            // controller.joinGroup(user, "1", "0", "", requestId, new
            // Callback<ServerResponse>() {
            // @Override
            // public void execute(ServerResponse serverResponse) {
            // controller.getHuizView().showMessage(serverResponse.getMsg());
            // if (serverResponse.getState().equals("1")) {
            // dataList.remove(position);
            // NewFriendListAdapter.this.notifyDataSetChanged();
            // } else {
            //
            // }
            // }
            // });
        }
    }

}