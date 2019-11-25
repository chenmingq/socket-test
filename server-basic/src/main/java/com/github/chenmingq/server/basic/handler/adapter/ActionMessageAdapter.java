package com.github.chenmingq.server.basic.handler.adapter;


import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.ICommand;

public class ActionMessageAdapter implements ICommand {

    public ActionMessageAdapter(AbstractMessage baseMsg) {
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
        MessageProcessAdapter.getInstance().requestProtoExecute(abstractMessage);
    }
}
