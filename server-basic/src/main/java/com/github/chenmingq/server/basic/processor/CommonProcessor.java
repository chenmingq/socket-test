package com.github.chenmingq.server.basic.processor;

import com.github.chenmingq.common.channel.AttributeUtil;
import com.github.chenmingq.common.channel.ChannelAttrKey;
import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.session.entiy.Session;
import com.github.chenmingq.common.utils.executor.ExecutorUtil;
import com.github.chenmingq.server.basic.handler.adapter.ActionMessageAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * projectName: nettyTest
 *
 * @author chenmingqin
 * create: 2019-09-30 23:42
 * description:
 **/

@Slf4j
public class CommonProcessor implements ICommandProcessor<AbstractMessage> {
    @Override
    public void processor(AbstractMessage abstractMessage) {
        Session session = AttributeUtil.get(abstractMessage.getSession().getChannel(), ChannelAttrKey.SESSION);
        if (null == session) {
            log.info("{}", "session不存在");
            return;
        }
        ExecutorUtil.commonExecutor.execute(new ActionMessageAdapter(abstractMessage));
    }
}
