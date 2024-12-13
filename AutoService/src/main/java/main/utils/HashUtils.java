package main.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    /**
     * Хеширует пароль.
     * <br />
     * Был выбран простой способ хеширования и для относительной криптостойкости пароль хешируется
     * вместе с хешом логина и с солью.
     *
     * @param login логин
     * @param password пароль
     * @return хеш пароля
     */
    public static String hash(String login, String password) {
        return (login.hashCode() + "//" + password + "-LOL").hashCode() + "";
    }
}
