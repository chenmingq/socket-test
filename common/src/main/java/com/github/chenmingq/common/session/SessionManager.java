package com.github.chenmingq.common.session;

import com.github.chenmingq.common.session.entiy.Session;
import com.google.common.collect.Maps;

import java.util.Map;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： session管理
 */

public class SessionManager {

    private static SessionManager instance = new SessionManager();

    public static SessionManager getInstance() {
        return instance;
    }

    public SessionManager() {
    }

    private final Map<Long, Session> sessionMap = Maps.newConcurrentMap();

    public void addSession(Session session) {
        sessionMap.put(session.getUserId(), session);
    }

    public Session getSession(long userId) {
        return sessionMap.get(userId);
    }

    public void removeSession(long userId) {
        sessionMap.remove(userId);
    }


    public boolean containsSession(long userId) {
        return sessionMap.containsKey(userId);
    }

}
