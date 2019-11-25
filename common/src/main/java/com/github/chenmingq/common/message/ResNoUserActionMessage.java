package com.github.chenmingq.common.message;


import com.github.chenmingq.common.session.entiy.Session;

/**
 * @author: chenmingqin
 * date: 2019/11/22 15:32
 * description:没有设置用户id的session的消息发送
 */


public class ResNoUserActionMessage implements ICommand {

    private AbstractMessage abstractMessage;

    public ResNoUserActionMessage(AbstractMessage abstractMessage) {
        this.abstractMessage = abstractMessage;
    }

    @Override
    public void run() {
        Session session = abstractMessage.getSession();
        if (session == null) {
            return;
        }
        session.sendMsg(abstractMessage.getMessage());
    }
}
