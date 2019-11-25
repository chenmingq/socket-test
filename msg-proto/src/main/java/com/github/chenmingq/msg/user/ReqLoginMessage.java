package com.github.chenmingq.msg.user;

import com.google.protobuf.InvalidProtocolBufferException;
import com.github.chenmingq.common.message.AbstractMessage;
import lombok.Getter;
import lombok.Setter;


/**
 * @author : aaa
 * @date : 2019-11-23 20:19:41
 * caveat 不要手动修改 
 * description 请求登录
 */
@Setter
@Getter
public class ReqLoginMessage extends AbstractMessage {

	private com.github.chenmingq.proto.UserProto.ReqLoginMessage proto;

	@Override
	public void deCoder(byte[] body) {
		try {
			this.proto = com.github.chenmingq.proto.UserProto.ReqLoginMessage.parseFrom(body);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] enCoder() {
		if (null == this.proto) {
			throw new NullPointerException("protoBuffer消息为null");
		}
		return this.proto.toByteArray();
	}

	@Override
	public int getModuleId() {
		return 100;
	}

	@Override
	public int getCmdId() {
		return 1;
	}
}