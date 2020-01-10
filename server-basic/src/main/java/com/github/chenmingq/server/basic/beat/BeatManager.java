package com.github.chenmingq.server.basic.beat;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author : chengmingqin
 * date : 2020/1/10
 * description : 心跳管理
 */

public class BeatManager {


    private static BeatManager instance = new BeatManager();

    public static BeatManager getInstance() {
        return instance;
    }

    public BeatManager() {
    }

    private final LinkedBlockingQueue<Long> USER_BEAT_QUEUE = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<Long> getUserBeatQueue() {
        return USER_BEAT_QUEUE;
    }

    public void addUser(long userId) {
        USER_BEAT_QUEUE.add(userId);
    }

    public void removeUser(long userId) {
        USER_BEAT_QUEUE.remove(userId);
    }


}
