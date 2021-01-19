package xyz.ly11.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * @author 29794
 * @date 1/17/2021 16:37
 * md5 工具类
 */
public class AppMd5Utils {

    /**
     * 生成盐
     *
     * @return 盐值
     */
    public static String createSalt() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


    /**
     * 生成加密字符串
     */
    public static String md5(String source, String salt, Integer hashIterations) {
        return new Md5Hash(source, salt, hashIterations).toString();
    }


}