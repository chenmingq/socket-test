package com.github.chenmingq.server.basic.beat;

import com.github.chenmingq.common.message.ICommand;
import com.github.chenmingq.common.utils.MessageUtil;
import com.github.chenmingq.msg.user.ResHeadMessage;
import com.github.chenmingq.proto.UserProto;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: chenmingqin
 * date: 2019/11/22
 * description:心跳消息
 */

@Slf4j
public class ServerHeartbeatMessage implements ICommand {

    /**
     * 上次心跳的时间
     */
    private long lastHeartbeatTime;
    /**
     * 间隔1000毫秒
     */
    private final static int INTERVALS_TIME = 1000;

    @Override
    public void run() {


        while (true) {
            long currentTime = System.currentTimeMillis();
            long time = currentTime - lastHeartbeatTime;
            if (time < INTERVALS_TIME) {
                continue;
            }
            lastHeartbeatTime = currentTime;
            LinkedBlockingQueue<Long> userBeatQueue = BeatManager.getInstance().getUserBeatQueue();
            for (long userId : userBeatQueue) {
                UserProto.ResHeadMessage.Builder headMsg = UserProto.ResHeadMessage.newBuilder();
                headMsg.setTimestamp(currentTime);
                ResHeadMessage msg = new ResHeadMessage();
                msg.setProto(headMsg.build());
                MessageUtil.sendMsg(userId, msg);
                log.info("心跳消息 -> 玩家-[{}] >>> [{}]", userId, msg.getProto().getTimestamp());
            }
        }
    }
}
