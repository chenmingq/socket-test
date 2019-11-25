package com.github.chenmingq.common.serializer.factory;


import java.io.*;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：Serializable序列化工具
 */

public class SerializableUtils {

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static byte[] serializer(Serializable obj) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(null, byteArrayOutputStream);
            closeStream(null, objectOutputStream);
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Serializable deserializer(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object o = objectInputStream.readObject();
            return (Serializable) o;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(byteArrayInputStream, null);
            closeStream(objectInputStream, null);
        }
        return null;
    }

    /**
     * 关闭 Stream流
     *
     * @param in
     * @param out
     */
    private static void closeStream(InputStream in, OutputStream out) {
        try {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
