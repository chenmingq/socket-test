package com.github.chenmmingq.client.test.session;

import com.github.chenmingq.common.message.HeaderMessage;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: chenmingqin
 * date: 2019/11/22 10:27
 * description:
 */

@Getter
@Setter
public class ClientSession {

    private Channel channel;

    public void sendMsg(HeaderMessage headerMessage) {
        if (null == this.channel) {
            return;
        }
        this.channel.writeAndFlush(headerMessage);
    }

}
