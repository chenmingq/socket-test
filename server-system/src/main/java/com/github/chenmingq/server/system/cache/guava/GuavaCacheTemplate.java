package com.github.chenmingq.server.system.cache.guava;

import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.server.basic.handler.adapter.FieldHandlerAdapter;
import com.github.chenmingq.server.system.entry.UserAll;
import com.github.chenmingq.server.system.mapper.user.UserMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheTemplate {

    @AutoIn
    private UserMapper userMapper;

    private static GuavaCacheTemplate instance = new GuavaCacheTemplate();

    public static GuavaCacheTemplate getInstance() {
        return instance;
    }

    public GuavaCacheTemplate() {
    }

    static {
        FieldHandlerAdapter.getInstance().newInstanceAll(instance);
    }

    private static LoadingCache<String, UserAll> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(
                    new CacheLoader<String, UserAll>() {
                        //默认的数据加载器，如果get值时候没有命中缓存的key，则调用此方法
                        @Override
                        public UserAll load(String s) throws Exception {
                            UserAll userAll = instance.userMapper.selectAccount(s);
                            if (null == userAll) {
                                log.info("查询账号 [{}] 没有数据", s);
                                return new UserAll();
                            }
                            instance.setKey(userAll.getAccount(), userAll);
                            return userAll;
                        }
                    }
            );

    public void setKey(String key, UserAll value) {
        localCache.put(key, value);
    }

    public UserAll getValue(String key) {
        return localCache.getIfPresent(key);
    }

    public void removeAll() {
        localCache.invalidateAll();
    }

    public void remove(String key) {
        localCache.invalidate(key);
    }

    public static void main(String[] args) {
        GuavaCacheTemplate instance = getInstance();
        UserAll userAll = new UserAll();
        userAll.setAccount("account");
        userAll.setId(1L);
        userAll.setUserName("user_name");
        getInstance().setKey("1", userAll);
        System.out.println(instance.getValue("3"));
        //FieldHandlerAdapter.getInstance().newInstanceAll(instance);
    }


}
