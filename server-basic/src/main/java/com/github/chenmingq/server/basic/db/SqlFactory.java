package com.github.chenmingq.server.basic.db;

import com.github.chenmingq.common.common.annotation.MapperScan;
import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Set;

@Slf4j
public class SqlFactory {

    private static SqlFactory instance = new SqlFactory();

    public static SqlFactory getInstance() {
        return instance;
    }

    public SqlFactory() {
    }

    public static SqlSession sqlSession;

    public void initDb() {
        Set<Class<?>> classes = ClassUtil.lordClazz(MapperScan.class, CommonConst.MAPPER_PACKAGE);
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(CommonConst.MY_BATIS_XML_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream, "db1");
        sqlSession = build.openSession(true);
        for (Class<?> aClass : classes) {
            if (!aClass.isInterface()) {
                continue;
            }
            MapperScan annotation = aClass.getAnnotation(MapperScan.class);
            if (null == annotation) {
                //须在mapper的interface 接口加入注解 @MapperScan
                throw new RuntimeException("mapper 配置不正确，mapper包下的interface 接口加入注解 @MapperScan");
            }
//            log.info("开始注册 MapperRegistry-> [{}]", aClass);
            sqlSession.getConfiguration().addMapper(aClass);
        }
        Connection connection = sqlSession.getConnection();
        log.info("{} -> {}", "DB_POOL创建成功", connection);
    }
}
