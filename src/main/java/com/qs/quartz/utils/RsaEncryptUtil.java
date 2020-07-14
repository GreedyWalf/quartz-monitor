package com.qs.quartz.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA实现实现加密解密工具类（非对称式加解密）
 *
 * @author qinyupeng
 * @since 2018-11-12 15:14:38
 */
public class RsaEncryptUtil {
    private static Logger logger = LoggerFactory.getLogger(RsaEncryptUtil.class);

    private static String KEY_ALGORITHM = "RSA";

    //private static int KEY_SIZE = 1024;

    /**
     * 生成RAS密钥对（包含公钥和私钥）
     * 使用rsa加密算法，需要设置所占字节大小
     */
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }


    /**
     * 通过公钥字符串获取公钥对象
     *
     * @param publicKeyStr 公钥经过Base64编码后生成的字符串
     * @return 返回公钥对象
     */
    public static RSAPublicKey getPublicKeyFromStr(String publicKeyStr) {
        byte[] buff = Base64.decodeBase64(publicKeyStr);
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //私钥使用的keySpec和公钥不一样
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(buff));
        } catch (NoSuchAlgorithmException e) {
            logger.error("指定算法不存在~", e);
        } catch (InvalidKeySpecException e) {
            logger.error("公钥非法~", e);
        }

        return (RSAPublicKey) publicKey;
    }

    /**
     * 通过私钥字符串获取私钥对象
     *
     * @param privateKeyStr 私钥字符串
     */
    public static RSAPrivateKey getPrivateKeyFromStr(String privateKeyStr) {
        byte[] buff = Base64.decodeBase64(privateKeyStr);
        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //私钥使用的keySpec和公钥不一样
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(buff));
        } catch (NoSuchAlgorithmException e) {
            logger.error("指定算法不存在~", e);
        } catch (InvalidKeySpecException e) {
            logger.error("公钥非法~", e);
        }

        return (RSAPrivateKey) privateKey;
    }


    /**
     * 使用公钥加密，获得加密后的字节数组
     *
     * @param publicKey     公钥
     * @param plainTextData 加密字节
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] plainTextData) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainTextData);
    }


    /**
     * 使用私钥解密
     *
     * @param privateKey 私钥
     * @param cipherData 加密后的明文
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] cipherData) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherData);
    }

    /**
     * 数据转16进制编码
     *
     * @param data 字节数组
     */
    public static String encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * 数据转16进制编码
     *
     * @param data        字节数组
     * @param toLowerCase 是否转换为小写
     */
    public static String encodeHex(final byte[] data, final boolean toLowerCase) {
        final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        final char[] toDigits = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }

        return new String(out);
    }


}