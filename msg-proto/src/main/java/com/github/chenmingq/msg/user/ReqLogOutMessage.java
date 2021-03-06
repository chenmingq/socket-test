package com.github.chenmingq.msg.user;

import com.github.chenmingq.common.message.AbstractMessage;
import lombok.Getter;
import lombok.Setter;


/**
 * @author : aaa
 * @date : 2019-11-23 20:19:41
 * caveat 不要手动修改 
 * description 退出登录
 */
@Setter
@Getter
public class ReqLogOutMessage extends AbstractMessage {

	@Override
	public void deCoder(byte[] body) {
	}

	@Override
	public byte[] enCoder() {
		return null;
	}

	@Override
	public int getModuleId() {
		return 100;
	}

	@Override
	public int getCmdId() {
		return 6;
	}
}