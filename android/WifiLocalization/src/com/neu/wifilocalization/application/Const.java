package com.neu.wifilocalization.application;

/**
 * 程序常量
 * 
 * @author alin
 * 
 */
public class Const {
    // public static final String SERVER_IP = "http://192.168.137.1/IPS/server";
    //public static final String SERVER_IP = "http://192.168.0.107/IPS/server";
    public static final String SERVER_IP = "http://115.28.42.251:8084";
    public static final String BASE_IMAGE = SERVER_IP + "/WifiLocalization";
    public static final String SEND_WIFI_DATA = SERVER_IP + "/WifiLocalization/wifiDataHandler.php";
    public static final String REQUEST_LOCALIZATION = SERVER_IP + "/WifiLocalization/localizationHandler.php";
    public static final String USER_INFO = SERVER_IP + "/WifiLocalization/index.php?action=user_info";
    public static final String USER_SEARCH = SERVER_IP + "/WifiLocalization/index.php?action=user_search";
    public static final String USER_REGISTER = SERVER_IP + "/WifiLocalization/userRegister.php";
    public static final String USER_LOGIN = SERVER_IP + "/WifiLocalization/userLogin.php";
    public static final String USER_UPDATE = SERVER_IP + "/WifiLocalization/userUpdate.php";
    public static final String USER_EDIT = SERVER_IP + "/WifiLocalization/index.php?action=user_edit";
    public static final String USER_FRIEND = SERVER_IP + "/WifiLocalization/index.php?action=user_friendList";
    public static final String USER_ADD_FRIEND = SERVER_IP + "/WifiLocalization/index.php?action=user_addFriend";
    public static final String USER_FRIEND_APPLY = SERVER_IP + "/WifiLocalization/index.php?action=user_friendApply";
    public static final String NODE_ADD = SERVER_IP + "/WifiLocalization/nodeAdd.php";
    public static final String NODE_SEARCH = SERVER_IP + "/WifiLocalization/index.php?action=node_search";
    public static final String NODE_DETAIL = SERVER_IP + "/WifiLocalization/index.php?action=node_detail";
    public static final String IMAGE = SERVER_IP + "/WifiLocalization/index.php?action=image_add";
    public static final String COMMENT_ADD = SERVER_IP + "/WifiLocalization/index.php?action=comment_add";
    public static final String COMMENT_GET = SERVER_IP + "/WifiLocalization/index.php?action=comment_get";
    public static final String CATEGORY_GET = SERVER_IP + "/WifiLocalization/index.php?action=category_get";
    public static final String ROUTE_SHORTEST = SERVER_IP + "/WifiLocalization/index.php?action=route_shortest";

    /* 请求码 */
    public static final int IMAGE_REQUEST_CODE = 0;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int RESULT_REQUEST_CODE = 2;

    /* 百度语音识别 */
    public static final String API_KEY = "IeRhGKYwjZczreWu6zcBu2cx";
    public static final String SECRET_KEY = "5BCh7r5SBdeNChUIVHX8KGax5V09u67i";

    /* 聊天tag */
    public static final String chatMessagePictureTag = "[1]";
    public static final String chatMessageSoundTag = "[2]";
    public static final String chatMessageLocalPictureTag = "[3]";

    // 聊天服务器openfire
    public static final String OPEN_FIRE_SERVER_IP = "192.168.0.107";
    public static final String OPEN_FIRE_SERVER_NAME = "localhost";
    public static final int OPEN_FIRE_SERVER_PORT = 5222;
    public static final String SMACK = "Spark 2.6.3";

    // JPUSH
    public static final String JPUSH_ALIAS = "PUSH_LOCATION_OF_";
    public static final String JPUSH_LOGIN_ALIAS = "USER_ID_";
}
