package com.github.chenmingq.server.basic.processor;


import com.github.chenmingq.common.utils.executor.ExecutorUtil;
import com.github.chenmingq.msg.user.ReqRegisterUserMessage;
import com.github.chenmingq.server.basic.handler.adapter.ActionMessageAdapter;

/**
 * @author: chenmingqin
 * date: 2019/11/23 15:59
 * description:用户注册请求 消息
 */

public class RegisterProcessor implements ICommandProcessor<ReqRegisterUserMessage> {


    @Override
    public void processor(ReqRegisterUserMessage reqRegisterUserMessage) {
        ExecutorUtil.loginExecutor.execute(new ActionMessageAdapter(reqRegisterUserMessage));
    }
}
