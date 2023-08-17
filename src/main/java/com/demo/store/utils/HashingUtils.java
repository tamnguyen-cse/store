package com.demo.store.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class HashingUtils {

    public final static Charset defaultCharset = StandardCharsets.UTF_8;

    private HashingUtils() {
    }

    /**
     * Hash from plain string to hashed string with hex encoding
     *
     * @param value the string value
     */
    public static String hashString(String value) {
        return DigestUtils.sha256Hex(value);
    }

    /**
     * Hash from plain string to hashed string with base 64 encoding
     *
     * @param value the string value
     */
    public static String base64HashString(String value) {
        return Base64.getEncoder().encodeToString(DigestUtils.sha256(value))
            .replaceAll("[^a-zA-Z0-9-]", "");
    }

    /**
     * Hash from plain string to hashed string with hex encoding
     *
     * @param value the string value
     */
    public static String hmac256String(String key, String value) {
        return String.valueOf(Hex.encodeHex(
            hmac256Byte(key.getBytes(defaultCharset), value.getBytes(defaultCharset))));
    }

    /**
     * Hash from byte data to hashed byte data
     *
     * @param value the byte[] value
     */
    public static byte[] hmac256Byte(byte[] key, byte[] value) {
        Mac mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, key);
        return mac.doFinal(value);
    }

    /**
     * Hash from plain string to hashed string with hex encoding
     *
     * @param value the string value
     */
    public static String hmac512String(String key, String value) {
        return String.valueOf(Hex.encodeHex(
            hmac512Byte(key.getBytes(defaultCharset), value.getBytes(defaultCharset))));
    }

    /**
     * Hash from byte data to hashed byte data
     *
     * @param value the byte[] value
     */
    public static byte[] hmac512Byte(byte[] key, byte[] value) {
        Mac mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_512, key);
        return mac.doFinal(value);
    }

}
