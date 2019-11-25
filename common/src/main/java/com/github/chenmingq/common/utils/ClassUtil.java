package com.github.chenmingq.common.utils;


import com.github.chenmingq.common.common.annotation.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： class操作工具
 */

@Slf4j
public class ClassUtil {

    public static void main(String[] args) {

        /*String packageName = "com.github.chenmingq.server.system.service.impl";
        lordClazz(packageName);*/

        Reflections reflections = new Reflections("com.github.chenmingq.server.system.service.impl");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ServiceImpl.class);

        for (Class<?> cl : classes) {
            System.out.println(cl.getCanonicalName());
        }

    }

    /**
     * 注解查找对应包下的class
     *
     * @param annotationClass
     * @param packageName
     * @return
     */
    public static Set<Class<?>> lordClazz(Class<? extends Annotation> annotationClass, String packageName) {
        return new Reflections(packageName).getTypesAnnotatedWith(annotationClass);
    }


    /**
     * 包名查找class
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> lordClazz(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            String packageDirName = packageName.replace(".", "/");
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                // 必须以文件的形式保存的
                if (!"file".equals(protocol)) {
                    continue;
                }
                // 获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(packageName, filePath, true, classSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classSet;
    }

    /**
     * 查找和添加包下的所有class文件
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive, Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        if (null == dirFiles) {
            return;
        }
        // 循环所有文件
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
