package com.github.chenmingq.server.system.beat;

import com.github.chenmingq.common.message.ICommand;
import com.github.chenmingq.common.session.SessionManager;
import com.github.chenmingq.common.utils.MessageUtil;
import com.github.chenmingq.msg.user.ResHeadMessage;
import com.github.chenmingq.proto.UserProto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: chenmingqin
 * date: 2019/11/22
 * description:心跳消息
 */

@Slf4j
public class ServerHeartbeatMessage implements ICommand {

    private long userId;

    public ServerHeartbeatMessage(long userId) {
        this.userId = userId;
    }

    private boolean running = true;

    /**
     * 上次心跳的时间
     */
    private long lastHeartbeatTime;

    @Override
    public void run() {

        // 间隔1000毫秒
        int intervalsTime = 1000;

        while (running) {
            if (!SessionManager.getInstance().containsSession(this.userId)) {
                running = false;
                break;
            }
            long currentTime = System.currentTimeMillis();
            long time = currentTime - lastHeartbeatTime;
            if (time < intervalsTime) {
                continue;
            }
            lastHeartbeatTime = currentTime;
            UserProto.ResHeadMessage.Builder headMsg = UserProto.ResHeadMessage.newBuilder();
            headMsg.setTimestamp(currentTime);
            ResHeadMessage msg = new ResHeadMessage();
            msg.setProto(headMsg.build());
            MessageUtil.sendMsg(this.userId, msg);
            log.info("心跳消息 -> [{}]", msg);
        }
    }
}
