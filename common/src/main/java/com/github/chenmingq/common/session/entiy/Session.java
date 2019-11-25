package com.github.chenmingq.common.session.entiy;

import com.github.chenmingq.common.message.HeaderMessage;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : chenmingqin
 * date : 2019-09-29
 * Project : socket-test
 * Description： session
 */

@Setter
@Getter
public class Session {


    private Channel channel;

    private long userId;

    public void sendMsg(HeaderMessage message) {
        if (null == channel) {
            return;
        }
        channel.writeAndFlush(message);
    }


}
