package com.github.chenmingq.server.system.service.impl.user;

import com.github.chenmingq.common.channel.AttributeUtil;
import com.github.chenmingq.common.channel.ChannelAttrKey;
import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.common.serializer.factory.ProtoStuffUtils;
import com.github.chenmingq.common.session.SessionManager;
import com.github.chenmingq.common.session.entiy.Session;
import com.github.chenmingq.common.utils.IdUtil;
import com.github.chenmingq.common.utils.MD5Utils;
import com.github.chenmingq.common.utils.MessageUtil;
import com.github.chenmingq.msg.user.ResLoginMessage;
import com.github.chenmingq.proto.UserProto;
import com.github.chenmingq.server.basic.beat.BeatManager;
import com.github.chenmingq.server.basic.listener.ListenerFactory;
import com.github.chenmingq.server.basic.listener.ListenerType;
import com.github.chenmingq.server.system.cache.guava.GuavaCacheTemplate;
import com.github.chenmingq.server.system.entry.Role;
import com.github.chenmingq.server.system.entry.UserAll;
import com.github.chenmingq.server.system.mapper.user.RoleMapper;
import com.github.chenmingq.server.system.mapper.user.UserMapper;
import com.github.chenmingq.server.system.service.user.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {

    @AutoIn
    private UserMapper userMapper;

    @AutoIn
    private RoleMapper roleMapper;

    @Override
    public void reqLogin(Session session, String account, String password, String code) {
        if (null != AttributeUtil.get(session.getChannel(), ChannelAttrKey.SESSION)) {
            return;
        }
        UserAll userAll = GuavaCacheTemplate.getInstance().getValue(account);
        if (null == userAll) {
            log.info("账号不存在 {}", account);
            return;
        }
        String md5Password = MD5Utils.md5Password(password);
        if (!md5Password.equals(userAll.getPassword())) {
            log.info("账号或者密码不正确 {}", account);
            loginResult(userAll, session, false);
            return;
        }
        AttributeUtil.set(session.getChannel(), ChannelAttrKey.SESSION, session);
        loginResult(userAll, session, true);
    }

    @Override
    public void reqRegister(Session session, String account, String userName, String password) {
        UserAll userAll = GuavaCacheTemplate.getInstance().getValue(account);
        if (null != userAll) {
            log.info("账号 [{}] 已存在", account);
            return;
        }
        log.info("账号注册成功,开始准备登陆 [{}]", account);

        AttributeUtil.set(session.getChannel(), ChannelAttrKey.SESSION, session);

        userAll = new UserAll();
        userAll.setUserName(userName);
        userAll.setAccount(account);
        userAll.setPassword(MD5Utils.md5Password(password));
        userAll.setCreateDate(new Date());

        GuavaCacheTemplate.getInstance().setKey(account, userAll);
        userMapper.insertUser(userAll);
        loginResult(userAll, session, true);
    }

    @Override
    public void logOut(Session session) {
        BeatManager.getInstance().removeUser(session.getUserId());
        SessionManager.getInstance().removeSession(session.getUserId());
        AttributeUtil.remove(session.getChannel(), ChannelAttrKey.SESSION);
    }

    private void insertData() {
        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setId(IdUtil.get());
            role.setAccount("ass" + i);
            role.setPassword("12345");
            role.setUserName("张三" + i);
            byte[] serializer = ProtoStuffUtils.serializer(role);
            roleMapper.insertDatas(role.getId(), serializer);
        }

    }

    private void selectDatas() {
        List<byte[]> bytes = roleMapper.selectAllData();
        if (null == bytes) {
            return;
        }
        for (byte[] aByte : bytes) {
            Role role = ProtoStuffUtils.deserializer(aByte, Role.class);
            if (null == role) {
                continue;
            }
            log.info("反序列化的数据 -> [{}]", role);
        }
    }


    /**
     * 登陆结果
     *
     * @param userAll
     * @param session
     * @param loginStatus
     */
    private void loginResult(UserAll userAll, Session session, boolean loginStatus) {
        UserProto.ResLoginMessage.Builder builder = UserProto.ResLoginMessage.newBuilder();
        if (loginStatus) {
            UserProto.UserInfo.Builder userInfo = UserProto.UserInfo.newBuilder();

            long userId = userAll.getId();
            userInfo.setUserId(userId);
            userInfo.setUserName(userAll.getUserName());
            builder.setUserInfo(userInfo);

            session.setUserId(userId);
            SessionManager.getInstance().addSession(session);

            ListenerFactory.event(ListenerType.LOGIN, userId);
            builder.setState(UserProto.ResLoginMessage.LOGIN_STATE.OK_VALUE);

            ResLoginMessage resLoginMessage = new ResLoginMessage();
            resLoginMessage.setProto(builder.build());

            MessageUtil.sendMsg(userId, resLoginMessage);
            log.info("账号 [{}] 登陆成功", userAll.getAccount());
        } else {

            builder.setState(UserProto.ResLoginMessage.LOGIN_STATE.FAILED_TO_LOGIN_VALUE);

            ResLoginMessage resLoginMessage = new ResLoginMessage();
            resLoginMessage.setProto(builder.build());
            resLoginMessage.setSession(session);
            MessageUtil.sendMsg(resLoginMessage);
        }
    }
}
