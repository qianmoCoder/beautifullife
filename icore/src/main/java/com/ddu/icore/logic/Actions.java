package com.ddu.icore.logic;

/**
 * Created by admin on 2016/1/6.
 */
public class Actions {

    public static int KILL_SERVICE = 0x0010;

    // Client发送消息给Service, 如果需要Service立即返回, 需要在Message的bundle中设置reply_to_msg
    public static String REPLY_TO_MSG = "reply_to_msg";

    public static String RE_SEND_ALL_SERVICE_MSG = "re_send_all_service_msg";

    // Service连接成功
    public static String SERVICE_CONNECTED_ACTION = "service_connected_action";

    // 接收服务端消息
    public static String RECEIVE_SERVICE_MSG_ACTION = "receive_service_msg_action";

    // 接收客户端消息
    public static String RECEIVE_CLIENT_MSG_ACTION = "receive_client_msg_action";

    // 发送服务端消息
    public static String SEND_SERVICE_MSG_ACTION = "send_service_msg_action";

    // 关闭Service进程
    public static String KILL_SERVICE_PROCESS = "kill_service_process";

    public static String EXIT_ACTION = "exit_action";

    public static String TEST_ACTION = "test_action";

    public static String DOWNLOAD_COMPLETE = "download_complete";
}
