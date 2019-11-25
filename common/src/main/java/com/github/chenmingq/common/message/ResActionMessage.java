package com.github.chenmingq.common.message;

import com.github.chenmingq.common.session.SessionManager;
import com.github.chenmingq.common.session.entiy.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：执行返回给客户端的消息
 */

@Slf4j
public class ResActionMessage implements ICommand {

    private long userId;
    private AbstractMessage abstractMessage;

    public ResActionMessage(long userId, AbstractMessage abstractMessage) {
        this.userId = userId;
        this.abstractMessage = abstractMessage;
    }

    @Override
    public void run() {
        Session session = SessionManager.getInstance().getSession(userId);
        if (null == session) {
            return;
        }
        session.sendMsg(abstractMessage.getMessage());
    }
}
