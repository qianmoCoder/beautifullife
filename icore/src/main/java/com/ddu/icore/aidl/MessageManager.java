package com.ddu.icore.aidl;

import android.os.Message;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by yzbzz on 2019-08-02.
 */
public class MessageManager {

    private ConcurrentLinkedQueue<Message> concurrentLinkedQueue;

    private MessageManager() {
        concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    }

    private static MessageManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static MessageManager instance = new MessageManager();
    }

    private void addMessage(Message message) {
        concurrentLinkedQueue.add(message);
    }

    private ConcurrentLinkedQueue<Message> getAllMessage() {
        return concurrentLinkedQueue;
    }

    private void clearMessages() {
        concurrentLinkedQueue.clear();
    }

    private boolean isNotNullOrEmpty() {
        return !(concurrentLinkedQueue.isEmpty());
    }

    public static void add(Message message) {
        getInstance().addMessage(message);
    }

    public static ConcurrentLinkedQueue<Message> getMessages() {
        return getInstance().getAllMessage();
    }

    public static void clear() {
        getInstance().clearMessages();
    }

    public static boolean isNotEmpty() {
        return getInstance().isNotNullOrEmpty();
    }
}
