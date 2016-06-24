package com.neu.wifilocalization.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public final class SerializeUtil {

    /**
     * 序列化对象
     * 
     * @throws IOException
     */

    public static byte[] serializeObject(Object object) throws IOException {
        ByteArrayOutputStream saos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(saos);
        oos.writeObject(object);
        oos.flush();
        return saos.toByteArray();
    }

    /**
     * 反序列化对象
     * 
     * @throws IOException
     * 
     * @throws ClassNotFoundException
     */

    public static Object deserializeObject(byte[] buf) throws IOException, ClassNotFoundException {
        Object object = null;
        ByteArrayInputStream sais = new ByteArrayInputStream(buf);
        ObjectInputStream ois = new ObjectInputStream(sais);
        object = ois.readObject();
        return object;
    }

    /**
     * 序列化对象为String字符串
     * 
     * @param o
     *            Object
     * @return String
     * @throws Exception
     */
    public static String writeObject(Object o) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(o);
        oos.flush();
        oos.close();
        bos.close();
        String cookieValue = new BASE64Encoder().encode(bos.toByteArray());
        return java.net.URLEncoder.encode(cookieValue, "UTF-8");
    }

    /**
     * 反序列化字符串为对象
     * 
     * @param object
     *            String
     * @return
     * @throws Exception
     */
    public static Object readObject(String object) throws Exception {
        object = java.net.URLDecoder.decode(object, "UTF-8");
        ByteArrayInputStream bis = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(object));
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object o = ois.readObject();
        bis.close();
        ois.close();
        return o;
    }

}
