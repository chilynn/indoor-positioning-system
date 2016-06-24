package com.neu.wifilocalization.mvc;

import java.util.List;

import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.huiztech.common.mvc.HuizController;
import com.huiztech.common.mvc.HuizView;
import com.neu.wifilocalization.model.Category;
import com.neu.wifilocalization.model.CommentEntity;
import com.neu.wifilocalization.model.IdServerResponse;
import com.neu.wifilocalization.model.LocationServerResponse;
import com.neu.wifilocalization.model.NewRequest;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.model.Position;
import com.neu.wifilocalization.model.RouteServerResponse;
import com.neu.wifilocalization.model.ServerResponse;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.model.UserServerResponse;

public abstract class AbstractController extends HuizController<HuizView> {

    protected int requestOffset = 0;

    public int getRequestOffset() {
        return requestOffset;
    }

    public void setRequestOffset(int requestOffset) {
        this.requestOffset = requestOffset;
    }

    /**
     * 登录
     * 
     * @param user
     * @param callback
     */
    public abstract void login(boolean showLoading, User user, Callback<UserServerResponse> callback);

    /**
     * 注册
     * 
     * @param user
     * @param callback
     */
    public abstract void regiseter(boolean showLoading, boolean hideLoading,User user, Callback<ServerResponse> callback);

    /**
     * 上传wifi信号数据
     * 
     * @param accessPointInfo
     * @param x
     * @param y
     * @param callback
     */
    public abstract void uploadWifiData(boolean showLoading, boolean hideLoading, String accessPointInfo, String x,
            String y, String aheadDegree, Callback<IdServerResponse> callback);

    /**
     * 请求定位
     * 
     * @param accessPointInfo
     * @param callback
     */
    public abstract void requestLocalization(boolean showLoading, boolean hideLoading, User user,
            String accessPointInfo, Callback<LocationServerResponse> callback);

    /**
     * 上传节点
     * 
     * @param showLoading
     * @param hideLoading
     * @param node
     * @param callback
     */
    public abstract void uploadNode(boolean showLoading, boolean hideLoading, Node node, Position position,
            String categoryId, Callback<IdServerResponse> callback);

    /**
     * 搜索节点
     * 
     * @param showLoading
     * @param hideLoading
     * @param content
     * @param callback
     */
    public abstract void searchNode(boolean showLoading, boolean hideLoading, String content, int offset,
            Callback<List<Node>> callback);

    /**
     * 修改用户信息
     * 
     * @param showLoading
     * @param hideLoading
     * @param user
     * @param file
     * @param callback
     */
    public abstract void updateUser(boolean showLoading, boolean hideLoading, User user, String filePath,
            Callback<ServerResponse> callback);

    /**
     * 获取用户信息
     * 
     * @param showLoading
     * @param hideLoading
     * @param username
     * @param callback
     */
    public abstract void getUserInfo(boolean showLoading, boolean hideLoading, User user, String userId,
            String username, Callback<User> callback);

    /**
     * 查询用户
     * 
     * @param showLoading
     * @param hideLoading
     * @param username
     * @param nearByFilter
     * @param callback
     */
    public abstract void searchUser(boolean showLoading, boolean hideLoading, User user, String username,
            String nearByFilter, Callback<UserServerResponse> callback);

    /**
     * 上传多图
     * 
     * @param showLoading
     * @param hideLoading
     * @param images
     * @param callback
     */
    public abstract void uploadImages(boolean showLoading, boolean hideLoading, String contentId, List<String> files,
            Callback<ServerResponse> callback);

    /**
     * 获取好友列表
     * 
     * @param showLoading
     * @param hideLoading
     * @param user
     * @param callback
     */
    public abstract void getUserFriendList(boolean showLoading, boolean hideLoading, User user,
            Callback<List<User>> callback);

    /**
     * 添加好友
     * 
     * @param showLoading
     * @param hideLoading
     * @param type
     * @param user
     * @param userId
     * @param relationId
     * @param callback
     */
    public abstract void addFriend(boolean showLoading, boolean hideLoading, String type, User user, String userId,
            String relationId, Callback<IdServerResponse> callback);

    /**
     * 获取好友请求列表
     * 
     * @param showLoading
     * @param hideLoading
     * @param user
     * @param callback
     */
    public abstract void getFriendApply(boolean showLoading, boolean hideLoading, User user,
            Callback<List<NewRequest>> callback);

    /**
     * 获取节点详情
     * 
     * @param showLoading
     * @param hideLoading
     * @param id
     * @param callback
     */
    public abstract void getNodeDetail(boolean showLoading, boolean hideLoading, User user, String id,
            Callback<Node> callback);

    /**
     * 添加评论
     * 
     * @param showLoading
     * @param hideLoading
     * @param user
     * @param conentId
     * @param content
     * @param callback
     */
    public abstract void addComment(boolean showLoading, boolean hideLoading, User user, CommentEntity comment,
            Callback<ServerResponse> callback);

    /**
     * 获取评论列表
     * 
     * @param listView
     * @param moreProgressBar
     * @param dataList
     * @param adapter
     * @param commentTypeId
     * @param targetId
     * @param offset
     */
    public abstract void getDetailReviewList(ListView listView, ProgressBar moreProgressBar,
            List<CommentEntity> dataList, BaseAdapter adapter, String commentTypeId, String targetId, int offset);

    /**
     * 获取分类
     * 
     * @param showLoading
     * @param hideLoading
     * @param callback
     */
    public abstract void getCategoryList(boolean showLoading, boolean hideLoading, Callback<List<Category>> callback);

    /**
     * 修改用户信息
     * 
     * @param showLoading
     * @param hideLoading
     * @param user
     * @param callback
     */
    public abstract void editUser(boolean showLoading, boolean hideLoading, User user, Callback<ServerResponse> callback);

    /**
     * 获取最短路径
     * 
     * @param showLoading
     * @param hideLoading
     * @param user
     * @param startId
     * @param endId
     * @param callback
     */
    public abstract void getShortestRoute(boolean showLoading, boolean hideLoading, User user, String startId,
            String endId, Callback<RouteServerResponse> callback);
}
