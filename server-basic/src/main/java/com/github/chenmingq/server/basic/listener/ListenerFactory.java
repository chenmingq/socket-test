package com.github.chenmingq.server.basic.listener;


import com.github.chenmingq.server.basic.handler.adapter.FieldHandlerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * projectName: socket-test
 *
 * @author chenmingqin
 * create: 2019-10-20 20:51
 * description: 监听器
 **/
public class ListenerFactory {


    private final static Map<ListenerType, List<IListener>> LISTENER_MAP = new HashMap<>();

    /**
     * 注册所有的监听器
     *
     * @param type
     * @param listener
     */
    public static void addListener(ListenerType type, IListener listener) {
        LISTENER_MAP.computeIfAbsent(type, k -> new ArrayList<>()).add(listener);
    }


    /**
     * 移除某个监听器
     *
     * @param type
     */
    public static boolean removeListener(ListenerType type, IListener listener) {
        List<IListener> iListeners = LISTENER_MAP.get(type);
        if (null == iListeners) {
            return false;
        }
        if (!iListeners.contains(listener)) {
            return false;
        }
        return iListeners.remove(listener);
    }

    /**
     * 事件执行器
     *
     * @param type
     * @param obj
     */
    public static void event(ListenerType type, Object... obj) {
        List<IListener> iListeners = LISTENER_MAP.get(type);
        if (null == iListeners) {
            return;
        }
        for (IListener listener : iListeners) {
            FieldHandlerAdapter.getInstance().newInstanceAll(listener);
            if (null == obj || obj.length < 1) {
                listener.event(null);
            } else {
                listener.event(obj[0]);
            }
        }
    }


}
