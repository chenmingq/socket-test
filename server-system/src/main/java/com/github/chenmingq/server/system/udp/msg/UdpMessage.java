package com.github.chenmingq.server.system.udp.msg;

import com.github.chenmingq.common.message.HeaderMessage;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

/**
 * @author : chengmingqin
 * date : 2020/7/2
 * description :
 */

@Getter
@Setter
public class UdpMessage {

    private InetSocketAddress socketAddress;

    private HeaderMessage headerMessage;

}
