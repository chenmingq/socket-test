package com.github.chenmingq.id.user;

import java.util.Arrays;

/**
 * @author : aaa
 * @date : 2019-11-23 20:19:41
 * caveat 不要手动修改 
 * description 用户
 */
public enum User {

	/**
	 * 用户模块id
	 */
	MODULE_ID("用户模块id", 100),


	/**
	 * 请求登录
	 */
	REQ_LOGIN_MESSAGE("请求登录", 1),
	/**
	 * 登录结果
	 */
	RES_LOGIN_MESSAGE("登录结果", 2),
	/**
	 * 请求获取短信验证码
	 */
	REQ_GET_SMS_CODE_MESSAGE("请求获取短信验证码", 3),
	/**
	 * 心跳
	 */
	RES_HEAD_MESSAGE("心跳", 4),
	/**
	 * 注册
	 */
	REQ_REGISTER_USER_MESSAGE("注册", 5),
	/**
	 * 退出登录
	 */
	REQ_LOG_OUT_MESSAGE("退出登录", 6);


	/**
	 * 用户模块id
	 */
	public static final int MODULE_ID_VALUE = 100;

	/**
	 * 请求登录
	 */
	public static final int REQ_LOGIN_MESSAGE_VALUE = 1;

	/**
	 * 登录结果
	 */
	public static final int RES_LOGIN_MESSAGE_VALUE = 2;

	/**
	 * 请求获取短信验证码
	 */
	public static final int REQ_GET_SMS_CODE_MESSAGE_VALUE = 3;

	/**
	 * 心跳
	 */
	public static final int RES_HEAD_MESSAGE_VALUE = 4;

	/**
	 * 注册
	 */
	public static final int REQ_REGISTER_USER_MESSAGE_VALUE = 5;

	/**
	 * 退出登录
	 */
	public static final int REQ_LOG_OUT_MESSAGE_VALUE = 6;

	private String desc;
	private int value;

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}

	User(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public User valueOf(int val) { 
		return Arrays.stream(User.values()).filter(i -> val == i.value).findAny().orElse(null);
	}

}