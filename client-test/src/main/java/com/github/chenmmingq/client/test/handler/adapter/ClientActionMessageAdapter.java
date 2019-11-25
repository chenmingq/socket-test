package com.github.chenmmingq.client.test.handler.adapter;


import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.ICommand;

/**
 * @author: chenmingqin
 * date: 2019/11/21 20:29
 * description:消息执行
 */

public class ClientActionMessageAdapter implements ICommand {

    public ClientActionMessageAdapter(AbstractMessage baseMsg) {
        this.baseMsg = baseMsg;
    }

    private AbstractMessage baseMsg;

    @Override
    public void run() {
        if (null == baseMsg) {
            return;
        }
        this.doAction(baseMsg);
    }

    private void doAction(AbstractMessage abstractMessage) {
        ClientMessageProcessAdapter.getInstance().requestProtoExecute(abstractMessage);
    }
}
