package com.github.chenmingq.server.basic.processor;


import com.github.chenmingq.common.utils.executor.ExecutorUtil;
import com.github.chenmingq.msg.user.ReqLoginMessage;
import com.github.chenmingq.server.basic.handler.adapter.ActionMessageAdapter;


/**
 * projectName: nettyTest
 *
 * @author chenmingqin
 * create: 2019-09-30 23:42
 * description: 登陆的消息执行
 **/

public class LoginProcessor implements ICommandProcessor<ReqLoginMessage> {

    @Override
    public void processor(ReqLoginMessage reqLoginMessage) {
        ExecutorUtil.loginExecutor.execute(new ActionMessageAdapter(reqLoginMessage));
    }
}
