package com.ddu.icore.logic;

/**
 * Created by admin on 2016/1/6.
 */
public class Actions {

    public static int KILL_SERVICE = 0x0010;

    // Client发送消息给Service, 如果需要Service立即返回, 需要在Message的bundle中设置reply_client_msg
    public static String REPLY_CLIENT_MSG = "reply_client_msg";

    // Service发送消息给Client, 如果需要Client立即返回, 需要在Message的bundle中设置reply_service_msg
    public static String REPLY_SERVICE_MSG = "reply_service_msg";

    // Service连接成功
    public static String SERVICE_CONNECTED_ACTION = "service_connected_action";

    // 服务端消息
    public static String SERVICE_MSG_ACTION = "service_msg_action";

    // 客户端消息
    public static String CLIENT_MSG_ACTION = "client_msg_action";

    public static String EXIT_ACTION = "exit_action";

    public static String TEST_ACTION = "test_action";

    public static String DOWNLOAD_COMPLETE = "download_complete";
}
