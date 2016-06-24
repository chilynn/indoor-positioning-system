package com.neu.wifilocalization.mvc;

import java.io.File;
import java.util.List;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.neu.wifilocalization.application.Const;
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
import com.neu.wifilocalization.utils.ResetFooterListViewHeight;

public class Controller extends AbstractController {

    private Gson gson;

    public Controller() {
        gson = new Gson();
    }

    public void fillUserInfo(com.lidroid.xutils.http.RequestParams params, User user) {
        if (user != null) {
            params.addBodyParameter("id", user.getId());
            params.addBodyParameter("username", user.getUsername());
            params.addBodyParameter("password", user.getPassword());
        } else {
        }
    }

    public void putFiles(com.lidroid.xutils.http.RequestParams params, List<String> files) {
        for (int i = 0; i < files.size(); i++) {
            try {
                params.addBodyParameter("file" + i, new File(files.get(i)), "application/octet-stream");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void login(boolean showLoading, User user, final Callback<UserServerResponse> callback) {
        String url = Const.USER_LOGIN;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("登录响应：" + responseInfo.result);
                        try {
                            UserServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    UserServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void regiseter(boolean showLoading, boolean hideLoading,User user, final Callback<ServerResponse> callback) {
        String url = Const.USER_REGISTER;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this,showLoading,hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            ServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    ServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void uploadWifiData(boolean showLoading, boolean hideLoading, String accessPointInfo, String x, String y,
            String aheadDegree, final Callback<IdServerResponse> callback) {
        String url = Const.SEND_WIFI_DATA;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addBodyParameter("access_point", accessPointInfo);
        params.addBodyParameter("map_x", x);
        params.addBodyParameter("map_y", y);
        params.addBodyParameter("ahead_degree", aheadDegree);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("上传wifi数据服务器响应：" + (String) responseInfo.result);
                        try {
                            IdServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    IdServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void requestLocalization(boolean showLoading, boolean hideLoading, User user, String accessPointInfo,
            final Callback<LocationServerResponse> callback) {
        String url = Const.REQUEST_LOCALIZATION;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        params.addBodyParameter("access_point", accessPointInfo);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            System.out.println("请求定位响应：" + (String) responseInfo.result);
                            LocationServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    LocationServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void uploadNode(boolean showLoading, boolean hideLoading, Node node, Position position, String categoryId,
            final Callback<IdServerResponse> callback) {
        String url = Const.NODE_ADD;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addBodyParameter("name", node.getName());
        params.addBodyParameter("description", node.getDescription());
        params.addBodyParameter("position_id", position.getId() + "");
        params.addBodyParameter("category_id", categoryId);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        //System.out.println("上传节点服务器返回："+responseInfo.result);
                        try {
                            IdServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    IdServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void searchNode(boolean showLoading, boolean hideLoading, String content, int offset,
            final Callback<List<Node>> callback) {
        String url = Const.NODE_SEARCH;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addBodyParameter("content", content);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("搜索响应：" + (String) responseInfo.result);
                        try {
                            List<Node> nodeList = gson.fromJson((String) responseInfo.result,
                                    new TypeToken<List<Node>>() {
                                    }.getType());
                            callback.execute(nodeList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void updateUser(boolean showLoading, boolean hideLoading, User user, String filePath,
            final Callback<ServerResponse> callback) {
        String url = Const.USER_UPDATE;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        if (!filePath.equals("")) {
            params.addBodyParameter("file", new File(filePath));
        }
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            ServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    ServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void getUserInfo(boolean showLoading, boolean hideLoading, User user, String userId, String username,
            final Callback<User> callback) {
        String url = Const.USER_INFO;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        if (user != null) {
            fillUserInfo(params, user);
        }
        if (!userId.equals("")) {
            params.addBodyParameter("searchId", userId);
        }
        if (!username.equals("")) {
            params.addBodyParameter("searchName", username);
        }
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            User user = gson.fromJson((String) responseInfo.result, User.class);
                            callback.execute(user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void uploadImages(boolean showLoading, boolean hideLoading, String contentId, List<String> files,
            final Callback<ServerResponse> callback) {
        String url = Const.IMAGE;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addBodyParameter("content_id", contentId);
        putFiles(params, files);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, hideLoading) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            ServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    ServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getUserFriendList(boolean showLoading, boolean hideLoading, User user,
            final Callback<List<User>> callback) {
        String url = Const.USER_FRIEND;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("好友列表:" + (String) responseInfo.result);
                        try {
                            List<User> userList = gson.fromJson((String) responseInfo.result,
                                    new TypeToken<List<User>>() {
                                    }.getType());
                            callback.execute(userList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void searchUser(boolean showLoading, boolean hideLoading, User user, String username, String nearByFilter,
            final Callback<UserServerResponse> callback) {
        String url = Const.USER_SEARCH;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        params.addBodyParameter("search", username);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("搜索用户服务器返回："+responseInfo.result);
                        try {
                            UserServerResponse userServerResponse = gson.fromJson((String) responseInfo.result,
                                    UserServerResponse.class);
                            callback.execute(userServerResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void addFriend(boolean showLoading, boolean hideLoading, String type, User user, String userId,
            String relationId, final Callback<IdServerResponse> callback) {
        String url = Const.USER_ADD_FRIEND;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        params.addBodyParameter("type", type);
        params.addBodyParameter("user_id", userId);
        params.addBodyParameter("relation_id", relationId);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            IdServerResponse idServerResponse = gson.fromJson((String) responseInfo.result,
                                    IdServerResponse.class);
                            callback.execute(idServerResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getFriendApply(boolean showLoading, boolean hideLoading, User user,
            final Callback<List<NewRequest>> callback) {
        String url = Const.USER_FRIEND_APPLY;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("好友申请："+responseInfo.result);
                        try {
                            List<NewRequest> requestList = gson.fromJson((String) responseInfo.result,
                                    new TypeToken<List<NewRequest>>() {
                                    }.getType());
                            callback.execute(requestList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getNodeDetail(boolean showLoading, boolean hideLoading, User user, String id,
            final Callback<Node> callback) {
        String url = Const.NODE_DETAIL;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        params.addBodyParameter("node_id", id);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("节点详情："+responseInfo.result);
                        try {
                            Node node = gson.fromJson((String) responseInfo.result, Node.class);
                            callback.execute(node);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void addComment(boolean showLoading, boolean hideLoading, User user, CommentEntity comment,
            final Callback<ServerResponse> callback) {
        String url = Const.COMMENT_ADD;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        params.addBodyParameter("content_id", comment.getContent_id());
        params.addBodyParameter("content", comment.getContent());
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            ServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    ServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getDetailReviewList(final ListView listView, final ProgressBar moreProgressBar,
            final List<CommentEntity> dataList, final BaseAdapter adapter, String commentTypeId, String targetId,
            int offset) {
        String url = Const.COMMENT_GET;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addBodyParameter("content_type_id", commentTypeId);
        params.addBodyParameter("content_id", targetId);
        params.addBodyParameter("offset", offset + "");
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, true, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        List<CommentEntity> commentList = gson.fromJson((String) responseInfo.result,
                                new TypeToken<List<CommentEntity>>() {
                                }.getType());
                        if (commentList.size() == 0) {
                            moreProgressBar.setVisibility(View.GONE);
                            requestOffset = requestOffset - dataList.size();
                        } else {
                            moreProgressBar.setVisibility(View.GONE);
                        }
                        listView.setAdapter(adapter);
                        dataList.addAll(commentList);
                        adapter.notifyDataSetChanged();
                        listView.setVisibility(View.VISIBLE);
                        ResetFooterListViewHeight.setListViewHeightBasedOnChildren(listView);
                    }
                });
    }

    @Override
    public void getCategoryList(boolean showLoading, boolean hideLoading, final Callback<List<Category>> callback) {
        String url = Const.CATEGORY_GET;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("获取分类：" + (String) responseInfo.result);
                        try {
                            List<Category> categoryList = gson.fromJson((String) responseInfo.result,
                                    new TypeToken<List<Category>>() {
                                    }.getType());
                            callback.execute(categoryList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void editUser(boolean showLoading, boolean hideLoading, User user, final Callback<ServerResponse> callback) {
        String url = Const.USER_EDIT;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        if (user.getNickname() != null) {
            params.addBodyParameter("nickname", user.getNickname());
        }
        if (user.getSignature() != null) {
            params.addBodyParameter("signature", user.getSignature());
        }
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        try {
                            ServerResponse serverResponse = gson.fromJson((String) responseInfo.result,
                                    ServerResponse.class);
                            callback.execute(serverResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getShortestRoute(boolean showLoading, boolean hideLoading, User user, String startId, String endId,
            final Callback<RouteServerResponse> callback) {
        String url = Const.ROUTE_SHORTEST;
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        fillUserInfo(params, user);
        params.addBodyParameter("start_id", startId);
        params.addBodyParameter("end_id", endId);
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, url, params,
                new MyRequestCallBack<String>(this, showLoading, true) {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        super.onSuccess(responseInfo);
                        System.out.println("获取路径的服务器响应：" + (String) responseInfo.result);
                        try {
                            RouteServerResponse routeServerResponse = gson.fromJson((String) responseInfo.result,
                                    RouteServerResponse.class);
                            callback.execute(routeServerResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
