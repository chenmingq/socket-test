package com.github.chenmingq.common.channel;

import com.github.chenmingq.common.session.entiy.Session;
import io.netty.util.AttributeKey;

public class ChannelAttrKey {

    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("SESSION");

    public static final AttributeKey<Boolean> LOGOUT_HANDLED = AttributeKey.newInstance("LOGOUT_HANDLED");

    public static final AttributeKey<Boolean> BACK_LOGIN = AttributeKey.newInstance("BACK_LOGIN");

    public static final AttributeKey<Integer> HOST_ID = AttributeKey.newInstance("HOST_ID");

    public static final AttributeKey<Integer> CLIENT_INDEX = AttributeKey.newInstance("CLIENT_INDEX");
}
